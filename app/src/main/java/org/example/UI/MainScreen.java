package org.example.UI;

import java.util.List;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import org.example.UI.CardComponents.QuestionCard;
import org.example.UI.CardComponents.RoundedButton;
import org.example.UI.QuestionHandler;
import org.example.models.Question;
import org.example.models.User;

public class MainScreen extends JFrame {
    private String questionsJSONPath;
    private String userJSONPath;
    private List<Question> questions;
    private User user;
    private QuestionHandler questionHandler;
    private RoundedButton startButton;
    private RoundedButton nextButton;
    private int currentQuestionIndex = 0;
    private QuestionCard currentQuestionCard;


    public MainScreen(String questionsJSONPath, String userJSONPath) {
        this.questionsJSONPath = questionsJSONPath;
        this.userJSONPath = userJSONPath;
        this.questionHandler = new QuestionHandler(); 
        this.questions = this.questionHandler.loadQuestions(questionsJSONPath);
        this.user = new User(userJSONPath);
        
        setTitle("Main Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 245, 250)); 
        createStartButton();
        createNextButton();
    }

    public void createStartButton() {
        startButton = new RoundedButton("Start Session", 30); 
        startButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        startButton.setOriginalBackgroundColor(new Color(70, 130, 180));
        startButton.setHoverBackgroundColor(new Color(100, 160, 210));
        startButton.setForeground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(300, 70)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(startButton, gbc);
        startButton.addActionListener(e -> {
            showQuestion();
        });
    }

    public void createNextButton() {
        nextButton = new RoundedButton("Next Question", 20);
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setOriginalBackgroundColor(new Color(0, 120, 0)); 
        nextButton.setHoverBackgroundColor(new Color(0, 150, 0)); 
        nextButton.setForeground(Color.WHITE);
        nextButton.setPreferredSize(new Dimension(200, 50));
        nextButton.setVisible(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(nextButton, gbc);
        nextButton.addActionListener(e -> {
            currentQuestionIndex++;
            showQuestion();
        });
    }

    public void showQuestion() {
        GridBagConstraints gbcCard = new GridBagConstraints();
        gbcCard.gridx = 0;
        gbcCard.gridy = 0;
        gbcCard.weightx = 1;
        gbcCard.weighty = 1;
        gbcCard.fill = GridBagConstraints.BOTH; 
        gbcCard.anchor = GridBagConstraints.CENTER;


        if (startButton != null) {
            remove(startButton);
            startButton = null;
            nextButton.setVisible(true); 
        }

        if (currentQuestionCard != null) {
            remove(currentQuestionCard);
            currentQuestionCard = null;
        }

        if (questions != null && !questions.isEmpty()) {
            if (currentQuestionIndex < questions.size()) {
                Question question = questions.get(currentQuestionIndex);
                currentQuestionCard = new QuestionCard(this.questionHandler, question, Color.BLUE);
                currentQuestionCard.setPreferredSize(new java.awt.Dimension(getWidth() * 2 / 3, getHeight() * 2 / 3));
                add(currentQuestionCard, gbcCard);
                currentQuestionCard.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No more questions.", "End of Session", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                if (nextButton != null) {
                    nextButton.setEnabled(false);
                }
                dispose();
                org.example.App.main(null);
            }
            revalidate();
            repaint();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No questions available.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            if (nextButton != null) {
                nextButton.setVisible(false);
            }
            dispose();
            org.example.App.main(null);
        }
    }
}
