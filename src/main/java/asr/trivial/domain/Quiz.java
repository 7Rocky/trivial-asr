package asr.trivial.domain;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import asr.trivial.domain.enums.Category;
import asr.trivial.domain.enums.Difficulty;
import asr.trivial.domain.enums.SelectedLanguage;

public class Quiz implements Serializable {

  private static final long serialVersionUID = 2280380175814344653L;

  public static final int AMOUNT = 5;
  public static final String TYPE = "multiple";

  private Category category;
  private Difficulty difficulty;
  private SelectedLanguage selectedLanguage = SelectedLanguage.ENGLISH;
  private List<Question> questions = new ArrayList<>(AMOUNT);

  public Quiz(Category category, Difficulty difficulty) {
    this.setCategory(category);
    this.setDifficulty(difficulty);
  }

  public Category getCategory() {
    return category;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public SelectedLanguage getSelectedLanguage() {
    return selectedLanguage;
  }

  public List<Question> getQuestions() {
    return questions;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void setSelectedLanguage(SelectedLanguage selectedLanguage) {
    this.selectedLanguage = selectedLanguage;
  }

  public void addQuestion(Question question) {
    questions.add(question);
  }

}
