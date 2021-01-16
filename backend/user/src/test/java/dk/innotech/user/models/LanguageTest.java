package dk.innotech.user.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Language-enum")
public class LanguageTest {
    @Test
    @DisplayName("Should have appropriate language codes")
    public void appropriateLanguageCodes() {
        assertThat(Language.DANISH.getLanguageCode()).isEqualTo("da");
        assertThat(Language.ENGLISH.getLanguageCode()).isEqualTo("en");
    }
}
