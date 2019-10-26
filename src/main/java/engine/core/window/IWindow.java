package engine.core.window;

public interface IWindow {
    String getTitle();
    void setTitle(String title);

    int getWidth();
    void setWidth(int width);

    int getHeight();
    void setHeight(int height);

    boolean isVerticalSync();
    void setVerticalSync(boolean verticalSync);

    void show();
    void hide();
    void focus();
    void close();

    void processMessages();
    void swapBuffers();
}