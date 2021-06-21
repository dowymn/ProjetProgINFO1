package view;

import control.*;
import model.*;
import util.Batview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines how the board will look during the games.
 * @author Eva Guignabodet
 */
public class Plateau extends Pages {

	private ButtonListener listener;
	private BoardListener boardListener;

	private final Game game;

	// PANELS
	private JPanel plateauPanel;
	private JPanel header;
	private JPanel footer;

	// Left side of the board
	private JPanel leftSide;
	private JPanel pioche;
	private JPanel actionButtonsPanel;
	private JPanel piocheOuvriers;
	private JPanel piocheBatiments;

	// Right side of the board
	private JPanel rightSide;
	private JPanel joueurCards;
	private JPanel ouvriersJr;
	private JPanel chantiersJr;
	private JPanel batsFinisJr;


	// LABELS
	// header
	private JLabel noTour;
	private JLabel ecusJoueur;
	private JLabel ptsVicJoueur;
	// footer
	private JLabel actionsRestantes;
	// left side
	private JLabel piocheTitle;
	private JLabel joueurTitle;

	// BUTTONS
	private JButton saveButton;
	private JButton[] actionButtons;


	//-----[ INITIALIZATION

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public Plateau(LesBatisseurs window, Game game) {
		super(window, "Plateau");

		if ( game == null ) {
			throw new IllegalArgumentException("Error : Plateau() : game mustn't be null.");
		}
		this.game = game;

		initialization();
		addComponents();

	}

	/**
	 * Initializes the components.
	 */
	private void initialization() {
		setLayout(new BorderLayout());
		this.setBackground(Batview.transpBlackColor);

		// LISTENERS
		this.listener = new ButtonListener(this);
		this.boardListener = new BoardListener(this);

		// PLATEAU
		this.plateauPanel = new JPanel(new GridLayout(1,2));
		plateauPanel.setBackground(Batview.transpBlackColor);
		plateauPanel.setBorder(new EmptyBorder(new Insets(30,0,0,0)));

		// HEADER
		this.header = new JPanel(new GridLayout(1,3));
		header.setBackground(Batview.transpWhiteColor);
		header.setBorder(Batview.retourBorder);

		// FOOTER
		this.footer = new JPanel(new GridLayout(1,2,0,0));
		footer.setBackground(Batview.transpaColor);

		// LEFT SIDE PANELS
		this.leftSide = new JPanel(new BorderLayout());
		leftSide.setBackground(Batview.transpaColor);
		// Action buttons panel
		this.actionButtonsPanel = new JPanel(new GridLayout(4,2,0,0));
		actionButtonsPanel.setBackground(Batview.transpaColor);
		actionButtonsPanel.setBorder(new EmptyBorder(new Insets(10,0,10,0)));
		// Pioche panel
		this.pioche = new JPanel(new GridLayout(2,1,0,15));
		pioche.setBackground(Batview.transpaColor);
		pioche.setBorder(new EmptyBorder(new Insets(0,15,0,15)));
		// Pioche ouvriers
		this.piocheOuvriers = new JPanel(new GridLayout(1,5));
		piocheOuvriers.setBackground(Batview.transpWhiteColor);
		piocheOuvriers.setBorder(Batview.piocheBorder);
		// Pioche batiments
		this.piocheBatiments = new JPanel(new GridLayout(1,5));
		piocheBatiments.setBackground(Batview.transpWhiteColor);
		piocheBatiments.setBorder(Batview.piocheBorder);

		// RIGHT SIDE PANELS
		this.rightSide = new JPanel(new BorderLayout());
		rightSide.setBackground(Batview.transpaColor);
		// JoueurCards
		joueurCards = new JPanel(new GridLayout(3,1,0,15));
		joueurCards.setBackground(Batview.transpaColor);
		joueurCards.setBorder(new EmptyBorder(new Insets(0,15,18,15)));
		// Ouvriers jr
		ouvriersJr = new JPanel(new GridLayout(1,5));
		ouvriersJr.setBackground(Batview.transpWhiteColor);
		ouvriersJr.setBorder(Batview.piocheBorder);
		// Chantiers jr
		chantiersJr = new JPanel(new GridLayout(1,5));
		chantiersJr.setBackground(Batview.transpWhiteColor);
		chantiersJr.setBorder(Batview.piocheBorder);
		// Batiments finis jr
		batsFinisJr = new JPanel(new GridLayout(1,5));
		batsFinisJr.setBackground(Batview.transpWhiteColor);
		batsFinisJr.setBorder(Batview.piocheBorder);


		// LABELS

		// Pioche title
		this.piocheTitle = new JLabel("Pioche");
		piocheTitle.setForeground(Color.WHITE);
		piocheTitle.setHorizontalAlignment(JLabel.CENTER);
		piocheTitle.setFont(Batview.gameTitle);
		piocheTitle.setVerticalAlignment(JLabel.NORTH);

		// Joueur title
		this.joueurTitle = new JLabel(game.getCurrent().getPlayerName());
		joueurTitle.setForeground(Color.WHITE);
		joueurTitle.setHorizontalAlignment(JLabel.CENTER);
		joueurTitle.setFont(Batview.gameTitle);
		joueurTitle.setVerticalAlignment(JLabel.NORTH);
		joueurTitle.setBorder(new EmptyBorder(new Insets(0,20,0,0)));

		// Actions restantes
		this.actionsRestantes = new JLabel("Actions restantes : " + game.getCurrent().getActions());
		actionsRestantes.setHorizontalAlignment(JLabel.CENTER);
		actionsRestantes.setFont(Batview.gameSubtitle);
		actionsRestantes.setForeground(Color.WHITE);

		// Tour
		this.noTour = new JLabel("Tour n°1");
		noTour.setHorizontalAlignment(JLabel.CENTER);
		noTour.setVerticalAlignment(JLabel.BOTTOM);
		noTour.setFont(Batview.hdrNvlPartie);
		noTour.setForeground(Color.BLACK);

		// Ecus
		this.ecusJoueur = new JLabel("Mes écus : " + game.getCurrent().getNbEcus());
		ecusJoueur.setHorizontalAlignment(JLabel.CENTER);
		ecusJoueur.setFont(Batview.gameSubtitle);
		ecusJoueur.setForeground(Color.BLACK);

		// Points de victoire
		this.ptsVicJoueur = new JLabel("Mes points de victoire : " + game.getCurrent().getNbPtsVic());
		ptsVicJoueur.setHorizontalAlignment(JLabel.CENTER);
		ptsVicJoueur.setFont(Batview.gameSubtitle);
		ptsVicJoueur.setForeground(Color.BLACK);


		// BUTTONS
		// Save button
		this.saveButton = new JButton();
		setImageGameBouton(saveButton, "bouton_sav_quitter.png");
		saveButton.setHorizontalAlignment(JButton.CENTER);
		saveButton.addActionListener(listener);
		saveButton.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		// Action buttons
		this.actionButtons = new JButton[8];
		for ( int i = 0 ; i < actionButtons.length ; i++ ) {
			actionButtons[i] = new JButton();
			actionButtons[i].addActionListener(boardListener);
			actionButtons[i].setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		}
		setImageGameBouton(actionButtons[0],"bouton_ouvrir_chantier.png");
		setImageGameBouton(actionButtons[1], "bouton_1ecu.png");
		setImageGameBouton(actionButtons[2], "bouton_recruter.png");
		setImageGameBouton(actionButtons[3], "bouton_3ecus.png");
		setImageGameBouton(actionButtons[4], "bouton_envoi_ouvrier.png");
		setImageGameBouton(actionButtons[5], "bouton_6ecus.png");
		setImageGameBouton(actionButtons[6], "bouton_acheter_action.png");
		setImageGameBouton(actionButtons[7], "bouton_finir_tour.png");



	}

