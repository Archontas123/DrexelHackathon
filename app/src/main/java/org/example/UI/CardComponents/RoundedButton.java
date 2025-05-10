package org.example.UI.CardComponents;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private int arc;
    private Color originalBackgroundColor;
    private Color hoverBackgroundColor;

    public RoundedButton(String text, int arc) {
        super(text);
        this.arc = arc;
        setContentAreaFilled(false);
        setFocusPainted(false);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (hoverBackgroundColor != null) {
                    setBackground(hoverBackgroundColor);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (originalBackgroundColor != null) {
                    setBackground(originalBackgroundColor);
                }
            }
        });
    }

    public void setOriginalBackgroundColor(Color color) {
        this.originalBackgroundColor = color;
        setBackground(color);
    }

    public void setHoverBackgroundColor(Color color) {
        this.hoverBackgroundColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        
        super.paintComponent(g);

        g2d.dispose();
    }
}
