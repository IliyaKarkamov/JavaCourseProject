package engine.core.window;

import java.util.Objects;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

final class GlfwInitializer {
    private static boolean initialized = false;
    private static int referenceCount = 0;

    private GlfwInitializer() {
    }

    static void initialize() {
        if (!initialized) {
            doInit();
            initialized = true;
        }

        ++referenceCount;
    }

    static void terminate() {
        if (--referenceCount == 0) {
            glfwTerminate();
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }
    }

    private static void doInit() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
    }
}