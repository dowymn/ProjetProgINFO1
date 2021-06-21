package model;

import util.Utili;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the common points of the different kind of players.
 * @author Eva Guignabodet
 */
public abstract class Player implements Serializable {

	protected static final Scanner scan = new Scanner(System.in);

	private final String playerName;
	protected final Game game;

	private ArrayList<WorkerCard> playerWorkers;
	private ArrayList<BuildingCard> playerBuildings;
	private ArrayList<MachineCard> playerMachines;

	protected HashMap<Card,Integer> constructionTurn; // will be used to apply the cost of the worker that are sent to work

	private int nbEcus;
	private int nbPtsVic;

	private int actions; // le nombre d'actions que le joueur peut encore faire

	/**
	 * Initializes the player.
	 * @param game the game the player is in
	 * @param playerName the player's name
	 */
	public Player(Game game, String playerName) {

		if ( playerName == null ) {
			System.err.println("Error : Player() : playerName is null. ");
			this.playerName = "player";
		} else {
			this.playerName = playerName;
		}

		if ( game == null ) {
			throw new IllegalArgumentException("Error : Player() : game mustn't be null.");
		} else {
			this.game = game;
		}

		playerWorkers = new ArrayList<>();
		playerBuildings = new ArrayList<>();
		playerMachines = new ArrayList<>();

		this.nbEcus = 10;
		this.nbPtsVic = 0;

		this.actions = 3;

		this.constructionTurn = new HashMap<>();

		hireWorker(game.getAvailableWorkers().get(0));
		resetActions();


	}

	public abstract void play();

	/**
	 * Allows the player to take a card from the available cards list.
	 * A player can only take a card if the wanted card is in the availableCards list.
	 * @param worker the wanted worker
	 * @return true if the card has been taken, else false
	 */
	public boolean hireWorker(WorkerCard worker) {
		boolean ok = false;
		int i = 0;
		while ( !ok && i < game.getAvailableWorkers().size() ) {
			// if the card is available
			if ( game.getAvailableWorkers().get(i) == worker ) { // if the player has enouth actions left
				if ( decActions(1) ) {
					ok = true;
					game.getAvailableWorkers().remove(i); // it is removed from the availableCards list
					playerWorkers.add(worker); // and it is added to he playerCards list
				}
			}
			i++;
		}
		return ok;
	}

	/**
	 * Allows the player to take a card from the available building cards list.
	 * A player can only take a card if the wanted card is in the availableCards list.
	 * @param card the wanted card
	 * @return true if the card has been taken, else false
	 */
	public boolean startBuilding(Card card) {
		boolean ok = false;
		// if the card is available
		if ( game.getAvailableBuildings().contains(card) ) {
			if ( card.getType() == TypeCard.Building ) {
				BuildingCard building = (BuildingCard) card;
				if (decActions(1)) { // if the player has enouth actions left
					ok = true;
					game.getAvailableBuildings().remove(building); // it is removed from the availableCards list
					playerBuildings.add(building); // and it is added to he playerCards list
					building.changeState(BuildingState.INPROGRESS);
				}
			} else if ( card.getType() == TypeCard.Machine ) {
				MachineCard building = (MachineCard) card;
				if (decActions(1)) { // if the player has enouth actions left
					ok = true;
					game.getAvailableBuildings().remove(building); // it is removed from the availableCards list
					playerMachines.add(building); // and it is added to he playerCards list
					building.changeState(BuildingState.INPROGRESS);
				}
			}
		}
		return ok;
	}

	/**
	 * Allows to finish a buidling when it is built.
	 * Removes all the workers and machines from the construction site.
	 * @param building the finished building
	 */
	private void finishBuilding(BuildingCard building) {
		for ( WorkerCard worker : building.getWorkers() ) {
			worker.changeWork(false);
		}
		for ( MachineCard mach : building.getMachines() ) {
			mach.changeUse(false);
		}
		building.changeState(BuildingState.FINISHED);
		earnVictoryPoints(building.getPtsVictoire());
		earnEcus(building.getEcusGagnes());
	}

