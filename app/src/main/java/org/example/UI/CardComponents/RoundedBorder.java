package org.example.UI.CardComponents;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder extends AbstractBorder {
    private Color color;
    private int arc;
    private int thickness;

    public RoundedBorder(Color color, int arc, int thickness) {
        this.color = color;
        this.arc = arc;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(new RoundRectangle2D.Double(x + thickness / 2.0, y + thickness / 2.0, 
                                            width - thickness, height - thickness, arc, arc));
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = thickness;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false; 
    }
}
