package asr.trivial.domain.enums;

public enum Category {

  GENERAL_KNOWLEDGE(9, "General knowledge"),
  BOOKS(10, "Books"),
  FILM(11, "Film"),
  MUSIC(12, "Music"),
  MUSICALS_AND_THEATRES(13, "Musicals and theatres"),
  TELEVISION(14, "Television"),
  VIDEO_GAMES(15, "Video games"),
  BOARD_GAMES(16, "Board games"),
  SCIENCE_AND_NATURE(17, "Science and nature"),
  COMPUTERS(18, "Computers"),
  MATHEMATICS(19, "Mathematics"),
  MYTHOLOGY(20, "Mythology"),
  SPORTS(21, "Sports"),
  GEOGRAPHY(22, "Geography"),
  HISTORY(23, "History"),
  POLITICS(24, "Politics"),
  ART(25, "Art"),
  CELEBRITIES(26, "Celebrities"),
  ANIMALS(27, "Animals"),
  VEHICLES(28, "Vehicles"),
  COMICS(29, "Comics"),
  GADGETS(30, "Gadgets"),
  ANIME_AND_MANGA(31, "Anime and manga"),
  CARTOON_AND_ANIMATIONS(32, "Cartoon and animations");

  private final int value;
  private final String text;

  private Category(int value, String text) {
    this.value = value;
    this.text = text;
  }

  public int getValue() {
    return value;
  }

  public String getText() {
    return text;
  }

  public static Category getCategory(int value) {
    for (Category category : Category.values()) {
      if (category.getValue() == value) {
        return category;
      }
    }

    return null;
  }

  public static Category getCategory(String text) {
    for (Category category : Category.values()) {
      if (category.getText().equals(text)) {
        return category;
      }
    }

    return null;
  }

  public static boolean isValid(int value) {
    for (Category category : Category.values()) {
      if (category.getValue() == value) {
        return true;
      }
    }

    return false;
  }

}
