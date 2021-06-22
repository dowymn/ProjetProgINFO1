package view;

import util.Batview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Defines the common components of all the pages of the graphical interface.
 * @author Eva Guignabodet
 */
public abstract class Pages extends JPanel {

	private LesBatisseurs window;
	private String pageTitle;

	private JLabel labogo; // contains the logo

	private JButton retourBouton;
	private JButton quitterBouton;


	//-----[ INITIALIZATION

	/**
	 * Calls the initialization() method.
	 * @param window the window instance
	 */
	public Pages(LesBatisseurs window, String pageTitle) {
		initComponents(window, pageTitle);
	}

	/**
	 * Initializes the components.
	 */
	private void initComponents(LesBatisseurs window, String pageTitle) {

		if ( window == null ) {
			throw new IllegalArgumentException("Error : Pages : initComponents() : window musn't be null.");
		} else {
			this.window = window;
		}

		if ( pageTitle == null ) {
			System.out.println("Error : Pages : initComponents() : pageTitle is null. ");
			this.pageTitle = "unknown page";
		} else {
			this.pageTitle = pageTitle;
		}

		this.retourBouton = new JButton("Retour");
		setImageBouton(retourBouton,"bouton_retour.png");
		this.quitterBouton = new JButton("Quitter");
		setImageBouton(quitterBouton,"bouton_quitter.png");

	}


	//-----[ ADDS

	/**
	 * Allows to get the big logo in order to put it into a panel.
	 * @return a JLabel that contains the logo
	 */
	public JLabel getLogo() {
		labogo = new JLabel();
		ImageIcon icon = Batview.sizedImage("logo_transp.png",750,190);

		labogo.setIcon(icon);
		labogo.setHorizontalAlignment(JLabel.CENTER);
		return labogo;
	}

	/**
	 * Allows to get the little logo in order to put it into a panel.
	 * @return a JLabel that contains the logo
	 */
	public JLabel getPetitLogo() {
		labogo = new JLabel();
		ImageIcon icon = Batview.sizedImage("logo_transp.png",550,140);

		labogo.setIcon(icon);
		labogo.setHorizontalAlignment(JLabel.CENTER);
		return labogo;
	}


	//----[ SETS

	/**
	 * Sets an image to a button, because the options allowed by swing are ugly.
	 * @param button the button
	 * @param imageName the image name
	 */
	public void setImageBouton(JButton button, String imageName) {
		ImageIcon im = new ImageIcon(Batview.PATHIMAGE + imageName);
		button.setText("");
		button.setIcon(im);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
	}

	/**
	 * Sets an image to a button, because the options allowed by swing are ugly.
	 * Is used for the different buttons available on the game board.
	 * @param button the button
	 * @param imageName the image name
	 */
	public void setImageGameBouton(JButton button, String imageName) {
		ImageIcon im = new ImageIcon(Batview.PATHGAMEBUTTONS + imageName);
		button.setText("");
		button.setIcon(im);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
	}

	/**
	 * Sets an image to a button, because the options allowed by swing are ugly.
	 * Is used for the different buttons available on the game board.
	 * @param button the button
	 * @param image the ImageIcon
	 */
	public void setCardBouton(JButton button, ImageIcon image) {
		button.setText("");
		button.setIcon(image);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
	}

	/**
	 * Sets an image to a label.
	 * @param label the label
	 * @param imageName the image name
	 */
	public void setImageLabel(JLabel label, String imageName) {
		ImageIcon im = new ImageIcon(Batview.PATHIMAGE + imageName);
		label.setText("");
		label.setIcon(im);
		label.setBackground(Batview.transpaColor);
	}

	/**
	 * Sets an image to a label.
	 * @param label the label
	 * @param image the image
	 */
	public void setImageLabel(JLabel label, ImageIcon image) {
		label.setText("");
		label.setIcon(image);
		label.setBackground(Batview.transpaColor);
	}


	//----[ GETTERS

	/**
	 * @return the LesBatisseurs instance
	 */
	public LesBatisseurs getWindow() {
		return this.window;
	}

	/**
	 * @return the title of the page
	 */
	public String getPageTitle() {
		return this.pageTitle;
	}

	/**
	 * @return the "Retour" button
	 */
	public JButton getRetourBouton() {
		return retourBouton;
	}

	/**
	 * @return the "Quitter" button
	 */
	public JButton getQuitterBouton() {
		return quitterBouton;
	}


	//-----[ OTHERS

	public abstract void returnButtonAction();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			BufferedImage bufimage = ImageIO.read(new File(Batview.PATHIMAGE+"wallpaper_blurred.png"));
			g.drawImage(bufimage, 0,0,getWidth(),getHeight(),null);
		} catch (IOException e) {
			System.out.println("Error : Pages : paintComponent() : " + e.getMessage());
		}
	}


}