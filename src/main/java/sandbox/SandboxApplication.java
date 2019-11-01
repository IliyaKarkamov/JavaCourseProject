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
import engine.renderer.opengl.exceptions.TextureLoadException;
import engine.renderer.opengl.interfaces.*;
import org.lwjgl.opengl.GL46C;

public class SandboxApplication extends Application {
    IVertexArray vertexArray;

    IVertexBuffer vertexBuffer;

    IIndexBuffer indexBuffer;

    IShader shader;
    ITexture2D texture;

    String vertexSource = "#version 330 core\n" +
            "                layout (location = 0) in vec3 aPos;\n" +
            "                layout (location = 1) in vec2 aTexCoord;\n" +
            "\n" +
            "                out vec2 TexCoord;\n" +
            "\n" +
            "                void main()\n" +
            "                {\n" +
            "                    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
            "                    TexCoord = aTexCoord;\n" +
            "                }";

    String fragmentSource = "#version 330 core\n" +
            "                out vec4 FragColor;\n" +
            "                in vec2 TexCoord;\n" +
            "\n" +
            "                uniform sampler2D ourTexture;\n" +
            "\n" +
            "                void main()\n" +
            "                {\n" +
            "                    FragColor = texture(ourTexture, TexCoord);\n" +
            "                }";

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
                // positions           // texture coords
                0.5f, 0.5f, 0.0f, 1.0f, 0.0f,   // top right
                0.5f, -0.5f, 0.0f, 1.0f, 1.0f,   // bottom right
                -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,   // bottom left
                -0.5f, 0.5f, 0.0f, 0.0f, 0.0f    // top left
        };

        vertexBuffer = new VertexBuffer(vertices, BufferUsage.StaticDraw);

        BufferLayout layout = new BufferLayout();
        layout.addElement(new BufferElement(DataType.Float3, "aPos", false));
        layout.addElement(new BufferElement(DataType.Float2, "aTexCoord", false));

        vertexBuffer.setLayout(layout);

        vertexArray.addVertexBuffer(vertexBuffer);

        int[] indices = {0, 1, 3, 1, 2, 3};
        indexBuffer = new IndexBuffer(indices, BufferUsage.StaticDraw);
        vertexArray.setIndexBuffer(indexBuffer);

        try {
            shader = new Shader("simple", vertexSource, fragmentSource);
        } catch (ShaderCompileException | ShaderLinkException e) {
            e.printStackTrace();
        }

        try {
            texture = Texture2D.create("assets/logo.png");
        } catch (TextureLoadException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void close() {
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

        GL46C.glClear(GL46C.GL_COLOR_BUFFER_BIT | GL46C.GL_DEPTH_BUFFER_BIT);

        shader.bind();
        vertexArray.bind();

        texture.bind(0);
        shader.setUniform("ourTexture", 0);

        GL46C.glDrawElements(GL46C.GL_TRIANGLES, indexBuffer.getCount(), GL46C.GL_UNSIGNED_INT, 0);
    }
}
