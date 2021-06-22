package util;

import model.Game;
import model.Player;

import java.io.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * @author Eva Guignabodet
 */
public class SaveGame {

    private static final String PATH = Batview.PATHSAVES;
    private static final String EXT = Batview.EXT;

    /**
     * Allows to save a pending game.
     * The user can choose the name of the saved game to find it easily later.
     * @param game the game
     * @param gameName the chosen fileName
     */
    public static void saveGame ( Game game, String gameName ) {

        try {
            ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(PATH + gameName + EXT));
            out.writeObject(game);

            out.close();
        } catch (IOException ex) {
            System.out.println("Error : SaveGame : saveGame() : " + ex.getMessage());
        }
    }

    /**
     * Allows to load an unfinished game and start playing where it stopped.
     * Is used in the textual version of the game.
     * @param fileName the name of the file
     */
    public static void loadGame ( String fileName ) {

        try {
            ObjectInputStream in = new ObjectInputStream( new FileInputStream(PATH + fileName + EXT));

            Game game = (Game) in.readObject();
            game.gameLoop();

            // the file is deleted once the game is over or has been saved again
            Utili.deleteSave(fileName);


        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error : SaveGame : saveGame() : " + ex.getMessage());
        }
    }

    /**
     * Allows to load an unfinished game and get the instance where it has been stopped.
     * Is used in the graphical version of the game.
     * @param fileName the name of the file
     */
    public static Game getSavedGame ( String fileName ) {
        Game game = null;
        try {
            ObjectInputStream in = new ObjectInputStream( new FileInputStream(PATH + fileName + EXT));
            game = (Game) in.readObject();
            // the file is deleted once the game is over or has been saved again
            Utili.deleteSave(fileName);


        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error : SaveGame : saveGame() : " + ex.getMessage());
        }
        return game;
    }

}
