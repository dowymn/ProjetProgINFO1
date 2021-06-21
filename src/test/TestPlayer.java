package test;

import model.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Tests the different Player classes : Player{abstract}, AutoPlayer, and HumanPlayer.
 * @author Eva Guignabodet
 */
public class TestPlayer {

    @Test()
    public void testAutoPlayer() {

        Game game = new Game(PlayerMode.HA,false,"jr1","jr2","","");

        Player p1 = new AutoPlayer(game,"jr1",false);
        assertEquals("jr1",p1.getPlayerName());

    }

    @Test()
    public void testHumanPlayer() {

        Game game = new Game(PlayerMode.HA,false,"jr1","jr2","",null);

        Player p1 = new HumanPlayer(game,"jr1",false);
        assertEquals("jr1",p1.getPlayerName());

    }

}
