package control;

import model.Card;
import model.WorkerCard;
import view.Plateau;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardListener implements ActionListener {

    private Plateau plateau;

    public boolean piocheOuvriers;
    public boolean piocheBatiments;
    public boolean jrOuvriers;
    public boolean jrChantiers;

    private Card workerToSend;


    /**
     * Initializes the game board.
     * @param plateau the linked instance
     */
    public CardListener(Plateau plateau) {
        if ( plateau == null ) {
            throw new IllegalArgumentException("Error : BoardListener() : plateau mustn't be null.");
        }
        this.plateau = plateau;

        this.piocheOuvriers = false;
        this.piocheBatiments = false;
        this.jrOuvriers = false;
        this.jrChantiers = false;

        workerToSend = null;

    }

    /**
     * ActionPerformed method.
     * If you don't know what it is used for, read the ActionListener javadoc.
     * @param e the ActionEvent param
     */
    public void actionPerformed(ActionEvent e) {

        if ( piocheOuvriers ) {
            Component[] components = plateau.getPiocheOuvriers().getComponents();
            int i = 0;
            boolean found = false;
            while ( i < components.length && !found ) {
                if ( e.getSource() == components[i] ) {
                    found = true;
                    plateau.setImageGameBouton(plateau.getActionButtons()[2], "bouton_recruter.png");
                    plateau.recruterOuvrierButton(plateau.getGame().getAvailableWorkers().get(i));
                }
                i++;
            }
            plateau.enableButtons();
            piocheOuvriers = false;
        }

        else if ( piocheBatiments ) {
            Component[] components = plateau.getPiocheBatiments().getComponents();

            int i = 0;
            boolean found = false;
            while ( i < components.length && !found ) {
                if ( e.getSource() == components[i] ) {
                    found = true;
                    plateau.setImageGameBouton(plateau.getActionButtons()[2], "bouton_recruter.png");
                    plateau.ouvrirChantierButton(plateau.getGame().getAvailableBuildings().get(i));
                }
                i++;
            }
            plateau.enableButtons();
            piocheBatiments = false;
        }

        else if ( jrOuvriers ) {
            Component[] components = plateau.getOuvriersJr().getComponents();

            int i = 0;
            boolean found = false;
            while ( i < components.length && !found ) {
                if ( e.getSource() == components[i] ) {
                    found = true;
                    workerToSend = plateau.getGame().getCurrent().getFreeWM().get(i);
                    components[i].setEnabled(false);
                }
                i++;
            }

            jrOuvriers = false;
            jrChantiers = true;
        }

        else if ( jrChantiers ) {
            Component[] components = plateau.getChantiersJr().getComponents();

            int i = 0;
            boolean found = false;
            while ( i < components.length && !found ) {
                if ( e.getSource() == components[i] ) {
                    found = true;
                    plateau.setImageGameBouton(plateau.getActionButtons()[2], "bouton_envoi_ouvrier.png");
                    plateau.envoiOuvrierButton(workerToSend, plateau.getGame().getCurrent().getChantiers().get(i));
                }
                i++;
            }

            plateau.enableButtons();
            jrChantiers = false;
        }

    }

}
