package engine.core.input.events;

import engine.core.events.interfaces.IEvent;

public class MouseScrollEvent implements IEvent {
    private float offsetX;
    private float offsetY;

    public MouseScrollEvent(float offsetX, float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    @Override
    public String getName() {
        return "Mouse scroll event";
    }

    @Override
    public Class<? extends IEvent> getType() {
        return MouseScrollEvent.class;
    }
}
