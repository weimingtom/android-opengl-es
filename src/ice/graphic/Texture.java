package ice.graphic;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import javax.microedition.khronos.opengles.GL11;
import java.util.HashMap;
import java.util.Map;

import static javax.microedition.khronos.opengles.GL11.*;

public class Texture implements GlRes {

    public static class Params {
        public static final Params DEFAULT;

        static {
            DEFAULT = new Params();
            DEFAULT.add(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            DEFAULT.add(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            DEFAULT.add(GL_TEXTURE_WRAP_S, GL_REPEAT);
            DEFAULT.add(GL_TEXTURE_WRAP_T, GL_REPEAT);
        }

        public Params() {
            paramMap = new HashMap<Integer, Integer>();
        }

        public void add(int pName, int value) {
            paramMap.put(pName, value);
        }

        public Map<Integer, Integer> getParamMap() {
            return paramMap;
        }

        private Map<Integer, Integer> paramMap;
    }


    public Texture(Bitmap bitmap) {
        this.bitmap = bitmap;
        params = Params.DEFAULT;
    }

    @Override
    public void attach(GL11 gl) {
        gl.glEnable(GL_TEXTURE_2D);

        if (!coordSupliedBySystem) {
            gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);

            //An advantage of representing particles with point sprites is that texture coordinate generation can be handled by the system
            //gl.glTexEnvi(GL_POINT_SPRITE_OES, GL_COORD_REPLACE_OES, GL_TRUE);
        }


        if (!loaded) {
            loadAndBind(gl);
        }
        else {
            gl.glBindTexture(GL_TEXTURE_2D, buffer[0]);
            if (subProvider != null) {
                GLUtils.texSubImage2D(GL_TEXTURE_2D, 0, xOffset, yOffset, subProvider);
                subProvider = null;
            }
        }
    }

    @Override
    public void unattach(GL11 gl) {
        gl.glDisable(GL_TEXTURE_2D);
        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    }

    private synchronized void loadAndBind(GL11 gl) {
        buffer = new int[1];
        gl.glGenTextures(buffer.length, buffer, 0);
        gl.glBindTexture(GL_TEXTURE_2D, buffer[0]);
        bindTextureParams(gl, params);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        loaded = true;
    }

    @Override
    public void release(GL11 gl) {
        if (!loaded) return;

        gl.glDeleteTextures(buffer.length, buffer, 0);
    }

    public void postSubData(int xoffset, int yoffset, Bitmap subPixel) {
        if (subPixel == bitmap) throw new IllegalArgumentException("subdata error !");
        if (this.subProvider != null) {
            System.out.println("Warning ! Texture subdata ignored !");
            return;
        }

        this.xOffset = xoffset;
        this.yOffset = yoffset;
        this.subProvider = subPixel;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private void bindTextureParams(GL11 gl, Params params) {
        Map<Integer, Integer> paramMap = params.getParamMap();
        for (int pName : paramMap.keySet()) {
            gl.glTexParameterf(GL_TEXTURE_2D, pName, paramMap.get(pName));
        }
    }

    public synchronized void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        loaded = false;
        buffer = new int[1];
    }

    public void setCoordSupliedBySystem(boolean coordSupliedBySystem) {
        this.coordSupliedBySystem = coordSupliedBySystem;
    }

    private boolean coordSupliedBySystem;
    private int xOffset, yOffset;
    private int[] buffer;
    private boolean loaded;
    private Bitmap bitmap;
    private Bitmap subProvider;
    private Params params;
}
