package com.vitorpg.clothingstore.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ChangeSubSceneEvent extends Event {
    public static final EventType<ChangeSubSceneEvent> SUBSCENE_CHANGED = new EventType<>(Event.ANY, "SUBSCENE_CHANGED");
    public static final EventType<ChangeSubSceneEvent> SUBSCENE_CHANGED_AND_COMMUNICATION = new EventType<>(Event.ANY, "SUBSCENE_CHANGED_AND_COMMUNICATION");

    private String sceneFxmlName;

    private Object controller;

    public ChangeSubSceneEvent(EventType<? extends Event> eventType, String sceneFxmlName) {
        super(eventType);
        this.sceneFxmlName = sceneFxmlName;
    }

    public ChangeSubSceneEvent(EventType<? extends Event> eventType, String sceneFxmlName, Object controller) {
        super(eventType);
        this.sceneFxmlName = sceneFxmlName;
        this.controller = controller;
    }

    public String getSceneFxmlName() {
        return sceneFxmlName;
    }

    public Object getController() {
        return controller;
    }
}
