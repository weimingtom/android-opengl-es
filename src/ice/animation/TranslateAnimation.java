package ice.animation;

import ice.node.Overlay;

import javax.microedition.khronos.opengles.GL11;

public class TranslateAnimation extends Animation {

    public static TranslateAnimation createMoveBy(long duration, float toXDelta, float toYDelta) {

        TranslateAnimation moveBy = new TranslateAnimation(duration, 0, toXDelta, 0, toYDelta);

        moveBy.setFillAfter(true);

        return moveBy;
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

        float translateX = fromXDelta;
        float translateY = fromYDelta;

        if (fromXDelta != toXDelta) {
            translateX = fromXDelta + ((toXDelta - fromXDelta) * interpolatedTime);
        }
        if (fromYDelta != toYDelta) {
            translateY = fromYDelta + ((toYDelta - fromYDelta) * interpolatedTime);
        }

        gl.glTranslatef(translateX, translateY, 0);
    }

    private float fromXDelta;
    private float toXDelta;
    private float fromYDelta;
    private float toYDelta;

}