	/**
	 * Adds the components to the main panel and into the components themselves.
	 */
	private void addComponents() {
		// Main adds
		add(header, BorderLayout.NORTH);
		add(plateauPanel, BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);

		// Header
		header.add(ecusJoueur);
		header.add(noTour);
		header.add(ptsVicJoueur);

		// Footer
		footer.add(actionsRestantes);
		footer.add(saveButton);

		// Plateau panel
		plateauPanel.add(leftSide);
		plateauPanel.add(rightSide);

		// LeftSide
		leftSide.add(piocheTitle, BorderLayout.NORTH);
		leftSide.add(pioche, BorderLayout.CENTER);
		leftSide.add(actionButtonsPanel, BorderLayout.SOUTH);

		// Pioche
		pioche.add(piocheOuvriers);
		pioche.add(piocheBatiments);

		// Right side
		rightSide.add(joueurTitle, BorderLayout.NORTH);
		rightSide.add(joueurCards);
		JScrollPane[] jsp = new JScrollPane[3];
		jsp[0] = new JScrollPane(ouvriersJr);
		jsp[1] = new JScrollPane(chantiersJr);
		jsp[2] = new JScrollPane(batsFinisJr);
		for (JScrollPane j : jsp) {
			j.setBackground(Batview.transpaColor);
			j.setBorder(Batview.emptyBorder);
		}
		joueurCards.add(jsp[0]);
		joueurCards.add(jsp[1]);
		joueurCards.add(jsp[2]);

		// Actions buttons panel
		for ( JButton button : actionButtons ) {
			actionButtonsPanel.add(button);
		}

		displayPioche();
		displayJrCards();

	}


