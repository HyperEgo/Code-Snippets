package com.ultimate-rad-games;

/**
 * Specialized label class to highlight text.
 */
public class HighlightLabel extends JLabel {

    private static final int X_RECT_POS = 0;
    private static final int Y_RECT_POS = 2;
    private static final int BUFFER = 3;

    private Color highlight, border;
    private Font font;
    private boolean toggle;
    private float height, width;

    /**
     * Constructor.
     * @param input String object input
     * @param font Font selector
     */
    public HighlightLabel(String input, Font font) {
        super(input);
        this.font = font;
        this.highlight = Color.YELLOW;
        this.border = Color.BLUE;
        this.toggle = true;
        this.height = 0;
        this.width = 0;
    }

    /**
     * Set highlight colors.
     * @param fill Color inner field
     * @param outer Color outer field
     */
    public void setHighlightColor(Color fill, Color outer) {
        this.highlight = fill;
        this.border = outer;
    }

    /**
     * Set highlight toggle effect.
     * @param toggle boolean object toggle
     */
    public void setToggle(boolean toggle) {
        this.toggle = toggle;
        this.repaint();
    }

    /**
     * Set highlight region based on prefix.
     * @param prefix String object input
     */
    public void setHighlightRegion(String prefix) {
        java.awt.FontMetrics metrics = this.getFontMetrics(font);
        height = metrics.getHeight() + BUFFER;
        width = metrics.stringWidth(prefix) + BUFFER;
        setToggle(true);
    }

    @Override
    protected void paintComponent(Graphic g) {  // rectangle outer + inner color fill
        if ( toggle ) {
            Rectangle2D.Float rect = new Rectangle2D.Float(X_RECT_POS, Y_RECT_POS, width, height);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(border);
            g2d.draw(rect);
            g2d.setColor(highlight);
            g2d.fill(rect);
        }
        super.paintComponet(g);
    }
}
