package jile.common;

public class PercentDecoder implements Singleton {

    private PercentDecoder() {
    }

    public static String decode(String encoded) {

        StringBuilder builder = new StringBuilder();

        String string = encoded;

        int index = 0;

        while (index < string.length()) {

            char c = string.charAt(index++);

            if (c == '%')
                c = (char) (Common.valueOfSymbol(string.charAt(index++)) * 16
                        + Common.valueOfSymbol(string.charAt(index++)));

            builder.append(c);
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(decode("a%20b"));
    }
}
