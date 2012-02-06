package ice.animation;

import android.view.animation.AnimationUtils;
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
        startTime = AnimationUtils.currentAnimationTimeMillis();
    }

    public void cancel() {
        cancel = true;
    }

    public void attach(GL11 gl, long currentTime) {

        if (currentTime < startTime) return;

        if (currentTime - startTime > duration) {
            if (loop) {
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

        onAttach(gl, interpolatedTime);
    }

    public void detach(GL11 gl) {
        onDetach(gl);
    }


    public void onComplete(final Drawable drawable, GL11 gl) {
        new Thread() {

            @Override
            public void run() {

                if (listener != null)
                    listener.onAnimationEnd(drawable);

            }

        }.start();
    }

    protected abstract void onAttach(GL11 gl, float interpolatedTime);

    protected void onDetach(GL11 gl) {

    }

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

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loopEnabled) {
        this.loop = loopEnabled;
    }


    public boolean isCanceled() {
        return cancel;
    }


    protected long startTime;
    protected long duration;

    protected boolean completed;
    protected boolean loop;

    private boolean cancel;

    protected Interpolator interpolator;
    private AnimationListener listener;
}