package ice.graphic.projection;

import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.GL_MODELVIEW;
import static javax.microedition.khronos.opengles.GL11.GL_PROJECTION;

/**
 * User: Jason
 * Date: 11-12-3
 * Time: 下午5:43
 */
public class PerspectiveProjection implements Projection {

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
    }


    private GLU glu;
    private float fovy;
    private float zNear, zFar;
}
