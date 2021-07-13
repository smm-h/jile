package jile.vis;

public class TextualHint implements Hint {

    private final String contents;

    public TextualHint(String text) {
        contents = text;
    }

    @Override
    public View visualize() {
        return new TextView(contents);
    }

    @Override
    public int getHash() {
        return contents.hashCode();
    }
}