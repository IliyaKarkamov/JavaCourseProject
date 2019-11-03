package engine.graphics.interfaces;

import engine.graphics.Mesh;
import engine.renderer.interfaces.IContext;
import engine.renderer.opengl.interfaces.IShader;

public interface IModel {
    void addMesh(Mesh mesh);

    void draw(IContext context, IShader shader);
}