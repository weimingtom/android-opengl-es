package ice.animation;

import ice.node.Drawable;

import javax.microedition.khronos.opengles.GL11;

public class ScaleAnimation extends Animation {

    public static ScaleAnimation createScaleTo(long duration, float toX, float toY) {
        return createScaleTo(duration, toX, toY, 1);
    }

    public static ScaleAnimation createScaleTo(long duration, float toX, float toY, float toZ) {

        ScaleAnimation scaleAnimation = new ScaleAnimation(duration, 1, toX, 1, toY, 1, toZ);

        scaleAnimation.setFillAfter(true);

        return scaleAnimation;
    }

    public ScaleAnimation(long duration, float fromX, float toX, float fromY, float toY) {
        this(duration, fromX, toX, fromY, toY, 1, 1);
    }

    public ScaleAnimation(long duration, float fromX, float toX, float fromY, float toY, int fromZ, float toZ) {
        super(duration);
        this.fromX = fromX;
        this.toX = toX;
        this.fromY = fromY;
        this.toY = toY;
        this.fromZ = fromZ;
        this.toZ = toZ;
    }

    @Override
    protected void applyFillAfter(Drawable drawable) {
        drawable.setScale(toX, toY, toZ);
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {

        float scaleX = fromX;
        float scaleY = fromY;
        float scaleZ = fromZ;

        if (fromX != toX)
            scaleX = fromX + ((toX - fromX) * interpolatedTime);

        if (fromY != toY)
            scaleY = fromY + ((toY - fromY) * interpolatedTime);

        if (fromZ != toZ)
            scaleY = fromZ + ((toZ - fromZ) * interpolatedTime);

        if (scaleX != 1 || scaleY != 1 || scaleZ != 1)
            gl.glScalef(scaleX, scaleY, scaleZ);
    }

    private float fromX = 1, fromY = 1, fromZ = 1;
    private float toX = 1, toY = 1, toZ = 1;
}
