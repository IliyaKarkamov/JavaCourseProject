package engine.core.input;

import engine.core.events.interfaces.IEventDispatcher;
import engine.core.events.interfaces.IEventListener;
import engine.core.input.enums.MouseButton;
import engine.core.input.events.MouseButtonPressEvent;
import engine.core.input.events.MouseButtonReleaseEvent;
import engine.core.input.events.MouseMoveEvent;
import engine.core.input.events.MouseScrollEvent;
import engine.core.input.interfaces.IMouse;
import org.joml.Vector2f;

import java.util.EnumMap;

public class Mouse implements IMouse {
    private IEventDispatcher eventDispatcher;

    private IEventListener buttonPressListener;
    private IEventListener buttonReleaseListener;
    private IEventListener mouseMoveListener;
    private IEventListener mouseScrollListener;

    private EnumMap<MouseButton, Boolean> states = new EnumMap<>(MouseButton.class);
    private Vector2f mousePosition = new Vector2f();
    private Vector2f mouseScrollOffset = new Vector2f();

    private boolean isActive = false;

    public Mouse(IEventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;

        this.buttonPressListener = event -> {
            states.put(((MouseButtonPressEvent) event).getButton(), true);
            return false;
        };

        this.buttonReleaseListener = event -> {
            states.put(((MouseButtonReleaseEvent) event).getButton(), false);
            return false;
        };

        this.mouseMoveListener = event -> {
            MouseMoveEvent mouseMoveEvent = (MouseMoveEvent) event;
            mousePosition.set(mouseMoveEvent.getX(), mouseMoveEvent.getY());
            return false;
        };

        this.mouseScrollListener = event -> {
            MouseScrollEvent mouseScrollEvent = (MouseScrollEvent) event;
            mouseScrollOffset.set(mouseScrollEvent.getOffsetX(), mouseScrollEvent.getOffsetY());
            return false;
        };
    }

    @Override
    public boolean isButtonPressed(MouseButton button) {
        return states.getOrDefault(button, false);
    }

    @Override
    public Vector2f getMousePosition() {
        return mousePosition;
    }

    @Override
    public Vector2f getScrollOffset() {
        try {
            return mouseScrollOffset;
        } finally {
            mouseScrollOffset.set(0, 0);
        }
    }

    @Override
    public void start() {
        eventDispatcher.addListener(MouseButtonPressEvent.class, buttonPressListener);
        eventDispatcher.addListener(MouseButtonReleaseEvent.class, buttonReleaseListener);
        eventDispatcher.addListener(MouseMoveEvent.class, mouseMoveListener);
        eventDispatcher.addListener(MouseScrollEvent.class, mouseScrollListener);

        isActive = true;
    }

    @Override
    public void stop() {
        eventDispatcher.removeListener(MouseButtonPressEvent.class, buttonPressListener);
        eventDispatcher.removeListener(MouseButtonReleaseEvent.class, buttonReleaseListener);
        eventDispatcher.removeListener(MouseMoveEvent.class, mouseMoveListener);
        eventDispatcher.removeListener(MouseScrollEvent.class, mouseScrollListener);

        isActive = false;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
