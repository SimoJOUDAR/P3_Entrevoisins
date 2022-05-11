package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavoriteNeighbourWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        assertTrue(neighbours.get(0).getIsFavorite());

        service.changeIsFavorite(neighbours.get(0));
        assertFalse(neighbours.get(0).getIsFavorite());

        service.changeIsFavorite(neighbours.get(0));
        assertTrue(neighbours.get(0).getIsFavorite());
    }

    //AddNewNeighbour Test ?
    @Test
    public void createNewNeighbour() {
        Neighbour newNeighbour = new Neighbour(13, "Scarlett",
                "https://i.pravatar.cc/150?u=a042581f4e29026704d",
                "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",
                "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot..",
                true);
        service.createNeighbour(newNeighbour);
        assertTrue(service.getNeighbours().contains(newNeighbour));
    }
}
