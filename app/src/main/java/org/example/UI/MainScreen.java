package org.example.UI;

import javax.swing.JFrame;

public class MainScreen extends JFrame {
    private String questionsJSONPath;
    private String userJSONPath;

    public MainScreen(String questionsJSONPath, String userJSONPath) {
        this.questionsJSONPath = questionsJSONPath;
        this.userJSONPath = userJSONPath;
        
        setTitle("Main Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

    }

    public void createStartButton() {
        javax.swing.JButton startButton = new javax.swing.JButton("Start Session");
        startButton.setBounds(50, 150, 200, 30);
        add(startButton);
        startButton.addActionListener(e -> {
            start();
        });
    }

    public void start(){
        
    }
    
}
