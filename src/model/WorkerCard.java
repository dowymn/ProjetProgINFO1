package model;

import util.Batview;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the Worker's cards.
 * @author Eva Guignabodet
 */
public class WorkerCard extends Card {

	private String cardImage;

	private int ecusAPayer;
	private int pierreProd;
	private int boisProd;
	private int savoirProd;
	private int tuileProd;
	private boolean isWorking;


	//-----[ INITIALIZATION

	/**
	 * Initializes the Worker Card
	 * @param name the name of the card
	 * @param imageName the name of the card image
	 * @param ecus the ecus needed to make the worker work
	 * @param pierre the stone produced by the worker
	 * @param bois the wood produced by the worker
	 * @param savoir the knowledge produced by the worker
	 * @param tuile the tils produced by the worker
	 */
	public WorkerCard(String name, String imageName, int ecus, int pierre, int bois, int savoir, int tuile) {
		super(name);
		this.setType(TypeCard.Worker);

		this.ecusAPayer = ecus;
		this.pierreProd = pierre;
		this.boisProd = bois;
		this.savoirProd = savoir;
		this.tuileProd = tuile;

		this.isWorking = false;

		initImage(imageName);
	}

	/**
	 * Initializes the card image.
	 */
	private void initImage(String name) {
		cardImage = Batview.PATHCARDS + "ouvriers/" + name;
	}


	//-----[ METHODS

	/**
	 * Allows to send a worker to work, or to get him back if his work is over.
	 * @param isWorking is true if the worker is going to work, false if he is going to stop working
	 */
	public void changeWork(boolean isWorking) {
		this.isWorking = isWorking;
	}


	//----[ GETTERS

	/**
	 * @return the ecus to pay to use the worker
	 */
	public int getEcusAPayer() {
		return this.ecusAPayer;
	}

	/**
	 * @return the number of stone produced by the worker
	 */
	public int getPierreProd() {
		return this.pierreProd;
	}

	/**
	 * @return the number of wood produced by the worker
	 */
	public int getBoisProd() {
		return this.boisProd;
	}

	/**
	 * @return the number of knowledge produced by the worker
	 */
	public int getSavoirProd() {
		return this.savoirProd;
	}

	/**
	 * @return the number of tils produced by the worker
	 */
	public int getTuileProd() {
		return this.tuileProd;
	}

	/**
	 * @return true if the worker is working, else false
	 */
	public boolean getIsWorking () {
		return this.isWorking;
	}

	/**
	 * @return the card image
	 */
	public String getCardImage() {
		return cardImage;
	}

}