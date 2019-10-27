package engine.core.input;

import engine.core.input.enums.KeyboardButton;

public interface IKeyboard {
    boolean isKeyPressed(KeyboardButton key);

    void start();
    void stop();

    boolean isActive();
}
