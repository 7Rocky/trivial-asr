package asr.trivial.domain;

import java.io.Serializable;

import java.util.Collection;
import java.util.TreeSet;

public class Question implements Serializable {

  private static final long serialVersionUID = 5094716942315511592L;

  private String questionTitle;
  private String correctAnswer;
  private Collection<String> answers = new TreeSet<>();

  public Question(String questionTitle, String correctAnswer, Collection<String> answers) {
    this.setQuestionTitle(questionTitle);
    this.setCorrectAnswer(correctAnswer);
    this.addAnswer(correctAnswer);
    this.addAllAnswers(answers);
  }

  public String getQuestionTitle() {
    return questionTitle;
  }

  public String getCorrectAnswer() {
    return correctAnswer;
  }

  public Collection<String> getAnswers() {
    return answers;
  }

  public void setQuestionTitle(String questionTitle) {
    this.questionTitle = questionTitle;
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
