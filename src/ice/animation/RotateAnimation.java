package ice.animation;

import javax.microedition.khronos.opengles.GL11;

public class RotateAnimation extends Animation {


    public RotateAnimation(long duration, float fromAngle, float toAngle) {
        super(duration);
        this.fromAngle = fromAngle;
        this.toAngle = toAngle;
        rotateZ = 1;
    }


    public void setRotateVector(float rotateX, float rotateY, float rotateZ) {
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {
        float angle = fromAngle + ((toAngle - fromAngle) * interpolatedTime);
        gl.glRotatef(angle, rotateX, rotateY, rotateZ);
    }

    private float fromAngle, toAngle;
    private float rotateX, rotateY, rotateZ;

}
