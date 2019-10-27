package engine.core.window;

import engine.core.events.IEventDispatcher;
import engine.core.input.enums.KeyboardButton;
import engine.core.input.enums.MouseButton;
import engine.core.input.events.*;
import engine.core.window.events.WindowCloseEvent;
import engine.core.window.events.WindowResizeEvent;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class Window implements IWindow {
    private IEventDispatcher eventDispatcher;
    private long windowHandle = NULL;

    private String title;

    private int width;
    private int height;

    private boolean verticalSync = false;

    public Window(IEventDispatcher eventDispatcher, String title, int width, int height) {
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

    @Override
    public void close() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);

        GlfwInitializer.terminate();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(windowHandle, title);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        glfwSetWindowSize(windowHandle, width, height);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        glfwSetWindowSize(windowHandle, width, height);
    }

    @Override
    public void show() {
        glfwShowWindow(windowHandle);
    }

    @Override
    public void hide() {
        glfwHideWindow(windowHandle);
    }

    @Override
    public void focus() {
        glfwFocusWindow(windowHandle);
    }

    @Override
    public boolean isVerticalSync() {
        return verticalSync;
    }

    @Override
    public void setVerticalSync(boolean verticalSync) {
        glfwSwapInterval(verticalSync ? 1 : 0);
        this.verticalSync = verticalSync;
    }

    @Override
    public void processMessages() {
        glfwPollEvents();
    }

    @Override
    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }

    private void setEventCallbacks() {
        glfwSetWindowSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;

            eventDispatcher.publish(new WindowResizeEvent(width, height));
        });

        glfwSetWindowCloseCallback(windowHandle, window -> eventDispatcher.publish(new WindowCloseEvent()));
        glfwSetCursorPosCallback(windowHandle, (window, xpos, ypos) -> eventDispatcher.publish(new MouseMoveEvent((float) xpos, (float) ypos)));
        glfwSetScrollCallback(windowHandle, (window, xoffset, yoffset) -> eventDispatcher.publish(new MouseScrollEvent((float) xoffset, (float) yoffset)));

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_PRESS:
                    eventDispatcher.publish(new KeyPressEvent(KeyboardButton.getValue(key)));
                    break;

                case GLFW_RELEASE:
                    eventDispatcher.publish(new KeyReleaseEvent(KeyboardButton.getValue(key)));
                    break;
            }
        });

        glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            switch (action) {
                case GLFW_PRESS:
                    eventDispatcher.publish(new MouseButtonPressEvent(MouseButton.getValue(button)));
                    break;

                case GLFW_RELEASE:
                    eventDispatcher.publish(new MouseButtonReleaseEvent(MouseButton.getValue(button)));
                    break;
            }
        });
    }
}



