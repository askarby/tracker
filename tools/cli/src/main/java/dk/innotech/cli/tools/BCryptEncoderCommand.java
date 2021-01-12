package dk.innotech.cli.tools;

import dk.innotech.cli.CliCommand;
import org.mindrot.jbcrypt.BCrypt;
import picocli.CommandLine.ParentCommand;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

/**
 * Utility for encoding passwords.<p/>
 *
 * Takes in a password (in clear-text), and BCrypt encodes.<p/>
 *
 * This is very useful for initial database population (what I primarily use it for) etc.
 */
@Command(
        name = "bcrypt-encode",
        description = "Takes in a clear-text password and encodes it using BCrypt to STDOUT."
)
public class BCryptEncoderCommand implements Callable<Integer> {
    @ParentCommand
    private CliCommand parent;

    @Parameters(index = "0", paramLabel = "<clear-text password>", description = "Password in clear-text")
    private String toEncode;

    @Override
    public Integer call() {
        var salt = BCrypt.gensalt();
        var encoded = BCrypt.hashpw(toEncode, salt);

        System.out.println("Entered password: '" + toEncode + "' got BCrypt hashed to: '" + encoded + "'");

        return 0;
    }
}