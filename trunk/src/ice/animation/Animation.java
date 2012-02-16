package ice.animation;

import android.view.animation.AnimationUtils;
import ice.animation.Interpolator.AccelerateDecelerateInterpolator;
import ice.animation.Interpolator.Interpolator;
import ice.node.Overlay;

import javax.microedition.khronos.opengles.GL11;

public abstract class Animation {


    public interface Listener {

        void onAnimationEnd(Overlay overlay);
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

//            else {   //如果不注释 打开fill after时 会卡哦
//                return;
//            }

        }

        float normalizedTime = ((float) (currentTime - startTime)) / (float) duration;

        //根据归一化时间调整时间插值
        float interpolatedTime = interpolator.getInterpolation(normalizedTime);

        onAttach(gl, interpolatedTime);
    }

    public void detach(GL11 gl) {
        onDetach(gl);
    }


    public void onComplete(final Overlay overlay, GL11 gl) {

        if (fillAfter)
            applyFillAfter(overlay);

        if (listener != null) {
            new Thread() {

                @Override
                public void run() {
                    listener.onAnimationEnd(overlay);
                }

            }.start();
        }

    }

    protected abstract void applyFillAfter(Overlay overlay);

    protected abstract void onAttach(GL11 gl, float interpolatedTime);

    protected void onDetach(GL11 gl) {

    }

    public long getDuration() {
        return duration;
    }

    public boolean isCompleted() {

        if (AnimationUtils.currentAnimationTimeMillis() - startTime > duration) {
            return !loop;
        }

        return false;
    }


    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
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

    public void setFillAfter(boolean fillAfter) {
        this.fillAfter = fillAfter;
    }

    private boolean fillAfter;

    protected long startTime;
    protected long duration;

    protected boolean loop;

    private boolean cancel;

    private Interpolator interpolator;
    private Listener listener;
}