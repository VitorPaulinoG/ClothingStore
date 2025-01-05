package com.vitorpg.clothingstore.events;

import javafx.event.Event;
import javafx.event.EventType;

public class RefreshPageEvent  extends Event {
    public static final EventType<RefreshPageEvent> REFRESH_PAGE_REQUESTED = new EventType<>(Event.ANY, "REFRESH_PAGE_REQUESTED");

    public RefreshPageEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
