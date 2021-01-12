package dk.innotech.cli;

import picocli.CommandLine;

public class Application {
    public static void main(String[] args) {
        var commandLine = new CommandLine(new CliCommand()).setCaseInsensitiveEnumValuesAllowed(true);
        var exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
