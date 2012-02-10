package ice.node.widget;

import android.graphics.*;
import ice.util.TextDrawer;

import javax.microedition.khronos.opengles.GL11;

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

    @Override
    public void draw(GL11 gl) {
        if (text != null)
            super.draw(gl);
    }

    @Override
    protected void onDraw(GL11 gl) {
        if (vertexData != null)
            super.onDraw(gl);
    }

    public void setText(String text) {
        setText(text, Color.WHITE, (int) height);
    }

    public void setText(String text, int color, int size) {
        setText(text, color, size, false);
    }

    public void setText(String text, int color, int size, boolean alignCenter) {
        if (!text.equals(this.text)) {
            setTextTexture(text, color, size, alignCenter);
            this.text = text;
        }
    }

    public void setTextTexture(String text, int color, int size, boolean alignCenter) {
        this.alignCenter = alignCenter;
        setBitmap(createTextTexture(text, color, size));
    }

    private Bitmap createTextTexture(String text, int color, int size) {
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

    private String text;
    private float realWidth;
    private boolean alignCenter;
}
