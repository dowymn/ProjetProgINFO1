package model;

import util.Batview;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the Machine's cards.
 * @author Eva Guignabodet
 */
public class MachineCard extends Card {

	private BuildingState state;

	private String cardImageR;
	private String cardImageV;

	private int ptsVictoire;
	private int pierreNec;
	private int boisNec;
	private int savoirNec;
	private int tuileNec;
	private int pierreProd;
	private int boisProd;
	private int savoirProd;
	private int tuileProd;
	private boolean isUsed;

	private ArrayList<WorkerCard> workers;
	private ArrayList<MachineCard> machines;


	//-----[ INITIALIZATION

	/**
	 * Initializes the Machine Card.
	 * @param name the name of the card
	 * @param imageNameR the name of the recto card image
	 * @param imageNameV the name of the verso card image
	 * @param ptsVictoire the number of pts victoire gained
	 * @param pierreP the number of stone produced
	 * @param boisP the number of wood produced
	 * @param savoirP the number of knowledge produced
	 * @param tuileP the number of tils produced
	 * @param pierreN the number of needed stone
	 * @param boisN the number of needed wood
	 * @param savoirN the number of needed knowledge
	 * @param tuileN the number of needed tils
	 */
	public MachineCard(String name, String imageNameR, String imageNameV, int ptsVictoire, int pierreP, int boisP, int savoirP, int tuileP, int pierreN, int boisN, int savoirN, int tuileN) {
		super(name);
		this.setType(TypeCard.Machine);
		this.state = BuildingState.NOTSTARTED;

		this.ptsVictoire = ptsVictoire;

		this.pierreProd = pierreP;
		this.boisProd = boisP;
		this.savoirProd = savoirP;
		this.tuileProd = tuileP;

		this.pierreNec = pierreN;
		this.boisNec = boisN;
		this.savoirNec = savoirN;
		this.tuileNec = tuileN;

		this.isUsed = false;

		workers = new ArrayList<>();
		machines = new ArrayList<>();

		initImage(imageNameR, imageNameV);
	}

	/**
	 * Initializes the card images.
	 */
	private void initImage(String nameR, String nameV) {
		cardImageR = Batview.PATHCARDS + "machines/" + nameR;
		cardImageV = Batview.PATHCARDS + "machines/" + nameV;
	}


	//-----[ METHODS

	/**
	 * Allows to change the state of a machine.
	 * @param isUsed the new state of the machine
	 */
	public void changeUse(boolean isUsed) {
		if ( isUsed && state == BuildingState.FINISHED ) {
			this.isUsed = isUsed;
		} else if ( !isUsed ) {
			this.isUsed = isUsed;
		} else {
			System.out.println("This machine is not finished, you can't use it yet.");
		}

	}

	/**
	 * Allows to change the state of a machine, according to the allowed stated in the State enum.
	 * @param newState the new state of the building
	 */
	public void changeState(BuildingState newState) {
		if ( newState != null ) {
			this.state = newState;
		} else {
			System.out.println("Error : MachineCard : changeState() : newState is null. ");
		}
	}

	/**
	 * Allows to send a worker on the machine.
	 * Checks if the worker is already working, if it is the case the worker is not sent and the method returns false.
	 * @param worker the worker
	 * @return true if the worker has been putted on the machine, else false
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
	 * Allows to send a machine on the machine.
	 * Checks if the machine is already used, if it is the case the machine is not sent and the method returns false.
	 * @param machine the machine
	 * @return true if the machine has been putted on the machine, else false
	 */
	public boolean addMachine(MachineCard machine) {
		boolean done = false;
		if ( !machine.getIsUsed() ) {
			machine.changeUse(true);
			machines.add(machine);
		}
		return done;
	}


	//----[ GETTERS

	/**
	 * @return the state attribute
	 */
	public BuildingState getState () {
		return this.state;
	}

	/**
	 * @return the pierreProd field
	 */
	public int getPierreProd() {
		return this.pierreProd;
	}

	/**
	 * @return the boisProd field
	 */
	public int getBoisProd() {
		return this.boisProd;
	}

	/**
	 * @return the savoirProd field
	 */
	public int getSavoirProd() {
		return this.savoirProd;
	}

	/**
	 * @return the tuileProd field
	 */
	public int getTuileProd() {
		return this.tuileProd;
	}

	/**
	 * @return the isUsed attribute
	 */
	public boolean getIsUsed() {
		return this.isUsed;
	}

	/**
	 * @return the number of victory points earned
	 */
	public int getPtsVictoire() {
		return ptsVictoire;
	}

	/**
	 * @return the number of necessary stone
	 */
	public int getPierreNec() {
		return pierreNec;
	}

	/**
	 * @return the number of necessary wood
	 */
	public int getBoisNec() {
		return boisNec;
	}

	/**
	 * @return the number of necessary knowledge
	 */
	public int getSavoirNec() {
		return savoirNec;
	}

	/**
	 * @return the number of necessary tils
	 */
	public int getTuileNec() {
		return tuileNec;
	}

	/**
	 * @return true if the machine is used, else false
	 */
	public boolean isUsed() {
		return isUsed;
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
	 * @return the recto card image
	 */
	public String getCardImageR() {
		return cardImageR;
	}

	/**
	 * @return the verso card image
	 */
	public String getCardImageV() {
		return cardImageV;
	}

}