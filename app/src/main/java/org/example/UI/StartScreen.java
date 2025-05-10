package org.example.UI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import org.example.UI.CardComponents.RoundedButton;

public class StartScreen extends JFrame {

    private JTextField questionsPathField;
    private JTextField userPathField;

    public StartScreen() {
        setTitle("Start Study Session");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 245, 250)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Setup Your Study Session");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.WEST;

        questionsPathField = createTextField(mainPanel, gbc, "Questions JSON Path:", 1);
        userPathField = createTextField(mainPanel, gbc, "User JSON Path:", 2);
        
        createStartButton(mainPanel, gbc);

        add(mainPanel);
        setVisible(true);
    }

    private JTextField createTextField(JPanel panel, GridBagConstraints gbc, String labelText, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        JTextField textField = new JTextField(30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(300, 35));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        gbc.gridy = gridy;
        gbc.weightx = 0.7;
        panel.add(textField, gbc);
        return textField;
    }

    private void createStartButton(JPanel panel, GridBagConstraints gbc) {
        RoundedButton startButton = new RoundedButton("Start Program", 20);
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setOriginalBackgroundColor(new Color(70, 130, 180)); 
        startButton.setHoverBackgroundColor(new Color(100, 160, 210)); 
        startButton.setForeground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(200, 50));
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(startButton, gbc);

        startButton.addActionListener(e -> {
            start();
        });
    }

    public void start() {
        String questionsJSONPath = questionsPathField.getText();
        String userJSONPath = userPathField.getText();

        if (isValidPath(questionsJSONPath) && isValidPath(userJSONPath)) {
            new MainScreen(questionsJSONPath, userJSONPath).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid JSON paths. Please check and try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
