package engine.renderer.opengl;

import engine.renderer.opengl.enums.DataType;

public class BufferElement {
    private DataType type;
    private String name;
    private int size;
    private int offset;
    private boolean normalized;

    public BufferElement(DataType type, String name, boolean normalized) {
        this.type = type;
        this.name = name;
        this.size = DataType.sizeOfType(type);
        this.offset = 0;
        this.normalized = normalized;
    }

    public int getComponentCount() {
        switch (type) {
            case Float:
            case Int:
            case Bool:
                return 1;
            case Float2:
            case Int2:
                return 2;
            case Float3:
            case Int3:
                return 3;
            case Float4:
            case Int4:
                return 4;
            case Mat3:
                return 3 * 3;
            case Mat4:
                return 4 * 4;
        }

        assert false;
        return 0;
    }

    public DataType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isNormalized() {
        return normalized;
    }
}
