package org.example.UI.CardComponents;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int arc;
    private Timer animationTimer;
    private Color targetBackgroundColor;
    private Color baseBackgroundColor; 

    private static final int ANIMATION_DURATION_MS = 200;
    private static final int ANIMATION_STEPS = 20;
    private int currentAnimationStep = 0;

    public RoundedPanel(LayoutManager layout, int arc, Color initialBackground) {
        super(layout);
        this.arc = arc;
        this.baseBackgroundColor = initialBackground;
        setBackground(initialBackground);
        setOpaque(false);
    }

    public RoundedPanel(int arc, Color initialBackground) {
        super();
        this.arc = arc;
        this.baseBackgroundColor = initialBackground;
        setBackground(initialBackground);
        setOpaque(false);
    }

    public void animateBackgroundTo(Color targetColor) {
        if (getBackground().equals(targetColor)) {
            if (animationTimer != null && animationTimer.isRunning()) {
                if (this.targetBackgroundColor == null || !this.targetBackgroundColor.equals(targetColor)) {
                    animationTimer.stop(); 
                } else {
                    return; 
                }
            } else if (getBackground().equals(targetColor)) {
                return; 
            }
        }
        
        this.targetBackgroundColor = targetColor;
        Color initialColor = getBackground();

        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        currentAnimationStep = 0;
        
        animationTimer = new Timer(ANIMATION_DURATION_MS / ANIMATION_STEPS, e -> {
            currentAnimationStep++;
            if (currentAnimationStep >= ANIMATION_STEPS) {
                setBackground(this.targetBackgroundColor); 
                ((Timer)e.getSource()).stop();
            } else {
                float ratio = (float) currentAnimationStep / ANIMATION_STEPS;
                int r = (int) (initialColor.getRed() * (1 - ratio) + this.targetBackgroundColor.getRed() * ratio);
                int g = (int) (initialColor.getGreen() * (1 - ratio) + this.targetBackgroundColor.getGreen() * ratio);
                int b = (int) (initialColor.getBlue() * (1 - ratio) + this.targetBackgroundColor.getBlue() * ratio);
                setBackground(new Color(r, g, b));
            }
        });
        animationTimer.start();
    }

    public void animateBackgroundTo(Color targetColor, Runnable onComplete) {
        if (getBackground().equals(targetColor)) {
            if (animationTimer != null && animationTimer.isRunning()) {
                if (this.targetBackgroundColor == null || !this.targetBackgroundColor.equals(targetColor)) {
                    animationTimer.stop();
                } else {
                    if (onComplete != null) onComplete.run();
                    return;
                }
            } else {
                 if (onComplete != null) onComplete.run();
                return;
            }
        }
        
        this.targetBackgroundColor = targetColor;
        Color initialColor = getBackground();

        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        currentAnimationStep = 0;
        
        animationTimer = new Timer(ANIMATION_DURATION_MS / ANIMATION_STEPS, e -> {
            currentAnimationStep++;
            if (currentAnimationStep >= ANIMATION_STEPS) {
                setBackground(this.targetBackgroundColor);
                ((Timer)e.getSource()).stop();
                if (onComplete != null) {
                    onComplete.run();
                }
            } else {
                float ratio = (float) currentAnimationStep / ANIMATION_STEPS;
                int r = (int) (initialColor.getRed() * (1 - ratio) + this.targetBackgroundColor.getRed() * ratio);
                int g = (int) (initialColor.getGreen() * (1 - ratio) + this.targetBackgroundColor.getGreen() * ratio);
                int b = (int) (initialColor.getBlue() * (1 - ratio) + this.targetBackgroundColor.getBlue() * ratio);
                setBackground(new Color(r, g, b));
            }
        });
        animationTimer.start();
    }
    
    public void setBaseBackgroundColor(Color color) {
        this.baseBackgroundColor = color;
        if (animationTimer == null || !animationTimer.isRunning()) {
            setBackground(color);
        }
    }
    
    public Color getBaseBackgroundColor() {
        return this.baseBackgroundColor;
    }

    public int getArc() {
        return this.arc;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2d.dispose();
    }
}
