package util;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GetAvailableFonts {
    public static void main(String[] a) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();
        PrintWriter out = null;
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter("./fonts.txt"));
            out = new PrintWriter(buf);
        } catch ( IOException e ) {
            System.out.println("nope");
        }
        StringBuilder print = new StringBuilder();
        for (Font f : fonts) {
           print.append(f.getFontName()+"\n");
        }
        out.println(print);
        out.close();
    }
}
