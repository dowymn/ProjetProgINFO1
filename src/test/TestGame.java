package test;

import model.Game;
import model.PlayerMode;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Tests the Game class.
 * @author Eva Guignabodet
 */
public class TestGame {

    @Test()
    public void testTheBuildersGame() {

        // Game with 2 players
        Game game1 = new Game(PlayerMode.HA,false, "jr1", "jr2", "", "");

        // Constructor and createPlayers() method
        assertNotNull(game1.getPlayers()[0]);
        assertNotNull(game1.getPlayers()[1]);
        assertNull(game1.getPlayers()[3]);
        assertNull(game1.getPlayers()[4]);

        // Constructor and initializeCards() methodh
        assertNotNull(game1.getAvailableBuildings());
        assertNotNull(game1.getAvailableWorkers());
        //assertNotNull(game1.getAvailableMachines());
        assertEquals(42,game1.getAvailableWorkers().size());

        // moveCard() method
        /*assertEquals(0,game1.getCardsP1().size());
        game1.moveCard(new WorkerCard("w1",5,2,3,4,1),game1.getPlayer1());
        assertEquals(1,game1.getCardsP1().size());*/


        // Game with 3 players
        Game game2 = new Game(PlayerMode.HAA,false, "jr1", "jr2", "jr3", "");

        // Constructor and createPlayers() method
        assertNotNull(game2.getPlayers()[0]);
        assertNotNull(game2.getPlayers()[1]);
        assertNotNull(game2.getPlayers()[2]);
        assertNull(game2.getPlayers()[3]);

        // Constructor and initializeCards() method
        assertNotNull(game2.getAvailableWorkers());
        //assertNotNull(game2.getAvailableMachines());
        assertNotNull(game2.getAvailableBuildings());


        // Game with 4 players
        Game game3 = new Game(PlayerMode.HHHA,false, "jr1", "jr2", "jr3", "jr4");

        // Constructor and createPlayers() method
        assertNotNull(game3.getPlayers()[0]);
        assertNotNull(game3.getPlayers()[1]);
        assertNotNull(game3.getPlayers()[2]);
        assertNotNull(game3.getPlayers()[3]);

        // Constructor and initializeCards() method
        assertNotNull(game3.getAvailableWorkers());
        //assertNotNull(game3.getAvailableMachines());
        assertNotNull(game3.getAvailableBuildings());

    }

}
