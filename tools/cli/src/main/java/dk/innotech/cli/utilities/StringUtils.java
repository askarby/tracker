package dk.innotech.cli.utilities;

public class StringUtils {
    public static boolean hasText(String input) {
        if (input == null) {
            return false;
        }
        return input.trim().length() > 0;
    }
}
