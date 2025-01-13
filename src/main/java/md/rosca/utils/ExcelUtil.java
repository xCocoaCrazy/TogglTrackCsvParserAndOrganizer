package md.rosca.utils;

import md.rosca.TimeEntry;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static md.rosca.sheet.processors.TotalTimeForEachProjectProcessor.populateTotalTimeForEachProjectTracked;
import static md.rosca.sheet.processors.TotalTimeTrackedSheetProcessor.populateTotalTimeTracked;
import static md.rosca.sheet.processors.TotalTimeByProjectProcessor.populateTotalTimeByProjectTracked;

public class ExcelUtil {
    private static CellStyle timeCellStyle;
    private static XSSFSheet previousSheet;

    public static void writeExcel(Map<String, List<TimeEntry>> dataByProject, String destination) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet totalTimeTracked = workbook.createSheet("Total Time Tracked");
            populateTotalTimeTracked(dataByProject, totalTimeTracked);

            XSSFSheet totalTimeByProjectTracked = workbook.createSheet("Total Time By Project");
            populateTotalTimeByProjectTracked(dataByProject, totalTimeByProjectTracked);

            populateTotalTimeForEachProjectTracked(dataByProject, workbook);

            // Write the workbook to the file system
            FileOutputStream outputStream = createFileOutputStream(destination);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileOutputStream createFileOutputStream(String destination) throws IOException {
        // Generate the file name with folder structure
        String filePath = FileNameGeneratorUtil.generateFileName(destination);

        // Get the parent folder of the file
        Path parentFolder = Path.of(filePath).getParent();

        // Create the parent folder if it doesn't exist
        if (parentFolder != null && !Files.exists(parentFolder)) {
            Files.createDirectories(parentFolder);
        }

        // Return the FileOutputStream for the file
        return new FileOutputStream(filePath);
    }


    public static void autoSizeColumns(Sheet totalTimeTracked, int numberOfColumns) {
        // Auto-size columns
        for (int i = 0; i < numberOfColumns; i++) {
            totalTimeTracked.autoSizeColumn(i);
        }
    }

    public static int createHeaderRows(Row headerRow, String... columns) {
        int numberOfColumns = 0;
        for (String column : columns) {
            headerRow.createCell(numberOfColumns++).setCellValue(column);
        }
        return numberOfColumns;
    }

    public static void createDurationCell(XSSFSheet sheet, XSSFRow row, int columnIndex, String entry) {
        XSSFCell durationCell = row.createCell(columnIndex);
        durationCell.setCellValue(ExcelUtil.convertTimeToExcelDate(entry));
        durationCell.setCellStyle(getTimeCellStyle(sheet));
    }

    public static CellStyle getTimeCellStyle(XSSFSheet sheet) {
        boolean firstTimeStartingTheApp = timeCellStyle == null && previousSheet == null;
        boolean processedSheetChanged = timeCellStyle != null && !previousSheet.equals(sheet);
        if (firstTimeStartingTheApp || processedSheetChanged) {
            previousSheet = sheet;
            timeCellStyle = sheet.getWorkbook().createCellStyle();
            // Setting the custom format for time as [h]:mm:ss to display hours and minutes correctly
            CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
            timeCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("[h]:mm:ss"));
        }
        return timeCellStyle;
    }

    // Helper method to convert time strings into Excel date-time values
    public static double convertTimeToExcelDate(String time) {
        String[] hms = time.split(":");
        int hours = Integer.parseInt(hms[0]);
        int minutes = Integer.parseInt(hms[1]);
        int seconds = Integer.parseInt(hms[2]);
        return hours / 24.0 + minutes / 1440.0 + seconds / 86400.0;
    }
}
