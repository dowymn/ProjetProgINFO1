package model;

import util.CSVReader;
import util.SaveGame;

import java.io.Serializable;
import java.util.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Creates the game and handles all the necessary calls to the other classes' methods.
 * @author Eva Guignabodet
 */
public class Game implements Serializable {

	private ArrayList<WorkerCard> availableWorkers;
	private ArrayList<Card> availableBuildings;

	private Player[] players;
	private Player current;
	private int noTour;

	private boolean end; // allows to know if the game will be over when all the players has played
	private boolean stopGame; // allows to stop the game if a player chooses to save and quit



	//-----[ INITIALIZATION

	/**
	 * Initializes the game when there are 4 players.
	 * @param mode the chosen mode of the game
	 * @param PName1 the player1's name
	 * @param PName2 the player2's name
	 * @param PName3 the player3's name
	 * @param PName4 the player4's name
	 */
	public Game(PlayerMode mode, boolean graphical, String PName1, String PName2, String PName3, String PName4) {

		if ( mode == null ) {
			throw new IllegalArgumentException("Error : Game() : mode mustn't be null.");
		}

		initializeCards();

		players = new Player[mode.toString().length()];

		// creation of the players
		if ( mode.toString().length() == 4 ) {
			createPlayers(graphical, PName1, PName2, PName3, PName4, mode);
		} else if ( mode.toString().length() == 3 ) {
			createPlayers(graphical, PName1,PName2,PName3,mode);
		} else if ( mode.toString().length() == 2 ) {
			createPlayers(graphical, PName1,PName2,mode);
		}

		this.end = false;
		this.noTour = 1;

		this.current = players[0];

		shuffleWorkerCards();
		shuffleBuildingCards();

		shufflePlayers();

	}

	/**
	 * Creates the players when they are 4.
	 * @param mode the chosen mode of the game
	 * @param PName1 the player1's name
	 * @param PName2 the player2's name
	 * @param PName3 the player3's name
	 * @param PName4 the player4's name
	 */
	private void createPlayers(boolean graphical, String PName1, String PName2, String PName3, String PName4, PlayerMode mode) {
		String smode = mode.toString();

		if ( smode.charAt(0) == 'H' ) {
			players[0] = new HumanPlayer(this, PName1, graphical);
		} else {
			players[0] = new AutoPlayer(this, PName1, graphical);
		}

		if ( smode.charAt(1) == 'H' ) {
			players[1] = new HumanPlayer(this, PName2, graphical);
		} else {
			players[1] = new AutoPlayer(this, PName2, graphical);
		}

		if ( smode.charAt(2) == 'H' ) {
			players[2] = new HumanPlayer(this, PName3, graphical);
		} else {
			players[2] = new AutoPlayer(this, PName3, graphical);
		}

		if ( smode.charAt(3) == 'H' ) {
			players[3] = new HumanPlayer(this, PName4, graphical);
		} else {
			players[3] = new AutoPlayer(this, PName4, graphical);
		}

	}

	/**
	 * Creates the players when they are 3.
	 * @param mode the chosen mode of the game
	 * @param PName1 the player1's name
	 * @param PName2 the player2's name
	 * @param PName3 the player3's name
	 */
	private void createPlayers(boolean graphical, String PName1, String PName2, String PName3, PlayerMode mode) {
		String smode = mode.toString();

		if ( smode.charAt(0) == 'H' ) {
			players[0] = new HumanPlayer(this, PName1, graphical);
		} else {
			players[0] = new AutoPlayer(this, PName1, graphical);
		}

		if ( smode.charAt(1) == 'H' ) {
			players[1] = new HumanPlayer(this, PName2, graphical);
		} else {
			players[1] = new AutoPlayer(this, PName2, graphical);
		}

		if ( smode.charAt(2) == 'H' ) {
			players[2] = new HumanPlayer(this, PName3, graphical);
		} else {
			players[2] = new AutoPlayer(this, PName3, graphical);
		}

	}

