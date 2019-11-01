package engine.renderer.opengl.interfaces;

import org.joml.*;

public interface IShader {
    void bind();

    void unbind();

    String getName();

    void setUniform(String name, int value);

    void setUniform(String name, float value);

    void setUniform(String name, Vector2f value);

    void setUniform(String name, Vector3f value);

    void setUniform(String name, Vector4f value);

    void setUniform(String name, Matrix3f value);

    void setUniform(String name, Matrix4f value);
}
