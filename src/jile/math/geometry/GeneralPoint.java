package jile.math.geometry;

import jile.math.numbers.Real;

public class GeneralPoint implements Point {

    private final Real[] array;

    public GeneralPoint(Real[] array) {
        this.array = array;
    }

    @Override
    public int getDimensions() {
        return array.length;
    }

    @Override
    public Integer getCardinality() {
        return array.length;
    }

    @Override
    public Real getElementAt(int index) {
        return array[index];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(8 * array.length);
        builder.append("("); // 1
        builder.append(array[0].toString()); // 6
        for (int i = 1; i < array.length; i++) {
            builder.append(", "); // 2
            builder.append(array[i].toString()); // 6
        }
        builder.append(")"); // 1
        return builder.toString();
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (int i = 0; i < array.length; i++)
            h ^= array[i].hashCode();
        return h;
    }

}
