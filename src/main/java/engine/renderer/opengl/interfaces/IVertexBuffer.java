package engine.renderer.opengl.interfaces;

import engine.renderer.opengl.BufferLayout;

public interface IVertexBuffer {
    void bind();
    void unbind();

    BufferLayout getLayout();
    void setLayout(BufferLayout layout);
}
