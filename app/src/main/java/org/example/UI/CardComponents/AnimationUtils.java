package org.example.UI.CardComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationUtils {
    public static void shakeComponent(JComponent component) {
        final Point originalLocation = component.getLocation();
        final int shakeDistance = 5;
        final int shakeCycles = 3; 
        final int shakeDelay = 30; 

        Timer shakeTimer = new Timer(shakeDelay, null);
        shakeTimer.addActionListener(new ActionListener() {
            private int currentCycle = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCycle >= shakeCycles * 2) { 
                    component.setLocation(originalLocation); 
                    shakeTimer.stop();
                    return;
                }

                int dx;
                if (currentCycle % 2 == 0) { 
                    dx = shakeDistance;
                } else { 
                    dx = -shakeDistance;
                }
                component.setLocation(originalLocation.x + dx, originalLocation.y);
                currentCycle++;
            }
        });
        shakeTimer.setInitialDelay(0);
        shakeTimer.start();
    }
}
