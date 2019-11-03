package engine.core.utils;

import java.util.Vector;

public class ArrayHelper {
    public static int[] toArray(Vector<Integer> vector, int defaultValue) {
        return vector.stream().mapToInt(x -> x != null ? x : defaultValue).toArray();
    }

    public static float[] toArray(Vector<Float> vector, float defaultValue) {
        float[] floatArray = new float[vector.size()];

        int i = 0;

        for (Float x : vector) {
            floatArray[i++] = x != null ? x : defaultValue;
        }

        return floatArray;
    }
}
