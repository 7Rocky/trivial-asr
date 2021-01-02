package asr.trivial.dominio;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

  private List<Question> questions = new ArrayList<>();
  
  public List<Question> getQuestions() {
    return questions;
  }
  
  public void addQuestion(Question question) {
    questions.add(question);
  }
  
  // public void addAllQuestions(List<Question> questions) {
  //   this.questions.addAll(questions);
  // }
  
}
