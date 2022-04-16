package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public Neighbour getNeighbour(long id) {
        Neighbour neighbour = null;
        for (Neighbour nbr:neighbours){
            if (nbr.getId()==id){
                neighbour = nbr;
                break;
            }
        }
        return neighbour;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        for (Neighbour neighbour:neighbours){
            if (neighbour.getIsFavorite()){
                favoriteNeighbours.add(neighbour);
            }
        }

        return favoriteNeighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeIsFavorite(Neighbour neighbour) {
        neighbour.setIsFavorite(!neighbour.getIsFavorite());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }
}
