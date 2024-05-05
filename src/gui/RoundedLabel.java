package src.gui;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import src.WordLadderGUI;

// Class for crating rounded square labels for each char in word
public class RoundedLabel extends JLabel {

    private int cornerRadius;
    private Color backgroundColor;
    private Border border;

    // Constructor
    public RoundedLabel(String text, int alignment, int cornerRadius, Color backgroundColor) {
        super(text, alignment);
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        setOpaque(false);
        setFont(WordLadderGUI.customFont.deriveFont(Font.BOLD,16));
    }

    // Setter
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint(); // Trigger repaint to apply the new color
    }

    // Method to create the rounded look
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        super.paintComponent(g2d);
        g2d.dispose();
    }

   // To add border
    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
    }

    @Override
    public Border getBorder() {
        return border;
    }

    @Override
    public void setBorder(Border border) {
        this.border = border;
        super.setBorder(border);
    }
}
