package jile.common;

import java.util.*;
import java.util.jar.*;
import java.nio.file.*;
import java.io.*;
import java.net.URLDecoder;

public abstract class Resource implements Identifiable {

    private static ClassLoader cl = Global.singleton().getClass().getClassLoader();

    private Resource() {
    }

    public static Resource of(String contents, String ext) {
        return new Resource.Virtual(contents, ext);
    }

    // public static String normalize(Path path) {
    // // Paths.get("").toAbsolutePath().normalize()
    // Path.of("");
    // new File("");
    // return path.normalize().toString().replaceAll("\\\\", "/");
    // }

    // /**
    // * Path must be relative.
    // *
    // * @throws IOException
    // */
    // public static Resource of(Path path) {
    // return of("/" + path.toString());
    // }

    public static Resource of(String address) {

        // Linux-specifix absolute path
        if (address.charAt(0) == '/') {
            return new Resource.Stored(Path.of(address));
        } else

        // Windows-specific absolute path
        if (address.charAt(1) == ':') {
            // address = address.replaceAll("\\\\", "/");
            return new Resource.Stored(Path.of(address));
        } else

        // Relative path
        {
            address = "res/" + address;
            var url = cl.getResource(address);
            if (url == null) {
                System.out.println("Resource could not be located: " + address);
                return null;
            } else {
                String protocol = url.getProtocol();
                switch (protocol) {
                    case "file":
                        return new Resource.Stored(Path.of("src/" + address));
                    case "jar":
                        // jar:[key]!/[sub]
                        String d = url.getPath();
                        try {
                            d = URLDecoder.decode(d, "UTF-8");
                        } catch (UnsupportedEncodingException e1) {
                            System.out.println("Failed to decode URL");
                        }
                        String key = d.substring(5, d.indexOf('!'));
                        String sub = d.substring(2 + d.indexOf('!'));
                        // sub = sub.substring(sub.lastIndexOf('/') + 1);
                        // System.out.println(d);
                        // System.out.println("[" + key + "]+[" + sub + "]");
                        try {
                            Resource.addArchive(key);
                            return new Resource.Archived(key, sub);
                        } catch (IOException e) {
                            System.out.println("Archive not found: " + key);
                            return null;
                        }
                    default:
                        System.out.println("Trying to load resource with unsupported protocol: " + protocol);
                        return null;
                }
            }
        }
    }

    private static final long NEVER = 0;

    private static long now() {
        return System.currentTimeMillis();
    }

    // must never be null
    private String contents;

    // timestamps
    private long lastModifiedInternally = NEVER;
    private long lastSynchronized = NEVER;

    public abstract boolean exists();

    public final boolean readFrom() {
        if (_readFrom()) {
            lastSynchronized = now();
            return true;
        }
        return false;
    }

