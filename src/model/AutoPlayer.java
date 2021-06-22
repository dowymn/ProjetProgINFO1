package model;

import util.Utili;

import java.util.Random;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the Auto player
 * @author Eva Guignabodet
 */
public class AutoPlayer extends Player {

	private final Random random;

	/**
	 * Initializes the player.
	 * @param game the game the player is in
	 * @param playerName the player's name
	 */
	public AutoPlayer(Game game, String playerName, boolean graphical) {
		super(game, playerName, graphical, true);

		this.random = new Random();
	}

	/**
	 * Allows the AutoPlayer to play his turn.
	 */
	public void play() {
		if ( !graphical ) {
			System.out.println("\n\n---------------------------------------------------------------------------------");
			System.out.println(getPlayerName() + " : " + getNbPtsVic() + " pts vic, " + getNbEcus() + " ecus (autoplayer)");
			System.out.println("---------------------------------------------------------------------------------");
		}

		boolean played = false;
		constructionTurn.clear();
		int asw;


		while ( !played ) {

			if ( !graphical ) {
				System.out.println("Actions restantes : " + getActions());
			}
			asw = random.nextInt(5)+1;

			if ( asw == 1 ) {
				asw = random.nextInt(5)+1;
				startBuilding(game.getAvailableBuildings().get(asw));
				if ( !graphical ) {
					System.out.println("Ouverture d'un chantier sur le bâtiment n°" + asw + ".");
				}
			}

			else if ( asw == 2 ) {
				asw = random.nextInt(5)+1;
				hireWorker(game.getAvailableWorkers().get(asw));
				if ( !graphical ) {
					System.out.println("Recrutement de l'ouvrier n°" + asw + ".");
				}
			}

			else if ( asw == 3 ) {
				if ( getFreeWM().size()>0 && getChantiers().size() > 0 ) {
					// choix de l'ouvrier
					asw = random.nextInt(getFreeWM().size()) + 1;
					Card worker = getFreeWM().get(asw-1);

					// choix du chantier
					if (getChantiers().size() > 0) {
						asw = random.nextInt(getChantiers().size()) + 1;
						Card building = getChantiers().get(asw - 1);
						sendWorkerToWork(worker, building);
						if ( !graphical ) {
							System.out.println("Envoi d'un " + worker.getCardName() + " travailler sur " + building.getCardName() + ".");
						}
					}
				}
			}

			else if ( asw == 4 ) {
				asw = random.nextInt(3);

				if ( asw == 0 ) {
					takeEcus(1);
					if ( !graphical ) {
						System.out.println("Prise de 1 écus.");
					}
				} else if ( asw == 1 ) {
					takeEcus(3);
					if ( !graphical ) {
						System.out.println("Prise de 3 écus.");
					}
				} else {
					takeEcus(6);
					if ( !graphical ) {
						System.out.println("Prise de 6 écus.");
					}
				}
			}

			else {
				played = true;
				if ( !graphical ) {
					System.out.println("Fin du tour.");
				}
			}

			if ( getActions() == 0 && !played ) {
				played = true;
				if ( !graphical ) {
					System.out.println("Fin du tour.");
				}
			}

		} // end of the while(!played){...}

		if ( !graphical ) {
			this.resetActions();
			game.isPlayerWinner(this);
		}
	}

}