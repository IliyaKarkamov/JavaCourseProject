package engine.core.input.interfaces;

import engine.core.input.enums.MouseButton;
import org.joml.Vector2f;

public interface IMouse {
    boolean isButtonPressed(MouseButton button);

    Vector2f getMousePosition();

    Vector2f getScrollOffset();

    void initialize();

    boolean isActive();
}
