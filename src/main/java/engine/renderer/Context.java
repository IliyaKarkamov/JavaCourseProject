package engine.renderer;

import engine.renderer.interfaces.IContext;
import engine.renderer.opengl.enums.Capability;
import engine.renderer.opengl.interfaces.IVertexArray;
import org.lwjgl.opengl.GL46C;

public final class Context implements IContext {
    @Override
    public void enable(Capability capability) {
        GL46C.glEnable(Capability.getValue(capability));
    }

    @Override
    public void disable(Capability capability) {
        GL46C.glDisable(Capability.getValue(capability));
    }

    @Override
    public void setViewport(int x, int y, int width, int height) {
        GL46C.glViewport(x, y, width, height);
    }

    @Override
    public void setClearColor(float r, float g, float b, float a) {
        GL46C.glClearColor(r, g, b, a);
    }

    @Override
    public void clear() {
        GL46C.glClear(GL46C.GL_COLOR_BUFFER_BIT | GL46C.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void drawIndexed(IVertexArray vertexArray) {
        GL46C.glDrawElements(GL46C.GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL46C.GL_UNSIGNED_INT, 0);
    }
}
