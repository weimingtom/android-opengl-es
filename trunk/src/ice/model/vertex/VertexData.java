package ice.model.vertex;

import ice.graphic.GlRes;
import ice.graphic.gl_status.GlStatusController;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class VertexData implements GlStatusController, GlRes {

    protected VertexData(int verticesCount, VertexAttributes attributes) {
        this.attributes = attributes;

        srcData = ByteBuffer.allocateDirect(attributes.vertexSize * verticesCount);
        srcData.order(ByteOrder.nativeOrder());

    }

    public void setVertices(float[] vertices) {
        FloatBuffer floatBuffer = srcData.asFloatBuffer();
        floatBuffer.put(vertices);

        srcData.position(0);
        srcData.limit(srcData.capacity());
    }

//    public FloatBuffer viewData() {
//        return srcData.asFloatBuffer();
//    }

    public int getVerticesCount() {
        return srcData.capacity() / attributes.vertexSize;
    }


    protected ByteBuffer srcData;

    protected VertexAttributes attributes;
}
