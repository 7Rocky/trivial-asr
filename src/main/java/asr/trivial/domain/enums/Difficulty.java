package asr.trivial.domain.enums;

public enum Difficulty {

  EASY("easy", "Easy"),
  MEDIUM("medium", "Medium"),
  HARD("hard", "Hard");
  
  private final String value;
  private final String text;

  private Difficulty(String value, String text) {
      this.value = value;
      this.text = text;
  }

  public String getValue() {
      return value;
  }
  
  public String getText() {
    return text;
}
  
  public static boolean isValid(String value) {
    for (Difficulty difficulty : Difficulty.values()) {
      if (difficulty.getValue().equals(value)) {
        return true;
      }
    }
    
    return false;
  }

}
