package md.rosca.processors;

import md.rosca.TimeTrackingReport;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "TimeTrackingParser", mixinStandardHelpOptions = true, description = "Parses a list of files and a destination folder")
public class CLIArgumentsProcessor implements Runnable {
    @CommandLine.Option(names = {"-f", "--files"}, required = true, split = ",",
            description = "Comma-separated list of files to process.")
    private List<String> files;

    @CommandLine.Option(names = {"-d", "--destination"}, required = true,
            description = "Destination folder.")
    private String destination;

    @Override
    public void run() {
        try {
            // Call your existing method with the parsed arguments
            TimeTrackingReport.run(files, destination);
        } catch (Exception e) {
            System.err.println("Error while running TimeTrackingReport: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
