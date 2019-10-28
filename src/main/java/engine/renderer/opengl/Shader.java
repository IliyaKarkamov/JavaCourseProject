package engine.renderer.opengl;

import engine.renderer.opengl.enums.DataType;
import engine.renderer.opengl.interfaces.IShader;
import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46C;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class Shader implements IShader {
    private int id;
    private String name;
    private Map<String, Integer> uniformLocation = new HashMap<>();

    public Shader(String name, String vertexSource, String fragmentSource) {

    }

    @Override
    public void bind() {
        GL46C.glUseProgram(id);
    }

    @Override
    public void unbind() {
        GL46C.glUseProgram(0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setUniform(String name, int value) {
        GL46C.glUniform1i(getUniformLocation(name), value);
    }

    @Override
    public void setUniform(String name, float value) {
        GL46C.glUniform1f(getUniformLocation(name), value);
    }

    @Override
    public void setUniform(String name, Vector2f value) {
        GL46C.glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    @Override
    public void setUniform(String name, Vector3f value) {
        GL46C.glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    @Override
    public void setUniform(String name, Vector4f value) {
        GL46C.glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    @Override
    public void setUniform(String name, Matrix3f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(DataType.sizeOfType(DataType.Mat3));
        GL46C.glUniformMatrix3fv(getUniformLocation(name), false, value.get(buffer));
    }

    @Override
    public void setUniform(String name, Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(DataType.sizeOfType(DataType.Mat4));
        GL46C.glUniformMatrix4fv(getUniformLocation(name), false, value.get(buffer));
    }

    private int getUniformLocation(String name) {
        Integer location = uniformLocation.get(name);

        if (location != null)
            return location;

        location = GL46C.glGetUniformLocation(id, name);
        uniformLocation.put(name, location);

        return location;
    }
}
