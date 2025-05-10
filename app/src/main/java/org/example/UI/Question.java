package org.example.UI;

public class Question {

    private String questionName;
    private int questionID;
    private String questionType;
    private String type;
    private String[] subtopic;
    private String image;
    private String question;
    private String[] answerChoices;
    private int answerIndex;
    private String[] explanations;
    private String[] hints;

    public Question(String questionName, int questionID, String questionType, String type, String[] subtopic, String image,
                    String question, String[] answerChoices, int answerIndex, String[] explanations, String[] hints) {
        this.questionName = questionName;
        this.questionID = questionID;
        this.questionType = questionType;
        this.type = type;
        this.subtopic = subtopic;
        this.image = image;
        this.question = question;
        this.answerChoices = answerChoices;
        this.answerIndex = answerIndex;
        this.explanations = explanations;
        this.hints = hints;
    }

    public String getQuestionName() {
        return questionName;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getQuestionType() {
        return questionType;
    }

    public String getType() {
        return type;
    }

    public String[] getSubtopic() {
        return subtopic;
    }

    public String getImage() {
        return image;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswerChoices() {
        return answerChoices;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public String[] getExplanations() {
        return explanations;
    }

    public String[] getHints() {
        return hints;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubtopic(String[] subtopic) {
        this.subtopic = subtopic;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerChoices(String[] answerChoices) {
        this.answerChoices = answerChoices;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public void setExplanations(String[] explanations) {
        this.explanations = explanations;
    }

    public void setHints(String[] hints) {
        this.hints = hints;
    }
 
}
