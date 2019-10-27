package engine.core.input.events;

import engine.core.events.IEvent;
import engine.core.input.enums.KeyboardButton;

public class KeyReleaseEvent implements IEvent {
    private KeyboardButton key;

    public KeyReleaseEvent(KeyboardButton key) {
        this.key = key;
    }

    public KeyboardButton getKey() {
        return key;
    }

    @Override
    public String getName() {
        return "Key release event";
    }

    @Override
    public Class<? extends IEvent> getType() {
        return KeyReleaseEvent.class;
    }
}
