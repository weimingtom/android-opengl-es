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

    public void setCenterOffset(float translateX, float translateY, float translateZ) {
        this.translateX = translateX;
        this.translateY = translateY;
        this.translateZ = translateZ;
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {

        boolean offset = translateX != 0 || translateY != 0 || translateZ != 0;

        if (offset)
            gl.glTranslatef(translateX, translateY, translateZ);

        float angle = fromAngle + ((toAngle - fromAngle) * interpolatedTime);
        gl.glRotatef(angle, rotateX, rotateY, rotateZ);

        if (offset)
            gl.glTranslatef(-translateX, -translateY, -translateZ);
    }

    private float translateX, translateY, translateZ;

    private float fromAngle, toAngle;
    private float rotateX, rotateY, rotateZ;

}
