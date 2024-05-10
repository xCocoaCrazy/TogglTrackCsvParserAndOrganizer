package md.rosca.sheet.processors;

import md.rosca.TimeEntry;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;

import static md.rosca.utils.ExcelUtil.autoSizeColumns;
import static md.rosca.utils.TimeUtil.addTime;
import static md.rosca.utils.TimeUtil.getTotalTime;

public class TotalTimeTrackedSheetProcessor {
    public static void populateTotalTimeTracked(Map<String, List<TimeEntry>> dataByProject, Sheet totalTimeTracked) {
        int rowNum = 0;
        Row headerRow = totalTimeTracked.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Project");
        headerRow.createCell(1).setCellValue("Description");
        headerRow.createCell(2).setCellValue("Duration");
        headerRow.createCell(3).setCellValue("Total Time");

        long totalTimeInSeconds = 0;
        for (List<TimeEntry> entries : dataByProject.values()) {
            for (TimeEntry entry : entries) {
                Row row = totalTimeTracked.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getProject());
                row.createCell(1).setCellValue(entry.getDescription());
                row.createCell(2).setCellValue(entry.getDuration());
                totalTimeInSeconds += addTime(entry.getDuration());
            }
        }

        totalTimeTracked.getRow(1).createCell(3).setCellValue(getTotalTime(totalTimeInSeconds));

        autoSizeColumns(totalTimeTracked, 4);
    }
}
