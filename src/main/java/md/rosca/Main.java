package md.rosca;

import md.rosca.processors.CLIArgumentsProcessor;
import org.apache.log4j.PropertyConfigurator;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
//        TimeTrackingReport.run(args[0], args[1]);
        int exitCode = new CommandLine(new CLIArgumentsProcessor()).execute(args);
        System.exit(exitCode);
    }
}