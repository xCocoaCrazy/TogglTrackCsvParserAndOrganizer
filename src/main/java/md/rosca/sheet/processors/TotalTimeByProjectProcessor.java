package md.rosca.sheet.processors;

import md.rosca.TimeEntry;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static md.rosca.utils.ChartUtil.createPieChart;
import static md.rosca.utils.ExcelUtil.*;
import static md.rosca.utils.TimeUtil.addTime;
import static md.rosca.utils.TimeUtil.getTotalTime;

/**
 * Processes data for each of the project
 */
public class TotalTimeByProjectProcessor {
    /**
     * Populates the sheet of the excel with all the data available
     * @param dataByProject Map where the key is the name of the project and all it's entries
     * @param totalTimeByProjectTracked sheet for each project
     */
    public static void populateTotalTimeByProjectTracked(Map<String, List<TimeEntry>> dataByProject, XSSFSheet totalTimeByProjectTracked) {
        int numberOfRows = 0;
        Row headerRow = totalTimeByProjectTracked.createRow(numberOfRows++);
        int numberOfColumns = createHeaderRows(headerRow, "Project", "Duration");

        Map<String, Long> totalTimeByEachProject = new HashMap<>();
        for (List<TimeEntry> entries : dataByProject.values()) {
            for (TimeEntry entry : entries) {
                String project = entry.getProject();
                String duration = entry.getDuration();
                if (!totalTimeByEachProject.containsKey(project)) {
                    totalTimeByEachProject.put(project, addTime(duration));
                } else {
                    totalTimeByEachProject.put(project, totalTimeByEachProject.get(project) + addTime(duration));
                }
            }
        }

        for (String project : totalTimeByEachProject.keySet()) {
            XSSFRow row = totalTimeByProjectTracked.createRow(numberOfRows++);
            row.createCell(0).setCellValue(project);
            createDurationCell(totalTimeByProjectTracked, row, 1, getTotalTime(totalTimeByEachProject.get(project)));
        }
        createPieChart(totalTimeByProjectTracked, numberOfRows, numberOfColumns, "Time spent by project", 0, 1);
        autoSizeColumns(totalTimeByProjectTracked, numberOfColumns);
    }
}
