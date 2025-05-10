package org.example;

import javax.swing.SwingUtilities;
import org.example.UI.StartScreen;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartScreen().setVisible(true);
            }
        });
    }
}
