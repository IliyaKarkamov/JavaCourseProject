package sandbox;

import engine.core.Application;
import engine.core.input.enums.KeyboardButton;
import engine.core.input.enums.MouseButton;
import engine.core.window.events.WindowCloseEvent;

public class SandboxApplication extends Application {
    SandboxApplication() {
        getEventDispatcher().addListener(WindowCloseEvent.class, event -> {
            setRunning(false);
            return false;
        });
    }

    @Override
    protected void update(float delta) {
        if (getKeyboard().isKeyPressed(KeyboardButton.A)) {
            System.out.println("A pressed!");
        }

        if (getMouse().isButtonPressed(MouseButton.MouseButtonLeft)) {
            System.out.println("Mouse left button pressed!");
        }

        getWindow().setTitle("FPS: " + 1 / delta);
    }
}