	/**
	 * Creates the players when they are 2.
	 * @param mode the chosen mode of the game
	 * @param PName1 the player1's name
	 * @param PName2 the player2's name
	 */
	private void createPlayers(boolean graphical, String PName1, String PName2, PlayerMode mode) {
		String smode = mode.toString();

		if ( smode.charAt(0) == 'H' ) {
			players[0] = new HumanPlayer(this, PName1, graphical);
		} else {
			players[0] = new AutoPlayer(this, PName1, graphical);
		}

		if ( smode.charAt(1) == 'H' ) {
			players[1] = new HumanPlayer(this, PName2, graphical);
		} else {
			players[1] = new AutoPlayer(this, PName2, graphical);
		}

	}

	/**
	 * Creates the ArrayLists for all the players, and the last one for the available cards.
	 * Initializes all the cards into the availableCards ArrayList.
	 */
	private void initializeCards() {

		availableWorkers = new ArrayList<>();
		availableBuildings = new ArrayList<>();
		ArrayList<String[]> templist;
		int i;

		templist = CSVReader.readBatiments();
		i = 1;
		for ( String[] elem : templist ) {
			availableBuildings.add(new BuildingCard( elem[0], "Batiment"+i+".png", Integer.parseInt(elem[1]), Integer.parseInt(elem[2]), Integer.parseInt(elem[3]), Integer.parseInt(elem[4]), Integer.parseInt(elem[5]), Integer.parseInt(elem[6]) ));
			i++;
		}

		templist = CSVReader.readMachines();
		i = 1;
		for ( String[] elem : templist ) {
			availableBuildings.add(new MachineCard( elem[0], "Machine"+i+"r.png", "Machine"+i+"v.png", Integer.parseInt(elem[5]), Integer.parseInt(elem[1]), Integer.parseInt(elem[2]), Integer.parseInt(elem[3]), Integer.parseInt(elem[4]), Integer.parseInt(elem[6]), Integer.parseInt(elem[7]), Integer.parseInt(elem[9]), Integer.parseInt(elem[9]) ));
			i++;
		}

		templist = CSVReader.readOuvriers();
		i = 1;
		String tmp = templist.get(0)[0];
		for ( String[] elem : templist ) {
			if ( !elem[0].equals(tmp) ) {
				i = 1;
				tmp = elem[0];
			}
			availableWorkers.add(new WorkerCard( elem[0], elem[0]+i+".png", Integer.parseInt(elem[1]), Integer.parseInt(elem[2]), Integer.parseInt(elem[3]), Integer.parseInt(elem[4]), Integer.parseInt(elem[5]) ));
			i++;
		}

	}

	/**
	 * Allows to shuffle the WorkerCard cards contained in a list.
	 */
	private void shuffleWorkerCards() {
		ArrayList<WorkerCard> temp = new ArrayList<>();
		Random rand = new Random();
		int length = availableWorkers.size();
		int rd;
		while ( length > 0 ) {
			rd = rand.nextInt(length);
			temp.add(availableWorkers.get(rd));
			availableWorkers.remove(rd);
			length--;
		}
		availableWorkers = temp;
	}

	/**
	 * Allows to shuffle the BuildingCard cards contained in a list.
	 */
	private void shuffleBuildingCards() {
		//ArrayList<BuildingCard> temp = new ArrayList<>();
		ArrayList<Card> temp = new ArrayList<>();
		Random rand = new Random();
			int length = availableBuildings.size();
		int rd;
		while ( length > 0 ) {
			rd = rand.nextInt(length);
			temp.add(availableBuildings.get(rd));
			availableBuildings.remove(rd);
			length--;
		}
		availableBuildings = temp;
	}

	/**
	 * Allows to shuffle the players list so the order the players are going to play is random.
	 */
	private void shufflePlayers() {
		ArrayList<Player> temp = new ArrayList<>(Arrays.asList(players));

		Random rand = new Random();
		int length = players.length;
		int i = 0;
		int rd;
		while ( length > 0 ) {
			rd = rand.nextInt(length);
			players[i] = temp.get(rd);
			temp.remove(rd);
			length--;
			i++;
		}

	}



	//-----[ GAME METHODS

	/**
	 * Defines the game loop, so what happens during a game turn.
	 */
	public void gameLoop() {

		stopGame = false;

		while ( !end && !stopGame ) {
			System.out.println("\n\n\n\n============================================== Tour n°" + noTour + " ==============================================");

			int i = 0;
			while ( i < players.length && !stopGame ) {
				printAvailableBats();
				//printAvailableMach();
				printAvailableWkrs();

				players[i].play();

				i++;
			}

			noTour++;
		}

		endOfGame(sendWinner());

	}

