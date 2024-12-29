package com.vitorpg.clothingstore.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ChangeSubSceneEvent extends Event {
    public static final EventType<ChangeSubSceneEvent> SUBSCENE_CHANGED = new EventType<>(Event.ANY, "SUBSCENE_CHANGED");

    private String sceneFxmlName;

    public ChangeSubSceneEvent(EventType<? extends Event> eventType, String sceneFxmlName) {
        super(eventType);
        this.sceneFxmlName = sceneFxmlName;
    }

    public String getSceneFxmlName() {
        return sceneFxmlName;
    }
}
