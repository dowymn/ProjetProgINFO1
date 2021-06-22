package test;

import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Tests the different Player classes : Player{abstract}, AutoPlayer, and HumanPlayer.
 * @author Eva Guignabodet
 */
public class TestPlayer {

    private Game game;

    private WorkerCard worker;
    private BuildingCard building;

    private AutoPlayer auto;
    private HumanPlayer human;

    @Before
    public void setUp() {
        game = new Game(PlayerMode.HA,false,"jr1","jr2",null,null);

        worker = new WorkerCard("Master", "Maitre1.png",5,3,2,3,0);
        building = new BuildingCard("Tower","Batiment1.png",3,2,6,4,2,1);

        auto = new AutoPlayer(game,"jr1",false);
        human = new HumanPlayer(game,"jr1",false);

    }

    @Test()
    public void testAutoPlayer() {
        assertEquals("jr1",auto.getPlayerName());
        assertEquals(1,human.getFreeWM().size());

    }

    @Test()
    public void testHumanPlayer() {
        assertEquals("jr1",human.getPlayerName());
        assertEquals(1,human.getFreeWM().size());

    }

}
