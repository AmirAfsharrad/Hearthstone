package GUI.Listeners;

import GUI.Events.CollectionsFilterEvent;

import java.util.EventListener;

public interface CollectionsFilterListener extends EventListener {
    void CollectionsFilterOccurred(CollectionsFilterEvent collectionsFilterEvent);
}
