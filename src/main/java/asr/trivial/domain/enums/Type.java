package asr.trivial.domain.enums;

public enum Type {

  MULTIPLE("multiple", "Multiple choice"),
  BOOLEAN("boolean", "True/False");
  
  private final String value;
  private final String text;

  private Type(String value, String text) {
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
    for (Type type : Type.values()) {
      if (type.getValue().equals(value)) {
        return true;
      }
    }
    
    return false;
  }

}
