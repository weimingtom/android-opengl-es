package ice.graphic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import ice.graphic.texture.Texture;
import ice.model.Size;

import javax.microedition.khronos.opengles.GL11;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static ice.graphic.texture.Texture.MAX_TEXTURE_SIZE;
import static ice.model.Constants.SIZE_OF_INTEGER;
import static javax.microedition.khronos.opengles.GL10.GL_EXTENSIONS;

/**
 * User: jason
 * Date: 12-2-8
 * Time: 下午5:55
 */
public class GlUtil {

    public static boolean queryExtension(GL11 gl, String name) {
        String extensions = gl.glGetString(GL_EXTENSIONS);
        return extensions.contains(name);
    }

    public static int getInteger(GL11 gl, int name) {
        ByteBuffer vfb = ByteBuffer.allocateDirect(SIZE_OF_INTEGER);
        vfb.order(ByteOrder.nativeOrder());

        IntBuffer buffer = vfb.asIntBuffer();

        gl.glGetIntegerv(name, buffer);

        return buffer.get(0);
    }

    public static Size findFitU_V(int originalWidth, int originalHeight) {

        if (Texture.isP_o_tSupported())
            return new Size(1, 1);


        int widthToFit = originalWidth;
        int heightToFit = originalHeight;

        if ((widthToFit != 1) && (widthToFit & (widthToFit - 1)) != 0) {
            int i = 1;
            while (i < widthToFit)
                i *= 2;
            widthToFit = i;
        }

        if ((heightToFit != 1) && (heightToFit & (heightToFit - 1)) != 0) {
            int i = 1;
            while (i < heightToFit)
                i *= 2;
            heightToFit = i;
        }

        float scale = 1;
        while (widthToFit > MAX_TEXTURE_SIZE || heightToFit > MAX_TEXTURE_SIZE) {
            widthToFit /= 2;
            heightToFit /= 2;
            scale /= 2.0f;
        }

        if (widthToFit != originalWidth || heightToFit != originalHeight) {
            return new Size(
                    (originalWidth * scale) / widthToFit,
                    (originalHeight * scale) / heightToFit
            );
        }
        else {
            return new Size(1, 1);
        }

    }

    public static Bitmap tryAdjust(Bitmap bitmap) {
        if (Texture.isP_o_tSupported()) return bitmap;

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        int widthToFit = originalWidth;
        int heightToFit = originalHeight;

        if ((widthToFit != 1) && (widthToFit & (widthToFit - 1)) != 0) {
            int i = 1;
            while (i < widthToFit)
                i *= 2;
            widthToFit = i;
        }

        if ((heightToFit != 1) && (heightToFit & (heightToFit - 1)) != 0) {
            int i = 1;
            while (i < heightToFit)
                i *= 2;
            heightToFit = i;
        }

        float scale = 1;
        while (widthToFit > MAX_TEXTURE_SIZE || heightToFit > MAX_TEXTURE_SIZE) {
            widthToFit /= 2;
            heightToFit /= 2;
            scale /= 2.0f;
        }

        if (widthToFit != originalWidth || heightToFit != originalHeight) {
            Bitmap adjustedBitmap = Bitmap.createBitmap(widthToFit, heightToFit, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(adjustedBitmap);
            if (scale != 1)
                canvas.scale(scale, scale);

            canvas.drawBitmap(bitmap, 0, 0, new Paint());

            return adjustedBitmap;
        }

        return bitmap;
    }
}
