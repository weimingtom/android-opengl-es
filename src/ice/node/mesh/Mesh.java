package ice.node.mesh;

import ice.graphic.Texture;
import ice.node.Drawable;
import ice.node.mesh.vertex.VertexData;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.GL_TEXTURE_COORD_ARRAY;
import static javax.microedition.khronos.opengles.GL11.GL_VERTEX_ARRAY;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 下午2:35
 */
public class Mesh extends Drawable {

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
        boolean useTexture = theTexture != null;
        if (useTexture)
            theTexture.attach(gl);

        boolean cullBack = callFace;
        if (cullBack) {
            gl.glFrontFace(GL11.GL_CCW);
            gl.glEnable(GL11.GL_CULL_FACE);
            gl.glCullFace(GL11.GL_BACK);
        }

        gl.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexData.getVerticesCount());

        gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
        gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);

        // gl.glDisableClientState(GL_INDEX_ARRAY);
        gl.glDisableClientState(GL_VERTEX_ARRAY);

        if (cullBack) {
            gl.glDisable(GL11.GL_CULL_FACE);
        }

        if (useTexture) {
            gl.glDisable(GL11.GL_TEXTURE_2D);
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
