package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get a Neighbour by id
     * @return {@link List}
     */
    Neighbour getNeighbour(long id);

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Get my Favorite Neighbours only
     * @return {@link List}
     */
    List<Neighbour> getFavoriteNeighbours();

    /**
     * Get my Favorite Neighbours only
     * @return {@link List}
     */
    void changeIsFavorite(Neighbour neighbour);

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);
}