	//-----[ METHODS

	/**
	 * Allows to display the Worker and Building cards of the deck.
	 */
	public void displayPioche() {

		double ratio = (double) 140/100;
		//int height = piocheOuvriers.getHeight()-10;
		//int width = (int) (height/ratio);
		int height = 140;
		if ( getWindow().getHeight() > 900 && getWindow().getHeight() < 1000 ) {
			height = 170;
		} else if ( getWindow().getHeight() > 1000 ) {
			height = 200;
		}
		int width = (int) (height/ratio);


		System.out.println("Before:");
		System.out.println("height = " + piocheOuvriers.getMaximumSize());
		System.out.println("width = " + piocheOuvriers.getWidth());

		// Workers
		int max = 5;
		if ( game.getAvailableWorkers().size() < 5 ) {
			max = game.getAvailableWorkers().size();
		}
		WorkerCard worker;
		for ( int i = 0 ; i < max ; i++ ) {
			worker = game.getAvailableWorkers().get(i);
			ImageIcon img = Batview.sizedCard(worker.getCardImage(),width,height);
			piocheOuvriers.add(new JLabel(img));
		}


		ratio = (double) 140/120;
		width = (int) (height/ratio);

		// Buildings
		max = 5;
		if ( game.getAvailableBuildings().size() < 5 ) {
			max = game.getAvailableWorkers().size();
		}
		Card card;
		for ( int i = 0 ; i < max ; i++ ) {
			card = game.getAvailableBuildings().get(i);
			if ( card.getType() == TypeCard.Building ) {
				BuildingCard building = (BuildingCard) card;
				ImageIcon img = Batview.sizedCard(building.getCardImage(),width,height);
				piocheBatiments.add(new JLabel(img));
			} else if ( card.getType() == TypeCard.Machine ) {
				MachineCard building = (MachineCard) card;
				ImageIcon img = Batview.sizedCard(building.getCardImageR(),width,height);
				piocheBatiments.add(new JLabel(img));
			}
		}

		System.out.println("After:");
		System.out.println("height = " + piocheOuvriers.getHeight());
		System.out.println("width = " + piocheOuvriers.getWidth());

	}

	/**
	 * Allows to display the Worker and Building cards of the current player.
	 */
	public void displayJrCards() {

		double ratio = (double) 140/100;
		//int height = piocheOuvriers.getHeight()-10;
		//int width = (int) (height/ratio);
		int height = 140;
		if ( getWindow().getHeight() > 900 && getWindow().getHeight() < 1000 ) {
			height = 170;
		} else if ( getWindow().getHeight() > 1000 ) {
			height = 200;
		}
		int width = (int) (height/ratio);

		Player player = game.getCurrent();

		// Workers
		WorkerCard worker;
		for ( int i = 0 ; i < player.getPlayerWorkers().size() ; i++ ) {
			worker = player.getPlayerWorkers().get(i);
			ImageIcon img = Batview.sizedCard(worker.getCardImage(),width,height);
			piocheOuvriers.add(new JLabel(img));
		}


		ratio = (double) 140/120;
		width = (int) (height/ratio);

		// Machines
		MachineCard machine;
		for ( int i = 0 ; i < player.getInProgressMachines().size() ; i++ ) {
			machine = player.getPlayerMachines().get(i);
			ImageIcon img = Batview.sizedCard(machine.getCardImageR(),width,height);
			ouvriersJr.add(new JLabel(img));
		}

		// Buildings
		BuildingCard building;
		for ( int i = 0 ; i < player.getInProgressMachines().size() ; i++ ) {
			building = player.getPlayerBuildings().get(i);
			ImageIcon img = Batview.sizedCard(building.getCardImage(),width,height);
			ouvriersJr.add(new JLabel(img));
		}

	}

	/**
	 * Defines what to do when the "Sauvegarder et quitter" button is clicked.
	 * Displays a pop-up to ask the player how he wants to name his save.
	 * Then calls the game's saveGame() method if a valid name is entered and if the "Valider" button is clicked.
	 */
	public void returnButtonAction() {
		String name = JOptionPane.showInputDialog(this,
				"Comment veux-tu nommer ta sauvegarde ?", null);

		if ( name != null && !name.equals("") ) {
			game.saveTheGame(name);
			getWindow().changeView("accueil");
		}

	}


	//-----[ GETTERS

	/**
	 * @return the "Sauvegarder et quitter" button
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * @return the list that contains the game action buttons
	 */
	public JButton[] getActionButtons() {
		return actionButtons;
	}
}