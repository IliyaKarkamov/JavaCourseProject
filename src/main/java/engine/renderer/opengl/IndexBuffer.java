package engine.renderer.opengl;

import engine.renderer.opengl.enums.BufferUsage;
import engine.renderer.opengl.interfaces.IIndexBuffer;
import org.lwjgl.opengl.GL46C;

public class IndexBuffer implements IIndexBuffer, AutoCloseable {
    private int id;
    private int count;

    public IndexBuffer(int[] data, BufferUsage usage) {
        id = GL46C.glCreateBuffers();
        count = data.length;

        GL46C.glBindBuffer(GL46C.GL_ELEMENT_ARRAY_BUFFER, id);
        GL46C.glBufferData(GL46C.GL_ELEMENT_ARRAY_BUFFER, data, BufferUsage.getValue(usage));
    }

    @Override
    public void bind() {
        GL46C.glBindBuffer(GL46C.GL_ELEMENT_ARRAY_BUFFER, id);
    }

    @Override
    public void unbind() {
        GL46C.glBindBuffer(GL46C.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void close() {
        GL46C.glDeleteBuffers(id);
    }
}
