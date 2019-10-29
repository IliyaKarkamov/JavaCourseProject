package engine.renderer.opengl.interfaces;

import engine.renderer.opengl.enums.TextureFormat;

public interface ITexture2D {
    void bind(int slot);

    TextureFormat getFormat();
    int getWidth();
    int getHeight();
}
