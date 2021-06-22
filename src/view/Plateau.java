package view;

import control.*;
import model.*;
import util.Batview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import java.util.Timer;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines how the board will look during the games.
 * @author Eva Guignabodet
 */
public class Plateau extends Pages {

	private ButtonListener listener;
	private BoardListener boardListener;
	private CardListener cardListener;

	private final Game game;
	private int nbTour;
	private boolean gameOver;

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
	public Plateau(LesBatisseurs window, Game game, boolean[] tutos, int nbTour, boolean end) {
		super(window, "Plateau");

		if ( game == null ) {
			throw new IllegalArgumentException("Error : Plateau() : game mustn't be null.");
		}
		this.game = game;

		if ( nbTour < 1 ) {
			throw new IllegalArgumentException("Error : Plateau() : nbTour mustn't be < 1.");
		}
		this.nbTour = nbTour;

		this.gameOver = end;

		initialization(tutos);
		addComponents();

		if ( game.getCurrent().getIsBot() ) {
			playBot();
		}

	}

	/**
	 * Initializes the components.
	 */
	private void initialization(boolean[] tutos) {
		setLayout(new BorderLayout());
		this.setBackground(Batview.transpBlackColor);

		// LISTENERS
		this.listener = new ButtonListener(this);
		this.cardListener = new CardListener(this);
		this.boardListener = new BoardListener(this, cardListener, tutos);

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
		piocheOuvriers.setBackground(Batview.transpaColor);
		piocheOuvriers.setBorder(Batview.piocheBorder);
		// Pioche batiments
		this.piocheBatiments = new JPanel(new GridLayout(1,5));
		piocheBatiments.setBackground(Batview.transpaColor);
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
		ouvriersJr.setBackground(Batview.transpaColor);
		ouvriersJr.setBorder(Batview.piocheBorder);
		// Chantiers jr
		chantiersJr = new JPanel(new GridLayout(1,5));
		chantiersJr.setBackground(Batview.transpaColor);
		chantiersJr.setBorder(Batview.piocheBorder);
		// Batiments finis jr
		batsFinisJr = new JPanel(new GridLayout(1,5));
		batsFinisJr.setBackground(Batview.transpaColor);
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
		this.noTour = new JLabel("Tour n°" + nbTour);
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
	 * Allows the auto player to play its turn.
	 */
	public void playBot() {
		game.getCurrent().play();
		JOptionPane.showMessageDialog(null,"Le joueur automatique vient de jouer." +
				"\nObserve ce qu'il a fait puis finis son tour.");
		disableButtons(actionButtons[7]);
	}

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


		// Workers
		int max = 5;
		if ( game.getAvailableWorkers().size() < 5 ) {
			max = game.getAvailableWorkers().size();
		}
		WorkerCard worker;
		for ( int i = 0 ; i < max ; i++ ) {
			worker = game.getAvailableWorkers().get(i);
			JButton lab = new JButton();
			setCardBouton(lab, Batview.sizedCard(worker.getCardImage(),width,height));
			lab.addActionListener(cardListener);

			piocheOuvriers.add(lab);
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
				JButton lab = new JButton();
				setCardBouton(lab, Batview.sizedCard(building.getCardImage(),width,height));
				lab.addActionListener(cardListener);
				piocheBatiments.add(lab);
			} else if ( card.getType() == TypeCard.Machine ) {
				MachineCard building = (MachineCard) card;
				JButton lab = new JButton();
				setCardBouton(lab, Batview.sizedCard(building.getCardImageR(),width,height));
				lab.addActionListener(cardListener);
				piocheBatiments.add(lab);
			}
		}

	}

	/**
	 * Allows to display the Worker and Building cards of the current player.
	 */
	public void displayJrCards() {

		double ratio = (double) 140/100;
		int height = 160;
		if ( getWindow().getHeight() > 900 && getWindow().getHeight() < 1000 ) {
			height = 180;
		} else if ( getWindow().getHeight() > 1000 ) {
			height = 200;
		}
		int width = (int) (height/ratio);

		Player player = game.getCurrent();
		Card card;

		// Workers
		for ( int i = 0 ; i < player.getFreeWM().size() ; i++ ) {
			card = player.getFreeWM().get(i);
			if ( card.getType() == TypeCard.Worker ) {
				WorkerCard worker = (WorkerCard) card;
				JButton lab = new JButton();
				setCardBouton(lab, Batview.sizedCard(worker.getCardImage(), width, height));
				lab.addActionListener(cardListener);
				lab.setVerticalAlignment(JButton.CENTER);
				ouvriersJr.add(lab);
			} else if ( card.getType() == TypeCard.Machine ) {
				MachineCard worker = (MachineCard) card;
				JButton lab = new JButton();
				setCardBouton(lab, Batview.sizedCard(worker.getCardImageV(), width, height));
				lab.addActionListener(cardListener);
				lab.setVerticalAlignment(JButton.CENTER);
				ouvriersJr.add(lab);
			}
		}


		ratio = (double) 140/120;
		width = (int) (height/ratio);

		// Buidlings
		for ( int i = 0 ; i < player.getChantiers().size() ; i++ ) {
			card = player.getChantiers().get(i);
			if ( card.getType() == TypeCard.Building ) {
				BuildingCard building = (BuildingCard) card;
				JButton lab = new JButton();
				setCardBouton(lab, Batview.sizedCard(building.getCardImage(),width,height));
				lab.setVerticalAlignment(JButton.CENTER);
				lab.addActionListener(cardListener);
				// the hover stats system that allows to show the card's stats
				lab.addMouseListener(new MouseAdapter() {
					public Timer timer;
					@Override
					public void mouseEntered(MouseEvent e) {
						if ( e.getSource() == lab ) {
							timer = startTimer(lab,getStatsChantier(building));
						}
					}
					@Override
					public void mouseExited(MouseEvent e) {
						timer.cancel();
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						timer.cancel();
					}
				});

				chantiersJr.add(lab);
			} else if ( card.getType() == TypeCard.Machine ) {
				MachineCard building = (MachineCard) card;
				JButton lab = new JButton();
				setCardBouton(lab, Batview.sizedCard(building.getCardImageR(),width,height));
				lab.setVerticalAlignment(JButton.CENTER);
				lab.addActionListener(cardListener);
				// the hover stats system that allows to show the card's stats
				lab.addMouseListener(new MouseAdapter() {
					public Timer timer;
					@Override
					public void mouseEntered(MouseEvent e) {
						if ( e.getSource() == lab ) {
							timer = startTimer(lab,getStatsChantier(building));
						}
					}
					@Override
					public void mouseExited(MouseEvent e) {
						timer.cancel();
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						timer.cancel();
					}
				});
				chantiersJr.add(lab);
			}

		}

		// Finished buildings
		for ( int i = 0 ; i < player.getFinishedBuildings().size() ; i++ ) {
			BuildingCard building = player.getFinishedBuildings().get(i);
			JButton lab = new JButton();
			setCardBouton(lab, Batview.sizedCard(building.getCardImage(), width, height));
			lab.addActionListener(cardListener);
			lab.setVerticalAlignment(JButton.CENTER);
			batsFinisJr.add(lab);
		}

	}

	/**
	 * Allows to get the actual stats of a card.
	 * @param card the card - building or machine
	 * @return a String chain that contains the card's stats
	 */
	private String getStatsChantier(Card card) {
		StringBuilder stats = new StringBuilder();

		if ( card.getType() == TypeCard.Building ) {
			BuildingCard building = (BuildingCard) card;
			int surPierre = building.getPierreNec();
			int surBois = building.getBoisNec();
			int surSavoir = building.getSavoirNec();
			int surTuile = building.getTuileNec();

			int pierre = 0;
			int bois = 0;
			int savoir = 0;
			int tuile = 0;
			for ( WorkerCard worker : building.getWorkers() ) {
				pierre += worker.getPierreProd();
				bois += worker.getBoisProd();
				savoir += worker.getSavoirProd();
				tuile += worker.getTuileProd();
			}
			for ( MachineCard worker : building.getMachines() ) {
				pierre += worker.getPierreProd();
				bois += worker.getBoisProd();
				savoir += worker.getSavoirProd();
				tuile += worker.getTuileProd();
			}
			stats.append("Pierre : ").append(pierre).append("/").append(surPierre);
			stats.append("\nBois   : ").append(bois).append("/").append(surBois);
			stats.append("\nSavoir : ").append(savoir).append("/").append(surSavoir);
			stats.append("\nTuile  : ").append(tuile).append("/").append(surTuile);
		}

		else if ( card.getType() == TypeCard.Machine ) {
			MachineCard building = (MachineCard) card;
			int surPierre = building.getPierreNec();
			int surBois = building.getBoisNec();
			int surSavoir = building.getSavoirNec();
			int surTuile = building.getTuileNec();

			int pierre = 0;
			int bois = 0;
			int savoir = 0;
			int tuile = 0;
			for ( WorkerCard worker : building.getWorkers() ) {
				pierre += worker.getPierreProd();
				bois += worker.getBoisProd();
				savoir += worker.getSavoirProd();
				tuile += worker.getTuileProd();
			}
			stats.append("Pierre : ").append(pierre).append("/").append(surPierre);
			stats.append("\nBois   : ").append(bois).append("/").append(surBois);
			stats.append("\nSavoir : ").append(savoir).append("/").append(surSavoir);
			stats.append("\nTuile  : ").append(tuile).append("/").append(surTuile);
		}

		return stats.toString();
	}

	/**
	 * Allows to display a message when the button is hovered for 3 seconds.
	 * @param lab the button
	 * @param text the text to show
	 * @return the created timer
	 */
	private Timer startTimer(JButton lab, String text) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(lab, text);
			}
		};

		Timer timer = new Timer(true);
		timer.schedule(task, 3000);
		return timer;
	}

	/**
	 * Allows to refresh the game board by calling the needed method from the LesBatisseurs class.
	 */
	public void refreshPlateau() {
		getWindow().newPlateauInstance(game, nbTour, this.gameOver);
	}



	//-----[ BUTTONS ACTIONS

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

	/**
	 * Allows to enable the action buttons.
	 */
	public void enableButtons() {
		for ( JButton button : actionButtons ) {
			button.setEnabled(true);
		}
	}

	/**
	 * Allows to disable the action buttons.
	 */
	public void disableButtons(JButton butbutton) {
		for ( JButton button : actionButtons ) {
			if ( button != butbutton ) {
				button.setEnabled(false);
			}
		}
	}

	/**
	 * Defines what happens when the "Ouvrir un chantier" button is clicked.
	 */
	public void ouvrirChantierButton(Card card) {
		game.getCurrent().startBuilding(card);
		refreshPlateau();
	}

	/**
	 * Defines what happens when the "Recruter un ouvrier" button is clicked.
	 * @param worker the selected worker
	 */
	public void recruterOuvrierButton(WorkerCard worker) {
		game.getCurrent().hireWorker(worker);
		refreshPlateau();
	}

	/**
	 * Defines what happens when the "Prendre 1 écu" button is clicked.
	 */
	public void prendre1EcuButton() {
		game.getCurrent().takeEcus(1);
		refreshPlateau();
	}

	/**
	 * Defines what happens when the "Prendre 3 écus" button is clicked.
	 */
	public void prendre3EcusButton() {
		game.getCurrent().takeEcus(3);
		refreshPlateau();
	}

	/**
	 * Defines what happens when the "Prendre 6 écus" button is clicked.
	 */
	public void prendre6EcusButton() {
		game.getCurrent().takeEcus(6);
		refreshPlateau();
	}

	/**
	 * Defines what happens when the "Envoyer travailler un ouvrier" button is clicked.
	 */
	public void envoiOuvrierButton(Card worker, Card building) {
		game.getCurrent().sendWorkerToWork(worker, building);
		refreshPlateau();
	}

	/**
	 * Defines what happens when the "Acheter une action" button is clicked.
	 */
	public void acheterActionButton( int nb ) {
		game.getCurrent().buyAction(nb);
		refreshPlateau();
	}

	/**
	 * Defines what happens when the "Finir mon tour" button is clicked.
	 */
	public void finirTourButton() {
		if ( getGame().isPlayerWinner(getGame().getCurrent()) ) {
			setGameOver(true);
		}
		if ( this.gameOver && game.getCurrent() == game.getPlayers()[game.getPlayers().length-1] ) {
			endOfGame();
		} else {
			if (game.getCurrent() == game.getPlayers()[0]) {
				nbTour++;
			}
			game.getCurrent().resetActions();
			game.getCurrent().resetConstructionTurn();
			game.changeCurrent();

			refreshPlateau();
		}
	}



	//-----[ END OF THE GAME

	/**
	 * Changes the state of the end attribute.
	 * @param end the attribute's state
	 */
	public void setGameOver(boolean end) {
		this.gameOver = end;
	}

	/**
	 * Defines what happens when this is the end of the game
	 */
	public void endOfGame() {
		Player winner = game.sendWinner();
		getWindow().changeView("finDuJeu",winner);
	}


	//-----[ GETTERS

	/**
	 * @return the game instance
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @return the piocheOuvriers panel
	 */
	public JPanel getPiocheOuvriers() {
		return piocheOuvriers;
	}

	/**
	 * @return the piocheBatiments panel
	 */
	public JPanel getPiocheBatiments() {
		return piocheBatiments;
	}

	/**
	 * @return the ouvriersJr panel
	 */
	public JPanel getOuvriersJr() {
		return ouvriersJr;
	}

	/**
	 * @return the chantiersJr panel
	 */
	public JPanel getChantiersJr() {
		return chantiersJr;
	}

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