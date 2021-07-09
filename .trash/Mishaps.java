package jile.nilex;

import java.util.HashSet;
import java.util.Set;

import jile.io.Resource;

public class Mishaps {

    private final Set<Mishap> set = new HashSet<Mishap>();

    public final Resource resource = Resource.of("", "txt");

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public void add(Mishap mishap) {
        set.add(mishap);
        // System.out.println(resource.getSize() == 0);
        // System.out.println(resource.getSize() == 0 ? "" :
        // (resource.getContents(false) + "\n"));
        // resource.setContents(resource.getSize() == 0 ? "" :
        // (resource.getContents(false) + "\n") + mishap.getReport());
        String contents = mishap.getReport();
        if (resource.getSize() > 0)
            contents += "\n" + resource.getContents(false);
        resource.setContents(contents);
    }
}
