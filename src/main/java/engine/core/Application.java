package engine.core;

import engine.core.events.EventDispatcherFactory;
import engine.core.events.IEvent;
import engine.core.events.IEventDispatcher;
import engine.core.events.IEventListener;
import engine.core.window.IWindow;
import engine.core.window.WindowFactory;
import engine.core.window.events.WindowCloseEvent;

import static org.lwjgl.opengl.GL11C.*;

public class Application {
    private IWindow window;
    private IEventDispatcher eventDispatcher;

    private boolean isRunning;

    public Application() {
        eventDispatcher = EventDispatcherFactory.create();
        window = WindowFactory.create(eventDispatcher, "Iliya's window", 800, 600);

        eventDispatcher.listen(WindowCloseEvent.class, new IEventListener() {
            @Override
            public boolean onEvent(IEvent event) {
                isRunning = false;
                return false;
            }
        });
    }

    public void run() {
        isRunning = true;

        window.show();

        while (isRunning) {
            window.processMessages();

            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            window.swapBuffers();
        }
    }
}
