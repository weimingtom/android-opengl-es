package ice.graphic.gl_status;

import ice.node.Overlay;

import javax.microedition.khronos.opengles.GL11;

/**
 * User: jason
 * Date: 12-2-21
 * Time: 上午11:21
 */
public class ColorController implements GlStatusController {

    public ColorController(float r, float g, float b, float a) {
        this.colors = new float[]{r, g, b, a};
    }

    public ColorController(float[] colors) {
        this.colors = colors;
    }

    @Override
    public void attach(GL11 gl) {
        if (colors != null)
            gl.glColor4f(colors[0], colors[1], colors[2], colors[3]);
    }

    @Override
    public boolean detach(GL11 gl, Overlay overlay) {
        if (colors != null)   //TODO 先这样吧
            gl.glColor4f(1, 1, 1, 1);

        return true;
    }

    public void setColors(float[] colors) {
        this.colors = colors;
    }

    private float[] colors;
}
