package engine.renderer;

import engine.renderer.enums.MoveDirection;
import engine.renderer.interfaces.ICamera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera implements ICamera {
    private static final float YAW = -90.0f;
    private static final float PITCH = 0.0f;
    private static final float ZOOM = 45.0f;

    private Vector3f position;
    private Vector3f front;
    private Vector3f up;
    private Vector3f right;
    private Vector3f worldUp;

    private float yaw;
    private float pitch;
    private float zoom;

    private boolean trailingDirection;

    public Camera(Vector3f position) {
        this.position = position;
        this.front = new Vector3f(0.f, 0.f, -1.f);
        this.worldUp = new Vector3f(0.f, 1.f, 0.f);

        this.yaw = YAW;
        this.pitch = PITCH;
        this.zoom = ZOOM;

        this.trailingDirection = false;

        updateCameraVectors();
    }

    private void updateCameraVectors() {
        final Vector3f front = new Vector3f();
        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(pitch));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        this.front = front.normalize();
        this.right = this.front.cross(worldUp, new Vector3f()).normalize();
        this.up = this.right.cross(this.front, new Vector3f()).normalize();
    }

    @Override
    public void move(MoveDirection direction, float amount) {
        if (direction == MoveDirection.Left)
            position.sub(right.mul(amount, new Vector3f()));
        else if (direction == MoveDirection.Right)
            position.add(right.mul(amount, new Vector3f()));
        else if (direction == MoveDirection.Forward)
            position.add(front.mul(amount, new Vector3f()));
        else if (direction == MoveDirection.Backward)
            position.sub(front.mul(amount, new Vector3f()));
    }

    @Override
    public void updateYaw(float yaw) {
        if (trailingDirection)
            this.yaw += yaw;
        else
            this.yaw -= yaw;

        updateCameraVectors();
    }

    @Override
    public void updatePitch(float pitch) {
        if (trailingDirection)
            this.pitch -= pitch;
        else
            this.pitch += pitch;

        if (this.pitch > 89.0f)
            this.pitch = 89.0f;

        if (this.pitch < -89.0f)
            this.pitch = -89.0f;

        updateCameraVectors();
    }

    @Override
    public void updateZoom(float zoom) {
        if (this.zoom >= 1.0f && this.zoom <= 45.0f)
            this.zoom -= zoom;

        if (this.zoom <= 1.0f)
            this.zoom = 1.0f;

        if (this.zoom >= 45.0f)
            this.zoom = 45.0f;
    }

    @Override
    public float getZoom() {
        return zoom;
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    @Override
    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(position, position.add(front, new Vector3f()), up, new Matrix4f());
    }
}
