package ice.animation;

import javax.microedition.khronos.opengles.GL11;

/**
 * 依赖 源因子GL_SRC_ALPHA实现，注意Drawable的混合因子设置
 */
public class AlphaAnimation extends Animation {

    public AlphaAnimation(long duration, float fromAlpha, float toAlpha) {
        super(duration);

        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;
    }

    @Override
    protected void onAttach(GL11 gl, float interpolatedTime) {
//        gl.glEnable(GL_BLEND);
//        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        //gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);

        float alpha = fromAlpha + ((toAlpha - fromAlpha) * interpolatedTime);

        gl.glColor4f(1, 1, 1, alpha);
    }

    @Override
    protected void onDetach(GL11 gl) {
        // gl.glDisable(GL_BLEND);

        gl.glColor4f(1, 1, 1, 1);
    }

    private float fromAlpha, toAlpha;
}
