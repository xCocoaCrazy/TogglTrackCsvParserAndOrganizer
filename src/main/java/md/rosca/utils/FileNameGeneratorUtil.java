package md.rosca.utils;

import md.rosca.TimeTrackingReport;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileNameGeneratorUtil {
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
