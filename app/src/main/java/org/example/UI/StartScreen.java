package org.example.UI;

import javax.swing.JFrame;


public class StartScreen extends JFrame {
   
    private javax.swing.JTextField questionsPathField;
    private javax.swing.JTextField userPathField;


    public StartScreen() {
        setTitle("Start Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(null); 
        setResizable(false);
        setVisible(true);
        questionsPathField = createTextField("Questions JSON Path:", 50, 50);
        userPathField = createTextField("User JSON Path:", 50, 100);
        createStartButton();
      
    }
    public javax.swing.JTextField createTextField(String label, int x, int y) {
        javax.swing.JLabel jLabel = new javax.swing.JLabel(label);
        jLabel.setBounds(x, y, 200, 30);
        add(jLabel);

        javax.swing.JTextField textField = new javax.swing.JTextField();
        textField.setBounds(x + 200, y, 300, 30);
        add(textField);
        return textField;
    }

    public void createStartButton() {
        javax.swing.JButton startButton = new javax.swing.JButton("Start Session");
        startButton.setBounds(50, 150, 200, 30);
        add(startButton);
        startButton.addActionListener(e -> {
            start();
        });
    }

    public void start() {
        String questionsJSONPath = questionsPathField.getText();
        String userJSONPath = userPathField.getText();
     
        if (isValidPath(questionsJSONPath) && isValidPath(userJSONPath)) {
            new MainScreen(questionsJSONPath,userJSONPath).setVisible(true);
            this.dispose(); 
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid JSON paths. Please check and try again.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }


    }
    public boolean isValidPath(String path) {
        if (path == null || path.trim().isEmpty() || !path.endsWith(".json")) {
            return false;
        }
        java.io.File file = new java.io.File(path);
        return file.exists() && file.isFile();
    }


}
