package view;

import control.*;
import util.Batview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines what will be shown then the player chooses to play in the main menu.
 * @author Eva Guignabodet
 */
public class MenuJouer extends Pages {

	ButtonListener listener;
	private JPanel jouerPanel;
	private JButton nouvellePartie;
	private JButton reprendrePartie;


	//-----[ INITIALIZATION

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public MenuJouer(LesBatisseurs window) {
		super(window,"Menu jouer");
		initialization();
		addComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initialization() {

		this.setLayout(new GridLayout(3,1));

		this.listener = new ButtonListener(this);

		// Page panel
		this.jouerPanel = new JPanel();
		GridLayout grd = new GridLayout(2,1);
		grd.setVgap(10);
		jouerPanel.setLayout(grd);
		jouerPanel.setBackground(Batview.transpaColor);

		// Bouton nouvelle partie
		this.nouvellePartie = new JButton("Nouvelle partie");
		setImageBouton(nouvellePartie,"bouton_nouvelle_partie.png");
		nouvellePartie.addActionListener(listener);

		// Bouton reprendre partie
		this.reprendrePartie = new JButton("Reprendre une partie");
		setImageBouton(reprendrePartie,"bouton_reprendre_partie.png");
		reprendrePartie.addActionListener(listener);

		// Bouton retour
		getRetourBouton().addActionListener(listener);
		getRetourBouton().setBorder(new EmptyBorder(new Insets(15,0,15,0)));

	}

	/**
	 * Adds the components to the panels.
	 */
	private void addComponents() {
		add(getLogo());
		add(jouerPanel);

		jouerPanel.add(nouvellePartie);
		jouerPanel.add(reprendrePartie);

		add(getRetourBouton());
	}


	//-----[ METHODS

	/**
	 * Defines what to do then the returnButton is clicked (i.e. which page will be accessed).
	 */
	public void returnButtonAction() {
		getWindow().changeView("accueil");
	}

	/**
	 * Allows to launch a new game.
	 */
	public void launchNew() {

	}


	//-----[ GETTERS

	/**
	 * @return the "Nouvelle partie" button
	 */
	public JButton getNouvellePartie() {
		return nouvellePartie;
	}

	/**
	 * @return the "Reprendre une partie" button
	 */
	public JButton getReprendrePartie() {
		return reprendrePartie;
	}
}