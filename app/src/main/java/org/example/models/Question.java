package org.example.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Question {
    private String test;
    private String domain;
    private String skill;
    private String difficulty;
    private String questionId;
    private String imagePath;
    private String question;
    private List<String> answerChoices;
    private int correctAnswer;
    private List<String> answerExplanations;
    private List<String> hints;



    public String getTest() { return test; }
    public void setTest(String test) { this.test = test; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }

    public String getSkill() { return skill; }
    public void setSkill(String skill) { this.skill = skill; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public List<String> getAnswerChoices() { return answerChoices; }
    public void setAnswerChoices(List<String> answerChoices) { this.answerChoices = answerChoices; }

    public int getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }

    public List<String> getAnswerExplanations() { return answerExplanations; }
    public void setAnswerExplanations(List<String> answerExplanations) { this.answerExplanations = answerExplanations; }

    public List<String> getHints() { return hints; }
    public void setHints(List<String> hints) { this.hints = hints; }

    @Override
    public String toString() {
        return "Question{" +
                "test='" + test + '\'' +
                ", domain='" + domain + '\'' +
                ", skill='" + skill + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", questionId='" + questionId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", question='" + question + '\'' +
                ", answerChoices=" + answerChoices +
                ", correctAnswer=" + correctAnswer +
                ", answerExplanations=" + answerExplanations +
                ", hints=" + hints +
                '}';
    }
}