    public final boolean writeTo(boolean overwrite, boolean createIfDoesNotExist) {
        if (exists()) {
            if (overwrite) {
                if (_writeTo(true)) {
                    lastSynchronized = now();
                    return true;
                }
            } else {
                if (createIfDoesNotExist) {
                    if (_create()) {
                        if (_writeTo(false)) {
                            lastSynchronized = now();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    final long getLastModifiedExternally() {
        try {
            return _getLastModifiedExternally();
        } catch (Throwable e) {
            return NEVER;
        }
    }

    abstract boolean _readFrom();

    abstract boolean _create();

    abstract boolean _writeTo(boolean overwrite);

    abstract long _getLastModifiedExternally();

    public abstract String getExt();

    public String getContents(boolean update) {
        if (update || contents == null)
            update();
        return contents;
    }

    /**
     * Returns true if and only if the contents change.
     * <p>
     * Does not change if {@code contents} is null.
     */
    public boolean setContents(String contents) {
        Objects.requireNonNull(contents);
        if (this.contents == null || !this.contents.equals(contents)) {
            this.contents = contents;
            indicesToLines.clear();
            lastModifiedInternally = now();
            return true;
        } else {
            return false;
        }
    }

    public boolean update() {
        // if the file was modified on disk, after it was loaded into memory
        if (getLastModifiedExternally() > lastModifiedInternally) {
            return _readFrom();
        } else {
            return true;
        }
    }

    public boolean isSynchronized() {
        return lastSynchronized > lastModifiedInternally && lastSynchronized > getLastModifiedExternally();
    }

    private static class Virtual extends Resource {

        private final String ext;

        Virtual(String contents, String ext) {
            Objects.requireNonNull(contents);
            setContents(contents);
            this.ext = ext;
        }

        @Override
        public String getExt() {
            return ext;
        }

        @Override
        public String getIdentity() {
            return "[not a file]." + ext;
        }

        @Override
        String _getTempFilenameInspiration() {
            return "";
        }

        @Override
        public boolean exists() {
            return true;
        }

        @Override
        boolean _readFrom() {
            return true;
        }

        @Override
        boolean _create() {
            return false;
        }

        @Override
        boolean _writeTo(boolean overwrite) {
            return true;
        }

        @Override
        long _getLastModifiedExternally() {
            return NEVER;
        }
    }

    private static Map<String, Map<String, JarEntry>> archives;

    private static void addArchive(String key) throws IOException {
        if (archives == null) {
            archives = new HashMap<String, Map<String, JarEntry>>();
        }
        if (!archives.containsKey(key)) {
            archives.put(key, new HashMap<String, JarEntry>());
        }
        var map = archives.get(key);
        var jar = new JarFile(key);
        var iterator = jar.entries().asIterator();
        while (iterator.hasNext()) {
            JarEntry entry = iterator.next();
            // System.out.println(entry.getName());
            map.put(entry.getName(), entry);
        }
        jar.close();
    }

    private static class Archived extends Resource {

        private final String key;
        private final String sub;
        private final String identity;

        public Archived(String key, String sub) {
            this.key = key;
            this.sub = sub;
            this.identity = "[" + key.substring(key.lastIndexOf("/") + 1) + "]" + sub;
        }

        @Override
        public String getExt() {
            return Common.getExt(sub);
        }

        @Override
        public String getIdentity() {
            return identity;
        }

        @Override
        public String _getTempFilenameInspiration() {
            return sub;
        }

        @Override
        public boolean exists() {
            return archives.get(key).containsKey(sub);
        }

        @Override
        boolean _readFrom() {
            try {
                var jar = new JarFile(key);
                var reader = new BufferedReader(new InputStreamReader(jar.getInputStream(archives.get(key).get(sub))));
                String newContents = "", line;
                while (true) {
                    line = reader.readLine();
                    // System.out.print("~~>");
                    // System.out.println(line);
                    if (line == null)
                        break;
                    else
                        newContents += line + "\n";
                }
                reader.close();
                jar.close();
                return setContents(newContents);
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        long _getLastModifiedExternally() {
            return new File(key).lastModified();
        }

        @Override
        boolean _create() {
            return false;
        }

        @Override
        boolean _writeTo(boolean overwrite) {
            return false;
        }
    }

    private static class Stored extends Resource {

        private final Path path;

        public Stored(Path path) {
            this.path = path;
        }

        @Override
        boolean _readFrom() {
            try {
                return setContents(Files.readString(path));
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        boolean _writeTo(boolean overwrite) {
            try {
                if (!exists() || overwrite) {
                    Files.writeString(path, getContents(false));
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        boolean _create() {
            try {
                Files.createFile(path);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        long _getLastModifiedExternally() {
            return path.toFile().lastModified();
        }

        @Override
        public String getIdentity() {
            return path.getFileName().toString();
        }

        @Override
        public String _getTempFilenameInspiration() {
            return path.getFileName().toString();
        }

        @Override
        public String getExt() {
            return Common.getExt(path.toString());
        }

        @Override
        public boolean exists() {
            return Files.exists(path);
        }
    }

    abstract String _getTempFilenameInspiration();

    public String getTempFilename() {

        // be inspired by the actual file address
        String s = _getTempFilenameInspiration();

        // keep only the filename; we need this to be as short as possible
        int i = s.lastIndexOf("/");
        if (i != -1)
            s = s.substring(i + 1);

        // get rid of its ext, we will add our own later
        i = s.lastIndexOf(".");
        if (i != -1)
            s = s.substring(0, i);

        // replace spaces with dashes so that the link is clickable on the terminal
        s = s.replaceAll(" ", "-");

        // fill nonsense if no/weak inspiration
        if (s.isEmpty())
            s = Common.stringOfValue(hashCode(), Common.RADIX_MAX).toLowerCase();

        // add ext so it is syntax-highlighted if clicked on and opened in editor
        if (getExt() != "")
            s += "." + getExt();

        return s;
    }

    private static Path tempdir = Path.of("c:/t/");

    public Path dump() {
        try {
            Files.createDirectories(tempdir);
            return Files.writeString(tempdir.resolve(getTempFilename()), contents);
        } catch (IOException e) {
            return null;
        }
    }

    private final TreeMap<Integer, Integer> indicesToLines = new TreeMap<Integer, Integer>();

    public String getPositionInfo(int position) {

        int line = 1, column = 1;

        int index = 0;

        // if possible, resume from a previous search's result
        var e = indicesToLines.floorEntry(position);
        if (e != null) {
            index = e.getKey();
            line = e.getValue();
        }

        while (index < position) {
            if (contents.charAt(index++) == '\n') {
                line++;
                column = 1;

                // save the search result every ten line
                if (line % 10 == 0)
                    indicesToLines.put(index, line);

            } else
                column++;
        }

        Path d = dump();

        return ((d != null && Files.exists(d)) ? d.toString() : "(" + getIdentity() + ")") + ":" + line + ":" + column;
    }

    public int getSize() {
        return contents.length();
    }
}