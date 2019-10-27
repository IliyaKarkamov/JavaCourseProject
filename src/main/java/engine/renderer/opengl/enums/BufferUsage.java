package engine.renderer.opengl.enums;

import org.lwjgl.opengl.GL46C;

public enum BufferUsage {
    StreamDraw,
    StreamRead,
    StreamCopy,
    StaticDraw,
    StaticRead,
    StaticCopy,
    DynamicDraw,
    DynamicRead,
    DynamicCopy;

    public static int getValue(BufferUsage usage) {
        switch (usage) {
            case StreamDraw:
                return GL46C.GL_STREAM_DRAW;
            case StreamRead:
                return GL46C.GL_STREAM_READ;
            case StreamCopy:
                return GL46C.GL_STREAM_COPY;
            case StaticDraw:
                return GL46C.GL_STATIC_DRAW;
            case StaticRead:
                return GL46C.GL_STATIC_READ;
            case StaticCopy:
                return GL46C.GL_STATIC_COPY;
            case DynamicDraw:
                return GL46C.GL_DYNAMIC_DRAW;
            case DynamicRead:
                return GL46C.GL_DYNAMIC_READ;
            case DynamicCopy:
                return GL46C.GL_DYNAMIC_COPY;
        }

        assert false;
        return 0;
    }
}
