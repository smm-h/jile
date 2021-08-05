package jile.vis;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiPredicate;

import jile.lingu.Token;
import jile.lingu.Setable;
import jile.common.Common;
import jile.common.Convertor;
import jile.common.LinkedTree;

public class ClassTree implements Visualizable {

    private final LinkedTree<Class<?>> tree = new LinkedTree<Class<?>>();

    // private final Set<Class<?>> past = new HashSet<Class<?>>();
    private final Map<Class<?>, Class<?>> past = new HashMap<Class<?>, Class<?>>();

    public final Setable<Boolean> hidePrivateClasses = new Setable<Boolean>(this, "hide_classes.private", true);
    public final Setable<Boolean> hideUnextendedClasses = new Setable<Boolean>(this, "hide_classes.unextended", false);
    public final Setable<Boolean> appendAdder = new Setable<Boolean>(this, "string.append.adder", false);
    public final Setable<Integer> stringMaxLength = new Setable<Integer>(this, "string.max_length", -1);

    public ClassTree() {

        // c.getDeclaredFields()
        // c.getDeclaredMethods()
        // c.getDeclaredConstructors()

        // add Object.class so the tree is never empty
        var classRep = Object.class;
        past.put(classRep, null);
        tree.add(classRep);
        tree.goToRoot();
    }

    public void add(Class<?> classRep) {
        add(classRep, null);
    }

    private void add(Class<?> classRep, Class<?> adder) {

        boolean hide = false;

        hide |= hidePrivateClasses.get() && classRep.getSimpleName().equals("");

        past.put(classRep, adder);

        var array = classRep.getClasses();

        for (int i = 0; i < array.length; i++)
            if (!past.containsKey(array[i]))
                add(array[i], classRep);

        Stack<Class<?>> chain = new Stack<Class<?>>();

        Class<?> c = classRep;

        while (c != null) {
            chain.push(c);
            c = c.getSuperclass();
        }

        hide |= hideUnextendedClasses.get() && chain.size() < 3;

        if (!hide) {
            while (!chain.isEmpty()) {
                c = chain.pop();
                if (tree.contains(c)) {
                    tree.goTo(c);
                } else {
                    tree.addAndGoTo(c);
                }
            }
        }

        tree.goToRoot();
    }

    @Override
    public TreeView<String> visualize() {
        var view = tree.convert(new Convertor<Class<?>, String>() {
            @Override
            public String convert(Class<?> source) {
                // source.isLocalClass()
                String string;
                string = source.getSimpleName();

                if (appendAdder.get()) {
                    var adder = past.get(source);
                    string += adder == null ? "" : (" added by: " + adder.getSimpleName());
                }

                if (stringMaxLength.get() != -1 && string.length() > stringMaxLength.get()) {
                    // only keep uppercase letters
                    // string = string.replaceAll("[^A-Z]", "");
                    string = string.substring(0, stringMaxLength.get()) + "...";
                }

                return string;
            }
        }).visualize();

        view.setScribe(new Scribe("Segoe UI Semibold", 14));
        view.connectorType.set(Views.Connector.SERIOUS_CURVE);
        view.node_padding.set(4f);
        // view.border_width.set(1.5f);
        // view.edge_widgh.set(1.5f);

        return view;
    }

    public static void main(String[] args) {

        var classTree = new ClassTree();

        classTree.hideUnextendedClasses.set(true);
        classTree.stringMaxLength.set(16);

        // add all classes from all classpaths
        var classLoader = classTree.getClass().getClassLoader();
        try {
            BiPredicate<Path, BasicFileAttributes> predicate = (Path path, BasicFileAttributes attributes) -> Common
                    .getExt(path.toString()).toLowerCase().equals("class");
            for (URL u : Common.over(classLoader.getResources("").asIterator())) {
                if (u.getProtocol() == "file") {
                    String classPath = URLDecoder.decode(u.getPath().substring(1), "utf-8");
                    int n = classPath.length();
                    for (Path classFile : Common.over(Files.find(Path.of(classPath), 99, predicate).iterator())) {
                        String s = classFile.toString();
                        s = s.substring(n, s.length() - 6).replaceAll("\\\\|/", ".");
                        classTree.add(Class.forName(s));
                    }
                }
            }

            classTree.add(Token.class);
            // for (Package p : Common.over(classLoader.getDefinedPackages())) {
            // System.out.println(p.getName());
            // }
            // classTree.add(classLoader.loadClass("jile.vis.CodeView"));
            // Class.forName(name, initialize, loader)
        } catch (Exception e) {
            e.printStackTrace();
        }
        Viewer.singleton().show(classTree.visualize());
    }
}
