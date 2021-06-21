package view;

import control.*;
import model.Game;
import model.PlayerMode;
import util.Batview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Page that allows to begin a new game.
 * @author Eva Guignabodet
 */
public class NouvellePartie extends Pages {

    private ButtonListener listener;
    private JPanel nouvellePartiePanel;

    private JLabel[] joueursLabel;
    private JTextField[] playerNames;
    ArrayList<JComboBox<TypeJoueur>> typeJrs;

    private JPanel boutonsPanel;
    private JButton validerBouton;


    //-----[ INITIALIZATION

    /**
     * Calls the initialization() method.
     * @param window the window instance
     */
    public NouvellePartie(LesBatisseurs window) {
        super(window, "Nouvelle Partie");
        initialization();
        addComponents();
    }

    /**
     * Initializes the components.
     */
    private void initialization() {

        setLayout(new BorderLayout());

        this.listener = new ButtonListener(this);

        // Boutons
        this.boutonsPanel = new JPanel(new GridLayout(1,2,50,0));
        boutonsPanel.setBackground(Batview.transpaColor);
        boutonsPanel.setBorder(Batview.retourBorder);
        getRetourBouton().setHorizontalAlignment(JButton.LEFT);
        getRetourBouton().addActionListener(listener);
        this.validerBouton = new JButton();
        setImageBouton(validerBouton,"bouton_valider.png");
        validerBouton.setHorizontalAlignment(JButton.RIGHT);
        validerBouton.addActionListener(listener);

        // Page panel
        this.nouvellePartiePanel = new JPanel();
        nouvellePartiePanel.setLayout(new GridLayout(5,3,30,20));
        nouvellePartiePanel.setBackground(Batview.transpaColor);
        nouvellePartiePanel.setBorder(new EmptyBorder(new Insets(120,300,120,300)));

        // Joueurs labels
        this.joueursLabel = new JLabel[4];
        for ( int i = 0 ; i < joueursLabel.length ; i++ ) {
            joueursLabel[i] = new JLabel();
            joueursLabel[i].setFont(Batview.jrNvlPartie);
            joueursLabel[i].setText("Joueur " + (i+1));
            joueursLabel[i].setForeground(Color.WHITE);
            joueursLabel[i].setHorizontalAlignment(JLabel.RIGHT);
        }

        // JComboBoxes
        this.typeJrs = new ArrayList<>();
        JComboBox<TypeJoueur> tjr;
        for ( int i = 0 ; i < 4 ; i++ ) {
            tjr = new JComboBox<>(TypeJoueur.values());
            tjr.setSelectedItem(TypeJoueur.Aucun);
            tjr.setBackground(Batview.yellowColor);
            tjr.setBorder(Batview.repPartieBorder);
            tjr.setFont(Batview.littleFont);
            typeJrs.add(tjr);
        }
        typeJrs.get(0).setSelectedItem(TypeJoueur.Humain);
        typeJrs.get(0).removeItem(TypeJoueur.Aucun);
        typeJrs.get(0).removeItem(TypeJoueur.Automatique);
        typeJrs.get(1).setSelectedItem(TypeJoueur.Humain);
        typeJrs.get(1).removeItem(TypeJoueur.Aucun);


        // JTextFields
        this.playerNames = new JTextField[4];
        JTextField txf;
        for ( int i = 0 ; i < playerNames.length ; i++ ) {
            txf = new JTextField();
            txf.setBorder(Batview.repPartieBorder);
            txf.setFont(Batview.littleFont);
            txf.setMargin(new Insets(10,10,10,10));
            playerNames[i] = txf;
        }

    }

