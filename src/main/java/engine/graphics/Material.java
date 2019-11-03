package engine.graphics;

import engine.renderer.opengl.interfaces.ITexture2D;
import org.joml.Vector4f;

public class Material {
    private static final Vector4f DEFAULT_COLOR = new Vector4f(1.f, 1.f, 1.f, 1.f);

    private Vector4f ambientColor;
    private Vector4f diffuseColor;
    private Vector4f specularColor;

    private float shininess;

    private ITexture2D diffuseTexture;

    public Material() {
        ambientColor = DEFAULT_COLOR;
        diffuseColor = DEFAULT_COLOR;
        specularColor = DEFAULT_COLOR;
        shininess = 0.f;
        diffuseTexture = null;
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public ITexture2D getDiffuseTexture() {
        return diffuseTexture;
    }

    public void setDiffuseTexture(ITexture2D diffuseTexture) {
        this.diffuseTexture = diffuseTexture;
    }
}
