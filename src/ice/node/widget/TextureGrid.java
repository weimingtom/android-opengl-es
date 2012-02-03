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
        super(bitmap.getWidth(), bitmap.getHeight());

        setPos(pos.x, pos.y, 0);

        bindTexture(new Texture(bitmap));
    }


    public boolean hitTest(int x, int y) {
        return x >= posX
                && x <= posX + width
                && y <= posY
                && y >= posY - height;
    }

}
