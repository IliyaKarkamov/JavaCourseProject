package engine.renderer.opengl;

import engine.renderer.opengl.enums.DataType;
import engine.renderer.opengl.exceptions.ShaderCompileException;
import engine.renderer.opengl.exceptions.ShaderLinkException;
import engine.renderer.opengl.interfaces.IShader;
import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46C;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Shader implements IShader {
    private int id;
    private String name;
    private Map<String, Integer> uniformLocation = new HashMap<>();

    public Shader(String name, String vertexSource, String fragmentSource) throws ShaderCompileException, ShaderLinkException {
        this.name = name;

        Map<Integer, String> sources = new HashMap<>();
        sources.put(GL46C.GL_VERTEX_SHADER, vertexSource);
        sources.put(GL46C.GL_FRAGMENT_SHADER, fragmentSource);

        compileAndLink(sources);
    }

    private void compileAndLink(Map<Integer, String> sources) throws ShaderCompileException, ShaderLinkException {
        id = GL46C.glCreateProgram();

        Vector<Integer> shaderIds = new Vector<>();

        for (Map.Entry<Integer, String> source : sources.entrySet()) {
            int shaderId = GL46C.glCreateShader(source.getKey());

            GL46C.glShaderSource(shaderId, source.getValue());
            GL46C.glCompileShader(shaderId);

            int[] isCompiled = new int[]{GL46C.GL_FALSE};
            GL46C.glGetShaderiv(shaderId, GL46C.GL_COMPILE_STATUS, isCompiled);

            if (isCompiled[0] == GL46C.GL_FALSE) {
                int[] maxLength = new int[1];
                GL46C.glGetShaderiv(shaderId, GL46C.GL_INFO_LOG_LENGTH, maxLength);

                ByteBuffer infoLog = BufferUtils.createByteBuffer(maxLength[0]);
                GL46C.glGetShaderInfoLog(shaderId, maxLength, infoLog);
                GL46C.glDeleteShader(shaderId);

                throw new ShaderCompileException(infoLog.toString());
            }

            GL46C.glAttachShader(id, shaderId);
            shaderIds.add(shaderId);
        }

        GL46C.glLinkProgram(id);

        int[] isLinked = new int[]{GL46C.GL_FALSE};
        GL46C.glGetProgramiv(id, GL46C.GL_LINK_STATUS, isLinked);

        if (isLinked[0] == GL46C.GL_FALSE) {
            int[] maxLength = new int[1];
            GL46C.glGetShaderiv(id, GL46C.GL_INFO_LOG_LENGTH, maxLength);

            ByteBuffer infoLog = BufferUtils.createByteBuffer(maxLength[0]);
            GL46C.glGetProgramInfoLog(id, maxLength, infoLog);
            GL46C.glDeleteProgram(id);

            for (int shaderId : shaderIds) {
                GL46C.glDeleteShader(shaderId);
            }

            throw new ShaderLinkException(infoLog.toString());
        }

        for (int shaderId : shaderIds) {
            GL46C.glDetachShader(id, shaderId);
            GL46C.glDeleteShader(shaderId);
        }
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
