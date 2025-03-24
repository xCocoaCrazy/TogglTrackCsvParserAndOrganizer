package md.rosca.processors;

import md.rosca.TimeEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProcessor {
    /**
     * Process data for each project
     * @param entries list of all the rows from csv.
     * @return Map where key is Project name and List
     *         with all the entries for that specific project
     */
    public static Map<String, List<TimeEntry>> processData(List<TimeEntry> entries) {
        Map<String, List<TimeEntry>> dataByProject = new HashMap<>();
        for (TimeEntry entry : entries) {
            dataByProject.putIfAbsent(entry.getProject(), new ArrayList<>());
            dataByProject.get(entry.getProject()).add(entry);
        }
        return dataByProject;
    }
}
