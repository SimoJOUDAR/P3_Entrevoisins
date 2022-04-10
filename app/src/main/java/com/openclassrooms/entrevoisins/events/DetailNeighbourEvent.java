package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class DetailNeighbourEvent {

    public Neighbour neighbour;

    public DetailNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
