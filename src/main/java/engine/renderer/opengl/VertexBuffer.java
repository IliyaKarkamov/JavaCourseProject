package engine.renderer.opengl;

import engine.renderer.opengl.enums.BufferUsage;
import engine.renderer.opengl.interfaces.IVertexBuffer;
import org.lwjgl.opengl.GL46C;

public class VertexBuffer implements IVertexBuffer, AutoCloseable {
    private int id;
    private BufferLayout layout;

    public VertexBuffer(float[] data, BufferUsage usage) {
        id = GL46C.glCreateBuffers();

        GL46C.glBindBuffer(GL46C.GL_ARRAY_BUFFER, id);
        GL46C.glBufferData(GL46C.GL_ARRAY_BUFFER, data, BufferUsage.getValue(usage));
    }

    @Override
    public void bind() {
        GL46C.glBindBuffer(GL46C.GL_ARRAY_BUFFER, id);
    }

    @Override
    public void unbind() {
        GL46C.glBindBuffer(GL46C.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public BufferLayout getLayout() {
        return layout;
    }

    @Override
    public void setLayout(BufferLayout layout) {
        this.layout = layout;
    }

    @Override
    public void close() {
        GL46C.glDeleteBuffers(id);
    }
}
