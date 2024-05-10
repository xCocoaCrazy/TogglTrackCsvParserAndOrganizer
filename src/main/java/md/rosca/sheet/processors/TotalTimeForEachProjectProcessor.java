package md.rosca.sheet.processors;

import md.rosca.TimeEntry;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

import static md.rosca.utils.ExcelUtil.autoSizeColumns;

public class TotalTimeForEachProjectProcessor {
    public static void populateTotalTimeForEachProjectTracked(Map<String, List<TimeEntry>> dataByProject, Workbook workbook) {
        for (String project : dataByProject.keySet()) {
            Sheet sheet = getSheet(workbook, project);
            List<TimeEntry> filteredListOfProject = dataByProject
                    .get(project)
                    .stream()
                    .filter(entry -> project.equals(entry.getProject()))
                    .toList();

            int rowNum = 0;
            createHeaderRows(sheet.createRow(rowNum++));
            for (TimeEntry entry : filteredListOfProject) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getProject());
                row.createCell(1).setCellValue(entry.getDescription());
                row.createCell(2).setCellValue(entry.getDuration());
            }
            autoSizeColumns(sheet, 3);
        }
    }

    private static Sheet getSheet(Workbook workbook, String project) {
        String sheetName = "Total Time " +
                ("without project".equalsIgnoreCase(project)
                        ? project
                        : "Project " + project);

        return workbook.createSheet(sheetName);
    }

    private static void createHeaderRows(Row headerRow) {
        headerRow.createCell(0).setCellValue("Project");
        headerRow.createCell(1).setCellValue("Description");
        headerRow.createCell(2).setCellValue("Duration");
    }
}
