package dk.innotech.user.models;

public enum Language {
    DANISH("da"), ENGLISH("en");

    Language(String languageCode) {
        this.languageCode = languageCode;
    }

    private final String languageCode;

    public String getLanguageCode() {
        return languageCode;
    }
}
