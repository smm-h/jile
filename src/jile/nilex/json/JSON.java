package jile.nilex.json;

import java.util.HashMap;
import java.util.LinkedList;

import jile.nilex.Code;
import jile.nilex.Encodeable;

public class JSON {
    private JSON() {
    }

    public static interface Element extends Encodeable {
        public java.lang.String serialize();

        @Override
        default public Code encode() {
            return new Code(serialize(), "json");
        }
    }

    public static class Map extends HashMap<String, Element> implements Element {
        @Override
        public java.lang.String serialize() {
            if (size() == 0)
                return "{}";
            else {
                StringBuilder builder = new StringBuilder(14 * size());
                builder.append('{');
                for (String key : keySet()) {
                    builder.append(key.serialize());
                    builder.append(": ");
                    builder.append(get(key).serialize());
                    builder.append(", ");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.deleteCharAt(builder.length() - 1);
                builder.append('}');
                return builder.toString();
            }
        }
    }

    public static class List extends LinkedList<Element> implements Element {
        @Override
        public java.lang.String serialize() {
            if (size() == 0)
                return "[]";
            else {
                StringBuilder builder = new StringBuilder(8 * size());
                builder.append('[');
                for (Element element : this) {
                    builder.append(element.serialize());
                    builder.append(", ");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.deleteCharAt(builder.length() - 1);
                builder.append(']');
                return builder.toString();
            }
        }
    }

    public static class String implements Element {
        public final String value;

        public String(String value) {
            this.value = value;
        }

        @Override
        public java.lang.String serialize() {
            return "\"" + value + "\"";
        }
    }

    public static class Number implements Element {
        public final double value;

        public Number(double value) {
            this.value = value;
        }

        @Override
        public java.lang.String serialize() {
            return Double.toString(value);
        }
    }

    public static class Boolean implements Element {
        public final boolean value;

        public Boolean(boolean value) {
            this.value = value;
        }

        @Override
        public java.lang.String serialize() {
            return value ? "true" : "false";
        }
    }
}
