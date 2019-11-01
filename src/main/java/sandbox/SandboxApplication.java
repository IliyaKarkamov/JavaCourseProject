package sandbox;

import engine.core.Application;
import engine.core.input.enums.KeyboardButton;
import engine.core.input.enums.MouseButton;
import engine.core.window.events.WindowCloseEvent;
import engine.renderer.Camera;
import engine.renderer.enums.MoveDirection;
import engine.renderer.interfaces.ICamera;
import engine.renderer.opengl.*;
import engine.renderer.opengl.enums.BufferUsage;
import engine.renderer.opengl.enums.Capability;
import engine.renderer.opengl.enums.DataType;
import engine.renderer.opengl.exceptions.ShaderCompileException;
import engine.renderer.opengl.exceptions.ShaderLinkException;
import engine.renderer.opengl.exceptions.ShaderLoadException;
import engine.renderer.opengl.exceptions.TextureLoadException;
import engine.renderer.opengl.interfaces.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class SandboxApplication extends Application {
    private IVertexArray vertexArray;
    private IVertexBuffer vertexBuffer;
    private IIndexBuffer indexBuffer;
    private ITexture2D texture;

    private IShader shader;
    private ICamera camera;

    private Vector2f mousePosition = null;

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
                // front
                -1.0f, -1.0f, 1.0f, 0.0f, 0.0f,
                1.0f, -1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                // top
                -1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, -1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f, 0.0f, 1.0f,
                // back
                1.0f, -1.0f, -1.0f, 0.0f, 0.0f,
                -1.0f, -1.0f, -1.0f, 1.0f, 0.0f,
                -1.0f, 1.0f, -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f, 0.0f, 1.0f,
                // bottom
                -1.0f, -1.0f, -1.0f, 0.0f, 0.0f,
                1.0f, -1.0f, -1.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f, 0.0f, 1.0f,
                // left
                -1.0f, -1.0f, -1.0f, 0.0f, 0.0f,
                -1.0f, -1.0f, 1.0f, 1.0f, 0.0f,
                -1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f, 0.0f, 1.0f,
                // right
                1.0f, -1.0f, 1.0f, 0.0f, 0.0f,
                1.0f, -1.0f, -1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f, 1.0f,
        };

        vertexBuffer = new VertexBuffer(vertices, BufferUsage.StaticDraw);

        BufferLayout layout = new BufferLayout();
        layout.addElement(new BufferElement(DataType.Float3, "aPos", false));
        layout.addElement(new BufferElement(DataType.Float2, "aTexCoord", false));

        vertexBuffer.setLayout(layout);

        vertexArray.addVertexBuffer(vertexBuffer);

        int[] indices = {
                // front
                0, 1, 2,
                2, 3, 0,
                // top
                4, 5, 6,
                6, 7, 4,
                // back
                8, 9, 10,
                10, 11, 8,
                // bottom
                12, 13, 14,
                14, 15, 12,
                // left
                16, 17, 18,
                18, 19, 16,
                // right
                20, 21, 22,
                22, 23, 20
        };

        indexBuffer = new IndexBuffer(indices, BufferUsage.StaticDraw);
        vertexArray.setIndexBuffer(indexBuffer);

        try {
            shader = Shader.create("simple", "shaders/simple/vertex.glsl", "shaders/simple/fragment.glsl");
        } catch (ShaderCompileException | ShaderLinkException | ShaderLoadException e) {
            e.printStackTrace();
        }

        try {
            texture = Texture2D.create("assets/logo.png");
        } catch (TextureLoadException e) {
            e.printStackTrace();
        }

        camera = new Camera(new Vector3f(100.f, 100.f, -10.f));

        getContext().enable(Capability.CullFace);
    }

    @Override
    protected void close() {
    }

    @Override
    protected void update(float delta) {
        final float speed = 4.f * delta;
        final float dragSpeed = 20.f * delta;
        final Vector2f position = getMouse().getMousePosition();


        if (getKeyboard().isKeyPressed(KeyboardButton.A)) {
            camera.move(MoveDirection.Left, speed);
        }

        if (getKeyboard().isKeyPressed(KeyboardButton.D)) {
            camera.move(MoveDirection.Right, speed);
        }

        if (getKeyboard().isKeyPressed(KeyboardButton.W)) {
            camera.move(MoveDirection.Forward, speed);
        }

        if (getKeyboard().isKeyPressed(KeyboardButton.S)) {
            camera.move(MoveDirection.Backward, speed);
        }

        if (mousePosition == null) {
            Vector2f v = getMouse().getMousePosition();

            mousePosition = new Vector2f();
            mousePosition.x = v.x;
            mousePosition.y = v.y;
        }

        if (getMouse().isButtonPressed(MouseButton.MouseButtonLeft)) {
            Vector2f offset = position.sub(mousePosition, new Vector2f());

            camera.updateYaw(offset.x * dragSpeed);
            camera.updatePitch(offset.y * dragSpeed);
        }

        getWindow().setTitle("FPS: " + 1 / delta);

        getContext().clear();

        shader.bind();
        vertexArray.bind();

        texture.bind(0);
        shader.setUniform("ourTexture", 0);

        Matrix4f projection = new Matrix4f();
        projection.perspective((float) Math.toRadians(camera.getZoom()), 16.f / 9.f, 0.01f, 100.0f);
        shader.setUniform("projection", projection);

        Matrix4f model = new Matrix4f();
        model = model.translate(100.f, 100.f, -20.f).scale(5.f);
        shader.setUniform("model", model);
        shader.setUniform("view", camera.getViewMatrix());

        getContext().drawIndexed(vertexArray);

        mousePosition.x = position.x;
        mousePosition.y = position.y;
    }
}
