package ice.node.widget;

import android.graphics.Color;
import ice.node.mesh.Grid;

import javax.microedition.khronos.opengles.GL11;

/**
 * User: Jason
 * Date: 12-2-4
 * Time: 下午6:21
 */
public class ColorOverlay extends Grid {

    public ColorOverlay(int color, float width, float height) {
        super(width, height);
        this.color = color;
    }

    @Override
    protected void onDraw(GL11 gl) {

        gl.glColor4f(
                Color.red(color) / 255f,
                Color.green(color) / 255f,
                Color.blue(color) / 255f,
                1
        );

        super.onDraw(gl);

        gl.glColor4f(1, 1, 1, 1);
    }

    private int color;
}
