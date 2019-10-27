package engine.renderer.opengl;

import engine.renderer.opengl.enums.DataType;
import engine.renderer.opengl.interfaces.IIndexBuffer;
import engine.renderer.opengl.interfaces.IVertexArray;
import engine.renderer.opengl.interfaces.IVertexBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46C;

import java.nio.IntBuffer;
import java.util.Vector;

public class VertexArray implements IVertexArray, AutoCloseable {
    private int id = 0;
    private int vertexBufferIndex = 0;

    private Vector<IVertexBuffer> vertexBuffers = new Vector<>();
    private IIndexBuffer indexBuffer;

    public VertexArray() {
        id = GL46C.glCreateVertexArrays();
    }

    @Override
    public void bind() {
        GL46C.glBindVertexArray(id);
    }

    @Override
    public void unbind() {
        GL46C.glBindVertexArray(0);
    }

    @Override
    public void addVertexBuffer(IVertexBuffer vertexBuffer) {
        bind();
        vertexBuffer.bind();

        final BufferLayout layout = vertexBuffer.getLayout();
        final int elementsCount = layout.getElementsCount();

        for (int i = 0; i < elementsCount; i++) {
            final BufferElement element = layout.getElement(i);

            IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
            intBuffer.put(element.getOffset());

            GL46C.glEnableVertexAttribArray(vertexBufferIndex);
            GL46C.glVertexAttribPointer(vertexBufferIndex,
                    element.getComponentCount(),
                    DataType.getValue(element.getType()),
                    element.isNormalized(),
                    layout.getStride(),
                    intBuffer);

            vertexBufferIndex++;
        }

        vertexBuffers.add(vertexBuffer);
    }

    @Override
    public void setIndexBuffer(IIndexBuffer indexBuffer) {
        bind();
        indexBuffer.bind();

        this.indexBuffer = indexBuffer;
    }

    @Override
    public int getVertexBuffersCount() {
        return vertexBuffers.size();
    }

    @Override
    public IVertexBuffer getVertexBuffer(int i) {
        return vertexBuffers.get(i);
    }

    @Override
    public IIndexBuffer getIndexBuffer() {
        return indexBuffer;
    }

    @Override
    public void close() throws Exception {
        GL46C.glDeleteVertexArrays(id);
    }
}
