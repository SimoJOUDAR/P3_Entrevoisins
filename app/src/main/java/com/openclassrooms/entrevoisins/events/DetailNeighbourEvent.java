package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class DetailNeighbourEvent {

    public long id;

    public DetailNeighbourEvent(long id) {
        this.id = id;
    }
}
