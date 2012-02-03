package ice.node.widget;

import android.graphics.Bitmap;
import android.graphics.PointF;
import ice.graphic.Texture;
import ice.node.mesh.Grid;
import ice.res.Res;

/**
 * User: ice
 * Date: 11-11-30
 * Time: 下午12:31
 */
public class TextureGrid extends Grid {

    public TextureGrid(int bitmapId) {
        this(Res.getBitmap(bitmapId));
    }

    public TextureGrid(Bitmap bitmap) {
        this(bitmap, new PointF());
    }

    public TextureGrid(Bitmap bitmap, PointF pos) {
        this(bitmap.getWidth(), bitmap.getHeight(), bitmap);

        setPos(pos.x, pos.y, 0);
    }

    public TextureGrid(int width, int height, int bitmapId) {
        this(width, height, Res.getBitmap(bitmapId));
    }

    public TextureGrid(int width, int height, Bitmap bitmap) {
        super(width, height);
        bindTexture(new Texture(bitmap));
    }
}
