package ice.animation;

import javax.microedition.khronos.opengles.GL11;

/**
 * User: jason
 * Date: 12-2-2
 * Time: 下午5:30
 */
public class TranslateRotateAnimation extends RotateAnimation {

    public TranslateRotateAnimation(long duration, float fromAngle, float toAngle) {
        super(duration, fromAngle, toAngle);
    }

    public void setTranslate(float translateX, float translateY, float translateZ) {
        this.translateX = translateX;
        this.translateY = translateY;
        this.translateZ = translateZ;
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {

        if (translateX != 0 || translateY != 0 || translateZ != 0)
            gl.glTranslatef(translateX, translateY, translateZ);

        super.onAttach(gl, interpolatedTime);
    }

    private float translateX, translateY, translateZ;
}
