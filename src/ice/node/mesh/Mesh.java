package ice.node.mesh;

import ice.graphic.Texture;
import ice.model.vertex.VertexData;
import ice.node.Overlay;

import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.*;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 下午2:35
 */
public class Mesh extends Overlay {

    private static final String TAG = Mesh.class.getSimpleName();

    public Mesh() {
        this(null);
    }

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

        boolean cullBack = callFace;

        if (cullBack) {
            gl.glFrontFace(GL_CCW);
            gl.glEnable(GL_CULL_FACE);
            gl.glCullFace(GL_BACK);
        }

        Texture theTexture = texture;
        boolean useTexture = (theTexture != null);

        if (useTexture) theTexture.attach(gl);

        vertexData.attach(gl);

        gl.glDrawArrays(GL_TRIANGLES, 0, vertexData.getVerticesCount());

        vertexData.detach(gl);

        if (useTexture) theTexture.detach(gl);

        if (cullBack) gl.glDisable(GL_CULL_FACE);
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

    public void setVertexData(VertexData vertexData) {
        this.vertexData = vertexData;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    private boolean callFace;
    protected Texture texture;

    protected VertexData vertexData;
}
