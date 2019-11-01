package engine.core.input.interfaces;

import engine.core.input.enums.KeyboardButton;

public interface IKeyboard {
    boolean isKeyPressed(KeyboardButton key);

    void initialize();

    boolean isActive();
}
