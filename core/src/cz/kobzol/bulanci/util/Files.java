package cz.kobzol.bulanci.util;

import java.io.File;
import java.util.Scanner;

/**
 * Utility class for files.
 */
public class Files {
    public static String readFile(String path) {
        try {
            return new Scanner( new File(path), "UTF-8" ).useDelimiter("\\A").next();
        }
        catch (Exception e) {
            return "";
        }
    }
}
