package control;

import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the actions performed when the user interacts with a button.
 * @author Eva Guignabodet
 */
public class ButtonListener implements ActionListener {

	private Accueil accueil;
	private MenuJouer menuJouer;
	private Regles regles;
	private NouvellePartie nouvellePartie;
	private ReprendrePartie reprendrePartie;
	private Plateau plateau;
	private FinDuJeu finDuJeu;

	/**
	 * Initializes the accueil field.
	 * @param accueil the linked instance
	 */
	public ButtonListener(Accueil accueil) {
		if ( accueil == null ) {
			throw new IllegalArgumentException("Error : ButtonListener() : accueil mustn't be null.");
		}
		this.accueil = accueil;
	}

	/**
	 * Initializes the regles field.
	 * @param regles the linked instance
	 */
	public ButtonListener(Regles regles) {
		if ( regles == null ) {
			throw new IllegalArgumentException("Error : ButtonListener() : regles mustn't be null.");
		}
		this.regles = regles;
	}

	/**
	 * Initializes the menuJouer field.
	 * @param menuJouer the linked instance
	 */
	public ButtonListener(MenuJouer menuJouer) {
		if ( menuJouer == null ) {
			throw new IllegalArgumentException("Error : ButtonListener() : menuJouer mustn't be null.");
		}
		this.menuJouer = menuJouer;
	}

	/**
	 * Initializes the nouvellePartie field.
	 * @param nouvellePartie the linked instance
	 */
	public ButtonListener(NouvellePartie nouvellePartie) {
		if ( nouvellePartie == null ) {
			throw new IllegalArgumentException("Error : ButtonListener() : nouvellePartie mustn't be null.");
		}
		this.nouvellePartie = nouvellePartie;
	}

	/**
	 * Initializes the choixPartie field.
	 * @param reprendrePartie the linked instance
	 */
	public ButtonListener(ReprendrePartie reprendrePartie) {
		if ( reprendrePartie == null ) {
			throw new IllegalArgumentException("Error : ButtonListener() : reprendrePartie mustn't be null.");
		}
		this.reprendrePartie = reprendrePartie;
	}

	/**
	 * Initializes the plateau field.
	 * @param plateau the linked instance
	 */
	public ButtonListener(Plateau plateau) {
		if ( plateau == null ) {
			throw new IllegalArgumentException("Error : ButtonListener() : plateau mustn't be null.");
		}
		this.plateau = plateau;
	}

	/**
	 * Initializes the finDuJeu field.
	 * @param finDuJeu the linked instance
	 */
	public ButtonListener(FinDuJeu finDuJeu) {
		if ( finDuJeu == null ) {
			throw new IllegalArgumentException("Error : ButtonListener() : plateau mustn't be null.");
		}
		this.finDuJeu = finDuJeu;
	}

	/**
	 * Defines what to do then a button is clicked, according to the linked instance.
	 * Because only the linked attribute is initialized, simply checks which one is != null and then does the job with this one.
	 * @param e the ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {

		if ( accueil != null ) {
			if ( e.getSource() == accueil.getJouerButton() ) {
				accueil.getWindow().changeView("menuJouer");
			} else if ( e.getSource() == accueil.getReglesButton() ) {
				accueil.getWindow().changeView("regles");
			} else if ( e.getSource() == accueil.getQuitterBouton() ) {
				System.exit(0);
			}
		}

		else if ( regles != null ) {
			if ( e.getSource() == regles.getRetourBouton() ) {
				regles.returnButtonAction();
			}
		}

		else if ( menuJouer != null ) {
			if ( e.getSource() == menuJouer.getRetourBouton() ) {
				menuJouer.returnButtonAction();
			} else if ( e.getSource() == menuJouer.getNouvellePartie() ) {
				menuJouer.getWindow().changeView("nouvellePartie");
			} else if ( e.getSource() == menuJouer.getReprendrePartie() ) {
				menuJouer.getWindow().changeView("reprendrePartie");
			}
		}

		else if ( nouvellePartie != null ) {
			if ( e.getSource() == nouvellePartie.getRetourBouton() ) {
				nouvellePartie.returnButtonAction();
			} else if ( e.getSource() == nouvellePartie.getValiderBouton() ) {
				nouvellePartie.computeInformation();
			}
		}

		else if ( reprendrePartie != null ) {
			if ( e.getSource() == reprendrePartie.getRetourBouton() ) {
				reprendrePartie.returnButtonAction();
			}
		}

		else if ( plateau != null ) {
			if ( e.getSource() == plateau.getSaveButton() ) {
				plateau.returnButtonAction();
			}
		}

		else if ( finDuJeu != null ) {
			if ( e.getSource() == finDuJeu.getQuitterBouton() ) {
				finDuJeu.returnButtonAction();
			}
		}

	}


}