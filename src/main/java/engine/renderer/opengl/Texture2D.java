package engine.renderer.opengl;

import engine.renderer.opengl.enums.TextureFormat;
import engine.renderer.opengl.exceptions.TextureLoadException;
import engine.renderer.opengl.interfaces.ITexture2D;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46C;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;

public class Texture2D implements ITexture2D {
    private int id;
    private int width;
    private int height;
    private TextureFormat format;

    public Texture2D(ByteBuffer data, int width, int height, TextureFormat format) {
        id = GL46C.glGenTextures();

        GL46C.glBindTexture(GL46C.GL_TEXTURE_2D, id);

        GL46C.glTexParameteri(GL46C.GL_TEXTURE_2D, GL46C.GL_TEXTURE_MIN_FILTER, GL46C.GL_LINEAR_MIPMAP_LINEAR);
        GL46C.glTexParameteri(GL46C.GL_TEXTURE_2D, GL46C.GL_TEXTURE_MAG_FILTER, GL46C.GL_LINEAR);
        GL46C.glTexParameteri(GL46C.GL_TEXTURE_2D, GL46C.GL_TEXTURE_WRAP_S, GL46C.GL_CLAMP_TO_EDGE);
        GL46C.glTexParameteri(GL46C.GL_TEXTURE_2D, GL46C.GL_TEXTURE_WRAP_T, GL46C.GL_CLAMP_TO_EDGE);

        GL46C.glTexImage2D(GL46C.GL_TEXTURE_2D,
                0,
                TextureFormat.getValue(format),
                width,
                height,
                0,
                TextureFormat.getValue(format),
                GL46C.GL_UNSIGNED_BYTE,
                data);

        GL46C.glGenerateMipmap(GL46C.GL_TEXTURE_2D);
        GL46C.glBindTexture(GL46C.GL_TEXTURE_2D, 0);
    }

    @Override
    public void bind(int slot) {
        GL46C.glBindTextureUnit(slot, id);
    }

    @Override
    public TextureFormat getFormat() {
        return format;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public static ITexture2D create(String resource) throws TextureLoadException {
        InputStream stream = Texture2D.class.getClassLoader().getResourceAsStream(resource);

        if (stream == null) {
            throw new TextureLoadException("Unable to open the given resource file. ");
        }

        ByteBuffer image;
        int width, height;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            ByteBuffer imageBuffer = stack.malloc(stream.available());
            imageBuffer.put(stream.readAllBytes());
            imageBuffer.flip();

            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            if (!stbi_info_from_memory(imageBuffer, w, h, comp)) {
                throw new TextureLoadException("Failed to load a texture file." + System.lineSeparator() + stbi_failure_reason());
            }

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
    }
}
