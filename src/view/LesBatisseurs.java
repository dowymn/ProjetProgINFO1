package view;

import model.Game;
import util.Batview;

import javax.swing.*;
import java.awt.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Handles the whole graphical interface
 * @author Eva Guignabodet
 */
public class LesBatisseurs extends JFrame {

	private CardLayout cardLayout;
	private JPanel cards;

	private Accueil accueil;
	private Regles regles;
	private MenuJouer menuJouer;
	private NouvellePartie nouvellePartie;
	private ReprendrePartie reprendrePartie;
	private Plateau plateau;

	/**
	 * Initializes the game and calls the initialization() method.
	 */
	public LesBatisseurs() {

		this.setTitle("Les Bâtisseurs : Moyen-Âge");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Dimension dimWindow = new Dimension(1500,900);
		this.setPreferredSize(dimWindow);
		this.setMinimumSize(dimWindow);
		//this.setResizable(false);

		initialization();
		this.add(cards);

		Image icon = Toolkit.getDefaultToolkit().getImage(Batview.PATHIMAGE+"icon.png");
		this.setIconImage(icon);

		this.pack();
		this.setLocationRelativeTo(null);

	}

	/**
	 * Initializes the components.
	 */
	private void initialization() {

		this.accueil = new Accueil(this);
		this.regles = new Regles(this);
		this.menuJouer = new MenuJouer(this);
		this.nouvellePartie = new NouvellePartie(this);
		this.reprendrePartie = new ReprendrePartie(this);
		//this.plateau = new Plateau(this);

		this.cardLayout = new CardLayout();
		this.cards = new JPanel(new CardLayout());
		cards.add(accueil, "accueil");
		cards.add(regles,"regles");
		cards.add(menuJouer,"menuJouer");
		cards.add(nouvellePartie,"nouvellePartie");
		cards.add(reprendrePartie,"reprendrePartie");
		//cards.add(plateau,"plateau");

	}

	/**
	 * Allows to change the current view.
	 * @param pageName the new page which has to be shown to the player
	 */
	public void changeView(String pageName) {
		cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards,pageName);
	}

	/**
	 * Allows to change the current view and launch a game.
	 * @param pageName the name of the page
	 * @param game the game instance
	 */
	public void changeView(String pageName, Game game) {
		plateau = new Plateau(this, game);
		cards.add(plateau, "plateau");

		cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards,pageName);
	}

}
