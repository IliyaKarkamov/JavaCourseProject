package engine.renderer.interfaces;

import engine.renderer.opengl.enums.Capability;
import engine.renderer.opengl.interfaces.IVertexArray;

public interface IContext {
    void enable(Capability capability);
    void disable(Capability capability);

    void setViewport(int x, int y, int width, int height);

    void setClearColor(float r, float g, float b, float a);

    void clear();

    void drawIndexed(IVertexArray vertexArray);
}
