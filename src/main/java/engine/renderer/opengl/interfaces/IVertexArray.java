package engine.renderer.opengl.interfaces;

public interface IVertexArray {
    void bind();

    void unbind();

    void addVertexBuffer(IVertexBuffer vertexBuffer);

    int getVertexBuffersCount();

    IVertexBuffer getVertexBuffer(int i);

    IIndexBuffer getIndexBuffer();

    void setIndexBuffer(IIndexBuffer indexBuffer);
}
