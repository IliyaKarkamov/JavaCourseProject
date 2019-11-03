package sandbox;

import engine.core.Application;
import engine.core.exceptions.ApplicationInitException;
import engine.core.input.enums.KeyboardButton;
import engine.core.input.enums.MouseButton;
import engine.core.window.events.WindowCloseEvent;
import engine.graphics.Camera;
import engine.graphics.enums.MoveDirection;
import engine.graphics.interfaces.ICamera;
import engine.graphics.interfaces.IModel;
import engine.renderer.opengl.enums.Capability;
import engine.renderer.opengl.interfaces.IShader;
import engine.resources.exceptions.ResourceLoadException;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class SandboxApplication extends Application {
    private IShader shader;
    private ICamera camera;

    private IModel model;

    private Vector2f mousePosition = null;

    SandboxApplication() {
        getEventDispatcher().addListener(WindowCloseEvent.class, event -> {
            setRunning(false);
            return false;
        });
    }

    @Override
    protected void init() throws ApplicationInitException {
        try {
            shader = getResourceManager().get(IShader.class, "shaders/simple/simple");
            camera = new Camera(new Vector3f(100.f, 100.f, 50f));

            model = getResourceManager().get(IModel.class, "assets/models/ba2/ba2.dae");

            getContext().enable(Capability.CullFace);
            getContext().enable(Capability.DepthTest);
            getContext().enable(Capability.Blend);
        } catch (ResourceLoadException e) {
            throw new ApplicationInitException("dadas", e);
        }
    }

    @Override
    protected void close() {
    }

    @Override
    protected void update(float delta) {
        final float speed = 15.f * delta;
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

        Matrix4f projection = new Matrix4f();
        projection.perspective((float) Math.toRadians(camera.getZoom()), 16.f / 9.f, 0.01f, 100.0f);
        shader.setUniform("projection", projection);

        Matrix4f model = new Matrix4f();
        model = model.translate(100.f, 100.f, -20.f);
        shader.setUniform("model", model);
        shader.setUniform("view", camera.getViewMatrix());

        this.model.draw(getContext(), shader);

        mousePosition.x = position.x;
        mousePosition.y = position.y;
    }
}
