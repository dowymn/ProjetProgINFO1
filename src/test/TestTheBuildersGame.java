package test;

import model.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Tests the TheBuildersGame class.
 * @author Eva Guignabodet
 */
public class TestTheBuildersGame {

    @Test()
    public void testTheBuildersGame() {

        TheBuildersGame game = new TheBuildersGame();
        assertEquals("here are the rules",game.getRules());

    }

}
