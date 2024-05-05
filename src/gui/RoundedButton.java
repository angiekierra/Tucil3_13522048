package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Custom class for rounded button
public class RoundedButton extends JButton {

    private int cornerRadius;
    private Color defaultColor;
    private Color hoverColor;


    public RoundedButton(String text, Color defaultColor, Color hoverColor, int cornerRadius) {
        super(text);
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.cornerRadius = cornerRadius;
        init();
    }

    // Disabling default config and making it change color  on hover
    private void init() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
            }
        });
    }

    // Method to set bg
    @Override
    public void addNotify() {
        super.addNotify();
        setBackground(defaultColor);
    }

    // Method to create the rounded look
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2.setColor(getForeground());
        g2.drawString(getText(), (getWidth() - g2.getFontMetrics().stringWidth(getText())) / 2, ((getHeight() - g2.getFontMetrics().getHeight()) / 2) + g2.getFontMetrics().getAscent());
        g2.dispose();
    }

    // Disabling default border
    @Override
    protected void paintBorder(Graphics g) {
        // Do not paint border
    }

    // Setter
    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    
}
