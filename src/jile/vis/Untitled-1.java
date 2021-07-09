
// Shape s = new TextLayout(tokenView.text, fonts[Sty],
// g.getFontRenderContext()).getOutline(null);

/*
 * Three methods of measuring the width of a text drawn in a mono-wdith font are
 * listed below: char width * char count, SwingUtilities.computeStringWidth, and
 * fontMetrics.stringWidth. All three work, but since the font is mono-width,
 * the first one is the most efficient.
 */
// w = characterWidth * text.length();
// w = SwingUtilities.computeStringWidth(fontMetrics, text);
// w = fontMetrics.stringWidth(text);