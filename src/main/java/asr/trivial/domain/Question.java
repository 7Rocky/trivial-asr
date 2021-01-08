package asr.trivial.domain;

import java.util.Collection;
import java.util.TreeSet;

public class Question {

  private String question;
  private String correctAnswer;
  private Collection<String> answers = new TreeSet<>();

  public Question(String question, String correctAnswer, Collection<String> answers) {
    this.setQuestion(question);
    this.setCorrectAnswer(correctAnswer);
    this.addAnswer(correctAnswer);
    this.addAllAnswers(answers);
  }

  public String getQuestion() {
    return question;
  }

  public String getCorrectAnswer() {
    return correctAnswer;
  }

  public Collection<String> getAnswers() {
    return answers;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public void setCorrectAnswer(String correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public void addAnswer(String answer) {
    this.answers.add(answer);
  }

  public void addAllAnswers(Collection<String> answers) {
    this.answers.addAll(answers);
  }

  public void setAnswers(Collection<String> answers) {
    this.answers = answers;
  }

}