	/**
	 * Allows to check if if the game is over of not.
	 * If this is the case, changes the end attribute to true.
	 * @return true if the game is over, else false
	 */
	public boolean isPlayerWinner(Player player) {
		if (player.getNbPtsVic() >= 17) {
			end = true;
		}
		return end;
	}

	/**
	 * Allows to get the winner of the game, according to the number of victory points of each player.
	 * @return the player who has won the game
	 */
	public Player sendWinner() {
		int vptemp;
		Player winner = players[0];
		int[][] points = new int[players.length][4];

		for ( int i = 0 ; i < points.length ; i++ ) {
			points[i] = players[i].earnEndOfGame();
		}

		boolean tmp = true;
		// we check if there is an equality with the final victory points
		for ( int i = 1 ; i < players.length ; i++ ) {
			if ( points[i][0] == points[i-1][0] ) {
				tmp = false;
			}
		}
		// if there are not, we look for the winner
		if ( tmp ) {
			vptemp = points[0][0];
			for ( int i = 0 ; i < players.length ; i++ ) {		// Yes a for loop is not the best here
				if ( vptemp < points[i][0] ) {		// but there are only 4 iterations
					vptemp = points[i][0];			// so it doesn't change anything.
					winner = players[i];
				}
			}
		// if there is an equality, we check the victory points without the ecus
		} else {
			tmp = true;
			for ( int i = 1 ; i < players.length ; i++ ) {
				if ( points[i][1] == points[i-1][1] ) {
					tmp = false;
				}
			}
			if ( tmp ) {
				vptemp = points[0][1];
				for ( int i = 0 ; i < players.length ; i++ ) {
					if ( vptemp < points[i][1] ) {
						vptemp = points[i][1];
						winner = players[i];
					}
				}
			// if there is still an equality, we check the ecus of each player
			} else {
				vptemp = points[0][2];
				for ( int i = 0 ; i < players.length ; i++ ) {
					if ( vptemp < points[i][2] ) {
						vptemp = points[i][2];
						winner = players[i];
					}
				}
			}
		}


		return winner;
	}

	/**
	 * Defines what happens when the game is over.
	 */
	private void endOfGame( Player player ) {
		System.out.println("\n\n|| ---- FIN DE LA PARTIE ---- ||\n");

		System.out.println("Le Premier Bâtisseur du Royaume est " + player.getPlayerName() + " !");
		System.out.println("Bravo à tous, et merci d'avoir joué !");
	}

	/**
	 * Allows to change the current player according to the players list order.
	 * Is only used in the graphic mode.
	 */
	public void changeCurrent() {

		boolean found = false;
		int i = 0;
		while ( !found && i < players.length ) {
			if ( players[i] == current ) {
				found = true;
				if ( i == players.length-1 ) {
					current = players[0];
				} else {
					current = players[i+1];
				}
			}
			i++;
		}

	}



	//-----[ GETTERS

	/**
	 * @return the players list
	 */
	public Player[] getPlayers() {
		return this.players;
	}

	/**
	 * @return the end field
	 */
	public boolean getEnd () {
		return this.end;
	}

	/**
	 * @return the available worker cards list
	 */
	public ArrayList<WorkerCard> getAvailableWorkers() {
		return this.availableWorkers;
	}

	/**
	 * @return the available building cards list
	 */
	public ArrayList<Card> getAvailableBuildings() {
		return this.availableBuildings;
	}

	/**
	 * @return the current player
	 */
	public Player getCurrent() {
		return current;
	}



	//-----[ PRINT METHODS

