package engine.core;

import engine.core.events.EventDispatcher;
import engine.core.events.interfaces.IEventDispatcher;
import engine.core.input.interfaces.IKeyboard;
import engine.core.input.interfaces.IMouse;
import engine.core.input.Keyboard;
import engine.core.input.Mouse;
import engine.core.window.interfaces.IWindow;
import engine.core.window.Window;

public abstract class Application {
    private final IWindow window;
    private final IEventDispatcher eventDispatcher;

    private final IKeyboard keyboard;
    private final IMouse mouse;

    private boolean isRunning;

    protected Application() {
        eventDispatcher = new EventDispatcher();
        window = new Window(eventDispatcher, "Iliya's window", 800, 600);

        keyboard = new Keyboard(eventDispatcher);
        mouse = new Mouse(eventDispatcher);
    }

    protected abstract void init();
    protected abstract void update(float delta);

    public void run() {
        init();

        setRunning(true);

        window.show();

        keyboard.start();
        mouse.start();

        long lastTime = System.nanoTime();

        while (isRunning) {
            final long currentTime = System.nanoTime();
            final float elapsedTime = (float) ((currentTime - lastTime) * 0.000000001); // elapsed time as seconds
            lastTime = currentTime;

            window.processMessages();

            update(elapsedTime);

            window.swapBuffers();
        }

        keyboard.stop();
        mouse.stop();
        window.close();
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
