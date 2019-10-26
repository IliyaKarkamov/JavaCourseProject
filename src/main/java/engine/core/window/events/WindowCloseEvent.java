package engine.core.window.events;

import engine.core.events.IEvent;

public final class WindowCloseEvent implements IEvent {
    @Override
    public String getName() {
        return "Window close event";
    }

    @Override
    public Class<? extends IEvent> getType() {
        return WindowCloseEvent.class;
    }
}
