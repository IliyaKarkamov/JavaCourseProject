package engine.graphics;

import engine.graphics.interfaces.IModel;
import engine.renderer.interfaces.IContext;
import engine.renderer.opengl.interfaces.IShader;

import java.util.Vector;

public class Model implements IModel {
    private Vector<Mesh> meshes = new Vector<>();

    @Override
    public void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    @Override
    public void draw(IContext context, IShader shader) {
        for (Mesh mesh : meshes) {
            mesh.draw(context, shader);
        }
    }
}
