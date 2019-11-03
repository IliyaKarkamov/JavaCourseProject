package engine.core;

import engine.core.events.EventDispatcher;
import engine.core.events.interfaces.IEventDispatcher;
import engine.core.exceptions.ApplicationInitException;
import engine.core.input.Keyboard;
import engine.core.input.Mouse;
import engine.core.input.interfaces.IKeyboard;
import engine.core.input.interfaces.IMouse;
import engine.core.window.Window;
import engine.core.window.events.WindowResizeEvent;
import engine.core.window.interfaces.IWindow;
import engine.renderer.Context;
import engine.renderer.interfaces.IContext;
import engine.resources.ResourceManager;
import engine.resources.factories.ModelFactory;
import engine.resources.factories.ShaderFactory;
import engine.resources.factories.TextureFactory;
import engine.resources.interfaces.IResourceManager;

public abstract class Application {
    private final IEventDispatcher eventDispatcher;
    private final Window window;

    private final IContext context;

    private final Keyboard keyboard;
    private final Mouse mouse;

    private final IResourceManager resourceManager;

    private boolean isRunning;

    protected Application() {
        eventDispatcher = new EventDispatcher();
        window = new Window(eventDispatcher, "Iliya's window", 800, 600);

        context = new Context();

        keyboard = new Keyboard(eventDispatcher);
        mouse = new Mouse(eventDispatcher);

        resourceManager = new ResourceManager();
        resourceManager.registerFactory(new TextureFactory());
        resourceManager.registerFactory(new ShaderFactory());
        resourceManager.registerFactory(new ModelFactory(resourceManager));

        eventDispatcher.addListener(WindowResizeEvent.class, event -> {
            WindowResizeEvent resizeEvent = (WindowResizeEvent) event;
            context.setViewport(0, 0, resizeEvent.getWidth(), resizeEvent.getHeight());
            return false;
        });
    }

    protected abstract void init() throws ApplicationInitException;

    protected abstract void close();

    protected abstract void update(float delta);

    public void run() throws ApplicationInitException {
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

    protected IEventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    protected IWindow getWindow() {
        return window;
    }

    protected IContext getContext() {
        return context;
    }

    protected IKeyboard getKeyboard() {
        return keyboard;
    }

    protected IMouse getMouse() {
        return mouse;
    }

    protected IResourceManager getResourceManager() {
        return resourceManager;
    }

    protected boolean isRunning() {
        return isRunning;
    }

    protected void setRunning(boolean running) {
        isRunning = running;
    }
}
