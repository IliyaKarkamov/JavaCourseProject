package engine.core;

import engine.core.window.IWindow;
import engine.core.window.WindowFactory;

import static org.lwjgl.opengl.GL11C.*;

public class Application {

    public void run() {
        IWindow window = WindowFactory.create("Iliya's window", 800, 600);

        window.show();

        while (true) {
            window.processMessages();

            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            window.swapBuffers();
        }
    }
}
