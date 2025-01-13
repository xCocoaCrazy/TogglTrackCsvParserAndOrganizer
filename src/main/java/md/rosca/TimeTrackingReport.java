package md.rosca;

import md.rosca.processors.DataProcessor;
import md.rosca.utils.CsvUtil;
import md.rosca.utils.ExcelUtil;

import java.util.List;
import java.util.Map;


public class TimeTrackingReport {
    public static String currentFileProcessedName = "";
    public static void run(String csvFilePath, String excelFilePath) {
        // Read CSV Data
        List<TimeEntry> entries = CsvUtil.readCsvData(csvFilePath);

        // Process data
        Map<String, List<TimeEntry>> dataByProject = DataProcessor.processData(entries);

        // Write to Excel
        ExcelUtil.writeExcel(dataByProject, excelFilePath);
    }

    public static void run(List<String> csvFilePathList, String excelFilePath) {
        for (String csvFilePath : csvFilePathList) {
            System.out.println("Started processing " + csvFilePath);
            currentFileProcessedName = csvFilePath;

            // Read CSV Data
            List<TimeEntry> entries = CsvUtil.readCsvData(csvFilePath);

            // Process data
            Map<String, List<TimeEntry>> dataByProject = DataProcessor.processData(entries);

            // Write to Excel
            ExcelUtil.writeExcel(dataByProject, excelFilePath);
            System.out.println("Finished processing " + csvFilePath);
        }
    }
}
