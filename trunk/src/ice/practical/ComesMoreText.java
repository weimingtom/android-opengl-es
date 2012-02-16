package ice.practical;

import android.view.animation.AnimationUtils;
import ice.animation.Interpolator.AccelerateDecelerateInterpolator;
import ice.animation.Interpolator.Interpolator;
import ice.model.Point3F;
import ice.node.widget.TextOverlay;

import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.GL_SCISSOR_TEST;

/**
 * User: jason
 * Date: 12-2-6
 * Time: 上午11:47
 */
public class ComesMoreText extends TextOverlay {

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

        finished = sub > during;

        if (!finished) {
            gl.glEnable(GL_SCISSOR_TEST);

            float interpolatedTime = interpolator.getInterpolation(sub / (float) during);

            currentScissor = (int) (getRealWidth() * interpolatedTime) + 1;

            Point3F absolutePos = getAbsolutePos();

            gl.glScissor((int) absolutePos.x, (int) (absolutePos.y - height), currentScissor, (int) height);
        }

        super.onDraw(gl);

        if (!finished)
            gl.glDisable(GL_SCISSOR_TEST);
    }

    public boolean isFinished() {
        return finished;
    }

    private long during;
    private long startStamp;
    private boolean finished;
    private int currentScissor;
}
