package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Manages some visual-realted things like colors, paths...
 * @author Eva Guignabodet
 */
public class Batview {

    // PATHS
    private static String MAIN = "./data/";
    public static final String PATHINFOS = MAIN + "infos/";
    public static final String PATHFONTS = MAIN + "fonts/";
    public static final String PATHIMAGE = MAIN + "images/";
    public static final String PATHCARDS = MAIN + "cards/";
    public static final String PATHGAMEBUTTONS = MAIN + "images/ingame/";
    public static final String PATHSAVES = MAIN + "saves/";
    public static final String EXT = ".bati";

    // COLORS
    public static final Color transpaColor = new Color(0,0,0,0);
    public static final Color greyColor = new Color(234,234,234);
    public static final Color redColor = new Color(187, 67, 67);
    public static final Color clearRedColor = new Color(250, 161, 161);
    public static final Color yellowColor = new Color(255, 212, 103);
    public static final Color transpWhiteColor = new Color(255,255,255, 124);
    public static final Color transpBlackColor = new Color(0, 0, 0, 55);

    // FONTS
    public final static Font littleFont = getFont("Laksaman.ttf",20);
    public final static Font repPartie = getFont("Laksaman.ttf",26);
    public final static Font jrNvlPartie = getFont("Laksaman-Bold.ttf",24);
    public final static Font hdrNvlPartie = getFont("Z003-MediumItalic.otf",45);
    public final static Font gameTitle = getFont("Z003-MediumItalic.otf",70);
    public final static Font gameSubtitle = getFont("Laksaman.ttf",24);

    // BORDERS
    public final static LineBorder repPartieBorder = new LineBorder(redColor,2);
    public final static EmptyBorder retourBorder = new EmptyBorder(new Insets(15,0,15,0));
    public final static LineBorder piocheBorder = new LineBorder(Color.WHITE,2);
    public final static EmptyBorder piocheBorderE = new EmptyBorder(new Insets(5,5,5,5));
    public final static EmptyBorder emptyBorder = new EmptyBorder(new Insets(0,0,0,0));

    // IMAGES
    /**
     * Allows to get an image in the good size.
     * @param image the image name
     * @param width the wanted width
     * @param height the wanted height
     * @return an ImageIcon that contains the resized image
     */
    public static ImageIcon sizedImage(String image, int width, int height) {
        ImageIcon icon = null;
        try {
            BufferedImage logo = ImageIO.read(new File(PATHIMAGE + image));
            Image limg = logo.getScaledInstance(width,height,Image.SCALE_SMOOTH);
            icon = new ImageIcon(limg);

        } catch (IOException e) {
            System.out.println("Error : Batview : sizedImage() : " + e.getMessage());
            icon = new ImageIcon(PATHIMAGE + image);
        }
        return icon;
    }

    /**
     * Allows to get an image in the good size.
     * @param image the image name
     * @param width the wanted width
     * @param height the wanted height
     * @return an ImageIcon that contains the resized image
     */
    public static ImageIcon sizedCard(String image, int width, int height) {
        ImageIcon icon = null;
        try {
            BufferedImage logo = ImageIO.read(new File(image));
            Image limg = logo.getScaledInstance(width,height,Image.SCALE_SMOOTH);
            icon = new ImageIcon(limg);

        } catch (IOException e) {
            System.out.println("Error : Batview : sizedCard() : " + e.getMessage());
            icon = new ImageIcon(PATHIMAGE + image);
        }
        return icon;
    }

    /**
     * Allows to get a font using its name.
     * @param fontName the name of the font
     * @return the font if found
     */
    private static Font getFont(String fontName, float size) {
        Font font = null;

        try {
            String path = PATHFONTS + fontName;
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStream inputStream = new BufferedInputStream(fileInputStream);

            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font = font.deriveFont(size);

        } catch (IOException | FontFormatException e) {
            System.err.println("Error : Batview : getFont() : " + e.getMessage());
        }

        return font;
    }

}
