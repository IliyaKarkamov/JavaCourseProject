package engine.resources.factories;

import engine.renderer.opengl.Shader;
import engine.renderer.opengl.exceptions.ShaderCompileException;
import engine.renderer.opengl.exceptions.ShaderLinkException;
import engine.renderer.opengl.exceptions.ShaderLoadException;
import engine.renderer.opengl.interfaces.IShader;
import engine.resources.exceptions.ResourceLoadException;
import engine.resources.interfaces.IResourceFactory;
import engine.resources.interfaces.IResourceManager;

import java.io.IOException;
import java.io.InputStream;

public class ShaderFactory implements IResourceFactory<IShader> {
    @Override
    public Class<IShader> getType() {
        return IShader.class;
    }

    @Override
    public IShader create(String resource) throws ResourceLoadException {
        try {
            String vertexSource;
            String fragmentSource;

            final ClassLoader classLoader = Shader.class.getClassLoader();

            try (final InputStream vertexStream = classLoader.getResourceAsStream(resource + ".vert.glsl");
                 final InputStream fragmentStream = classLoader.getResourceAsStream(resource + ".frag.glsl")) {

                if (vertexStream == null || fragmentStream == null) {
                    throw new ShaderLoadException("Unable to load vertex or fragment shader resource.");
                }

                vertexSource = new String(vertexStream.readAllBytes());
                fragmentSource = new String(fragmentStream.readAllBytes());
            } catch (IOException e) {
                throw new ShaderLoadException("Unable to load vertex or fragment shader resource. " + e.getMessage());
            }

            return new Shader(resource.substring(resource.lastIndexOf('/') + 1), vertexSource, fragmentSource);
        } catch (ShaderLoadException | ShaderCompileException | ShaderLinkException e) {
            throw new ResourceLoadException("Shader resource creation failed.", e);
        }
    }

    @Override
    public IResourceManager getResourceManager() {
        return null;
    }
}
