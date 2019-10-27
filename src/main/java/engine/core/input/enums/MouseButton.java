package engine.core.input.enums;

public enum MouseButton {
    Unknown(-1),
    MouseButtonLeft(0),
    MouseButtonRight(1),
    MouseButtonMiddle(2),
    MouseButton4(3),
    MouseButton5(4),
    MouseButton6(5),
    MouseButton7(6),
    MouseButton8(7);

    private final int value;
    private static final MouseButton[] values = MouseButton.values();

    MouseButton(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MouseButton getValue(int value) {
        for (MouseButton type : values) {
            if (type.getValue() == value) {
                return type;
            }
        }

        return MouseButton.Unknown;
    }
}
