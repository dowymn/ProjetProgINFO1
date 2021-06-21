package test;

import model.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * [ M2107 - Projet de programmation ] Les Bâtisseurs : Moyen-Âge
 * Tests the different Card classes : Card{abstract}, WorkerCard, BuildingCard and MachineCard.
 * @author Eva Guignabodet
 */
public class TestCard {

    @Test()
    public void testWorkerCard () {

        WorkerCard card = new WorkerCard("Master", "Maitre1.png",5,3,2,3,0);

        assertNotNull(card.getCardName());
        assertEquals("Master",card.getCardName());

        assertEquals(5,card.getEcusAPayer());
        assertEquals(3,card.getPierreProd());
        assertEquals(2,card.getBoisProd());
        assertEquals(3,card.getSavoirProd());
        assertEquals(0,card.getTuileProd());
        assertTrue(card.isAvailable());

        card.changeWork(true);
        assertTrue(card.getIsWorking());
        card.changeWork(false);
        assertFalse(card.getIsWorking());

    }

    @Test()
    public void testBuildingCard () {

        BuildingCard card = new BuildingCard("Tower","Batiment1.png",3,2,6,4,2,1);

        assertNotNull(card.getCardName());
        assertEquals("Tower",card.getCardName());

        assertEquals(3,card.getEcusGagnes());
        assertEquals(2,card.getPtsVictoire());
        assertEquals(6,card.getPierreNec());
        assertEquals(4,card.getBoisNec());
        assertEquals(2,card.getSavoirNec());
        assertEquals(1,card.getTuileNec());
        assertTrue(card.isAvailable());

        assertEquals(BuildingState.NOTSTARTED,card.getState());
        card.changeState(BuildingState.INPROGRESS);
        assertEquals(BuildingState.INPROGRESS,card.getState());
        card.changeState(BuildingState.FINISHED);
        assertEquals(BuildingState.FINISHED,card.getState());

    }

    @Test()
    public void testMachineCard () {

        MachineCard card = new MachineCard("Rotule","Machine1r.png","Machine1v.png",2,6,4,2,1,3,1,0,0);

        assertNotNull(card.getCardName());
        assertEquals("Rotule",card.getCardName());

        assertEquals(2,card.getPtsVictoire());
        assertEquals(6,card.getPierreProd());
        assertEquals(4,card.getBoisProd());
        assertEquals(2,card.getSavoirProd());
        assertEquals(1,card.getTuileProd());
        assertEquals(3,card.getPierreNec());
        assertEquals(1,card.getBoisNec());
        assertEquals(0,card.getSavoirNec());
        assertEquals(0,card.getTuileNec());
        assertTrue(card.isAvailable());

        card.changeUse(true);
        assertTrue(card.getIsUsed());
        card.changeUse(false);
        assertFalse(card.getIsUsed());

    }

}
