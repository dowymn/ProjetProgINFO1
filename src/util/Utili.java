package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Allows to check if a String chain is parsable into something else.
 * Contains also a few other useful things.
 * @author Eva Guignabodet
 */
public class Utili {

    // STRING CHECKS

    /**
     * Checks if the String chain is parsable as an Integer or not.
     * @param s the String chain
     * @return true if it can be parsed into an Integer, else false
     */
    public static boolean parseInteger( String s ) {
        boolean ok = true;
        try {
            Integer.parseInt(s);
        } catch ( NumberFormatException ex ) {
            ok = false;
        }
        return ok;
    }

    /**
     * Allows to know if the String chain that is supposed to contain Integers is between the two bounds given as parameters.
     * Also checks if the String chain can be parsed as an Integer or not.
     * @param s the String chain
     * @param inf the inferior bound (included!)
     * @param sup the superior bound (included!)
     * @return true if the String chain is into the bounds, else false
     */
    public static boolean intIsInto( String s, int inf, int sup ) {
        boolean ok = false;
        if ( parseInteger(s) ) {
            if ( Integer.parseInt(s) >= inf && Integer.parseInt(s) <= sup ) {
                ok = true;
            }
        }
        return ok;
    }

    /**
     * Allows to know if the String chain that is supposed to contain Integers is a specified number.
     * Also checks if the String chain can be parsed as an Integer or not.
     * @param s the String chain
     * @param nb the number the String is supposed to be
     * @return true if the String chain is the number, else false
     */
    public static boolean intIs( String s, int nb ) {
        boolean ok = false;
        if ( parseInteger(s) ) {
            if ( Integer.parseInt(s) == nb ) {
                ok = true;
            }
        }
        return ok;
    }


    // OTHERS

    /**
     * Allows to get all the files from a directory.
     */
    public static File[] readDirectory (String path) {
        File dir = new File(path);
        File[] listFiles = dir.listFiles();
        if ( listFiles != null ) {
            sortFiles(listFiles);
        }

        return listFiles;
    }

    /**
     * Sorts the files with the Bubble sort, according to the file's names.
     */
    private static void sortFiles (File[] listFiles) {
        for ( int i = listFiles.length ; i > 0 ; i-- ){
            for (int j = 0 ; j < listFiles.length-1 ; j++) {
                if (listFiles[j+1].getName().compareTo(listFiles[j].getName()) < 0) {
                    File temp = listFiles[j];
                    listFiles[j] = listFiles[j+1];
                    listFiles[j+1] = temp;
                }
            }
        }
    }

    /**
     * Allows to delete a file in the saves directory.
     * @param fileName the name of the save
     */
    public static void deleteSave(String fileName) {
        Path path = FileSystems.getDefault().getPath(Batview.PATHSAVES + fileName + Batview.EXT);
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.err.println("Error : Utili : deleteSave() : " + e.getMessage());
        }

    }

    /**
     * Allows to remove the extention of the file name
     * @param fileName the name of the file
     * @return the file name without the extention
     */
    public static String removeExt(String fileName) {
        return fileName.substring(0, fileName.indexOf('.'));
    }

}
