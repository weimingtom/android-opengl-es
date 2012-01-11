package ice.node.particle_system;

import android.opengl.GLUtils;
import android.view.animation.AnimationUtils;
import ice.graphic.Texture;
import ice.node.Drawable;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static javax.microedition.khronos.opengles.GL11.*;
import static ice.model.Constants.*;

/**
 * User: jason
 * Date: 12-1-11
 * Time: 下午3:29
 */
public abstract class PointParticleSystem extends Drawable {


    public PointParticleSystem(int maxParticleNum, Texture texture) {
        this.maxParticleNum = maxParticleNum;
        this.texture = texture;
        //blend = true;

        ByteBuffer vfb = ByteBuffer.allocateDirect(SIZE_OF_FLOAT * 2 * maxParticleNum);
        vfb.order(ByteOrder.nativeOrder());
        vertexBuffer = vfb.asFloatBuffer();

        vfb = ByteBuffer.allocateDirect(SIZE_OF_FLOAT * 4 * maxParticleNum);
        vfb.order(ByteOrder.nativeOrder());
        colorBuffer = vfb.asFloatBuffer();
    }

    public void init() {
        if (inited) {
            System.out.println("already inited !");
            return;
        }

        Particle[] particles = onSetupParticles(maxParticleNum);
        if (particles.length > maxParticleNum)
            throw new IllegalStateException("first generated particles size shoud smaller than " + maxParticleNum);

        this.particles = particles;
        inited = true;
    }

    public void reset() {
        inited = false;
    }


    public void step() {
        if (!inited) throw new IllegalStateException("particles not setup yet !");
        onUpdateParticles(particles, AnimationUtils.currentAnimationTimeMillis());
    }

    protected abstract Particle[] onSetupParticles(int maxParticleNum);

    protected abstract void onUpdateParticles(Particle[] particles, long current);

    @Override
    protected void onDraw(GL11 gl) {
        int liveCount = fillActive();

        if (liveCount > 0) {
            drawActiveParticles(gl, liveCount);
        }

        step();
    }

    private void drawActiveParticles(GL11 gl, int liveCount) {
        gl.glEnable(GL_POINT_SPRITE_OES);
//        gl.glEnableClientState(GL_POINT_SIZE_ARRAY_OES);
        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL_COLOR_ARRAY);

        boolean blendState = blend;
        if (blendState) {
            gl.glEnable(GL_BLEND);
            gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE);
            gl.glDisable(GL_DEPTH_TEST);
        }


        // texture.attach(gl);
//        gl.glEnable(GL_TEXTURE_2D);
//        GLUtils.texImage2D(GL_TEXTURE_2D, 0, texture.getBitmap(), 0);

        vertexBuffer.position(0);
        colorBuffer.position(0);
        colorBuffer.limit(4 * liveCount);
        vertexBuffer.limit(2 * liveCount);

        gl.glVertexPointer(2, GL_FLOAT, 0, vertexBuffer);
        gl.glColorPointer(4, GL_FLOAT, 0, colorBuffer);
        //todo  gl.glPointSizePointerOES(GL_FLOAT, 0, vertexBuffer);

        gl.glPointSize(20);

//        gl.glEnable(GL_TEXTURE_2D);
//        int[] buffer = new int[1];
//        gl.glGenTextures(buffer.length, buffer, 0);
//        gl.glBindTexture(GL_TEXTURE_2D, buffer[0]);
//        GLUtils.texImage2D(GL_TEXTURE_2D, 0, texture.getBitmap(), 0);

        texture.attach(gl);

        gl.glTexEnvi(GL_POINT_SPRITE_OES, GL_COORD_REPLACE_OES, GL_TRUE);


        gl.glDrawArrays(GL_POINTS, 0, liveCount / 2);

        gl.glDisable(GL_TEXTURE_2D);
        //texture.unattach(gl);

        gl.glDisableClientState(GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL_COLOR_ARRAY);
//        gl.glDisableClientState(GL11.GL_POINT_SIZE_ARRAY_OES);
        gl.glDisable(GL11.GL_POINT_SPRITE_OES);

        if (blendState) {
            gl.glDisable(GL_BLEND);
            gl.glEnable(GL_DEPTH_TEST);
            gl.glDisableClientState(GL_COLOR_ARRAY);
        }
    }

    private int fillActive() {
        vertexBuffer.position(0);
        colorBuffer.position(0);

        int liveCount = 0;

        for (int index = 0; index < maxParticleNum; index++) {

            Particle particle = particles[index];

            if (particle.alive) {
                vertexBuffer.put(
                        new float[]{particle.posX, particle.posY}
                );

                colorBuffer.put(
                        new float[]{particle.colorR, particle.colorG, particle.colorB, particle.colorA}
                );

                liveCount++;
            }

        }

        return liveCount;
    }

    private boolean blend;
    private Texture texture;
    private int maxParticleNum;
    private boolean inited;
    protected Particle particles[];
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
}
