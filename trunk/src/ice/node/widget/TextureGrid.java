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

    public TextureGrid(float width, float height) {
        this(width, height, 0);
    }

    public TextureGrid(float width, float height, int bitmapId) {
        this(
                width,
                height,
                bitmapId == 0 ? null : Res.getBitmap(bitmapId)
        );
    }

    public TextureGrid(float width, float height, Bitmap bitmap) {
        super(width, height, false);

        if (bitmap != null) {
            setBitmap(bitmap);
        }
    }

    public void setBitmap(int bitmap) {
        setBitmap(Res.getBitmap(bitmap));
    }

    public void setBitmap(Bitmap bitmap) {
        if (texture != null) {
            texture.setBitmap(bitmap);
        }
        else {
            bindTexture(new Texture(bitmap));
        }

        if (vertexData == null) {
            setUpVertex(texture.getMaxU(), texture.getMaxV());
        }
    }
}
