package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class DetailNeighbourEvent {

    public Neighbour neighbour;
    public int position;

    public DetailNeighbourEvent(Neighbour neighbour, int position) {
        this.neighbour = neighbour;
        this.position = position;
    }
}
