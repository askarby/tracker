package dk.innotech.cli.tools;

import dk.innotech.cli.CliCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Option;

/**
 * Utility for creating timestamps.<p/>
 *
 * Flexible utility to create Epoch timestamps from various formats.
 */
@Command(
        name = "time-encode",
        description = "Takes in a date/time, converts it to a timestamp and prints to STDOUT."
)
public class TimestampCommand implements Callable<Integer> {
    @ParentCommand
    private CliCommand parent;

    @Parameters(index = "0", paramLabel = "<date/time>", description = "Date/time to convert")
    private String toConvert;

    @Option(
            names = { "-p", "--pattern"},
            defaultValue = "yyyy-MM-dd HH:mm:ss",
            description = "Pattern to parse date/time from"
    )
    private String pattern;

    @Option(
            names = { "-o", "--output"},
            paramLabel = "<output precision>",
            defaultValue = "milliseconds",
            description = "Precision to output with"
    )
    private Precision precision;

    @Override
    public Integer call()  {
        try {
            var formatter = DateTimeFormatter.ofPattern(pattern);
            var localDateTime = LocalDateTime.parse(toConvert, formatter);
            var instant = localDateTime.toInstant(ZoneOffset.UTC);
            var converted = precision.fromInstant(instant);

            System.out.println("Converted '" + toConvert + "' to: '" + converted + " (epoch in " + precision.getAbbreviation() + ")");
            return 0;
        } catch (Exception e) {
            System.err.println("Unable to parse input: '" + toConvert + "'! (as valid input for pattern '" + pattern +"')\n");
            System.out.println(new CommandLine(this).getUsageMessage());
            return 1;
        }
    }

    enum Precision {
        MILLISECONDS(1000, "milliseconds", "ms"),
        SECONDS(0, "seconds", "s");

        private final int factor;
        private final String[] patterns;

        Precision(int factor, String ... patterns) {
            this.factor = factor;
            this.patterns = patterns;
        }

        String getAbbreviation() {
            return patterns[1];
        }

        public long fromInstant(Instant source) {
            return source.toEpochMilli() * factor;
        }
    }
}

