package engine.core.window;

import engine.core.events.IEventDispatcher;
import engine.core.window.events.WindowCloseEvent;
import engine.core.window.events.WindowResizeEvent;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

final class Window implements IWindow {
    private IEventDispatcher eventDispatcher;
    private long windowHandle = NULL;

    private String title;

    private int width;
    private int height;

    private boolean verticalSync = false;

    Window(IEventDispatcher eventDispatcher, String title, int width, int height) {
        this.eventDispatcher = eventDispatcher;

        GlfwInitializer.initialize();

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_FALSE);

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);

        if (windowHandle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(windowHandle);

        GL.createCapabilities();

        setEventCallbacks();
    }

    public void close() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);

        GlfwInitializer.terminate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(windowHandle, title);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        glfwSetWindowSize(windowHandle, width, height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        glfwSetWindowSize(windowHandle, width, height);
    }

    public void show() {
        glfwShowWindow(windowHandle);
    }

    public void hide() {
        glfwHideWindow(windowHandle);
    }

    public void focus() {
        glfwFocusWindow(windowHandle);
    }

    public boolean isVerticalSync() {
        return verticalSync;
    }

    public void setVerticalSync(boolean verticalSync) {
        glfwSwapInterval(verticalSync ? 1 : 0);
        this.verticalSync = verticalSync;
    }

    public void processMessages() {
        glfwPollEvents();
    }

    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }

    private void setEventCallbacks() {
        glfwSetWindowSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;

            eventDispatcher.publish(new WindowResizeEvent(width, height));
        });

        glfwSetWindowCloseCallback(windowHandle, window -> {
            eventDispatcher.publish(new WindowCloseEvent());
        });
    }
}


