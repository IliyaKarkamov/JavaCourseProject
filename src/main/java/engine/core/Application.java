package engine.core;

import engine.core.events.EventDispatcher;
import engine.core.events.interfaces.IEventDispatcher;
import engine.core.input.Keyboard;
import engine.core.input.Mouse;
import engine.core.input.interfaces.IKeyboard;
import engine.core.input.interfaces.IMouse;
import engine.core.window.Window;
import engine.core.window.interfaces.IWindow;

public abstract class Application {
    private final Window window;
    private final IEventDispatcher eventDispatcher;

    private final Keyboard keyboard;
    private final Mouse mouse;

    private boolean isRunning;

    protected Application() {
        eventDispatcher = new EventDispatcher();
        window = new Window(eventDispatcher, "Iliya's window", 800, 600);

        keyboard = new Keyboard(eventDispatcher);
        mouse = new Mouse(eventDispatcher);
    }

    protected abstract void init();

    protected abstract void close();

    protected abstract void update(float delta);

    public void run() {
        init();
        keyboard.initialize();
        mouse.initialize();

        setRunning(true);

        window.show();

        try {
            long lastTime = System.nanoTime();

            while (isRunning) {
                final long currentTime = System.nanoTime();
                final float elapsedTime = (float) ((currentTime - lastTime) * 0.000000001); // elapsed time as seconds
                lastTime = currentTime;

                window.processMessages();

                update(elapsedTime);

                window.swapBuffers();
            }
        } finally {
            keyboard.close();
            mouse.close();
            window.close();
            close();
        }
    }

    protected IWindow getWindow() {
        return window;
    }

    protected IEventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    protected IKeyboard getKeyboard() {
        return keyboard;
    }

    protected IMouse getMouse() {
        return mouse;
    }

    protected boolean isRunning() {
        return isRunning;
    }

    protected void setRunning(boolean running) {
        isRunning = running;
    }
}