	/**
	 * Prints the available buildings and their caracteristics.
	 */
	private void printAvailableBats() {
		System.out.println("\nBâtiments disponibles                          RESSOURCES NÉCESSAIRES           RESSOURCES PRODUITES");
		System.out.println("--------------------------------------------------------------------------------------------------------------");
		System.out.println("No  NOM                      GAINS   PTS VIC   PIERRE   BOIS   SAVOIR   TUILE   PIERRE   BOIS   SAVOIR   TUILE");
		//                  1   Un instrument de mesure    0        4        2       2        2       2       0       0       0        0

		StringBuilder print = new StringBuilder();
		int max = 5;
		if ( availableBuildings.size() < 5 ) {
			max = availableBuildings.size();
		}

		for ( int i = 0 ; i < max ; i++ ) {
			Card unkcard = getAvailableBuildings().get(i);

			//BUILDINGS
			if ( unkcard.getType() == TypeCard.Building ) {
				BuildingCard card = (BuildingCard) unkcard;
				// number
				print.append(i + 1).append("   ");
				// name
				print.append(card.getCardName()); // 20 + 2
				int ecart = 23 - card.getCardName().length();
				for (int j = 0; j < ecart; j++) { print.append(" "); }
				// the earned ecus
				if (card.getEcusGagnes() > 9) { print.append("   ");
				} else { print.append("    "); }
				print.append(card.getEcusGagnes());
				// pts vic
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(card.getPtsVictoire());
				// pierre
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(card.getPierreNec());
				// bois
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(card.getBoisNec());
				// savoir
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(card.getSavoirNec());
				// tuile
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(card.getTuileNec());

				// Ressources produites
				for (int j = 0 ; j < 7 ; j++) { print.append(" ");}
				print.append(0);
				for (int j = 0 ; j < 7 ; j++) { print.append(" ");}
				print.append(0);
				for (int j = 0 ; j < 7 ; j++) { print.append(" ");}
				print.append(0);
				for (int j = 0 ; j < 8 ; j++) { print.append(" ");}
				print.append(0);

				System.out.println(print);
				print = new StringBuilder();

			// MACHINES
			} else if ( unkcard.getType() == TypeCard.Machine ) {
				MachineCard card = (MachineCard) unkcard;
				// number
				print.append(i + 1).append("   ");
				// name
				print.append(card.getCardName()); // 23 + 2
				int ecart = 27 - card.getCardName().length();
				for ( int j = 0 ; j<ecart ; j++) { print.append(" ");}
				print.append(0);
				// pts vic
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(card.getPtsVictoire());
				// RESSOURCES NÉCESSAIRES
				// pierre
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(card.getPierreNec());
				// bois
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(card.getBoisNec());
				// savoir
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(card.getSavoirNec());
				// tuile
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(card.getTuileNec());

				// RESSOURCES PRODUITES
				// pierre
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(card.getPierreProd());
				// bois
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(card.getBoisProd());
				// savoir
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(card.getSavoirProd());
				// tuile
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(card.getTuileProd());


				System.out.println(print);
				print = new StringBuilder();
			}
		}
	}

	/**
	 * Prints the available workers and their caracteristics.
	 */
	private void printAvailableWkrs() {
		System.out.println("\nOuvriers disponibles");
		System.out.println("-----------------------------------------------------");
		System.out.println("No  NOM         COÛT   PIERRE   BOIS   SAVOIR   TUILE");
		//                  1   Compagnon    6       4        2       2        2

		StringBuilder print = new StringBuilder();
		int max = 5;
		if ( availableWorkers.size() < 5 ) {
			max = availableWorkers.size();
		}
		for (int i = 0; i < max; i++) {
			WorkerCard card = availableWorkers.get(i);
			// number
			print.append(i + 1).append("   ");
			// name
			print.append(card.getCardName()); // 9 + 2
			int ecart = 11 - card.getCardName().length();
			for (int j = 0; j < ecart; j++) { print.append(" "); }
			// coût
			for (int j = 0; j < 2; j++) { print.append(" "); }
			print.append(card.getEcusAPayer());

			// pierre
			for (int j = 0; j < 7; j++) { print.append(" "); }
			print.append(card.getPierreProd());
			// bois
			for (int j = 0; j < 7; j++) { print.append(" "); }
			print.append(card.getBoisProd());
			// savoir
			for (int j = 0; j < 7; j++) { print.append(" "); }
			print.append(card.getSavoirProd());
			// tuile
			for (int j = 0; j < 8; j++) { print.append(" "); }
			print.append(card.getTuileProd());


			System.out.println(print);
			print = new StringBuilder();
		}
	}



	//-----[ SAVE METHOD

	/**
	 * Allows to save the current game by calling the saveGame() method from the SaveGame class.
	 * Also stops the current game.
	 * @param gameName the name of the save
	 */
	public void saveTheGame(String gameName) {
		stopGame = true;
		SaveGame.saveGame(this, gameName );
	}



}