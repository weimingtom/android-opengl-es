package ice.animation;

import ice.node.Overlay;

import javax.microedition.khronos.opengles.GL11;

public class TranslateAnimation extends Animation {

    public TranslateAnimation(long duration, float toXDelta, float toYDelta) {
        this(duration, 0, toXDelta, 0, toYDelta);
    }

    public TranslateAnimation(long duration, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        super(duration);
        this.fromXDelta = fromXDelta;
        this.toXDelta = toXDelta;
        this.fromYDelta = fromYDelta;
        this.toYDelta = toYDelta;
    }

    @Override
    protected void applyFillAfter(Overlay overlay) {
        overlay.setPos(overlay.getPosX() + toXDelta, overlay.getPosY() + toYDelta);
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {

        float translateX = 0;
        float translateY = 0;

        if (fromXDelta != toXDelta) {
            translateX = fromXDelta + ((toXDelta - fromXDelta) * interpolatedTime);
        }
        if (fromYDelta != toYDelta) {
            translateY = fromYDelta + ((toYDelta - fromYDelta) * interpolatedTime);
        }

        if (translateX != 0 || translateY != 0)
            gl.glTranslatef(translateX, translateY, 0);
    }

    protected float fromXDelta;
    protected float toXDelta;
    protected float fromYDelta;
    protected float toYDelta;

}