	/**
	 * Allows to finish a machine when it is built.
	 * Removes all the workers and machines from the construction site.
	 * @param machine the finished machine
	 */
	private void finishMachine(MachineCard machine) {
		for ( WorkerCard worker : machine.getWorkers() ) {
			worker.changeWork(false);
		}
		for ( MachineCard mach : machine.getMachines() ) {
			machine.changeUse(false);
		}
		earnVictoryPoints(machine.getPtsVictoire());
		machine.changeState(BuildingState.FINISHED);
	}

	/**
	 * Allows to know if the card given as a parameter - a building or a machine - is finished or not.
	 * Checks if the sum of all the ressources given by the workers and the machines on the construction is at least the sum needed.
	 * If it is the case, the method finishBuilding (or finishMachine) is called.
	 * @param card the building / machine
	 */
	public void isFinishedBuilding(Card card) {
		int sumBois = 0;
		int sumPierre = 0;
		int sumSavoir = 0;
		int sumTuile = 0;

		if ( card.getType() == TypeCard.Building ) {
			BuildingCard building = (BuildingCard) card;

			for ( WorkerCard worker : building.getWorkers() ) {
				sumBois += worker.getBoisProd();
				sumPierre += worker.getPierreProd();
				sumSavoir += worker.getSavoirProd();
				sumTuile += worker.getTuileProd();
			}
			for ( MachineCard mach : building.getMachines() ) {
				sumBois += mach.getBoisProd();
				sumPierre += mach.getPierreProd();
				sumSavoir += mach.getSavoirProd();
				sumTuile += mach.getTuileProd();
			}

			if ( sumBois >= building.getBoisNec() && sumPierre == building.getPierreNec()
			&& sumSavoir == building.getSavoirNec() && sumTuile == building.getTuileNec() ) {
				finishBuilding(building);
			}

		} else if ( card.getType() == TypeCard.Machine ) {
			MachineCard machine = (MachineCard) card;

			for ( WorkerCard worker : machine.getWorkers() ) {
				sumBois += worker.getBoisProd();
				sumPierre += worker.getPierreProd();
				sumSavoir += worker.getSavoirProd();
				sumTuile += worker.getTuileProd();
			}

			for ( MachineCard mach : machine.getMachines() ) {
				sumBois += mach.getBoisProd();
				sumPierre += mach.getPierreProd();
				sumSavoir += mach.getSavoirProd();
				sumTuile += mach.getTuileProd();
			}

			if ( sumBois >= machine.getBoisNec() && sumPierre == machine.getPierreNec()
					&& sumSavoir == machine.getSavoirNec() && sumTuile == machine.getTuileNec() ) {
				finishMachine(machine);
			}

		}
	}

	/**
	 * Allows to send a worker to work on a building by calling the addWorker() method from the BuildingCard or MachineCard class.
	 * The good one is chosen by checking the type of the card given as a parameter.
	 * If the worker has been sent to work, returns true. If the worker was already working on another building and then can't be sent to work on the wanted one, returns false.
	 * @param worker the worker
	 * @param card the building
	 * @return true if the worker has been sent to work on the building, else false
	 */
	public boolean sendWorkerToWork(WorkerCard worker, Card card) {
		boolean done = false;

		if ( constructionTurn.containsKey(card) ) {
			constructionTurn.replace(card, constructionTurn.get(card)+1);
		} else {
			constructionTurn.put(card,1);
		}

		if ((nbEcus - worker.getEcusAPayer()) >= 0 && decActions(constructionTurn.get(card))) {
			if (card.getType() == TypeCard.Building) {
				done = ((BuildingCard) card).addWorker(worker);
			} else if (card.getType() == TypeCard.Machine) {
				done = ((MachineCard) card).addWorker(worker);
			}
		}

		if ( done ) {
			if ( !earnEcus(-worker.getEcusAPayer()) ) {
				actions += 1;
				done = false;
				System.out.println("Vous n'avez plus assez d'écus pour envoyer l'ouvrier au travail.");
				System.out.println("Utilisez votre action pour autre chose.");
			} else {
				isFinishedBuilding(card);
			}
		}

		return done;
	}

