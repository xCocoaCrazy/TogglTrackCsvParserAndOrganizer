package md.rosca.utils;

import md.rosca.TimeEntry;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static md.rosca.sheet.processors.TotalTimeForEachProjectProcessor.populateTotalTimeForEachProjectTracked;
import static md.rosca.sheet.processors.TotalTimeTrackedSheetProcessor.populateTotalTimeTracked;
import static md.rosca.sheet.processors.TotalTimeByProjectProcessor.populateTotalTimeByProjectTracked;

public class ExcelUtil {
    public static void writeExcel(Map<String, List<TimeEntry>> dataByProject, String excelFilePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet totalTimeTracked = workbook.createSheet("Total Time Tracked");
            populateTotalTimeTracked(dataByProject, totalTimeTracked);

            Sheet totalTimeByProjectTracked = workbook.createSheet("Total Time By Project");
            populateTotalTimeByProjectTracked(dataByProject, totalTimeByProjectTracked);

            populateTotalTimeForEachProjectTracked(dataByProject, workbook);

            // Write the workbook to the file system
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void autoSizeColumns(Sheet totalTimeTracked, int numberOfColumns) {
        // Auto-size columns
        for (int i = 0; i < numberOfColumns; i++) {
            totalTimeTracked.autoSizeColumn(i);
        }
    }
}
