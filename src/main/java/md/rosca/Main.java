package md.rosca;

import org.apache.log4j.PropertyConfigurator;

public class Main {
    public static void main(String[] args) {
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        TimeTrackingReport.run(args[0], args[1]);
    }
}