	/**
	 * Allows to send a machine on a building by calling the addMachine() method from the BuildingCard class.
	 * If the machine has been sent on a building, returns true. If the machine was already working on another building and then can't be sent to work on the wanted one, returns false.
	 * @param machine the machine
	 * @param card the building or machine
	 * @return true if the worker has been sent to work on the building, else false
	 */
	public boolean putMachineOnConstruction(MachineCard machine, Card card) {
		boolean done = false;

		if ( constructionTurn.containsKey(card) ) {
			constructionTurn.replace(card, constructionTurn.get(card)+1);
		} else {
			constructionTurn.put(card,1);
		}

		if ( decActions(constructionTurn.get(card)) ) {
			if (card.getType() == TypeCard.Building) {
				done = ((BuildingCard) card).addMachine(machine);
			} else if (card.getType() == TypeCard.Machine) {
				done = ((MachineCard) card).addMachine(machine);
			}
		}

		if ( done ) {
			isFinishedBuilding(card);
		}

		return done;
	}

	/**
	 * Allows the player to take some ecus by giving some of his actions :
	 * - for 1 action, he can have 1 ecu
	 * - for 2 actions, he can have 3 ecus
	 * - for 3 actions, he can have 6 ecus
	 * @param ecus the numbe of ecus needed
	 */
	public void takeEcus ( int ecus ) {
		if ( ecus == 1 ) {
			if ( decActions(1) ) {
				this.nbEcus += 1;
			}
		} else if ( ecus == 3 ) {
			if ( decActions(2) ) {
				this.nbEcus += 3;
			}
		} else if ( ecus == 6 ) {
			if ( decActions(3) ) {
				this.nbEcus += 6;
			}
		} else {
			System.out.println("Vous devez choisir entre 1, 3 ou 6 écus.");
		}
	}

	/**
	 * Allows to decrease the number of actions of the player.
	 * If the player doesn't have enouth actions, he is proposed to pay with some ecus.
	 * If he refuses or if he doesn't have enouth ecus, nothing is done.
	 * @param howmany the number of actions that has to be removed
	 * @return true if the actions have been decreased, else false
	 */
	public boolean decActions( int howmany ) {
		boolean done = false;
		int rest = actions - howmany;

		if ( rest < 0 ) {
			System.out.println("Attention ! Tu n'as pas assez d'actions, il te faudra payer " + rest*(-5) + " ecus.");
			System.out.println("1: Payer\n2: Ne rien faire");
			String line;
			do {

				line = scan.nextLine();
				if ( Utili.intIs(line,1) ) {
					if ( this.nbEcus-(-5)*rest > 0 ) {
						this.earnEcus(-(-5) * rest);
						this.actions = 0;
						done = true;
					} else {
						System.out.println("Tu n'as pas assez d'écus !");
					}
				} else if ( Utili.intIs(line,2) ) {
					System.out.println("Aucun écu ne t'a été déboursé et aucune action n'a été effectuée.");
				}

			} while ( !Utili.intIsInto(line,1,2) );
		} else {
			this.actions -= howmany;
			done = true;
			//System.out.println("Il te reste " + actions + " actions.");
		}

		return done;
	}

	/**
	 * Resets the number of available actions to its default value.
	 * This method has to be used at the end of each turn.
	 */
	public void resetActions() {
		this.actions = 3;
	}

