package engine.renderer.opengl.interfaces;

public interface IIndexBuffer {
    void bind();

    void unbind();

    int getCount();
}
