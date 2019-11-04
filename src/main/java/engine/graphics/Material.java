package engine.graphics;

import engine.renderer.opengl.interfaces.ITexture2D;

public class Material {
    private ITexture2D diffuseTexture;
    private ITexture2D specularTexture;
    private ITexture2D emissionTexture;

    private float shininess;

    public Material() {
        shininess = 0.f;
        diffuseTexture = null;
        specularTexture = null;
        emissionTexture = null;
    }

    public ITexture2D getDiffuseTexture() {
        return diffuseTexture;
    }

    public void setDiffuseTexture(ITexture2D diffuseTexture) {
        this.diffuseTexture = diffuseTexture;
    }

    public ITexture2D getSpecularTexture() {
        return specularTexture;
    }

    public void setSpecularTexture(ITexture2D specularTexture) {
        this.specularTexture = specularTexture;
    }

    public ITexture2D getEmissionTexture() {
        return emissionTexture;
    }

    public void setEmissionTexture(ITexture2D emissionTexture) {
        this.emissionTexture = emissionTexture;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
}
