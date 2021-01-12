package dk.innotech.cli;

import dk.innotech.cli.tools.BCryptEncoderCommand;
import dk.innotech.cli.tools.TimestampCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "tools",
        mixinStandardHelpOptions = true,
        subcommands = {
                BCryptEncoderCommand.class,
                TimestampCommand.class
        })
public class CliCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        System.err.println("Please invoke a subcommand\n");
        System.out.println(new CommandLine(this).getUsageMessage());
        return -1;
    }
}
