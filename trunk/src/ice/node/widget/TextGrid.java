package ice.node.widget;

import android.graphics.*;
import ice.util.TextDrawer;

/**
 * User: jason
 * Date: 12-2-6
 * Time: 上午10:19
 */
public class TextGrid extends TextureGrid {

    public TextGrid(int width, int height) {
        super(width, height);
    }

    public TextGrid(int width, int height, String text) {
        super(width, height);

        setText(text, Color.WHITE, height);
    }


    public void setText(String text, int color, int size) {
        this.text = text;
        setTextParams(color, size, false);
    }

    public void setText(String text, int color, int size, boolean alignCenter) {
        this.text = text;
        setTextParams(color, size, alignCenter);
    }

    public void setTextParams(int color, int size, boolean alignCenter) {
        this.alignCenter = alignCenter;
        setBitmap(createTextTexture(color, size));
    }

    private Bitmap createTextTexture(int color, int size) {
        Bitmap textTexture = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(textTexture);

        Paint painter = new Paint(Paint.ANTI_ALIAS_FLAG);

        painter.setColor(color);

        Rect region = new Rect(0, 0, (int) width, size);

        realWidth = TextDrawer.drawTextInLine(canvas, painter, text, region, alignCenter);
        return textTexture;
    }

    public float getRealWidth() {
        return realWidth;
    }

    //    @Override
//    protected void onDraw(GL11 gl) {
//        gl.glEnable(GL_SCISSOR_TEST);
//
//        gl.glScissor(0, (int) (getAbsolutePos().y - height), (int) width, (int) height);
//
//        super.onDraw(gl);
//
//        gl.glDisable(GL_SCISSOR_TEST);
//    }

    private String text;
    private float realWidth;
    private boolean alignCenter;
}
