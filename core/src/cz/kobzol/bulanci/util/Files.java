package cz.kobzol.bulanci.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    /**
     * Gets all files from the given path with the given extensions.
     * @param path path to folder
     * @param extensions extensions (comma delimited)
     * @return list of files with the given extension from the given folder
     */
    public static List<String> getFilesWithExtension(String path, final String extensions) {
        File directory = new File(path).getAbsoluteFile();
        ArrayList<String> files = new ArrayList<String>();
        final String[] extensionList = extensions.split(",");

        if (directory.isDirectory()) {
            files.addAll(Arrays.asList(directory.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    for (String extension : extensionList) {
                        if (name.endsWith(extension)) {
                            return true;
                        }
                    }

                    return false;
                }
            })));
        }

        return files;
    }
}
