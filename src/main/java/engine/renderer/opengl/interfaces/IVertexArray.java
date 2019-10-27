package engine.renderer.opengl.interfaces;

public interface IVertexArray {
    void bind();
    void unbind();

    void addVertexBuffer(IVertexBuffer vertexBuffer);
    void setIndexBuffer(IIndexBuffer indexBuffer);

    int getVertexBuffersCount();
    IVertexBuffer getVertexBuffer(int i);

    IIndexBuffer getIndexBuffer();
}
