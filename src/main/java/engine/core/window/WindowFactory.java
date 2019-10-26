package engine.core.window;

public class WindowFactory {
    public static IWindow create(String title, int width, int height) {
        return new Window(title, width, height);
    }
}
