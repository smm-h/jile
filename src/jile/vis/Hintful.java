package jile.vis;

public interface Hintful {
    public Hint getHint();

    public class Text implements Hintful {

        private final String text;
        private final Hint hint;

        public Text(String text, String hint) {
            this.text = text;
            this.hint = new TextualHint(text);
        }

        @Override
        public Hint getHint() {
            return hint;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
