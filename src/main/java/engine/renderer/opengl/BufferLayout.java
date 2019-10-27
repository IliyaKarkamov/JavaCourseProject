package engine.renderer.opengl;

import java.util.Vector;

public class BufferLayout {
    private Vector<BufferElement> elements = new Vector<>();
    private int stride;

    public void addElement(BufferElement element) {
        elements.add(element);
        calculateOffsetsAndStride();
    }

    public int getElementsCount() {
        return elements.size();
    }

    public BufferElement getElement(int i) {
        return elements.get(i);
    }

    public int getStride() {
        return stride;
    }

    private void calculateOffsetsAndStride() {
        int offset = 0;
        stride = 0;

        for (BufferElement element : elements) {
            element.setOffset(offset);
            offset += element.getSize();
            stride += element.getSize();
        }
    }
}
