package jile.lingu.json;

import jile.vis.Viewer;

public class JSONTest {
    public static void main(String[] args) {
        Viewer.singleton().show(JSONLanguage.singleton().maker.makeFrom("test").visualize());
    }
}
