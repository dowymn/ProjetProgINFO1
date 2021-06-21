package control;

import view.Plateau;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardListener implements ActionListener {

    private Plateau plateau;

    /**
     * Initializes the linked instance.
     * @param plateau the linked instance
     */
    public BoardListener(Plateau plateau) {
        if ( plateau == null ) {
            throw new IllegalArgumentException("Error : BoardListener() : plateau mustn't be null.");
        } else {
            this.plateau = plateau;
        }
    }

    /**
     * ActionPerformed method.
     * If you don't know what it is used for, read the ActionListener javadoc.
     * @param e the ActionEvent param
     */
    public void actionPerformed(ActionEvent e) {
        if ( plateau != null ) {
            JButton[] buttons = plateau.getActionButtons();

            if ( e.getSource() == buttons[0] ) {

            } else if ( e.getSource() == buttons[1] ) {

            } else if ( e.getSource() == buttons[2] ) {

            } else if ( e.getSource() == buttons[3] ) {

            } else if ( e.getSource() == buttons[4] ) {

            } else if ( e.getSource() == buttons[5] ) {

            } else if ( e.getSource() == buttons[6] ) {

            } else if ( e.getSource() == buttons[7] ) {

            }

        }
    }

}
