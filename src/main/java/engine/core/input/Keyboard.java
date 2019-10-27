package engine.core.input;

import engine.core.events.interfaces.IEventDispatcher;
import engine.core.events.interfaces.IEventListener;
import engine.core.input.enums.KeyboardButton;
import engine.core.input.events.KeyPressEvent;
import engine.core.input.events.KeyReleaseEvent;
import engine.core.input.interfaces.IKeyboard;

import java.util.EnumMap;

public class Keyboard implements IKeyboard {
    private IEventDispatcher eventDispatcher;

    private IEventListener keyPressListener;
    private IEventListener keyReleaseListener;

    private EnumMap<KeyboardButton, Boolean> states = new EnumMap<>(KeyboardButton.class);

    private boolean isActive = false;

    public Keyboard(IEventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;

        this.keyPressListener = event -> {
            states.put(((KeyPressEvent) event).getKey(), true);
            return false;
        };

        this.keyReleaseListener = event -> {
            states.put(((KeyReleaseEvent) event).getKey(), false);
            return false;
        };
    }

    @Override
    public boolean isKeyPressed(KeyboardButton key) {
        return states.getOrDefault(key, false);
    }

    @Override
    public void start() {
        eventDispatcher.addListener(KeyPressEvent.class, keyPressListener);
        eventDispatcher.addListener(KeyReleaseEvent.class, keyReleaseListener);

        isActive = true;
    }

    @Override
    public void stop() {
        eventDispatcher.removeListener(KeyPressEvent.class, keyPressListener);
        eventDispatcher.removeListener(KeyReleaseEvent.class, keyReleaseListener);

        isActive = false;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
