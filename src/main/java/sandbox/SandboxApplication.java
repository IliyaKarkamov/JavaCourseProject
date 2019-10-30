package sandbox;

import engine.core.Application;
import engine.core.input.enums.KeyboardButton;
import engine.core.input.enums.MouseButton;
import engine.core.window.events.WindowCloseEvent;
import engine.renderer.opengl.*;
import engine.renderer.opengl.enums.BufferUsage;
import engine.renderer.opengl.enums.DataType;
import engine.renderer.opengl.exceptions.ShaderCompileException;
import engine.renderer.opengl.exceptions.ShaderLinkException;
import engine.renderer.opengl.interfaces.IIndexBuffer;
import engine.renderer.opengl.interfaces.IShader;
import engine.renderer.opengl.interfaces.IVertexArray;
import engine.renderer.opengl.interfaces.IVertexBuffer;
import org.lwjgl.opengl.GL46C;

public class SandboxApplication extends Application {
    IVertexArray vertexArray;

    IVertexBuffer vertexBuffer;
    IIndexBuffer indexBuffer;

    IShader shader;

    String vertexSource = "#version 400 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
            "}";

    String fragmentSource = "#version 400 core\n" +
            "out vec4 FragColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" +
            "} ";

    SandboxApplication() {
        getEventDispatcher().addListener(WindowCloseEvent.class, event -> {
            setRunning(false);
            return false;
        });
    }

    @Override
    protected void init() {
        vertexArray = new VertexArray();

        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };

        vertexBuffer = new VertexBuffer(vertices, BufferUsage.StaticDraw);

        try {
            shader = new Shader("simple", vertexSource, fragmentSource);
        } catch (ShaderCompileException | ShaderLinkException e) {
            e.printStackTrace();
        }

        BufferLayout layout = new BufferLayout();
        layout.addElement(new BufferElement(DataType.Float, "aPos", false));

        vertexBuffer.setLayout(layout);

        vertexArray.bind();
        vertexArray.addVertexBuffer(vertexBuffer);
    }

    @Override
    protected void update(float delta) {
        if (getKeyboard().isKeyPressed(KeyboardButton.A)) {
            System.out.println("A pressed!");
        }

        if (getMouse().isButtonPressed(MouseButton.MouseButtonLeft)) {
            System.out.println("Mouse left button pressed!");
        }

        getWindow().setTitle("FPS: " + 1 / delta);

        shader.bind();
        vertexArray.bind();
        GL46C.glDrawArrays(GL46C.GL_TRIANGLES, 0, 3);
    }
}
