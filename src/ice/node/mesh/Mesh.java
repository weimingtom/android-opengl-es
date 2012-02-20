package ice.node.mesh;

import ice.graphic.texture.Texture;
import ice.model.vertex.VertexData;
import ice.node.Overlay;
import ice.util.GlUtil;

import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.*;

/**
 * User: ice
 * Date: 11-11-14
 * Time: 下午2:35
 */
public class Mesh extends Overlay {

    public enum FaceMode {
        Front, Back, BothSide
    }

    public Mesh() {
        this(null);
    }

    public Mesh(VertexData vertexData) {
        this(vertexData, null);
    }

    public Mesh(VertexData vertexData, Texture texture) {
        this.vertexData = vertexData;
        faceMode = FaceMode.Front;
        bindTexture(texture);
    }

    @Override
    protected void onDraw(GL11 gl) {

        boolean faceModeSwitchTemp = faceModeSwitch;
        FaceMode faceModeTemp = faceMode;

        boolean cullFaceStatus = false;
        int faceMode = 0;

        if (faceModeSwitchTemp) {

            cullFaceStatus = gl.glIsEnabled(GL_CULL_FACE);
            faceMode = GlUtil.getInteger(gl, GL_FRONT_FACE);

            switch (faceModeTemp) {

                case Front:
                    if (!cullFaceStatus)
                        gl.glEnable(GL_CULL_FACE);
                    gl.glFrontFace(GL_CCW);
                    gl.glCullFace(GL_BACK);
                    break;

                case Back:
                    if (!cullFaceStatus)
                        gl.glEnable(GL_CULL_FACE);
                    gl.glFrontFace(GL_CW);
                    gl.glCullFace(GL_BACK);
                    break;

                case BothSide:
                    gl.glDisable(GL_CULL_FACE);
                    break;
            }

        }


        Texture theTexture = texture;
        boolean useTexture = (theTexture != null);

        if (useTexture)
            theTexture.attach(gl);

        vertexData.attach(gl);

        gl.glDrawArrays(GL_TRIANGLES, 0, vertexData.getVerticesCount());

        vertexData.detach(gl);

        if (useTexture) theTexture.detach(gl);

        if (faceModeSwitchTemp) {

            if (cullFaceStatus) {
                gl.glEnable(GL_CULL_FACE);
            }
            else {
                gl.glDisable(GL_CULL_FACE);
            }

            gl.glFrontFace(faceMode);
        }

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


    public void enableFaceModeSwitch(FaceMode faceMode) {
        this.faceMode = faceMode;
        faceModeSwitch = true;
    }

    private boolean faceModeSwitch;

    private FaceMode faceMode;

    protected Texture texture;

    protected VertexData vertexData;


}
