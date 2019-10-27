package engine.renderer.opengl.enums;

import org.lwjgl.opengl.GL46C;

public enum DataType {
    Float,
    Float2,
    Float3,
    Float4,
    Mat3,
    Mat4,
    Int,
    Int2,
    Int3,
    Int4,
    Bool;

    public static int sizeOfType(DataType type) {
        switch (type) {
            case Float:
                return java.lang.Float.BYTES;
            case Float2:
                return java.lang.Float.BYTES * 2;
            case Float3:
                return java.lang.Float.BYTES * 3;
            case Float4:
                return java.lang.Float.BYTES * 4;
            case Mat3:
                return java.lang.Float.BYTES * 3 * 3;
            case Mat4:
                return java.lang.Float.BYTES * 4 * 4;
            case Int:
                return Integer.BYTES;
            case Int2:
                return Integer.BYTES * 2;
            case Int3:
                return Integer.BYTES * 3;
            case Int4:
                return Integer.BYTES * 4;
            case Bool:
                return 1;
        }

        assert false;
        return 0;
    }

    public static int getValue(DataType type) {
        switch (type) {
            case Float:
            case Float2:
            case Float3:
            case Float4:
            case Mat3:
            case Mat4:
                return GL46C.GL_FLOAT;
            case Int:
            case Int4:
            case Int3:
            case Int2:
                return GL46C.GL_INT;
            case Bool:
                return GL46C.GL_BOOL;
        }

        assert false;
        return 0;
    }
}
