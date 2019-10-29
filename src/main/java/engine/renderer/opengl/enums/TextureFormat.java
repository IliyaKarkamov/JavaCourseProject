package engine.renderer.opengl.enums;

import org.lwjgl.opengl.GL46C;

public enum TextureFormat {
    None,
    RGB,
    RGBA;

    public static int getValue(TextureFormat type) {
        switch (type) {
            case RGB:
                return GL46C.GL_RGB;
            case RGBA:
                return GL46C.GL_RGBA;
        }

        assert false;
        return 0;
    }
}
