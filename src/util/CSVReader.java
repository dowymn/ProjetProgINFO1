package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Allows to read the CSV files that contains the cards information.
 * @author Eva Guignabodet
 */
public abstract class CSVReader {

    private static final String PATH = Batview.PATHCARDS;

    /**
     * Reads the CSV file and extracts the Cards' information.
     * @return an ArrayList which contains String lists with the information about the Cards
     */
    public static ArrayList<String[]> readFile(String fileName) {

        ArrayList<String[]> list = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new FileReader(PATH + fileName));
            String line;
            String splitter = ";";

            in.readLine(); // we don't want the first line

            while ((line = in.readLine()) != null) {

                // we make sur all the tabs have the same size
                if ( line.charAt(line.length()-1)==';' ) {
                    line += '0';
                }

                String[] lineList = line.split(splitter);

                for ( int i = 0 ; i < lineList.length ; i++ ) {
                    if ( lineList[i].equals("") ) {
                        lineList[i] = "0";
                    }
                }

                list.add(lineList);
            }
        } catch ( IOException e ) {
            System.out.println("Error : CSVReader : readFile() : " + e.getMessage());
        }

        return list;
    }

    /**
     * Calls the readFile() method with the path to the Building file.
     * @return an ArrayList which contains String lists with the information about the Buildings
     */
    public static ArrayList<String[]> readBatiments() {
        return readFile("cartes batisseurs batiments.csv");
    }

    /**
     * Calls the readFile() method with the path to the Machinies file.
     * @return an ArrayList which contains String lists with the information about the Machines
     */
    public static ArrayList<String[]> readMachines() {
        return readFile("cartes batisseurs machines.csv");
    }

    /**
     * Calls the readFile() method with the path to the Workers file.
     * @return an ArrayList which contains String lists with the information about the Workers
     */
    public static ArrayList<String[]> readOuvriers() {
        return readFile("cartes batisseurs ouvriers.csv");
    }


}
