package ice.animation;

import ice.node.Overlay;

import javax.microedition.khronos.opengles.GL11;

/**
 * User: Mike.Hu
 * Date: 12-2-28
 * Time: 下午3:35
 */
public class SpaceTranslateAnimation extends TranslateAnimation {

    public SpaceTranslateAnimation(long duration, float toXDelta, float toYDelta, float toZDelta) {
        this(duration, 0, toXDelta, 0, toYDelta, 0, toZDelta);
    }

    public SpaceTranslateAnimation(long duration, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, float fromZDelta, float toZDelta) {

        super(duration, fromXDelta, toXDelta, fromYDelta, toYDelta);
        this.fromZDelta = fromZDelta;
        this.toZDelta = toZDelta;
    }

    @Override
    protected void applyFillAfter(Overlay overlay) {

        overlay.setPos(overlay.getPosX() + toXDelta, overlay.getPosY() + toYDelta, overlay.getPosZ() + toZDelta);
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {

        float translateX = 0;
        float translateY = 0;
        float translateZ = 0;

        if (fromXDelta != toXDelta) {
            translateX = fromXDelta + ((toXDelta - fromXDelta) * interpolatedTime);
        }
        if (fromYDelta != toYDelta) {
            translateY = fromYDelta + ((toYDelta - fromYDelta) * interpolatedTime);
        }
        if (fromZDelta != toZDelta) {
            translateZ = fromZDelta + ((toZDelta - fromZDelta) * interpolatedTime);
        }

        if (translateX != 0 || translateY != 0 || translateZ != 0)
            gl.glTranslatef(translateX, translateY, translateZ);
    }

    private float fromZDelta;
    private float toZDelta;
}
