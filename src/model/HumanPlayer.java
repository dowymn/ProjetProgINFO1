package model;

import util.Utili;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the Human player.
 * @author Eva GuignabodetcheckEnd
 */
public class HumanPlayer extends Player {

	/**
	 * Initializes the player.
	 * @param game the game the player is in
	 * @param playerName the player's name
	 */
	public HumanPlayer(Game game, String playerName, boolean graphical) {
		super(game, playerName, graphical, false);
	}

	/**
	 * Allows the HumanPlayer to play his turn.
	 */
	public void play() {
		System.out.println("\n\n\n---------------------------------------------------------------------------------");
		System.out.println(getPlayerName() + " : " + getNbPtsVic() + " pts vic, " + getNbEcus() + " ecus");
		System.out.println("---------------------------------------------------------------------------------");

		boolean played = false;
		constructionTurn.clear();

		String answer;
		boolean ok;

		while ( !played ) {
			System.out.println("Actions restantes : " + getActions());
			ok = false;
			System.out.println("\nQue veux-tu faire ?");
			System.out.println("1: Ouvrir un chantier");
			System.out.println("2: Recruter un ouvrier");
			System.out.println("3: Envoyer travailler un ouvrier");
			System.out.println("4: Prendre un ou plusieurs écus");
			System.out.println("5: Finir mon tour");
			System.out.println("0: Sauvegarder et quitter la partie");

			do {
				answer = scan.nextLine();

				// answer = 1
				if (Utili.intIs(answer, 1)) {
					System.out.println("Quel bâtiment souhaites-tu construire ? (donne le numéro)");
					do {
						answer = scan.nextLine();
						if (Utili.intIsInto(answer, 1, 5)) {
							startBuilding(game.getAvailableBuildings().get(Integer.parseInt(answer)-1));
							ok = true;
						}
					} while (!ok);

				// answer = 2
				} else if (Utili.intIs(answer, 2)) {
					System.out.println("Quel ouvrier veux-tu recruter ? (donne le numéro)");
					do {
						answer = scan.nextLine();
						if ( Utili.intIsInto(answer, 1, 5) ) {
							hireWorker(game.getAvailableWorkers().get(Integer.parseInt(answer) - 1));
							System.out.println("Ouvrier recruté !");
							ok = true;
						}
					} while (!ok);

				// answer = 3
				} else if (Utili.intIs(answer, 3) ) {
					if ( getFreeWM().size()>0 && getChantiers().size() > 0 ) {
						System.out.println("Quel ouvrier veux-tu envoyer travailler ? (donne le numéro)");
						printFreeWkrs();
						do {
							answer = scan.nextLine();
							if (Utili.intIsInto(answer, 1, getFreeWM().size())) {
								Card worker = getFreeWM().get(Integer.parseInt(answer) - 1);

								if ( getChantiers().size() > 0 ) {
									System.out.println("Sur quel chantier doit-il travailler ? (donne le numéro)");
									printChantiers();
									do {
										answer = scan.nextLine();
										if (Utili.intIsInto(answer, 1, getChantiers().size())) {
											sendWorkerToWork(worker, getChantiers().get(Integer.parseInt(answer) - 1));
											ok = true;
										}
									} while (!ok);
								} else {
									ok = true;
									System.out.println("Tu n'as pas de bâtiments en cours de construction.");
								}

								} while (Utili.intIsInto(answer, 1, 2) && !ok);

						} while (!ok);
					} else {
						System.out.println("Tu n'as pas d'ouvriers de libres, ou bien aucun chantier n'est en cours.");
						ok = true;
					}


				// answer = 4
				} else if (Utili.intIs(answer, 4)) {
					System.out.println("Rappel : 1 écu coûte 1 action, 3 écus 2 actions et 6 écus 3 actions.");
					System.out.println("Veux-tu 1, 3 ou 6 écus ?");
					do {
						answer = scan.nextLine();
						if ( Utili.intIs(answer,1) || Utili.intIs(answer,3) || Utili.intIs(answer,6) ) {
							takeEcus(Integer.parseInt(answer));
							ok = true;
						}
					} while (!ok);


				// answer = 5
				} else if (Utili.intIs(answer, 5)) {
					played = true;
					ok = true;
					System.out.println("Fin du tour.");


				// answer = 6
				} else if ( Utili.intIs(answer,0)) {
					System.out.println("Comment veux-tu nommer ta sauvegarde ?");
					System.out.println("Attention ! Si une sauvegarde portant ce nom existe déjà, elle sera remplacée.");
					String name = scan.nextLine();
					game.saveTheGame(name);

					played = true;
					ok = true;
				}


			} while (Utili.intIsInto(answer, 1, 5) && !ok);

		} // end of the while(!played){...}


		this.resetActions();
		game.isPlayerWinner(this);
	}

