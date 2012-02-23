package ice.node.widget;

import android.graphics.*;
import ice.util.TextDrawer;

/**
 * //TODO 效率或者可以优化一下
 * <p/>
 * User: jason
 * Date: 12-2-6
 * Time: 上午10:19
 */
public class TextOverlay extends BitmapOverlay {

    public TextOverlay(float width, float height) {
        super(width, height);

        painter = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    public void setText(String text) {
        setText(text, painter.getColor(), (int) getHeight());
    }

    public void setText(String text, int color) {
        setText(text, color, (int) getHeight());
    }

    public void setText(String text, int color, int size) {
        setText(text, color, size, alignCenter);
    }

    public void setText(String text, int color, boolean alignCenter) {
        setText(text, color, (int) getHeight(), alignCenter);
    }

    public void setText(String text, int color, int size, boolean alignCenter) {
        setTextTexture(text, color, size, alignCenter);
        this.text = text;
    }

    public void setTextTexture(String text, int color, int size, boolean alignCenter) {
        if (text.equals(this.text)) return;

        this.alignCenter = alignCenter;

        setBitmap(createTextTexture(text, color, size));
    }

    private Bitmap createTextTexture(String text, int color, int size) {
        Bitmap textTexture = Bitmap.createBitmap((int) getWidth(), (int) getHeight(), Bitmap.Config.ARGB_8888);

        writeText(textTexture, text, color, size);

        return textTexture;
    }

    private void writeText(Bitmap textTexture, String text, int color, int size) {
        Canvas canvas = new Canvas(textTexture);

        painter.setColor(color);

        Rect region = new Rect(0, 0, (int) getWidth(), size);

        canvas.drawColor(Color.argb(0, 0, 0, 0));

        realWidth = TextDrawer.drawTextInLine(canvas, painter, text, region, alignCenter);
    }

    public float getRealWidth() {
        return realWidth;
    }

    private Paint painter;
    private String text;
    private float realWidth;
    private boolean alignCenter;
}
