package asr.trivial.dominio;

import java.util.ArrayList;
import java.util.List;

public class Question {

  private String question;
  private String correctAnswer;
  private List<String> answers = new ArrayList<>();

  
  public Question(String question, String correctAnswer, List<String> answers) {
    this.setQuestion(question);
    this.setCorrectAnswer(correctAnswer);
    this.addAllAnswers(answers);
  }
  
  public String getQuestion() {
    return question;
  }
  
  public String getCorrectAnswer() {
    return correctAnswer;
  }
  
  public List<String> getAnswers() {
    return answers;
  }

  public void setQuestion(String question) {
    this.question = question;
  }
  
  public void setCorrectAnswer(String correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public void addAllAnswers(List<String> answers) {
    this.answers.addAll(answers);
  }
  
}
