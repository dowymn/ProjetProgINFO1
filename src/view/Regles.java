package view;

import control.*;
import util.Batview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the page where the rules of the game are shown.
 * @author Eva Guignabodet
 */
public class Regles extends Pages {

	private ButtonListener listener;
	private JPanel reglesPanel;

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public Regles(LesBatisseurs window) {
		super(window, "Regles");
		initialization();
		addComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initialization() {

		setLayout(new BorderLayout());

		this.listener = new ButtonListener(this);

		// Panel de la page
		this.reglesPanel = new JPanel();
		reglesPanel.setBackground(Batview.transpaColor);

		// Bouton retour
		getRetourBouton().addActionListener(listener);
		getRetourBouton().setBorder(Batview.retourBorder);

	}

	/**
	 * Adds the components to the main panel.
	 */
	private void addComponents() {
		add(getPetitLogo(), BorderLayout.NORTH);
		showRules();
		add(getRetourBouton(), BorderLayout.SOUTH);
	}

	/**
	 * Allows to add the rules to the main panel.
	 */
	private void showRules() {



	}

	/**
	 * Allows to get an image in the good size.
	 * @param image the image name
	 * @param width the wanted width
	 * @param height the wanted height
	 * @return an ImageIcon that contains the resized image
	 */
	private ImageIcon sizedImage(String image, int width, int height) {
		ImageIcon icon = null;
		try {
			BufferedImage logo = ImageIO.read(new File(image));
			Image limg = logo.getScaledInstance(width,height,Image.SCALE_SMOOTH);
			icon = new ImageIcon(limg);

		} catch (IOException e) {
			System.out.println("Error : Batview : sizedImage() : " + e.getMessage());
			icon = new ImageIcon(image);
		}
		return icon;
	}

	/**
	 * Defines what to do then the returnButton is clicked (i.e. which page will be accessed).
	 */
	public void returnButtonAction() {
		getWindow().changeView("accueil");
	}

}