package engine.core.window.events;

import engine.core.events.interfaces.IEvent;

public final class WindowResizeEvent implements IEvent {
    private int width;
    private int height;

    public WindowResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String getName() {
        return "Window resize event";
    }

    @Override
    public Class<? extends IEvent> getType() {
        return WindowResizeEvent.class;
    }
}
