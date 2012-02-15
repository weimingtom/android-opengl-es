package ice.practical;

import android.graphics.Color;
import ice.node.widget.TextGrid;

import javax.microedition.khronos.opengles.GL11;

/**
 * User: jason
 * Date: 12-2-10
 * Time: 下午5:25
 */
public class Fps extends TextGrid {

    public Fps() {
        super(100, 30);
        setPos(0, height);
    }

    @Override
    public void draw(GL11 gl) {

        fps++;

        if (System.currentTimeMillis() - lastUpdate > 1000) {
            setText("fps : " + fps, Color.BLUE, (int) getHeight());
            fps = 0;
            lastUpdate = System.currentTimeMillis();
        }

        super.draw(gl);
    }


    private int fps;
    private long lastUpdate;

}