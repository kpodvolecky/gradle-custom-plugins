package com.github.kpodvolecky.less.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static Path getDestinationPath(Path baseDirectory, Path sourceFile, Path destinationDirectory) {
        Path relativeDestPath = baseDirectory.relativize(sourceFile);
        Path destination = Paths.get(destinationDirectory.toString(), relativeDestPath.toString());
        String retVal = destination.toString().replaceAll("\\.less$", ".css");
        return Paths.get(retVal);
    }

    public static String getDestinationPath(String baseDirectory, String sourceFile, String destinationDirectory) {
        return getDestinationPath(Paths.get(baseDirectory), Paths.get(sourceFile), Paths.get(destinationDirectory)).toString();
    }

}
