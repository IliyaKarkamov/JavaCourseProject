package engine.core.window;

import engine.core.events.IEventDispatcher;

public class WindowFactory {
    public static IWindow create(IEventDispatcher eventDispatcher, String title, int width, int height) {
        return new Window(eventDispatcher, title, width, height);
    }
}
