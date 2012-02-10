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

        painter = new Paint(Paint.ANTI_ALIAS_FLAG);
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
        setTextTexture(text, color, size, alignCenter);
        this.text = text;
    }

    public void setTextTexture(String text, int color, int size, boolean alignCenter) {
        if (text.equals(this.text)) return;

        this.alignCenter = alignCenter;

        if (texture != null) {
            Bitmap bitmap = texture.getBitmap();

            if (doubleBuffer == null) {
                doubleBuffer = Bitmap.createBitmap(bitmap);
            }

            writeText(doubleBuffer, text, color, size);

            Bitmap temp = bitmap;
            bitmap = doubleBuffer;
            doubleBuffer = temp;

            setBitmap(bitmap);
        }
        else {
            setBitmap(createTextTexture(text, color, size));
        }
    }

    private Bitmap createTextTexture(String text, int color, int size) {
        Bitmap textTexture = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);

        writeText(textTexture, text, color, size);

        return textTexture;
    }

    private void writeText(Bitmap textTexture, String text, int color, int size) {
        Canvas canvas = new Canvas(textTexture);

        painter.setColor(color);

        Rect region = new Rect(0, 0, (int) width, size);

        canvas.drawColor(Color.BLACK);

        realWidth = TextDrawer.drawTextInLine(canvas, painter, text, region, alignCenter);
    }

    public float getRealWidth() {
        return realWidth;
    }

    private Bitmap doubleBuffer;
    private Paint painter;
    private String text;
    private float realWidth;
    private boolean alignCenter;
}
