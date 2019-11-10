package sandbox;

import engine.core.Application;
import engine.core.exceptions.ApplicationInitException;
import engine.core.input.enums.KeyboardButton;
import engine.core.input.enums.MouseButton;
import engine.core.window.events.WindowCloseEvent;
import engine.graphics.Camera;
import engine.graphics.Light;
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
    private static final float cameraSensitivity = 0.05f;

    private IShader shader;
    private ICamera camera;

    private IModel model;
    private Light light;

    SandboxApplication() {
        getEventDispatcher().addListener(WindowCloseEvent.class, event -> {
            setRunning(false);
            return false;
        });
    }

    @Override
    protected void init() throws ApplicationInitException {
        try {
            getWindow().setVerticalSync(false);

            shader = getResourceManager().get(IShader.class, "D:/Projects/JavaCourseProject/resources/shaders/simple/simple");
            camera = new Camera(new Vector3f(100.f, 100.f, 50f));

            model = getResourceManager().get(IModel.class, "D:/Projects/JavaCourseProject/resources/assets/models/ba2/Quandtum_BA-2_v1_1.dae");

            light = new Light();
            light.setPosition(new Vector3f(150.f, 200.f, -50.f));
            light.setAmbient(new Vector3f(0.2f, 0.2f, 0.2f));
            light.setDiffuse(new Vector3f(0.5f, 0.5f, 0.5f));
            light.setSpecular(new Vector3f(1.0f, 1.0f, 1.0f));

            getContext().enable(Capability.CullFace);
            getContext().enable(Capability.DepthTest);
        } catch (ResourceLoadException e) {
            throw new ApplicationInitException("Failed to initialize the application.", e);
        }
    }

    @Override
    protected void close() {
    }

    @Override
    protected void update(float delta) {
        final float speed = 15.f * delta;

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

        if (getMouse().isButtonPressed(MouseButton.MouseButtonLeft)) {
            Vector2f offset = getMouse().getPositionOffset();
            camera.updateYaw(offset.x * cameraSensitivity);
            camera.updatePitch(offset.y * cameraSensitivity);
        }

        getWindow().setTitle("FPS: " + 1 / delta);
        getContext().clear();

        shader.bind();
        shader.setUniform("light.position", light.getPosition());
        shader.setUniform("light.ambient", light.getAmbient());
        shader.setUniform("light.diffuse", light.getDiffuse());
        shader.setUniform("light.specular", light.getSpecular());
        shader.setUniform("viewPos", camera.getPosition());

        Matrix4f projection = new Matrix4f();
        projection.perspective((float) Math.toRadians(camera.getZoom()), 16.f / 9.f, 0.01f, 100.0f);

        shader.setUniform("projection", projection);
        shader.setUniform("view", camera.getViewMatrix());

        float x = 100.f;
        float y = 100.f;
        float z = -20.f;
        final float offset = 10.f;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    Matrix4f model = new Matrix4f();
                    model = model.translate(x, y, z).rotate((float) Math.toRadians(-90.f), new Vector3f(1, 0, 0));
                    shader.setUniform("model", model);

                    this.model.draw(getContext(), shader);

                    z -= offset;
                }

                y += offset;
                z = -20.f;
            }

            x += offset;
            y = 100.f;
        }
    }
}
