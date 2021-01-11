package dk.innotech.user.cli;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StringUtils;

/**
 * Utility application for encoding passwords.<p/>
 *
 * Simple CLI application that takes in a password, and BCrypt encodes it with the settings of the
 * user (Spring Boot) micro-service.<p/>
 *
 * This is very useful for initial database population (what I primarily use it for) etc.
 */
public class PasswordEncoderApplication {

    public static void main(String[] args) {
        if (args.length < 1 || !StringUtils.hasText(args[0])) {
            System.out.println("""
                    PasswordEncoderApplication should be invoked with:
                    
                    java dk.innotech.user.cli.PasswordEncoderApplication <clear-text-password>
                    """);
            System.exit(255);
        }

        var salt = BCrypt.gensalt();

        var toEncode = args[0];
        var encoded = BCrypt.hashpw(toEncode, salt);

        System.out.println("Entered password: '" + toEncode + "' got BCrypt hashed to: '" + encoded + "'");
    }
}
