package view;

import control.*;
import util.Batview;

import javax.swing.*;
import java.awt.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the components of the game's main menu
 * @author Eva Guignabodet
 */
public class Accueil extends Pages {

	private ButtonListener listener;

	private JPanel accueilPanel;
	private JButton jouerButton;
	private JButton reglesButton;

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public Accueil(LesBatisseurs window) {
		super(window, "Les Bâtisseurs : Moyen-Âge");
		this.setLayout(new GridLayout(3,1));
		initialization();
		addComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initialization() {

		add(getLogo());

		GridLayout grd = new GridLayout(2,1);
		grd.setVgap(10);

		this.listener = new ButtonListener(this);

		this.accueilPanel = new JPanel();
		accueilPanel.setLayout(grd);
		accueilPanel.setBackground(Batview.transpaColor);

		// Bouton jouer
		this.jouerButton = new JButton("Jouer");
		setImageBouton(jouerButton,"bouton_jouer.png");
		jouerButton.addActionListener(listener);

		// Bouton règles
		this.reglesButton = new JButton("Règles");
		setImageBouton(reglesButton,"bouton_regles.png");
		reglesButton.addActionListener(listener);

		// Bouton Quitter
		getQuitterBouton().addActionListener(listener);

	}

	/**
	 * Adds the components to the main panel.
	 */
	private void addComponents() {
		accueilPanel.add(jouerButton);
		accueilPanel.add(reglesButton);
		add(this.accueilPanel);
		add(getQuitterBouton());
	}

	/**
	 * Defines what to do then the returnButton is clicked (i.e. which page will be accessed).
	 */
	public void returnButtonAction() {

	}

	public JButton getJouerButton() {
		return jouerButton;
	}

	public JButton getReglesButton() {
		return reglesButton;
	}
}