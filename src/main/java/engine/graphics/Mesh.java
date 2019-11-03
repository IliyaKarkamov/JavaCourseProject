package engine.graphics;

import engine.renderer.interfaces.IContext;
import engine.renderer.opengl.*;
import engine.renderer.opengl.enums.BufferUsage;
import engine.renderer.opengl.enums.DataType;
import engine.renderer.opengl.interfaces.IShader;
import engine.renderer.opengl.interfaces.ITexture2D;
import engine.renderer.opengl.interfaces.IVertexArray;
import engine.renderer.opengl.interfaces.IVertexBuffer;

public class Mesh {
    private IVertexArray vertexArray;
    private Material material;

    public Mesh(float[] positions, float[] normals, float[] texCoords, int[] indices, Material material) {
        vertexArray = new VertexArray();
        vertexArray.bind();

        {
            IVertexBuffer positionsBuffer = new VertexBuffer(positions, BufferUsage.StaticDraw);

            BufferLayout layout = new BufferLayout();
            layout.addElement(new BufferElement(DataType.Float3, "aPos", false));
            positionsBuffer.setLayout(layout);

            vertexArray.addVertexBuffer(positionsBuffer);
        }

        {
            IVertexBuffer normalsBuffer = new VertexBuffer(normals, BufferUsage.StaticDraw);

            BufferLayout layout = new BufferLayout();
            layout.addElement(new BufferElement(DataType.Float3, "aNormal", false));
            normalsBuffer.setLayout(layout);

            vertexArray.addVertexBuffer(normalsBuffer);
        }

        {
            IVertexBuffer texCoordsBuffer = new VertexBuffer(texCoords, BufferUsage.StaticDraw);

            BufferLayout layout = new BufferLayout();
            layout.addElement(new BufferElement(DataType.Float2, "aTexCoords", false));
            texCoordsBuffer.setLayout(layout);

            vertexArray.addVertexBuffer(texCoordsBuffer);
        }

        vertexArray.setIndexBuffer(new IndexBuffer(indices, BufferUsage.StaticDraw));

        this.material = material;

        vertexArray.unbind();
    }

    public void draw(IContext context, IShader shader) {
        shader.bind();
        vertexArray.bind();

        shader.setUniform("material.ambientColor", material.getAmbientColor());
        shader.setUniform("material.diffuseColor", material.getDiffuseColor());
        shader.setUniform("material.specularColor", material.getSpecularColor());
        shader.setUniform("material.shininess", material.getShininess());

        final ITexture2D texture = material.getDiffuseTexture();

        if (texture != null) {
            texture.bind(0);
            shader.setUniform("material.diffuseTexture", 0);
        }

        context.drawIndexed(vertexArray);
    }
}
