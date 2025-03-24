package md.rosca.utils;

import md.rosca.TimeTrackingReport;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Util that helps generate the name for the Excel file
 */
public class FileNameGeneratorUtil {
    /**
     * Generates the file name.
     * @param destination destination as file or folder
     * @return name of the file with extension
     */
    public static String generateFileName(String destination) {
        String pathType = inferPathType(destination);
        if (pathType.equals("folder")) {
            StringBuilder builder = new StringBuilder();
            String fileName = replaceExtension(Path
                    .of(TimeTrackingReport.currentFileProcessedName)
                    .getFileName()
                    .toString(), ".xlsx");
            if (destination.endsWith("/")) {
                return builder.append(destination).append(fileName).toString();
            }
            return builder.append(destination).append("/").append(fileName).toString();
        } else if (pathType.equals("file")) {
            return replaceExtension(destination, ".xlsx");
        }
        return pathType;
    }

    /**
     * Method to understand if the destination path is folder or file
     * @param pathString path
     * @return "file" if path points to a file, otherwise "folder"
     */
    public static String inferPathType(String pathString) {
        Path path = Path.of(pathString);

        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                return "folder";
            } else if (Files.isRegularFile(path)) {
                return "file";
            }
        } else {
            // Infer from the name
            if (pathString.contains(".") && !pathString.endsWith(".")) {
                return "file";
            } else {
                return "folder";
            }
        }
        return "invalid";
    }

    /**
     * Replaces .csv with .xlsx
     * @param fileName name of the file
     * @param newExtension new extension that you want to use
     * @return name of the file
     */
    private static String replaceExtension(String fileName, String newExtension) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            // No extension found, just add the new one
            return fileName + newExtension;
        }
        // Replace the existing extension
        return fileName.substring(0, dotIndex) + newExtension;
    }
}
