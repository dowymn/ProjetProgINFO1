package view;

import model.Game;
import model.Player;
import util.Batview;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Handles the whole graphical interface
 * @author Eva Guignabodet
 */
public class LesBatisseurs extends JFrame {

	private CardLayout cardLayout;
	private JPanel cards;

	private Accueil accueil;
	private MenuJouer menuJouer;
	private NouvellePartie nouvellePartie;
	private ReprendrePartie reprendrePartie;
	private Plateau plateau;
	private FinDuJeu finDuJeu;

	private boolean[] tutos;

	/**
	 * Initializes the game and calls the initialization() method.
	 */
	public LesBatisseurs() {

		this.setTitle("Les Bâtisseurs : Moyen-Âge");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Dimension dimWindow = new Dimension(1500,900);
		this.setPreferredSize(dimWindow);
		this.setMinimumSize(dimWindow);
		this.setAlwaysOnTop(true);

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
		this.menuJouer = new MenuJouer(this);
		this.nouvellePartie = new NouvellePartie(this);
		this.reprendrePartie = new ReprendrePartie(this);

		this.cardLayout = new CardLayout();
		this.cards = new JPanel(new CardLayout());
		cards.add(accueil, "accueil");
		cards.add(menuJouer,"menuJouer");
		cards.add(nouvellePartie,"nouvellePartie");
		cards.add(reprendrePartie,"reprendrePartie");

		this.tutos = new boolean[8];
		Arrays.fill(tutos, true);

	}

	/**
	 * Allows to change the current view.
	 * @param pageName the new page which has to be shown to the player
	 */
	public void changeView(String pageName) {
		if ( pageName.equals("reprendrePartie") ) {
			cards.remove(reprendrePartie);
			reprendrePartie = new ReprendrePartie(this);
			cards.add(reprendrePartie, "reprendrePartie");
		} else if ( pageName.equals("regles") ) {
			openRegles();
		}
		cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards,pageName);
	}

	/**
	 * Allows to change the current view and launch a game.
	 * Is only used to launch the first instance of the game board.
	 * @param pageName the name of the page
	 * @param game the game instance
	 */
	public void changeView(String pageName, Game game) {
		plateau = new Plateau(this, game, tutos, 1, false);
		cards.add(plateau, "plateau");

		changeView(pageName);
	}

	/**
	 * Allows to change the current view and the end
	 * Is only used to launch the first instance of the game board.
	 * @param pageName the name of the page
	 * @param winner the player who has won the game
	 */
	public void changeView(String pageName, Player winner) {
		finDuJeu = new FinDuJeu(this, winner);
		cards.add(finDuJeu,"finDuJeu");

		changeView(pageName);
	}

	/**
	 * Allows to refresh the game board.
	 * @param game the game instance
	 * @param noTour the turn number
	 */
	public void newPlateauInstance(Game game, int noTour, boolean end) {
		cards.remove(plateau);
		this.plateau = new Plateau(this, game, tutos, noTour, end);
		cards.add(plateau, "plateau");
		changeView("plateau");
	}

	/**
	 * Opens the rules PDF file.
	 */
	private void openRegles() {
		File fichier = new File(Batview.PATHINFOS+"regles.pdf");

		if ( Desktop.isDesktopSupported() ) {
			Desktop desktop = Desktop.getDesktop();
			if ( desktop.isSupported(Desktop.Action.OPEN) ) {
				try {
					desktop.open(fichier);
				} catch ( IOException e ) {
					System.out.println("Error : LesBatisseurs : openRegles() : " + e.getMessage());
				}
			}
		}
	}

}
