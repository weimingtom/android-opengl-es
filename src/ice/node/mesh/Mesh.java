package ice.node.mesh;

import ice.graphic.gl_status.CullFaceController;
import ice.graphic.texture.Texture;
import ice.model.vertex.VertexData;
import ice.node.Overlay;

import javax.microedition.khronos.opengles.GL11;

import static ice.graphic.gl_status.CullFaceController.FaceMode;
import static javax.microedition.khronos.opengles.GL11.GL_TRIANGLES;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 下午2:35
 */
public class Mesh extends Overlay {

    public Mesh() {
        this(null);
    }

    public Mesh(VertexData vertexData) {
        this(vertexData, null);
    }

    public Mesh(VertexData vertexData, Texture texture) {
        this.vertexData = vertexData;
        bindTexture(texture);

        cullFaceController = new CullFaceController(FaceMode.Front);
    }

    @Override
    protected void onDraw(GL11 gl) {

        cullFaceController.attach(gl);

        Texture theTexture = texture;
        boolean useTexture = (theTexture != null);

        if (useTexture)
            theTexture.attach(gl);

        vertexData.attach(gl);

        gl.glDrawArrays(GL_TRIANGLES, 0, vertexData.getVerticesCount());

        vertexData.detach(gl, this);

        if (useTexture) theTexture.detach(gl, this);

        cullFaceController.detach(gl, this);
    }

    public void bindTexture(Texture texture) {
        this.texture = texture;
    }

    public void unbindTexture() {
        texture = null;
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

    public void setFaceMode(FaceMode faceMode) {
        cullFaceController.setFaceMode(faceMode);
    }

    private CullFaceController cullFaceController;

    protected Texture texture;

    protected VertexData vertexData;


}
