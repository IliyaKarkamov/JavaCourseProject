package engine.core.input.events;

import engine.core.events.interfaces.IEvent;
import engine.core.input.enums.MouseButton;

public class MouseButtonReleaseEvent implements IEvent {
    private MouseButton button;

    public MouseButtonReleaseEvent(MouseButton button) {
        this.button = button;
    }

    public MouseButton getButton() {
        return button;
    }

    @Override
    public String getName() {
        return "Mouse button release event";
    }

    @Override
    public Class<? extends IEvent> getType() {
        return MouseButtonReleaseEvent.class;
    }
}
