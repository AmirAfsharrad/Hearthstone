package GUI.Events;

import Places.Place;

import java.util.EventObject;

public class ChangePlaceEvent extends EventObject {
    Place destination;

    public ChangePlaceEvent(Object source, Place destination) {
        super(source);
        this.destination = destination;
    }

    public Place getDestination() {
        return destination;
    }

    public void setDestination(Place destination) {
        this.destination = destination;
    }
}
