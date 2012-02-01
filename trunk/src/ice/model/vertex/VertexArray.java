package ice.model.vertex;

import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.*;


public class VertexArray extends VertexData {

    private static final String TAG = VertexArray.class.getSimpleName();

    public VertexArray(int verticesCount, VertexAttributes attributes) {
        super(verticesCount, attributes);
    }


    @Override
    public void attach(GL11 gl) {

        for (int i = 0, size = attributes.size(); i < size; i++) {

            VertexAttribute attribute = attributes.get(i);
            int offset = attribute.getOffset();
            int dimension = attribute.getDimension();

            switch (attribute.getUsage()) {
                case Position:
                    gl.glEnableClientState(GL_VERTEX_ARRAY);
                    srcData.position(offset);
                    gl.glVertexPointer(dimension, GL_FLOAT, attributes.vertexSize, srcData);
                    break;

                case Color:
                    gl.glEnableClientState(GL_COLOR_ARRAY);
                    srcData.position(offset);
                    gl.glColorPointer(dimension, GL_FLOAT, attributes.vertexSize, srcData);
                    break;

                case Normal:
                    gl.glEnableClientState(GL_NORMAL_ARRAY);
                    srcData.position(offset);
                    gl.glNormalPointer(GL_FLOAT, attributes.vertexSize, srcData);
                    break;

                case TextureCoordinates:
                    gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
                    //gl.glClientActiveTexture(GL.GL_TEXTURE0 + textureUnit++);
                    srcData.position(offset);
                    gl.glTexCoordPointer(dimension, GL_FLOAT, attributes.vertexSize, srcData);
                    break;
            }
        }

    }

    @Override
    public void detach(GL11 gl) {
        gl.glDisableClientState(GL_VERTEX_ARRAY);
    }

    @Override
    public void release(GL11 gl) { //TODO 名副其实？
        int numAttributes = attributes.size();

        for (int i = 0; i < numAttributes; i++) {

            VertexAttribute attribute = attributes.get(i);
            switch (attribute.getUsage()) {
                case Position:
                    break; // no-op, we also need a position bound in gles
                case Color:
                    gl.glDisableClientState(GL_COLOR_ARRAY);
                    break;
                case Normal:
                    gl.glDisableClientState(GL_NORMAL_ARRAY);
                    break;
                case TextureCoordinates:
                    gl.glClientActiveTexture(GL_TEXTURE_2D);
                    gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);
                    break;
            }
        }

        srcData.position(0);
        srcData.limit(srcData.capacity());
    }

}
