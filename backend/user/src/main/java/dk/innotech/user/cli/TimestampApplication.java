package dk.innotech.user.cli;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Utility for creating timestamps.<p/>
 *
 * Flexible utility to create Epoch timestamps in various formats.
 */
public class TimestampApplication {
    private static final String PATTERN_FLAG = "--pattern";
    private static final String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String PRECISION_FLAG = "--precision";
    private static final String DEFAULT_PRECISION = "milliseconds";

    public static void main(String[] args) {
        if (args.length < 1 || args[0].startsWith("--")) {
            printUsage(255);
        }

        var pattern = getFlagValue(args, PATTERN_FLAG, DEFAULT_FORMAT_PATTERN);
        var precision = getPrecision(args);
        var toConvert = args[0];

        try {
            var formatter = DateTimeFormatter.ofPattern(pattern);
            var localDateTime = LocalDateTime.parse(toConvert, formatter);
            var instant = localDateTime.toInstant(ZoneOffset.UTC);
            var converted = switch (precision) {
                case SECONDS -> instant.toEpochMilli() * 1000;
                case MILLISECONDS -> instant.toEpochMilli();
            };

            System.out.println("Converted '" + toConvert + "' to: '" + converted + " (epoch in " + precision.getAbbreviation() + ")");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Unable to parse input: '" + toConvert + "'! (as valid input for pattern '" + pattern +"')\n");
            printUsage(1);
        }
    }

    private static void printUsage(int exitCode) {
        System.out.println("""
                TimestampApplication should be invoked with:
                
                java dk.innotech.user.cli.TimestampApplication <date/time> [--pattern format-string>] [--precision mode]
                
                With the following optional arguments:
                
                --pattern       The pattern to parse the date from (as LocalDate), eg.:
                                   "yyyy-MM-dd HH:mm:ss" (default format when absent)
                                   
                                (Applicable to any java.time.format.DateTimeFormatter pattern)
                                   
                --precision     The precision to output, either:
                                   "milliseconds" (or "ms") (default precision when absent)
                                   "seconds" (or "s")
                """);
        System.exit(exitCode);
    }

    private static Precision getPrecision(String[] args) {
        String raw = getFlagValue(args, PRECISION_FLAG, DEFAULT_PRECISION);
        return Precision.fromString(raw);
    }

    private static String getFlagValue(String[] args, String flag, String defaultValue) {
        String value = defaultValue;

        if (!flag.startsWith("--")) {
            flag = "--" + flag;
        }

        for (var i=0; i<args.length; i++) {
            var each = args[i];
            if (each.equals(flag)) {
                if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
                    value = args[i + 1];
                }
            }
        }

        return value;
    }

    enum Precision {
        MILLISECONDS("milliseconds", "ms"),
        SECONDS("seconds", "s");

        private final String[] patterns;

        Precision(String ... patterns) {
            this.patterns = patterns;
        }

        String getName() {
            return patterns[0];
        }

        String getAbbreviation() {
            return patterns[1];
        }

        public static Precision fromString(String input) {
            for (Precision each : Precision.values()) {
                if (Arrays.asList(each.patterns).contains(input)) {
                    return each;
                }
            }
            return null;
        }
    }
}

