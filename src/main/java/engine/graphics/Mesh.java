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

        shader.setUniform("material.shininess", material.getShininess());

        final ITexture2D diffuseTexture = material.getDiffuseTexture();

        if (diffuseTexture != null) {
            diffuseTexture.bind(0);
            shader.setUniform("material.diffuse", 0);
        }

        final ITexture2D specularTexture = material.getSpecularTexture();

        if (specularTexture != null) {
            specularTexture.bind(1);
            shader.setUniform("material.specular", 1);
        }

        final ITexture2D emissionTexture = material.getEmissionTexture();

        if (emissionTexture != null) {
            emissionTexture.bind(2);
            shader.setUniform("material.emission", 2);
        }

        context.drawIndexed(vertexArray);
    }
}
