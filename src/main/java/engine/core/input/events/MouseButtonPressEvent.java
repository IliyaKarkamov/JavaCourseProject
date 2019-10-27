package engine.core.input.events;

import engine.core.events.IEvent;
import engine.core.input.enums.MouseButton;

public class MouseButtonPressEvent implements IEvent {
    private MouseButton button;

    public MouseButtonPressEvent(MouseButton button) {
        this.button = button;
    }

    public MouseButton getButton() {
        return button;
    }

    @Override
    public String getName() {
        return "Mouse button press event";
    }

    @Override
    public Class<? extends IEvent> getType() {
        return MouseButtonPressEvent.class;
    }
}
