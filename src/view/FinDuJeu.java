package view;

import javax.swing.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines how the end of the game will look (scores, who wins...).
 * @author Eva Guignabodet
 */
public class FinDuJeu extends Pages {

	private JPanel finPanel;
	private JLabel nomGagnant;
	private JLabel ptsGagnant;

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public FinDuJeu(LesBatisseurs window) {
		super(window, "Fin du jeu");

	}

	/**
	 * Initializes the components.
	 */
	private void initialization() {

	}

	/**
	 * Defines what to do then the returnButton is clicked (i.e. which page will be accessed).
	 */
	public void returnButtonAction() {

	}

}