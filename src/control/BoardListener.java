package control;

import util.Utili;
import view.Plateau;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardListener implements ActionListener {

    private Plateau plateau;
    private CardListener cardListener;
    boolean[] tutos;

    /**
     * Initializes the linked instance.
     * @param plateau the linked instance
     */
    public BoardListener(Plateau plateau, CardListener cardListener, boolean[] tutos) {
        if ( plateau == null ) {
            throw new IllegalArgumentException("Error : BoardListener() : plateau mustn't be null.");
        }
        this.plateau = plateau;

        if ( cardListener == null ) {
            throw new IllegalArgumentException("Error : BoardListener() : cardListener mustn't be null.");
        }
        this.cardListener = cardListener;

        if ( tutos == null ) {
            throw new IllegalArgumentException("Error : BoardListener() : tutos mustn't be null.");
        }
        this.tutos = tutos;
    }

    /**
     * ActionPerformed method.
     * If you don't know what it is used for, read the ActionListener javadoc.
     * @param e the ActionEvent param
     */
    public void actionPerformed(ActionEvent e) {
        if ( plateau != null ) {
            JButton[] buttons = plateau.getActionButtons();

            // Ouvrir un chantier
            if ( e.getSource() == buttons[0] ) {
                plateau.setImageGameBouton(plateau.getActionButtons()[0],"bouton_ouvrir_chantier_c.png");
                if ( tutos[0] ) {
                    JOptionPane.showMessageDialog(plateau,"Clique sur le bâtiment que tu veux construire.");
                    JOptionPane.showMessageDialog(plateau,"Tu pourras voir où en est la construction de ton bâtiment " +
                            "\nen passant la souris dessus pendant 3 secondes.");
                    tutos[0] = false;
                }

                cardListener.piocheBatiments = true;
                plateau.disableButtons(buttons[0]);
            }

            // Prendre 1 écu
            else if ( e.getSource() == buttons[1] ) {
                plateau.prendre1EcuButton();
            }

            // Recruter un ouvrier
            else if ( e.getSource() == buttons[2] ) {
                plateau.setImageGameBouton(plateau.getActionButtons()[2],"bouton_recruter_c.png");
                if ( tutos[2] ) {
                    JOptionPane.showMessageDialog(plateau,"Clique sur l'ouvrier que tu veux recruter.");
                    tutos[2] = false;
                }
                cardListener.piocheOuvriers = true;
                plateau.disableButtons(buttons[2]);
            }

            // Prendre 3 écus
            else if ( e.getSource() == buttons[3] ) {
                plateau.prendre3EcusButton();
            }

            // Envoyer travailler un ouvrier
            else if ( e.getSource() == buttons[4] ) {
                if ( plateau.getGame().getCurrent().getFreeWM().size() > 0 ) {
                    if ( plateau.getGame().getCurrent().getChantiers().size() > 0 ) {
                        plateau.setImageGameBouton(plateau.getActionButtons()[4],"bouton_envoi_ouvrier_c.png");
                        if ( tutos[4] ) {
                            JOptionPane.showMessageDialog(plateau,"Clique sur l'ouvrier que tu veux envoyer travailler, puis sur le bâtiment en question.");
                            tutos[4] = false;
                        }
                        cardListener.jrOuvriers = true;
                        plateau.disableButtons(buttons[4]);
                    } else {
                        JOptionPane.showMessageDialog(plateau,"Tu n'as pas de chantier en cours.");
                    }
                } else {
                    JOptionPane.showMessageDialog(plateau,"Tu n'as pas d'ouvrier au chômage.");
                }

            }

            // Prendre 6 écus
            else if ( e.getSource() == buttons[5] ) {
                plateau.prendre6EcusButton();

            }

            // Acheter une action
            else if ( e.getSource() == buttons[6] ) {
                plateau.setImageGameBouton(plateau.getActionButtons()[6],"bouton_acheter_action_c.png");

                String rep;
                do {
                    rep = JOptionPane.showInputDialog(plateau,
                            "Combien d'actions veux-tu acheter ?", null);
                } while (rep == null || !Utili.parseInteger(rep));

                plateau.acheterActionButton(Integer.parseInt(rep));

                plateau.setImageGameBouton(plateau.getActionButtons()[6], "bouton_acheter_action.png");

            }

            // Finir mon tour
            else if ( e.getSource() == buttons[7] ) {
                plateau.finirTourButton();

            }

        }
    }

}
