package ice.animation;

import ice.animation.Interpolator.AccelerateDecelerateInterpolator;
import ice.animation.Interpolator.Interpolator;
import ice.node.Drawable;

import javax.microedition.khronos.opengles.GL11;

public abstract class Animation {


    public interface AnimationListener {
        void onAnimationEnd(Drawable drawable);
    }

    private static final long NOT_STARTED = 0;

    public Animation(long duration) {
        this.duration = duration;
        interpolator = new AccelerateDecelerateInterpolator();
        startTime = NOT_STARTED;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void cancel() {
        cancel = true;
    }

    public void apply(GL11 gl, long currentTime) {

        if (currentTime < startTime) return;

        if (currentTime - startTime > duration) {
            if (loopEnabled) {
                startTime = currentTime;
            }
            else {
                if (!completed) completed = true;
                return;
            }

        }


        float normalizedTime = 0;
        if (duration != 0) {
            normalizedTime = ((float) (currentTime - startTime)) / (float) duration;
        }

        //根据归一化时间调整时间插值
        float interpolatedTime = interpolator.getInterpolation(normalizedTime);

        onApply(gl, interpolatedTime);
    }

    protected abstract void onApply(GL11 gl, float interpolatedTime);


    public long getDuration() {
        return duration;
    }

    public boolean isCompleted() {
        return completed;
    }


    public AnimationListener getListener() {
        return listener;
    }

    public void setListener(AnimationListener listener) {
        this.listener = listener;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public boolean isLoopEnabled() {
        return loopEnabled;
    }

    public void enableLoop(boolean loopEnabled) {
        this.loopEnabled = loopEnabled;
    }


    public boolean isCanceled() {
        return cancel;
    }


    protected long startTime;
    protected long duration;

    protected boolean completed;
    protected boolean loopEnabled;


    private boolean cancel;

    protected Interpolator interpolator;
    private AnimationListener listener;
}
