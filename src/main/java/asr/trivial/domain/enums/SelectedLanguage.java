package asr.trivial.domain.enums;

import com.ibm.watson.language_translator.v3.util.Language;

public enum SelectedLanguage {

  ENGLISH(Language.ENGLISH, "English", "en-US_AllisonV3Voice"),
  FRENCH(Language.FRENCH, "French", "fr-FR_ReneeV3Voice"),
  GERMAN(Language.GERMAN, "German", "de-DE_ErikaV3Voice"),
  ITALIAN(Language.ITALIAN, "Italian", "it-IT_FrancescaV3Voice"),
  JAPANESE(Language.JAPANESE, "Japanese", "ja-JP_EmiV3Voice"),
  PORTUGUESE(Language.PORTUGUESE, "Portuguese", "pt-BR_IsabelaV3Voice"),
  SPANISH(Language.SPANISH, "Spanish", "es-ES_LauraV3Voice"),;

  private final String value;
  private final String text;
  private final String voice;

  private SelectedLanguage(String value, String text, String voice) {
    this.value = value;
    this.text = text;
    this.voice = voice;
  }

  public String getValue() {
    return value;
  }

  public String getText() {
    return text;
  }

  public String getVoice() {
    return voice;
  }

  public static SelectedLanguage getSelectedLanguage(String value) {
    for (SelectedLanguage selectedLanguage : SelectedLanguage.values()) {
      if (selectedLanguage.getValue().equals(value)) {
        return selectedLanguage;
      }
    }

    return null;
  }

  public static boolean isValid(String value) {
    for (SelectedLanguage selectedLanguage : SelectedLanguage.values()) {
      if (selectedLanguage.getValue().equals(value)) {
        return true;
      }
    }

    return false;
  }

}
