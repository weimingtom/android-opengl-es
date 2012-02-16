package ice.node.widget;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import ice.graphic.texture.Texture;
import ice.node.mesh.Grid;
import ice.res.Res;

/**
 * User: ice
 * Date: 11-11-30
 * Time: 下午12:31
 */
public class BitmapOverlay extends Grid {

    public BitmapOverlay(int bitmapId) {
        this(Res.getBitmap(bitmapId));
    }

    public BitmapOverlay(Bitmap bitmap) {
        this(bitmap, new PointF());
    }

    public BitmapOverlay(Bitmap bitmap, PointF pos) {
        this(bitmap.getWidth(), bitmap.getHeight(), bitmap);

        setPos(pos.x, pos.y, 0);
    }

    public BitmapOverlay(float width, float height) {
        this(width, height, 0);
    }

    public BitmapOverlay(float width, float height, int bitmapId) {
        this(
                width,
                height,
                bitmapId == 0 ? null : Res.getBitmap(bitmapId)
        );
    }

    public BitmapOverlay(float width, float height, Bitmap bitmap) {
        super(width, height, false);

        if (bitmap != null) {
            setBitmap(bitmap);
        }
    }

    public BitmapOverlay(Bitmap bitmap, Point pos) {
        this(bitmap);
        setPos(pos.x, pos.y);
    }

    public void setBitmap(int bitmap) {
        setBitmap(Res.getBitmap(bitmap));
    }

    public Bitmap setBitmap(Bitmap bitmap) {
        if (texture != null) {
            texture.setBitmap(bitmap);
        }
        else {
            bindTexture(new Texture(bitmap));
        }

        if (vertexData == null) {
            setUpVertex(texture.getMaxU(), texture.getMaxV());
        }

        return texture.getBitmap();
    }

}
