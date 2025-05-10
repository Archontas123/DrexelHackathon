package org.example.UI.CardComponents;

import javax.swing.*;
import java.awt.*;

public class HoverAnimatedRoundedPanel extends RoundedPanel {
    private Timer scaleTimer;
    private double currentScale = 1.0;
    private double targetScale = 1.0;
    private static final double HOVER_SCALE_FACTOR = 1.03;
    private static final int SCALE_ANIMATION_DURATION_MS = 100;
    private static final int SCALE_ANIMATION_STEPS = 10;
    private int currentScaleStep = 0;

    public HoverAnimatedRoundedPanel(LayoutManager layout, int arc, Color initialBackground) {
        super(layout, arc, initialBackground);
        setupMouseListener();
    }

    public HoverAnimatedRoundedPanel(int arc, Color initialBackground) {
        super(arc, initialBackground);
        setupMouseListener();
    }

    private void setupMouseListener() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                animateScaleTo(HOVER_SCALE_FACTOR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                animateScaleTo(1.0);
            }
        });
    }

    private void animateScaleTo(double newTargetScale) {
        if (this.targetScale == newTargetScale && (scaleTimer != null && scaleTimer.isRunning())) {
            return;
        }
        if (this.currentScale == newTargetScale && (scaleTimer == null || !scaleTimer.isRunning())) {
            return;
        }

        this.targetScale = newTargetScale;
        double initialScale = this.currentScale;

        if (scaleTimer != null && scaleTimer.isRunning()) {
            scaleTimer.stop();
        }
        currentScaleStep = 0;

        scaleTimer = new Timer(SCALE_ANIMATION_DURATION_MS / SCALE_ANIMATION_STEPS, e -> {
            currentScaleStep++;
            if (currentScaleStep >= SCALE_ANIMATION_STEPS) {
                currentScale = this.targetScale;
                ((Timer) e.getSource()).stop();
            } else {
                float ratio = (float) currentScaleStep / SCALE_ANIMATION_STEPS;
                currentScale = initialScale * (1 - ratio) + this.targetScale * ratio;
            }
            repaint(); 
        });
        scaleTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        double scaledWidth = width * currentScale;
        double scaledHeight = height * currentScale;
        double translateX = (width - scaledWidth) / 2.0;
        double translateY = (height - scaledHeight) / 2.0;

        g2d.translate(translateX, translateY);
        g2d.scale(currentScale, currentScale);
        
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, width, height, getArc(), getArc());
        
        g2d.dispose();
        
        super.paintComponent(g);
    }
}
