package ice.animation;

import ice.node.Drawable;

import javax.microedition.khronos.opengles.GL11;

public class ScaleAnimation extends Animation {

    private float mFromX;
    private float mToX;
    private float mFromY;
    private float mToY;

    public ScaleAnimation(long duration, float mFromX, float mToX, float mFromY, float mToY) {
        super(duration);
        this.mFromX = mFromX;
        this.mToX = mToX;
        this.mFromY = mFromY;
        this.mToY = mToY;
    }

    @Override
    protected void applyFillAfter(Drawable drawable) {
        drawable.setScale(scaleX, scaleY, scaleZ);
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {

        scaleX = scaleY = 1.0f;

        if (mFromX != 1.0f || mToX != 1.0f) {
            scaleX = mFromX + ((mToX - mFromX) * interpolatedTime);
        }

        if (mFromY != 1.0f || mToY != 1.0f) {
            scaleY = mFromY + ((mToY - mFromY) * interpolatedTime);
        }

        gl.glScalef(scaleX, scaleY, scaleZ);
    }

    private float scaleX, scaleY, scaleZ;
}
