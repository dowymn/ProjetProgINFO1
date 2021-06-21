package model;

import util.Batview;
import util.SaveGame;
import util.Utili;
import view.LesBatisseurs;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Class that handles the game.
 * @author Eva Guignabodet
 */
public class TheBuildersGame {

	private Game gamePlay;
	private PlayerMode mode;
	private String gameRules;
	private static final Scanner scan = new Scanner(System.in);


	//-----[ INITIALIZATION

	/**
	 * Initializes the game and launches it.
	 */
	public TheBuildersGame() {

		initialization();
		launchAll();

	}

	/**
	 * Initializes the needed components.
	 */
	private void initialization() {
		computeRules();
	}


	//-----[ GAME

	/**
	 * Launches the game.
	 * The user has to choose if he wants to play the game with the textual or graphical mode.
	 */
	public void launchAll() {
		System.out.println("Lancement du jeu Les Bâtisseurs : Moyen-Âge.\n");
		System.out.println("Souhaitez-vous jouer en mode texte ou graphique ?");
		System.out.println("1: TEXTE");
		System.out.println("2:GRAPHIQUE");
		String line;

		do {
			line = scan.nextLine();

			if ( Utili.intIs(line,1) ) {
				startOfTextGame();
				menuTxt();

			} else if ( Utili.intIs(line,2) ) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new LesBatisseurs().setVisible(true);
					}
				});
			}
		} while ( !Utili.intIsInto(line,1,2) );
	}

	/**
	 * Prints the information about the game to begin in the text mode.
	 */
	private void startOfTextGame() {
		try {
			Scanner in = new Scanner(new FileReader(Batview.PATHINFOS+"title.bati"));
			while ( in.hasNext() ) {
				System.out.println(in.nextLine());
			}
		} catch (IOException e) {
			System.out.println("Error : Game : startOfTextGame() : " + e.getMessage());
		}

	}

	/**
	 * Menu of the game.
	 * Allows the user to choose between starting a new game, loading a game or reading the rules.
	 */
	private void menuTxt() {

		System.out.println("\n\n|| ------ MENU DU JEU ------ ||\n");
		String line;

		System.out.println("1: Nouvelle partie");
		System.out.println("2: Reprendre une partie");
		System.out.println("3: Règles du jeu");
		System.out.println("4: Quitter le jeu");

		do {
			line = scan.nextLine();

			if ( Utili.intIs(line,1) ) {
				nouvellePartieTxt();
				menuTxt();
			} else if ( Utili.intIs(line,2) ) {
				reprendrePartieTxt();
				menuTxt();
			} else if ( Utili.intIs(line,3) ) {
				printRegles();
				menuTxt();
			} else if ( Utili.intIs(line,4) ) {
				quitterJeu();
			}

		} while ( !Utili.intIsInto(line,1,4) );

	}

	/**
	 * Configures the game according to the content of the configuration file given as a parameter.
	 */
	private void nouvellePartieTxt() {

		System.out.println("\n\n|| ---- NOUVELLE PARTIE ---- ||\n");
		String line;

		// choix du mode
		System.out.println("Choisissez un mode de jeu :");
		System.out.println("2 joueurs ->   1: HA    2: HH");
		System.out.println("3 joueurs ->   3: HAA   4: HHA   5: HHH");
		System.out.println("4 joueurs ->   6: HAAA  7: HHAA  8: HHHA 9: HHHH");

		do {
			line = scan.nextLine();

			if ( Utili.intIs(line,1) ) { mode = PlayerMode.HA; }
			else if ( Utili.intIs(line,2) ) { mode = PlayerMode.HH; }
			else if ( Utili.intIs(line,3) ) { mode = PlayerMode.HAA; }
			else if ( Utili.intIs(line,4) ) { mode = PlayerMode.HHA; }
			else if ( Utili.intIs(line,5) ) { mode = PlayerMode.HHH; }
			else if ( Utili.intIs(line,6) ) { mode = PlayerMode.HAAA; }
			else if ( Utili.intIs(line,7) ) { mode = PlayerMode.HHAA; }
			else if ( Utili.intIs(line,8) ) { mode = PlayerMode.HHHA; }
			else if ( Utili.intIs(line,9) ) { mode = PlayerMode.HHHH; }

		} while ( mode == null );

		// choix du nom des joueurs et instanciation de game
		System.out.print("\nNom du joueur 1 : ");
		String p1 = scan.nextLine();
		System.out.print("Nom du joueur 2 : ");
		String p2 = scan.nextLine();
		if ( mode.toString().length() == 2 ) {
			gamePlay = new Game(mode, p1, p2, null, null);
		}

		if ( mode.toString().length() == 3 ) {
			System.out.print("Nom du joueur 3 : ");
			String p3 = scan.nextLine();
			gamePlay = new Game(mode,p1,p2,p3,null);

		} else if ( mode.toString().length() == 4 ) {
			System.out.print("Nom du joueur 3 : ");
			String p3 = scan.nextLine();
			System.out.print("Nom du joueur 4 : ");
			String p4 = scan.nextLine();
			gamePlay = new Game(mode,p1,p2,p3,p4);
		}

		System.out.println("\n\n\n");

		gamePlay.gameLoop();

	}

	/**
	 * Prints the saved games and allows the player to launch one of them.
	 */
	private void reprendrePartieTxt() {
		System.out.println("\n\n|| -- REPRENDRE UNE PARTIE -- ||\n");

		File[] fileList = Utili.readDirectory(Batview.PATHSAVES);
		if ( fileList.length != 0 ) {
			System.out.println("Quelle partie veux-tu reprendre ?");
			int i = 1;
			for (File file : fileList) {
				System.out.println(i + ": " + Utili.removeExt(file.getName()));
				i++;
			}

			String ans;
			boolean done = false;
			do {
				ans = scan.nextLine();
				int k = 0;
				while (k < fileList.length && !done) {
					if (Utili.intIs(ans, k + 1)) {
						done = true;
						String name = fileList[k].getName();
						name = Utili.removeExt(name);
						SaveGame.loadGame(name);
					}
					k++;
				}
			} while (!Utili.intIsInto(ans, 1, fileList.length + 1));

		} else {
			System.out.println("Il n'y a pas de parties sauvegardées.");
		}

	}

	/**
	 * Allows to get the rules written into a file.
	 */
	public String getRules() {
		return gameRules;
	}

	/**
	 * Prints a message when the game is closed.
	 */
	public void quitterJeu() {
		System.out.println("Merci d'avoir joué !");
	}

	/**
	 * Computes the rules from a file where they are written.
	 */
	private void computeRules() {
		try {
			gameRules = "";
			Scanner rules = new Scanner(new FileReader(Batview.PATHINFOS+"regles.bati"));
			while ( rules.hasNext() ) {
				gameRules += rules.nextLine() + "\n";
			}
		} catch (IOException e) {
			System.out.println("Error : TheBuildersGame : computeRules() : " + e.getMessage());
			this.gameRules = "here are the rules";
		}
	}

	/**
	 * Prints the rules of the game.
	 */
	private void printRegles() {
		System.out.println("\n\n|| ----- RÈGLES DU JEU ----- ||\n");
		System.out.println(gameRules);
	}

}