	/**
	 * Allows the player to earn ecus.
	 * @param ecus the number of ecus earned by the player.
	 */
	public boolean earnEcus(int ecus) {
		boolean done = false;

		if ( (nbEcus+ecus) >= 0 ) {
			this.nbEcus += ecus;
			done = true;
		} else {
			System.out.println("Vous n'avez pas assez d'écus pour effectuer cette action.");
		}

		return done;
	}

	/**
	 * Allows the player to earn victory points.
	 * @param vp the number of victory points earned by the player.
	 */
	public void earnVictoryPoints(int vp) {
		if ( vp < 0 ) {
			System.err.println("Error : Player : earnVictoryPoints() : vp is < 0. ");
		} else {
			this.nbPtsVic += vp;
		}
	}

	/**
	 * At the end of the game, each 10 ecus allows the player to get 1 victory point.
	 * So this method calls the earnVictoryPoints() method with nbEcus/10 as a parameter.
	 * In order to respect the different rules for a game end, returns an int[] tab that contains :
	 * - the final number of victory points at the index 0
	 * - the number of victory points without the added ecus at the index 1
	 * - the number of ecus at the index 2
	 * @return an int[] tab
	 */
	public int[] earnEndOfGame() {
		int[] tab = new int[3];
		tab[1] = nbPtsVic;
		tab[2] = nbEcus;
		earnVictoryPoints(nbEcus/10);
		tab[0] = nbPtsVic;

		return tab;
	}


	//-----[ GETTERS

	/**
	 * @return the game the player is in
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * @return the name of the player
	 */
	public String getPlayerName() {
		return this.playerName;
	}

	/**
	 * @return the workers of the player
	 */
	public ArrayList<WorkerCard> getPlayerWorkers() {
		return this.playerWorkers;
	}

	/**
	 * @return the buildings of the player
	 */
	public ArrayList<BuildingCard> getPlayerBuildings() {
		return this.playerBuildings;
	}

	/**
	 * @return the machines of the player
	 */
	public ArrayList<MachineCard> getPlayerMachines() {
		return this.playerMachines;
	}

	/**
	 * Allows to get the workers who don't work on any construction.
	 * @return an ArrayList that contains the free workers
	 */
	public ArrayList<WorkerCard> getFreeWorkers() {
		ArrayList<WorkerCard> list = new ArrayList<>();

		for ( WorkerCard card : playerWorkers ) {
			if ( !card.getIsWorking() ) {
				list.add( card );
			}
		}

		return list;
	}

	/**
	 * Allows to get the machines that are not used on any construction.
	 * @return an ArrayList that contains the free machines
	 */
	public ArrayList<MachineCard> getFreeMachines() {
		ArrayList<MachineCard> list = new ArrayList<>();

		for ( MachineCard card : playerMachines ) {
			if ( !card.getIsUsed() ) {
				list.add( card );
			}
		}

		return list;
	}

	/**
	 * Allows to get the buildings whose construction is in progress.
	 * @return an ArrayList that contains buildings
	 */
	public ArrayList<BuildingCard> getInProgressBuildings() {
		ArrayList<BuildingCard> list = new ArrayList<>();

		for ( BuildingCard card : playerBuildings ) {
			if ( card.getState() == BuildingState.INPROGRESS ) {
				list.add( card );
			}
		}

		return list;
	}

	/**
	 * Allows to get the machines whose construction is in progress.
	 * @return an ArrayList that contains the machines
	 */
	public ArrayList<MachineCard> getInProgressMachines() {
		ArrayList<MachineCard> list = new ArrayList<>();

		for ( MachineCard card : playerMachines ) {
			if ( card.getState() == BuildingState.INPROGRESS ) {
				list.add( card );
			}
		}

		return list;
	}

	/**
	 * @return the number of ecus the player has
	 */
	public int getNbEcus() {
		return nbEcus;
	}

	/**
	 * @return the number of victory points the player has
	 */
	public int getNbPtsVic() {
		return nbPtsVic;
	}

	/**
	 * @return the number of actions the player still has
	 */
	public int getActions() {
		return actions;
	}
}