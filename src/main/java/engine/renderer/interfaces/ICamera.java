package engine.renderer.interfaces;

import engine.renderer.enums.MoveDirection;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface ICamera {
    void move(MoveDirection direction, float amount);

    void updateYaw(float yaw);

    void updatePitch(float pitch);

    void updateZoom(float zoom);

    float getZoom();

    Vector3f getPosition();

    Matrix4f getViewMatrix();
}
