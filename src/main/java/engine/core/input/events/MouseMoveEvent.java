package engine.core.input.events;

import engine.core.events.interfaces.IEvent;

public class MouseMoveEvent implements IEvent {
    private float x;
    private float y;

    public MouseMoveEvent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String getName() {
        return "Mouse move event";
    }

    @Override
    public Class<? extends IEvent> getType() {
        return MouseMoveEvent.class;
    }
}
