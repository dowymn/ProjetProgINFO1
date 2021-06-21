package view;

import control.*;
import util.Batview;
import util.Utili;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the graphical interface for the choice of the game the player wants to load.
 * @author Eva Guignabodet
 */
public class ReprendrePartie extends Pages {

	private ButtonListener listener;
	private LoadGameListener loadListener;
	private JPanel reprendrePartiePanel;
	private ArrayList<JButton> partiesButtons;
	private File[] saves;


	//-----[ INITIALIZATION

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public ReprendrePartie(LesBatisseurs window) {
		super(window, "Reprendre une partie");
		initialization();
		addComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initialization() {

		setLayout(new BorderLayout());

		// Listeners
		this.listener = new ButtonListener(this);
		this.loadListener = new LoadGameListener(this);

		// Page panel
		this.reprendrePartiePanel = new JPanel();
		reprendrePartiePanel.setBackground(Batview.transpaColor);
		reprendrePartiePanel.setBorder(new EmptyBorder(new Insets(50,400,50,400)));

		this.saves = Utili.readDirectory(Batview.PATHSAVES);

		// Buttons
		this.partiesButtons = new ArrayList<>();
		createButtons();
		addButtons();

		// Return button
		getRetourBouton().addActionListener(listener);
		getRetourBouton().setBorder(Batview.retourBorder);

	}

	/**
	 * Adds the components to the main panel.
	 */
	private void addComponents() {
		add(getPetitLogo(), BorderLayout.NORTH);
		add(reprendrePartiePanel);
		add(getRetourBouton(), BorderLayout.SOUTH);
	}


	//-----[ METHODS

	/**
	 * Creates the buttons that allow to choose the game to load.
	 */
	private void createButtons() {
		JButton button;
		for ( File file : saves ) {
			button = new JButton(Utili.removeExt(file.getName()));
			button.setBackground(Batview.yellowColor);
			button.setBorder(Batview.repPartieBorder);
			button.setFont(Batview.repPartie);
			button.addActionListener(loadListener);
			partiesButtons.add(button);
		}
	}

	/**
	 * Adds the buttons to the panel.
	 */
	private void addButtons() {
		GridLayout grid = new GridLayout(saves.length,1);
		reprendrePartiePanel.setLayout(grid);
		if ( saves.length < 6 ) {
			if ( saves.length%2==0 ) {
				grid.setRows(6);
				reprendrePartiePanel.add(new JLabel());
			} else {
				grid.setRows(7);
				reprendrePartiePanel.add(new JLabel());
			}
		}
		grid.setVgap(10);

		for ( JButton button : partiesButtons ) {
			reprendrePartiePanel.add(button);
		}

	}




	/**
	 * Defines what to do then the returnButton is clicked (i.e. which page will be accessed).
	 */
	public void returnButtonAction() {
		getWindow().changeView("menuJouer");
	}

	/**
	 * Allows to launch a game a the point it has been stopped.
	 * @param gameName the name of the game
	 */
	public void launchGame(String gameName) {

	}

}