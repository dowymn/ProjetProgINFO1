package model;

import java.io.Serializable;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the Card's common points.
 * @author Eva Guignabodet
 */
public abstract class Card implements Serializable {

	private TypeCard type = TypeCard.None;
	private String cardName;
	private boolean available;

	/**
	 * Initializes the card.
	 * @param cardName the name of the card
	 */
	public Card(String cardName) {

		if ( cardName != null ) {
			this.cardName = cardName;
		}

		this.available = true;
	}

	//-----[ GETTERS

	/**
	 * @return the available field
	 */
	public boolean isAvailable() {
		return this.available;
	}

	/**
	 * Sets the available attribute.
	 * @param available is true if the new state of the card is "available", else false
	 */
	public void setAvailable (boolean available) {
		this.available = available;
	}

	/**
	 * @return the type of the card
	 */
	public TypeCard getType() {
		return this.type;
	}

	/**
	 * @param type the new type of the card.
	 */
	protected void setType (TypeCard type ) {
		this.type = type;
	}

	/**
	 * @return the cardName field
	 */
	public String getCardName() {
		return this.cardName;
	}

	/**
	 * Gives a printable String chain that allows to print the card.
	 * @return the card's String chain
	 */
	public String toString() {

		StringBuilder ret = new StringBuilder();
		ret.append(" ----------- ");
		ret.append("|           |");
		ret.append("|           |");
		ret.append("|           |");
		ret.append("|           |");
		ret.append("|           |");
		ret.append(" ----------- ");

		return ret.toString();
	}

}