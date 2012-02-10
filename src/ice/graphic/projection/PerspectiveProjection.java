package ice.graphic.projection;

import android.opengl.GLU;
import ice.engine.EngineContext;

import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.GL_MODELVIEW;
import static javax.microedition.khronos.opengles.GL11.GL_PROJECTION;

/**
 * gl.glFrustumf(-width / 2, width / 2, -height / 2, height / 2, zNear, zFar);
 * 与
 * glu.gluPerspective(
 * gl,
 * fovy,
 * (float) width / (float) height,
 * zNear,
 * zFar
 * );
 * 等价.
 * <p/>
 * <p/>
 * User: Jason
 * Date: 11-12-3
 * Time: 下午5:43
 */
public class PerspectiveProjection implements Projection {
    public static final float Z_NEAR = 0.1f, DEFAULT_DEPTH = 1000;


    public PerspectiveProjection(GLU glu, float fovy) {
        this(glu, fovy, Z_NEAR, getZFarOfWindow(fovy) + DEFAULT_DEPTH);
    }


    public PerspectiveProjection(GLU glu, float fovy, float zNear, float zFar) {
        this.glu = glu;
        this.fovy = fovy;
        this.zNear = zNear;
        this.zFar = zFar;
    }

    @Override
    public void setUp(GL11 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(
                gl,
                fovy,
                (float) width / (float) height,
                zNear,
                zFar
        );

        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        //double halfFovy = Math.toRadians(fovy / 2.0f);
        // float zEye = (float) (height / (2 * Math.tan(halfFovy)));
//        GLU.gluLookAt(
//                gl,
//
//                width / 2,
//                height / 2,
//                near,
//
//                width / 2,
//                height / 2,
//                zFar,
//
//                0.0f,
//                1.0f,
//                0.0f
//        );
    }

    public static float getZFarOfWindow(float fovy) {
        double halfFovy = Math.toRadians(fovy / 2.0f);
        int height = EngineContext.getInstance().getApp().getHeight();

        return (float) (height / (2 * Math.tan(halfFovy)));
    }


    public float getZFarOfWindow() {
        return getZFarOfWindow(fovy);
    }


    private GLU glu;
    private float fovy;
    private float zNear, zFar;
}
