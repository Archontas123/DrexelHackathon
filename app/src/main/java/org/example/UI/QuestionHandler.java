package org.example.UI;

import org.example.UI.CardComponents.QuestionCard;
import org.example.models.Question;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class QuestionHandler {
    private int currentHintIndex = -1;
    private boolean explanationVisible = false;

    public QuestionHandler() {
    }

    public static List<Question> loadQuestions(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new Gson();
            Type questionListType = new TypeToken<List<Question>>() {}.getType();
            return gson.fromJson(reader, questionListType);
        } catch (IOException e) {
            System.err.println("Failed to load questions: " + e.getMessage());
            return null;
        }
    }

    public boolean isCorrectAnswer(Question question, String selectedAnswerText) {
      /*   if (selectedAnswerText == null || question == null || question.getAnswerChoices() == null) {
            return false;
        } */
        int correctAnswerIndex = question.getCorrectAnswer();
        if (correctAnswerIndex < 0 || correctAnswerIndex >= question.getAnswerChoices().size()) {
            return false; 
        }
        return selectedAnswerText.equals(question.getAnswerChoices().get(correctAnswerIndex));
    }

    public void handleHintToggle(QuestionCard card, Question question) {
        List<String> hints = question.getHints();

        if (hints == null || hints.isEmpty()) {
            card.displayHintText("No hints available for this question.");
            card.setHintButtonText("Show Hint");
            card.setHintLabelVisible(true);
            currentHintIndex = -1; 
            return;
        }

        currentHintIndex++;

        if (currentHintIndex < hints.size()) {
            card.displayHintText(hints.get(currentHintIndex));
            card.setHintLabelVisible(true);
            if (currentHintIndex < hints.size() - 1) {
                card.setHintButtonText("Next Hint");
            } else {
                card.setHintButtonText("Hide Hint");
            }
        } else {
            card.setHintLabelVisible(false);
            card.setHintButtonText("Show Hint");
            currentHintIndex = -1; 
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
        if (question != null && question.getAnswerChoices() != null && question.getCorrectAnswer() >= 0 && question.getCorrectAnswer() < question.getAnswerChoices().size()) {
            correctAnswerFullText = question.getAnswerChoices().get(question.getCorrectAnswer());
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
        currentHintIndex = -1; 
        explanationVisible = false; 
    }
}
