package jile.nilex;

import java.util.*;

import jile.nilex.IndividualTokenType.IndividualToken;
import jile.common.Identifiable;
import jile.common.Resource;
import jile.vis.CodeView;
import jile.vis.Visualizable;
import jile.vis.View.Mode;

public class Code implements Identifiable, Visualizable {

    public final Language language;
    private Resource resource;

    public Code(Resource resource) {
        this(resource, Languages.singleton().getLanguageByExt(resource.getExt()));
    }

    public Code(String encoded, Language language) {
        this(Resource.of(encoded, ""), language);
    }

    public Code(String encoded, String ext) {
        this(Resource.of(encoded, ext));
    }

    public Code(Resource resource, Language language) {
        this.resource = Objects.requireNonNull(resource);
        this.language = language == null ? TextLanguage.singleton() : language;
        beProcessed();
    }

    private boolean canBeProcessed = false;

    public boolean canBeProcessed() {
        return canBeProcessed;
    }

    private synchronized void beProcessed() {
        // String i = getIdentity();
        // System.out.println("\n" + i + "\n" + "<".repeat(i.length()) + "\n");

        mishaps.write(this, new HashMap<IndividualToken, Set<Mishap>>());

        canBeProcessed = true;

        language.processor.process(this);

        canBeProcessed = false;

        // System.out.println("\n" + i + "\n" + ">".repeat(i.length()) + "\n");
    }

    @Override
    public CodeView visualize() {
        return new CodeView(Mode.INTERACTIVE, this);
    }

    @Override
    public String getIdentity() {
        return resource.getIdentity();
    }

    public String getExt() {
        return resource.getExt();
    }

    public String getString() {
        return resource.getContents(false);
    }

    public boolean load() {
        if (resource.readFrom()) {
            beProcessed();
            return true;
        } else {
            return false;
        }
    }

    public boolean save() {
        return resource.writeTo(true, true);
    }

    public boolean saveAs(Resource resource) {
        return saveAs(resource, false);
    }

    public boolean saveAs(Resource resource, boolean overwrite) {
        if (resource != null) {
            // preparing to change resource
            if (!resource.equals(resource)) {
                // if there is nothing on disk, or
                if (this.resource == null
                        // if the contents on disk are the same as in memory
                        || this.resource.isSynchronized()) {

                    // cut ties with the previous file, as it is synchronized
                    this.resource = resource;
                    // write to the new file
                    return resource.writeTo(overwrite, true);
                }
            }
        }
        return false;
    }

    public Resource getResource() {
        return resource;
    }

    public class Process {

        private String name;

        public Process(String name) {
            this.name = name;
            // String i = name + ": " + getCode().getIdentity();
            // System.out.println("\n\t" + i + "\n\t" + "<".repeat(i.length()) + "\n");
        }

        private boolean safe = true;

        private boolean finished = false;

        private final List<Mishap> myMishaps = new LinkedList<Mishap>();

        public void issue(Mishap mishap) {
            Objects.requireNonNull(mishap, "cannot issue a null mishap.");
            if (finished) {
                System.out.println("Tried to add a mishap.");
            } else {
                mishap.setProcess(this);
                if (safe) {
                    if (mishap.fatal) {
                        safe = false;
                    }
                }
                myMishaps.add(mishap);
                // if (mishap.fatal) System.out.println("\t" + mishap.toString());
            }
        }

        public Code getCode() {
            return Code.this;
        }

        /**
         * Terminates the process and returns false if it encountered any fatal mishaps,
         * and true if it did not and ran safely.
         */
        public boolean finish() {

            finished = true;

            // String i = name + ": " + getCode().getIdentity();
            // System.out.println("\n\t" + i + "\n\t" + ">".repeat(i.length()) + "\n");

            if (safe) {
                return true;

            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                System.out.println("" + myMishaps.size() + " mishap(s) during: '" + name + "' of: " + getIdentity());
                for (Mishap mishap : myMishaps)
                    System.out.println("\t" + mishap.toString());

                var map = mishaps.read(getCode());

                for (Mishap mishap : myMishaps) {
                    IndividualToken key = mishap.token;
                    if (!map.containsKey(key)) {
                        map.put(key, new HashSet<Mishap>());
                    }
                    map.get(key).add(mishap);
                }

                return false;
            }
        }
    }

    public String getPositionInfo(int position) {
        return resource.getPositionInfo(position);
    }

    public int getSize() {
        return resource.getSize();
    }

    public static final Port<List<IndividualToken>> syntax = new Port<List<IndividualToken>>("CodeView:syntax");

    public static final Port<Map<IndividualToken, Set<Mishap>>> mishaps = new Port<Map<IndividualToken, Set<Mishap>>>(
            "CodeView:mishaps");

    public static final Port<Map<IndividualToken, Resource>> links = new Port<Map<IndividualToken, Resource>>(
            "CodeView:links");
}