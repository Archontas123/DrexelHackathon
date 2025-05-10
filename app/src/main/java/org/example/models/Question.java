package org.example.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Question {
    private String Test;
    private String Domain;
    private String Skill;
    private String Difficulty;
    private String question_id;
    private String image_path;
    private String question;
    private List<String> answer_choices;
    private String correct_answer;
    private List<String> answer_explanations;
    private List<String> hints;

    // Constructor that loads a list of questions from a JSON file
    public static List<Question> loadFromFile(String jsonFilePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(jsonFilePath)) {
            Type questionListType = new TypeToken<List<Question>>() {}.getType();
            return gson.fromJson(reader, questionListType);
        }
    }

    // Getters (optional, but useful)
    public String getTest() {
        return Test;
    }

    public String getDomain() {
        return Domain;
    }

    public String getSkill() {
        return Skill;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public String getQuestionId() {
        return question_id;
    }

    public String getImagePath() {
        return image_path;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswerChoices() {
        return answer_choices;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    public List<String> getAnswerExplanations() {
        return answer_explanations;
    }

    public List<String> getHints() {
        return hints;
    }
}
