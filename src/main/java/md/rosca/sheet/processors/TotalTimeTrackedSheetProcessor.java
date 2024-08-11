package md.rosca.sheet.processors;

import md.rosca.TimeEntry;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;
import java.util.Map;

import static md.rosca.utils.ChartUtil.createPieChart;
import static md.rosca.utils.ExcelUtil.*;
import static md.rosca.utils.TimeUtil.addTime;
import static md.rosca.utils.TimeUtil.getTotalTime;

public class TotalTimeTrackedSheetProcessor {
    public static void populateTotalTimeTracked(Map<String, List<TimeEntry>> dataByProject, XSSFSheet totalTimeTracked) {
        int numberOfRows = 0;

        Row headerRow = totalTimeTracked.createRow(numberOfRows++);
        int numberOfColumns = createHeaderRows(headerRow, "Project", "Description", "Duration", "Total Time");

        long totalTimeInSeconds = 0;
        for (List<TimeEntry> entries : dataByProject.values()) {
            for (TimeEntry entry : entries) {
                XSSFRow row = totalTimeTracked.createRow(numberOfRows++);
                row.createCell(0).setCellValue(entry.getProject());
                row.createCell(1).setCellValue(entry.getDescription());
                createDurationCell(totalTimeTracked, row, 2, entry.getDuration());
                totalTimeInSeconds += addTime(entry.getDuration());
            }
        }

        createDurationCell(totalTimeTracked, totalTimeTracked.getRow(1), 3, getTotalTime(totalTimeInSeconds));

        createPieChart(totalTimeTracked, numberOfRows, numberOfColumns, "Time spent on each activity", 1, 2);
        autoSizeColumns(totalTimeTracked, numberOfColumns);
    }
}
