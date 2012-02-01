package ice.animation;

import android.graphics.Point;

import javax.microedition.khronos.opengles.GL11;

public class TranslateAnimation extends Animation {

    private float fromXDelta;
    private float toXDelta;
    private float fromYDelta;
    private float toYDelta;

    public TranslateAnimation(long duration, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        super(duration);
        this.fromXDelta = fromXDelta;
        this.toXDelta = toXDelta;
        this.fromYDelta = fromYDelta;
        this.toYDelta = toYDelta;
    }

    public TranslateAnimation(long duration, Point currentPosition, float toAbsoluteX, float toAbsoluteY) {
        super(duration);
        this.toXDelta = toAbsoluteX - currentPosition.x;
        this.toYDelta = toAbsoluteY - currentPosition.y;
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {

        float translateX = fromXDelta;
        float translateY = fromYDelta;

        if (fromXDelta != toXDelta) {
            translateX = fromXDelta + ((toXDelta - fromXDelta) * interpolatedTime);
        }
        if (fromYDelta != toYDelta) {
            translateY = fromYDelta + ((toYDelta - fromYDelta) * interpolatedTime);
        }

        gl.glTranslatef(translateX, -translateY, 0);
    }

}
