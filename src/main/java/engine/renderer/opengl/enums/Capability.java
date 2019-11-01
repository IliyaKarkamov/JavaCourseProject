package engine.renderer.opengl.enums;

import org.lwjgl.opengl.GL46C;

public enum Capability {
    Blend,
    DepthTest,
    CullFace;

    public static int getValue(Capability capability) {
        switch (capability) {
            case Blend:
                return GL46C.GL_BLEND;
            case DepthTest:
                return GL46C.GL_DEPTH_TEST;
            case CullFace:
                return GL46C.GL_CULL_FACE;
        }

        assert false;
        return 0;
    }
}
