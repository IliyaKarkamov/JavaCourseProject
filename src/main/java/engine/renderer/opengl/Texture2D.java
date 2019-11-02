package engine.renderer.opengl;

import engine.renderer.opengl.enums.TextureFormat;
import engine.renderer.opengl.interfaces.ITexture2D;
import org.lwjgl.opengl.GL46C;

import java.nio.ByteBuffer;

public class Texture2D implements ITexture2D, AutoCloseable {
    private int id;
    private int width;
    private int height;
    private TextureFormat format;

    public Texture2D(ByteBuffer data, int width, int height, TextureFormat format) {
        id = GL46C.glGenTextures();

        this.width = width;
        this.height = height;
        this.format = format;

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

    @Override
    public void close() {
        GL46C.glDeleteTextures(id);
    }
}
