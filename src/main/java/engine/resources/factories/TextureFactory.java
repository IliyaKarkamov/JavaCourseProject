package engine.resources.factories;

import engine.renderer.opengl.Texture2D;
import engine.renderer.opengl.enums.TextureFormat;
import engine.renderer.opengl.exceptions.TextureLoadException;
import engine.renderer.opengl.interfaces.ITexture2D;
import engine.resources.exceptions.ResourceLoadException;
import engine.resources.interfaces.IResourceFactory;
import engine.resources.interfaces.IResourceManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;

public class TextureFactory implements IResourceFactory<ITexture2D> {
    @Override
    public Class<ITexture2D> getType() {
        return ITexture2D.class;
    }

    @Override
    public ITexture2D create(String resource) throws ResourceLoadException {
        try {
            InputStream stream = Texture2D.class.getClassLoader().getResourceAsStream(resource);

            if (stream == null) {
                throw new TextureLoadException("Unable to open the given resource file.");
            }

            ByteBuffer image;
            int width, height;

            try (MemoryStack stack = MemoryStack.stackPush()) {
                ByteBuffer imageBuffer = BufferUtils.createByteBuffer(stream.available());
                imageBuffer.put(stream.readAllBytes());
                imageBuffer.flip();

                IntBuffer w = stack.mallocInt(1);
                IntBuffer h = stack.mallocInt(1);
                IntBuffer comp = stack.mallocInt(1);

                if (!stbi_info_from_memory(imageBuffer, w, h, comp)) {
                    throw new TextureLoadException("Failed to load a texture file." + System.lineSeparator() + stbi_failure_reason());
                }

                stbi_set_flip_vertically_on_load(true);

                image = stbi_load_from_memory(imageBuffer, w, h, comp, STBI_rgb_alpha);

                if (image == null) {
                    throw new TextureLoadException("Failed to load a texture file." + System.lineSeparator() + stbi_failure_reason());
                }

                width = w.get();
                height = h.get();
            } catch (IOException e) {
                throw new TextureLoadException("Unable to read the given resource file. " + e.getMessage());
            }

            ITexture2D texture;

            try {
                texture = new Texture2D(image, width, height, TextureFormat.RGBA);
            } finally {
                stbi_image_free(image);
            }

            return texture;
        } catch (TextureLoadException e) {
            throw new ResourceLoadException("Texture resource creation failed!", e);
        }
    }

    @Override
    public IResourceManager getResourceManager() {
        return null;
    }
}
