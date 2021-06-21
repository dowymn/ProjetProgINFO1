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
	public HumanPlayer(Game game, String playerName) {
		super(game, playerName);
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
					if ( getFreeWorkers().size()>0 && (getInProgressBuildings().size() > 0 || getInProgressMachines().size() > 0) ) {
						System.out.println("Quel ouvrier veux-tu envoyer travailler ? (donne le numéro)");
						printFreeWkrs();
						do {
							answer = scan.nextLine();
							if (Utili.intIsInto(answer, 1, getFreeWorkers().size())) {
								WorkerCard worker = getFreeWorkers().get(Integer.parseInt(answer) - 1);

								System.out.println("Doit-il travailler sur un bâtiment ou une machine ?");
								System.out.println("1: Bâtiment");
								System.out.println("2: Machine");
								do {
									answer = scan.nextLine();
									if (Utili.intIs(answer, 1)) {
										if ( getInProgressBuildings().size() > 0 ) {
											System.out.println("Sur quel chantier doit-il travailler ? (donne le numéro)");
											printInProgressBats();
											do {
												answer = scan.nextLine();
												if (Utili.intIsInto(answer, 1, getInProgressBuildings().size())) {
													sendWorkerToWork(worker, getInProgressBuildings().get(Integer.parseInt(answer) - 1));
													ok = true;
												}
											} while (!ok);
										} else {
											ok = true;
											System.out.println("Tu n'as pas de bâtiments en cours de construction.");
										}

									} else if (Utili.intIs(answer, 2)) {
										if ( getInProgressMachines().size() > 0 ) {
											System.out.println("Sur quel chantier doit-il travailler ? (donne le numéro)");
											printInProgressMach();
											do {
												answer = scan.nextLine();
												if (Utili.intIsInto(answer, 1, getInProgressMachines().size())) {
													sendWorkerToWork(worker, getInProgressMachines().get(Integer.parseInt(answer) - 1));
													ok = true;
												}
											} while (!ok);
										} else {
											ok = true;
											System.out.println("Tu n'as pas de machines en cours de construction.");
										}
									}

								} while (Utili.intIsInto(answer, 1, 2) && !ok);

							}

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
		System.out.println("-----------------------------------------------------");
		System.out.println("No  NOM         COÛT   PIERRE   BOIS   SAVOIR   TUILE");
		//                  1   Compagnon    6       4        2       2        2

		StringBuilder print = new StringBuilder();

		for (int i = 0; i < getFreeWorkers().size(); i++) {
			WorkerCard card = getFreeWorkers().get(i);
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

	/**
	 * Prints the machines whose construction is in progress, and their caracteristics.
	 */
	private void printInProgressMach() {
		System.out.println("\nMachines en cours de construction      RESSOURCES NÉCESSAIRES           RESSOURCES PRODUITES");
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println("No  NOM                      PTS VIC   PIERRE   BOIS   SAVOIR   TUILE   PIERRE   BOIS   SAVOIR   TUILE");
		//                  1   Un instrument de mesure     6        4        2       2        2       2      2       2        2

		StringBuilder print = new StringBuilder();

		for (int i = 0; i < getInProgressMachines().size(); i++) {
			MachineCard card = getInProgressMachines().get(i);
			// number
			print.append(i + 1).append("   ");
			// name
			print.append(card.getCardName()); // 23 + 2
			int ecart = 25 - card.getCardName().length();
			for (int j = 0; j < ecart; j++) { print.append(" "); }
			// pts vic
			for (int j = 0; j < 3; j++) { print.append(" "); }
			print.append(card.getPtsVictoire());
			// RESSOURCES NÉCESSAIRES
			// pierre
			for (int j = 0; j < 8; j++) { print.append(" "); }
			print.append(card.getPierreNec());
			// bois
			for (int j = 0; j < 7; j++) { print.append(" "); }
			print.append(card.getBoisNec());
			// savoir
			for (int j = 0; j < 7; j++) { print.append(" "); }
			print.append(card.getSavoirNec());
			// tuile
			for (int j = 0; j < 8; j++) { print.append(" "); }
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

	/**
	 * Prints the buildings whose construction is in progress, and their caracteristics.
	 */
	private void printInProgressBats() {
		System.out.println("\nBâtiments en cours de construction");
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("No  NOM                   GAINS   PTS VIC   PIERRE   BOIS   SAVOIR   TUILE");
		//                  1   La maison bourgeoise   16        4        2       2        2       2

		StringBuilder print = new StringBuilder();

		for ( int i = 0 ; i < getInProgressBuildings().size() ; i++ ) {
			BuildingCard card = getInProgressBuildings().get(i);
			// number
			print.append(i + 1).append("   ");
			// name
			print.append( card.getCardName() ); // 20 + 2
			int ecart = 20 - card.getCardName().length();
			for ( int j = 0 ; j < ecart ; j++ ) { print.append(" "); }
			// the earned ecus
			if ( card.getEcusGagnes() > 9 ) { print.append("   "); }
			else { print.append("    "); }
			print.append(card.getEcusGagnes());
			// pts vic
			for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
			print.append(card.getPtsVictoire());
			// pierre
			for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
			print.append(card.getPierreNec());
			// bois
			for ( int j = 0 ; j < 7 ; j++ ) { print.append(" "); }
			print.append(card.getBoisNec());
			// savoir
			for ( int j = 0 ; j < 8 ; j++ ) { print.append(" "); }
			print.append(card.getSavoirNec());
			// tuile
			for ( int j = 0 ; j < 7 ; j++ ) { print.append(" "); }
			print.append(card.getTuileNec());
			System.out.println(print);
			print = new StringBuilder();
		}
	}

}