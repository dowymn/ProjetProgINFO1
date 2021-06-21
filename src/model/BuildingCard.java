package model;

import util.Batview;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the Building's cards.
 * @author Eva Guignabodet
 */
public class BuildingCard extends Card {

	private BuildingState state;

	private String cardImage;

	private int ecusGagnes;
	private int ptsVictoire;
	private int pierreNec;
	private int boisNec;
	private int savoirNec;
	private int tuileNec;

	private ArrayList<WorkerCard> workers;
	private ArrayList<MachineCard> machines;


	//-----[ INITIALIZATION

	/**
	 * Initializes the BuildingCard
	 * @param name the name of the card
	 * @param imageName the name of the card image
	 * @param ecus the number of ecus gained by the player
	 * @param ptsVictoire the number of pts victoire earned by the player
	 * @param pierre the number of stone needed
	 * @param bois the number of wood needed
	 * @param savoir the number of knowledge needed
	 * @param tuile the number of tiles needed
	 */
	public BuildingCard(String name, String imageName, int ecus, int ptsVictoire, int pierre, int bois, int savoir, int tuile) {
		super(name);
		this.setType(TypeCard.Building);

		this.ecusGagnes = ecus;
		this.ptsVictoire = ptsVictoire;
		this.pierreNec = pierre;
		this.boisNec = bois;
		this.savoirNec = savoir;
		this.tuileNec = tuile;

		this.state = BuildingState.NOTSTARTED;

		workers = new ArrayList<>();
		machines = new ArrayList<>();

		initImage(imageName);
	}

	/**
	 * Initializes the card image.
	 */
	private void initImage(String name) {
		cardImage = Batview.PATHCARDS + "batiments/" + name;
	}


	//-----[ METHODS

	/**
	 * Allows to change the state of a building, according to the allowed stated in the State enum.
	 * @param newState the new state of the building
	 */
	public void changeState(BuildingState newState) {
		if ( newState != null ) {
			this.state = newState;
		} else {
			System.out.println("Error : BuildingCard : changeState() : newState is null. ");
		}
	}

	/**
	 * Allows to send a worker on the building.
	 * Checks if the worker is already working, if it is the case the worker is not sent and the method returns false.
	 * @param worker the worker
	 * @return true if the worker has been putted on the building, else false
	 */
	public boolean addWorker(WorkerCard worker) {
		boolean done = false;
		if ( !worker.getIsWorking() ) {
			worker.changeWork(true);
			workers.add(worker);
		}
		return done;
	}

	/**
	 * Allows to send a machine on the building.
	 * Checks if the machine is already used, if it is the case the machine is not sent and the method returns false.
	 * @param machine the machine
	 * @return true if the machine has been putted on the building, else false
	 */
	public boolean addMachine(MachineCard machine) {
		boolean done = false;
		if ( !machine.getIsUsed() ) {
			machine.changeUse(true);
			machines.add(machine);
		}
		return done;
	}



	//-----[ GETTERS

	/**
	 * @return the ecus field
	 */
	public int getEcusGagnes() {
		return this.ecusGagnes;
	}

	/**
	 * @return the ptsVictoire field
	 */
	public int getPtsVictoire() {
		return this.ptsVictoire;
	}

	/**
	 * @return the pierreNec field
	 */
	public int getPierreNec() {
		return this.pierreNec;
	}

	/**
	 * @return the boisNec field
	 */
	public int getBoisNec() {
		return this.boisNec;
	}

	/**
	 * @return the savoirNec attribute
	 */
	public int getSavoirNec() {
		return this.savoirNec;
	}

	/**
	 * @return the tuileNec attribute
	 */
	public int getTuileNec() {
		return this.tuileNec;
	}

	/**
	 * @return the state attribute
	 */
	public BuildingState getState () {
		return this.state;
	}

	/**
	 * @return the workers that work on this machine
	 */
	public ArrayList<WorkerCard> getWorkers() {
		return workers;
	}

	/**
	 * @return the machines that are used to build this machine
	 */
	public ArrayList<MachineCard> getMachines() {
		return machines;
	}

	/**
	 * @return the card image
	 */
	public String getCardImage() {
		return cardImage;
	}

}