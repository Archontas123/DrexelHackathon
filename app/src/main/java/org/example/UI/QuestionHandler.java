package org.example.UI;

public class QuestionHandler {
    
    public QuestionHandler() {
    }

    public boolean isCorrectAnswer(String userAnswer, String correctAnswer) {
        return userAnswer.equalsIgnoreCase(correctAnswer);
    }

    

}

