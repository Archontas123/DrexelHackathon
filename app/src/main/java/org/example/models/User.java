package    org.example.models;

//written by AI

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class User {
    private String username;
    private List<String> questions_right;
    private List<String> questions_wrong;

    public User(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new Gson();
            User userFromJson = gson.fromJson(reader, User.class);

            this.username = userFromJson.username;
            this.questions_right = userFromJson.questions_right;
            this.questions_wrong = userFromJson.questions_wrong;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getQuestions_right() {
        return questions_right;
    }

    public void setQuestions_right(List<Integer> questions_right) {
        this.questions_right = questions_right;
    }

    public List<Integer> getQuestions_wrong() {
        return questions_wrong;
    }

    public void setQuestions_wrong(List<Integer> questions_wrong) {
        this.questions_wrong = questions_wrong;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "username='" + username + '\'' +
                ", questions_right=" + questions_right +
                ", questions_wrong=" + questions_wrong +
                '}';
    }
}