	/**
	 * Prints the free workers and their caracteristics.
	 */
	private void printFreeWkrs() {
		System.out.println("\nOuvriers au chômage");
		System.out.println("-------------------------------------------------------------------");
		System.out.println("No  NOM                       COÛT   PIERRE   BOIS   SAVOIR   TUILE");
		//                  1   Un instrument de mesure    6       4        2       2        2

		StringBuilder print = new StringBuilder();
		WorkerCard worker = null;
		MachineCard machine = null;

		for (int i = 0; i < getFreeWM().size(); i++) {
			Card unkcard = getFreeWM().get(i);
			if ( unkcard.getType() == TypeCard.Worker ) {
				worker = (WorkerCard) unkcard;

				// number
				print.append(i + 1).append("   ");
				// name
				print.append(worker.getCardName()); // 9 + 2
				int ecart = 25 - worker.getCardName().length();
				for (int j = 0; j < ecart; j++) { print.append(" "); }
				// coût
				for (int j = 0; j < 2; j++) { print.append(" "); }
				print.append(worker.getEcusAPayer());

				// pierre
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(worker.getPierreProd());
				// bois
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(worker.getBoisProd());
				// savoir
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(worker.getSavoirProd());
				// tuile
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(worker.getTuileProd());

			} else if ( unkcard.getType() == TypeCard.Machine ) {
				machine = (MachineCard) unkcard;

				// number
				print.append(i + 1).append("   ");
				// name
				print.append(machine.getCardName()); // 9 + 2
				int ecart = 25 - machine.getCardName().length();
				for (int j = 0; j < ecart; j++) { print.append(" "); }
				// coût
				for (int j = 0; j < 2; j++) { print.append(" "); }
				print.append(0);

				// pierre
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(machine.getPierreProd());
				// bois
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(machine.getBoisProd());
				// savoir
				for (int j = 0; j < 7; j++) { print.append(" "); }
				print.append(machine.getSavoirProd());
				// tuile
				for (int j = 0; j < 8; j++) { print.append(" "); }
				print.append(machine.getTuileProd());
			}

			System.out.println(print);
			print = new StringBuilder();
		}
	}

	/**
	 * Prints the buildings whose construction is in progress, and their caracteristics.
	 */
	private void printChantiers() {
		System.out.println("\nBâtiments en cours de construction");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("No  NOM                      GAINS   PTS VIC   PIERRE   BOIS   SAVOIR   TUILE");
		//                  1   Un instrument de mesure   16        4        2       2        2       2

		StringBuilder print = new StringBuilder();
		BuildingCard building = null;
		MachineCard machine = null;

		for ( int i = 0 ; i < getChantiers().size() ; i++ ) {

			Card card = getChantiers().get(i);
			if ( card.getType() == TypeCard.Building ) {
				building = (BuildingCard) card;
				// number
				print.append(i + 1).append("   ");
				// name
				print.append( building.getCardName() ); // 20 + 2
				int ecart = 23 - building.getCardName().length();
				for ( int j = 0 ; j < ecart ; j++ ) { print.append(" "); }
				// the earned ecus
				if ( building.getEcusGagnes() > 9 ) { print.append("   "); }
				else { print.append("    "); }
				print.append(building.getEcusGagnes());
				// pts vic
				for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
				print.append(building.getPtsVictoire());
				// pierre
				for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
				print.append(building.getPierreNec());
				// bois
				for ( int j = 0 ; j < 7 ; j++ ) { print.append(" "); }
				print.append(building.getBoisNec());
				// savoir
				for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
				print.append(building.getSavoirNec());
				// tuile
				for ( int j = 0 ; j < 7 ; j++ ) { print.append(" "); }
				print.append(building.getTuileNec());

			} else if ( card.getType() == TypeCard.Machine ) {
				machine = (MachineCard) card;
				// number
				print.append(i + 1).append("   ");
				// name
				print.append( machine.getCardName() ); // 20 + 2
				int ecart = 23 - machine.getCardName().length();
				for ( int j = 0 ; j < ecart ; j++ ) { print.append(" "); }
				// the earned ecus
				print.append("    ");
				print.append(0);
				// pts vic
				for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
				print.append(machine.getPtsVictoire());
				// pierre
				for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
				print.append(machine.getPierreNec());
				// bois
				for ( int j = 0 ; j < 7 ; j++ ) { print.append(" "); }
				print.append(machine.getBoisNec());
				// savoir
				for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
				print.append(machine.getSavoirNec());
				// tuile
				for ( int j = 0 ; j < 7 ; j++ ) { print.append(" "); }
				print.append(machine.getTuileNec());
			}

			System.out.println(print);
			print = new StringBuilder();
		}

	}

}