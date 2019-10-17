package sandbox;

import engine.core.Window;

import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();

        window.show();

        while (true) {
            window.processMessages();

            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            window.swapBuffers();
        }
    }
}