    /**
     * Adds the components to the main panel.
     */
    private void addComponents() {

        add(getPetitLogo(), BorderLayout.NORTH);
        add(nouvellePartiePanel, BorderLayout.CENTER);

        boutonsPanel.add(validerBouton);
        boutonsPanel.add(getRetourBouton());
        add(boutonsPanel, BorderLayout.SOUTH);

        // Header
        JLabel hdrEmpty = new JLabel();
        nouvellePartiePanel.add(hdrEmpty);

        JLabel hdrType = new JLabel("Type de joueur");
        hdrType.setFont(Batview.hdrNvlPartie);
        hdrType.setHorizontalAlignment(JLabel.CENTER);
        hdrType.setForeground(Color.WHITE);
        nouvellePartiePanel.add(hdrType);

        JLabel hdrName = new JLabel("Nom du joueur");
        hdrName.setFont(Batview.hdrNvlPartie);
        hdrName.setHorizontalAlignment(JLabel.CENTER);
        hdrName.setForeground(Color.WHITE);
        nouvellePartiePanel.add(hdrName);

        // Information to fill
        for ( int i = 0 ; i < 4 ; i++ ) {
            nouvellePartiePanel.add(joueursLabel[i]);
            nouvellePartiePanel.add(typeJrs.get(i));
            nouvellePartiePanel.add(playerNames[i]);
        }

    }


    //-----[ METHODS

    /**
     * Checks if the information has been correctly filled by the user.
     * @param mode the chosen mode
     * @param names the list of names
     * @return true if the information is correct, else false
     */
    private boolean verifInformation( String mode, ArrayList<String> names ) {
        boolean result = true;
        if ( mode.length() != names.size() ) {
            result = false;
        }
        for ( String name : names ) {
            if ( name.equals("") ) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Computes the information given by the user and then calls the launchGame() method.
     */
    public void computeInformation() {
        String mode = "";
        ArrayList<String> names = new ArrayList<>();

        for ( int i = 0 ; i < 4 ; i++ ) {
            if ( typeJrs.get(i).getSelectedItem() == TypeJoueur.Humain ) {
                names.add( playerNames[i].getText() );
                mode += "H";
            }
        }
        for ( int i = 0 ; i < 4 ; i++ ) {
            if ( typeJrs.get(i).getSelectedItem() == TypeJoueur.Automatique ) {
                names.add( playerNames[i].getText() );
                mode += "A";
            }
        }

        if ( verifInformation(mode, names) ) {

            // we add null values to the name ArrayList because it is needed to call the game
            // but these values are managed in the Game class, so don't worry
            if ( names.size() == 2 ) {
                names.add(null);
                names.add(null);
            } else if ( names.size() == 3 ) {
                names.add(null);
            }

            PlayerMode type = PlayerMode.valueOf(mode);
            launchGame(type, names.get(0), names.get(1), names.get(2), names.get(3));

        } else {

            for ( int i = 0 ; i < playerNames.length ; i++ ) {
                if ( typeJrs.get(i).getSelectedItem() != TypeJoueur.Aucun && playerNames[i].getText().equals("") ) {
                    playerNames[i].setBackground(Batview.clearRedColor);
                }
            }

        }

    }

    /**
     * Lauches the game.
     * @param mode the player mode
     * @param p1 the 1st player's name
     * @param p2 the 2nd player's name
     * @param p3 the 3rd player's name
     * @param p4 the 4th player's name
     */
    private void launchGame(PlayerMode mode, String p1, String p2, String p3, String p4) {
        Game game = new Game(mode,true, p1, p2, p3, p4);
        resetComponents();
        getWindow().changeView("plateau", game);
    }

    private void resetComponents() {
        for ( JTextField name : playerNames ) {
            name.setText("");
        }

        typeJrs.get(1).setSelectedItem(TypeJoueur.Humain);
        typeJrs.get(2).setSelectedItem(TypeJoueur.Aucun);
        typeJrs.get(3).setSelectedItem(TypeJoueur.Aucun);

    }

    /**
     * Defines what to do then the returnButton is clicked (i.e. which page will be accessed).
     */
    public void returnButtonAction() {
        getWindow().changeView("menuJouer");
        resetComponents();
    }


    //-----[ GETTERS

    /**
     * @return the "Valider" button
     */
    public JButton getValiderBouton() {
        return validerBouton;
    }
}