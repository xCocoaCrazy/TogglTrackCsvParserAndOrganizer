package md.rosca.sheet.processors;

import md.rosca.TimeEntry;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

import static md.rosca.utils.ChartUtil.createPieChart;
import static md.rosca.utils.ExcelUtil.*;

public class TotalTimeForEachProjectProcessor {
    /**
     * Populates the sheet of the excel with all the data available
     * @param dataByProject Map where the key is the name of the project and all it's entries
     * @param workbook sheet for project
     */
    public static void populateTotalTimeForEachProjectTracked(Map<String, List<TimeEntry>> dataByProject, XSSFWorkbook workbook) {
        for (String project : dataByProject.keySet()) {
            XSSFSheet sheet = getSheet(workbook, project);
            List<TimeEntry> filteredListOfProject = dataByProject
                    .get(project)
                    .stream()
                    .filter(entry -> project.equals(entry.getProject()))
                    .toList();

            int numberOfRows = 0;
            int numberOfColumns = createHeaderRows(sheet.createRow(numberOfRows++), "Project", "Description", "Duration");
            for (TimeEntry entry : filteredListOfProject) {
                XSSFRow row = sheet.createRow(numberOfRows++);
                row.createCell(0).setCellValue(entry.getProject());
                row.createCell(1).setCellValue(entry.getDescription());
                createDurationCell(sheet, row, 2, entry.getDuration());
            }
            createPieChart(sheet, numberOfRows, numberOfColumns, "Time spent " + project, 1, 2);
            autoSizeColumns(sheet, numberOfColumns);
        }
    }

    /**
     * Get name of the sheet
     * @param workbook current workbook
     * @param project name of the project
     * @return sheet with set name
     */
    private static XSSFSheet getSheet(XSSFWorkbook workbook, String project) {
        String sheetName = "Total Time " +
                ("without project".equalsIgnoreCase(project)
                        ? project
                        : "Project " + project);

        return workbook.createSheet(sheetName);
    }
}
