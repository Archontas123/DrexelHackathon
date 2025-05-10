package org.example.UI;

import java.util.List;

import javax.swing.JFrame;
import java.awt.Color;
import org.example.UI.CardComponents.QuestionCard;
import org.example.UI.QuestionHandler;
import org.example.models.Question;
import org.example.models.User;

public class MainScreen extends JFrame {
    private String questionsJSONPath;
    private String userJSONPath;
    private List<Question> questions;
    private User user;
    private QuestionHandler questionHandler;
    private javax.swing.JButton startButton;
    private javax.swing.JButton nextButton;
    private int currentQuestionIndex = 0;
    private QuestionCard currentQuestionCard;


    public MainScreen(String questionsJSONPath, String userJSONPath) {
        this.questionsJSONPath = questionsJSONPath;
        this.userJSONPath = userJSONPath;
        this.questionHandler = new QuestionHandler(); 
        this.questions = this.questionHandler.loadQuestions(questionsJSONPath);
        this.user = new User(userJSONPath);
        
        setTitle("Main Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(null); 
        createStartButton();
        createNextButton();

    }

    public void createStartButton() {
        startButton = new javax.swing.JButton("Start Session");
        startButton.setBounds(50, 150, 200, 30);
        add(startButton);
        startButton.addActionListener(e -> {
            showQuestion();
        });
    }

    public void createNextButton() {
        nextButton = new javax.swing.JButton("Next Question");
        nextButton.setBounds(getWidth() - 200 - 20, getHeight() - 70, 200, 30); // Positioned at bottom right
        nextButton.setVisible(false); 
        add(nextButton);
        nextButton.addActionListener(e -> {
            currentQuestionIndex++;
            showQuestion();
        });
    }

    public void showQuestion() {
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
                add(currentQuestionCard);
                currentQuestionCard.setBounds(0, 0, getWidth(), getHeight() - 80); 
                currentQuestionCard.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No more questions.", "End of Session", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                if (nextButton != null) {
                    nextButton.setEnabled(false);
                }
            }
            revalidate();
            repaint();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No questions available.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            if (nextButton != null) {
                nextButton.setVisible(false);
            }
        }
    }
}
