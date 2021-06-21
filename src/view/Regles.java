package view;

import control.*;
import util.Batview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the page where the rules of the game are shown.
 * @author Eva Guignabodet
 */
public class Regles extends Pages {

	private ButtonListener listener;
	private JPanel reglesPanel;

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public Regles(LesBatisseurs window) {
		super(window, "Regles");
		initialization();
		addComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initialization() {

		setLayout(new BorderLayout());

		this.listener = new ButtonListener(this);

		// Panel de la page
		this.reglesPanel = new JPanel();
		reglesPanel.setBackground(Batview.transpaColor);

		// Bouton retour
		getRetourBouton().addActionListener(listener);
		getRetourBouton().setBorder(Batview.retourBorder);

	}

	/**
	 * Adds the components to the main panel.
	 */
	private void addComponents() {
		add(getPetitLogo(), BorderLayout.NORTH);
		add(reglesPanel, BorderLayout.CENTER);
		add(getRetourBouton(), BorderLayout.SOUTH);
	}

	private void showPDF() {

	}

	/**
	 * Defines what to do then the returnButton is clicked (i.e. which page will be accessed).
	 */
	public void returnButtonAction() {
		getWindow().changeView("accueil");
	}

}