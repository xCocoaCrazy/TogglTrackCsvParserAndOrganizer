package md.rosca.sheet.processors;

import md.rosca.TimeEntry;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static md.rosca.utils.ExcelUtil.autoSizeColumns;
import static md.rosca.utils.TimeUtil.addTime;
import static md.rosca.utils.TimeUtil.getTotalTime;

public class TotalTimeByProjectProcessor {
    public static void populateTotalTimeByProjectTracked(Map<String, List<TimeEntry>> dataByProject, Sheet totalTimeByProjectTracked) {
        int rowNum = 0;
        Row headerRow = totalTimeByProjectTracked.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Project");
        headerRow.createCell(1).setCellValue("Duration");

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
            Row row = totalTimeByProjectTracked.createRow(rowNum++);
            row.createCell(0).setCellValue(project);
            row.createCell(1).setCellValue(getTotalTime(totalTimeByEachProject.get(project)));
        }

        autoSizeColumns(totalTimeByProjectTracked, 4);
    }
}
