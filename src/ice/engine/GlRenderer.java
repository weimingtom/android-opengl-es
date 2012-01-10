package ice.engine;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import ice.graphic.projection.Projection;
import ice.node.DrawableParent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL10.*;

/**
 * User: ice
 * Date: 12-1-6
 * Time: 下午4:24
 */
public class GlRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = GlRenderer.class.getSimpleName();

    public GlRenderer(Projection projection) {
        this.projection = projection;

        drawDispatcher = new DrawableParent<Scene>();
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GL11 gl = (GL11) gl10;

        gl.glClearColor(0, 0, 0, 1.0f);
        gl.glShadeModel(GL_SMOOTH);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        onInit(gl);

        System.out.println("GL_RENDERER = " + gl.glGetString(GL_RENDERER));
        System.out.println("GL_VENDOR = " + gl.glGetString(GL_VENDOR));
        System.out.println("GL_VERSION = " + gl.glGetString(GL_VERSION));
        System.out.println("GL_EXTENSIONS = " + gl.glGetString(GL_EXTENSIONS));
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GL11 gl = (GL11) gl10;

        projection.setUp(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GL11 gl = (GL11) gl10;

        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        App app = EngineContext.getInstance().getApp();

        gl.glTranslatef(-(app.getWidth() >> 1), -(app.getHeight() >> 1), 0);

        drawDispatcher.draw(gl);

        log(gl);

        try {
            Thread.sleep(10);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public DrawableParent<Scene> getDrawDispatcher() {
        return drawDispatcher;
    }


    protected void onInit(GL11 gl) {

    }

    private void log(GL11 gl) {
        for (int errorCode = gl.glGetError(); errorCode != GL_NO_ERROR; errorCode = gl.glGetError()) {
            Log.e(TAG, GLU.gluErrorString(errorCode));
        }

        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > 1000) {
            Log.i(TAG, "FPS: " + frames);
            frames = 0;
            lastTime = currentTime;

        }

    }




    private int frames;
    private long lastTime;
    private Projection projection;
    private DrawableParent<Scene> drawDispatcher;
}
