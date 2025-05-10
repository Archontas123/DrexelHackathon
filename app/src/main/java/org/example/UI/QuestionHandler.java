package org.example.UI;

import org.example.UI.CardComponents.QuestionCard;
import org.example.models.Question;

import java.awt.Color;

public class QuestionHandler {
    private boolean hintVisible = false;
    private boolean explanationVisible = false;

    public QuestionHandler() {
    }

    public boolean isCorrectAnswer(Question question, String selectedAnswerText) {
        if (selectedAnswerText == null || question == null || question.getAnswerChoices() == null) {
            return false;
        }
        int correctAnswerIndex = question.getAnswerIndex();
        if (correctAnswerIndex < 0 || correctAnswerIndex >= question.getAnswerChoices().length) {
            return false; 
        }
        return selectedAnswerText.equals(question.getAnswerChoices()[correctAnswerIndex]);
    }

    public void handleHintToggle(QuestionCard card, Question question) {
        hintVisible = !hintVisible;
        if (hintVisible) {
            String hintText = "Placeholder hint."; 
            if (question != null) {
                String[] hints = question.getHints();
                if (hints != null && hints.length > 0) {
                    hintText = hints[0]; 
                }


            }
            card.displayHintText(hintText);
            card.setHintButtonText("Hide Hint");
            card.setHintLabelVisible(true);
        } else {
            card.setHintButtonText("Show Hint");
            card.setHintLabelVisible(false);
        }
    }

    public void handleCheckAnswer(QuestionCard card, Question question, String selectedAnswerActionCommand) {
        if (selectedAnswerActionCommand == null) {
            card.triggerShakeAnimation();
            card.showDialog("Please select an answer first!");
            return;
        }

        boolean isCorrect = isCorrectAnswer(question, selectedAnswerActionCommand);
        String explanationText;
        String correctAnswerFullText = "";
        if (question != null && question.getAnswerChoices() != null && question.getAnswerIndex() >= 0 && question.getAnswerIndex() < question.getAnswerChoices().length) {
            correctAnswerFullText = question.getAnswerChoices()[question.getAnswerIndex()];
        }


        if (isCorrect) {
            explanationText = "Correct!";
        } else {
            explanationText = "Incorrect. The correct answer is " + correctAnswerFullText;
        }
        
        card.switchToFeedbackView(isCorrect, explanationText);
        
        explanationVisible = false; 
        card.setFeedbackQuestionMarkLabelText("\u2753 Show Explanation");
        card.setFeedbackExplanationLabelVisible(false);
    }

    public void handleExplanationToggle(QuestionCard card) {
        explanationVisible = !explanationVisible;
        card.setFeedbackExplanationLabelVisible(explanationVisible);
        card.setFeedbackQuestionMarkLabelText(explanationVisible ? "\u2753 Hide Explanation" : "\u2753 Show Explanation");
    }

    public void handleRetry(QuestionCard card) {
        card.switchToQuestionViewAndReset();
        hintVisible = false; 
        explanationVisible = false; 
    }
}
