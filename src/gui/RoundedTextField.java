package src.gui;

import javax.swing.*;
import java.awt.*;

// Class for custom rounded text input
public class RoundedTextField extends JTextField {
    private int cornerRadius;

    // Constructor , disabling default config
    public RoundedTextField(int columns, int cornerRadius) {
        super(columns);
        this.cornerRadius = cornerRadius;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 8, 10)); // Add padding
    }

    // Method to create the rounded look
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw the rounded border
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        super.paintComponent(g2d);

        g2d.dispose();
    }
}