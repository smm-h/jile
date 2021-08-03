package jile.math.settheory.eulerdiagram;

import java.util.HashSet;
import java.util.Set;

import jile.math.settheory.FiniteUniversalSet;
import jile.math.settheory.StoredUniversalSet;
import jile.vis.Viewer;

public class EulerDiagramTest {

    public static final int traitCount = 3;

    private static class EmulatedParticular implements Particular<EmulatedParticular> {
        boolean[] traits;
        String serialization;

        EmulatedParticular() {
            traits = new boolean[traitCount];
            serialization = "";
            for (int i = 0; i < traits.length; i++) {
                traits[i] = Math.random() >= 0.5;
                serialization += traits[i] ? '1' : '0';
            }
        }

        @Override
        public String toString() {
            return serialization;
        }

        @Override
        public boolean check(IntrinsicQuality<? extends Particular<EmulatedParticular>> q) {
            // TODO Auto-generated method stub
            return false;
        }
    }

    public static void main(String[] args) {
        Set<EmulatedParticular> s = new HashSet<EmulatedParticular>();
        for (int i = 0; i < 10; i++) {
            s.add(new EmulatedParticular());
        }
        FiniteUniversalSet<EmulatedParticular> u = new StoredUniversalSet<EmulatedParticular>(s);
        EulerDiagram<EmulatedParticular> d = new EulerDiagram<EmulatedParticular>(u);
        Viewer.singleton().show(d);
    }
}
