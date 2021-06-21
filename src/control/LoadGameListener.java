package control;

import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * @author Eva Guignabodet
 */
public class LoadGameListener implements ActionListener {

	ReprendrePartie reprendrePartie;

	/**
	 * Initializes the choixPartie field.
	 * @param reprendrePartie the linked instance
	 */
	public LoadGameListener(ReprendrePartie reprendrePartie) {

	}

	/**
	 * Defines what to do then a button is clicked, according to the linked instance.
	 * Because only the linked attribute is initialized, simply checks which one is != null and then does the job with this one.
	 * @param actionEvent the ActionEvent
	 */
	public void actionPerformed(ActionEvent actionEvent) {

	}
}