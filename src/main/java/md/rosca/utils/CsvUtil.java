package md.rosca.utils;

import md.rosca.TimeEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {
    public static List<TimeEntry> readCsvData(String filePath) {
        List<TimeEntry> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                TimeEntry entry = new TimeEntry(values[0], values[2], values[3]);
                entries.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
