package asr.trivial.domain;

import java.util.ArrayList;
import java.util.List;

import asr.trivial.domain.enums.Category;
import asr.trivial.domain.enums.Difficulty;

public class Quiz {

  public static final int AMOUNT = 5;
  public static final String TYPE = "multiple";

  private Category category;
  private Difficulty difficulty;
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

  public List<Question> getQuestions() {
    return questions;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void addQuestion(Question question) {
    questions.add(question);
  }

}
