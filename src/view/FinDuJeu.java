package view;

import control.ButtonListener;
import model.Player;
import util.Batview;

import javax.swing.*;
import java.awt.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines how the end of the game will look (scores, who wins...).
 * @author Eva Guignabodet
 */
public class FinDuJeu extends Pages {

	private ButtonListener listener;
	private JLabel nomGagnant;

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public FinDuJeu(LesBatisseurs window, Player winner) {
		super(window, "Fin du jeu");
		initialization(winner);
		addComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initialization(Player winner) {
		this.listener = new ButtonListener(this);

		this.setLayout(new GridLayout(3,1));

		this.nomGagnant = new JLabel("Le meilleur bâtisseur du royaume est " + winner.getPlayerName() + " !");
		nomGagnant.setFont(Batview.gameTitle);
		nomGagnant.setForeground(Color.WHITE);
		nomGagnant.setVerticalAlignment(JLabel.CENTER);
		nomGagnant.setHorizontalAlignment(JLabel.CENTER);

		getQuitterBouton().addActionListener(listener);
	}

	/**
	 * Adds the components to the panels.
	 */
	private void addComponents() {
		add(getLogo());

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(0, 0, 0, 77));
		panel.add(nomGagnant, BorderLayout.CENTER);
		add(panel);
		add(getQuitterBouton());
	}

	/**
	 * Defines what to do then the returnButton is clicked (i.e. which page will be accessed).
	 */
	public void returnButtonAction() {
		getWindow().changeView("accueil");
	}

}