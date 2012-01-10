package ice.node.mesh;

import android.util.Log;
import ice.graphic.Primitives;
import ice.graphic.Texture;
import ice.node.Drawable;
import ice.node.mesh.vertex.VertexData;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.GL_TEXTURE_COORD_ARRAY;
import static javax.microedition.khronos.opengles.GL11.*;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 下午2:35
 */
public class Mesh extends Drawable {

    private static final String TAG = Mesh.class.getSimpleName();

    public Mesh(VertexData vertexData) {
        this(vertexData, null);
    }

    public Mesh(VertexData vertexData, Texture texture) {
        this.vertexData = vertexData;
        bindTexture(texture);
        callFace = true;
    }

    @Override
    protected void onDraw(GL11 gl) {
        vertexData.attach(gl);

        Texture theTexture = texture;
        boolean useTexture = (theTexture != null);
        if (useTexture)
            theTexture.attach(gl);

        boolean cullBack = callFace;
        if (cullBack) {
            gl.glFrontFace(GL_CCW);
            gl.glEnable(GL_CULL_FACE);
            gl.glCullFace(GL_BACK);
        }

        gl.glDrawArrays(GL_TRIANGLES, 0, vertexData.getVerticesCount());

        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        gl.glDisableClientState(GL_VERTEX_ARRAY);

        if (cullBack) {
            gl.glDisable(GL_CULL_FACE);
        }

        if (useTexture) {
            gl.glDisable(GL_TEXTURE_2D);
            gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        }
    }

    public void bindTexture(Texture texture) {
        this.texture = texture;
    }

    public void unbindTexture() {
        texture = null;
    }

    public void setCallFace(boolean callFace) {
        this.callFace = callFace;
    }

    public VertexData getVertexData() {
        return vertexData;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    private boolean callFace;
    protected Texture texture;

    private VertexData vertexData;
}
