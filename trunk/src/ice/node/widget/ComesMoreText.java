package ice.node.widget;

import android.view.animation.AnimationUtils;
import ice.animation.Interpolator.AccelerateDecelerateInterpolator;
import ice.animation.Interpolator.Interpolator;

import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.GL_SCISSOR_TEST;

/**
 * User: jason
 * Date: 12-2-6
 * Time: 上午11:47
 */
public class ComesMoreText extends TextGrid {

    private static Interpolator interpolator = new AccelerateDecelerateInterpolator();

    public ComesMoreText(int width, int height, long during) {
        super(width, height);
        this.during = during;
    }

    @Override
    protected void onDraw(GL11 gl) {

        long current = AnimationUtils.currentAnimationTimeMillis();

        if (startStamp == 0)
            startStamp = current;

        long sub = current - startStamp;

        boolean clip = sub < during;

        if (clip) {
            gl.glEnable(GL_SCISSOR_TEST);

            float interpolatedTime = interpolator.getInterpolation(sub / (float) during);

            currentScissor = (int) (getRealWidth() * interpolatedTime) + 1;

            gl.glScissor(0, (int) (getAbsolutePos().y - height), currentScissor, (int) height);
        }

        super.onDraw(gl);

        if (clip)
            gl.glDisable(GL_SCISSOR_TEST);
    }

    private long during;
    private long startStamp;
    private int currentScissor;
}
