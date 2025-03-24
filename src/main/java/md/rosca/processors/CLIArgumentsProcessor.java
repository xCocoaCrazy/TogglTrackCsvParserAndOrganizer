package md.rosca.processors;

import md.rosca.TimeTrackingReport;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;

/**
 * Parses the arguments from CLI
 */
@Command(name = "TimeTrackingParser", mixinStandardHelpOptions = true, description = "Parses a list of files and a destination folder")
public class CLIArgumentsProcessor implements Runnable {
    /**
     * Put all the files that you want to process.
     * Could either put one file in "", or multiple files but all of them should be under the "".
     * Example:
     * -f "/path/to/file/csvFile.csv" for one file
     * -f "/path/to/file/csvFile1.csv,/path/to/file/csvFile2.csv" for multiple files
     */
    @Option(names = {"-f", "--files"}, required = true, split = ",",
            description = "Comma-separated list of files to process.")
    private List<String> files;

    /**
     * Put the destination where the excels should be saved.
     * Could either put a directory or a file name if you have single file.
     * If you give multiple files but set destination as a file the last
     * file that is processed is the one that will be saved as file.
     * Example:
     * -d "/path/to/save/finalNameForCsv.excel" for one file
     * -d "/path/to/save/FolderForSavingExcels/" for multiple files
     */
    @Option(names = {"-d", "--destination"}, required = true,
            description = "Destination folder.")
    private String destination;

    @Override
    public void run() {
        try {
            TimeTrackingReport.run(files, destination);
        } catch (Exception e) {
            System.err.println("Error while running TimeTrackingReport: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
