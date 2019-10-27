package engine.core.input.events;

import engine.core.events.IEvent;
import engine.core.input.enums.KeyboardButton;

public class KeyPressEvent implements IEvent {
    private KeyboardButton key;

    public KeyPressEvent(KeyboardButton key) {
        this.key = key;
    }

    public KeyboardButton getKey() {
        return key;
    }

    @Override
    public String getName() {
        return "Key press event";
    }

    @Override
    public Class<? extends IEvent> getType() {
        return KeyPressEvent.class;
    }
}
