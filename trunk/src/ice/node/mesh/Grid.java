package ice.node.mesh;

import ice.model.Point3F;
import ice.model.vertex.*;

/**
 * 中心在左上点.
 * User: ice
 * Date: 11-11-22
 * Time: 下午6:22
 */
public class Grid extends Mesh {

    public static VertexData createVertexData(float width, float height, int stepX, int stepY, float maxU, float maxV, boolean vboMode) {

        VertexAttribute[] attributesArray = new VertexAttribute[]{
                new VertexAttribute(
                        VertexAttribute.Usage.Position,
                        2
                ),

                new VertexAttribute(
                        VertexAttribute.Usage.TextureCoordinates,
                        2
                )
        };

        int squareNum = stepX * stepY;
        int verticesCount = squareNum * 2 * 3;

        VertexData vertexData;

        if (vboMode) {
            vertexData = new VertexBufferObject(
                    verticesCount,
                    new VertexAttributes(attributesArray)
            );
        }
        else {
            vertexData = new VertexArray(
                    verticesCount,
                    new VertexAttributes(attributesArray)
            );
        }


        float[] vertices = new float[verticesCount * (2 + 2)];


        int[] indexes = new int[]{
                3, 0, 2,
                1, 2, 0
        };

        float buttomLeftX = -width / 2;
        float buttomLeftY = -height / 2;
        float eachSquareWidth = width / stepX;
        float eachSquareHeight = height / stepY;

        for (int square = 0, elementIndex = 0; square < squareNum; square++) {

            int currentStepX = square % stepX;
            int currentStepY = square / stepY;


            float[][] data = new float[][]{
                    {
                            //左下角
                            buttomLeftX + currentStepX * eachSquareWidth,
                            buttomLeftY + currentStepY * eachSquareHeight,
                            maxU * currentStepX / (float) stepX,
                            maxV * (stepY - currentStepY) / (float) stepY
                    },
                    {
                            //右下角
                            buttomLeftX + (currentStepX + 1) * eachSquareWidth,
                            buttomLeftY + currentStepY * eachSquareHeight,
                            maxU * (currentStepX + 1) / (float) stepX,
                            maxV * (stepY - currentStepY) / (float) stepY
                    },
                    {
                            //右上角
                            buttomLeftX + (currentStepX + 1) * eachSquareWidth,
                            buttomLeftY + (currentStepY + 1) * eachSquareHeight,
                            maxU * (currentStepX + 1) / (float) stepX,
                            maxV * (stepY - currentStepY - 1) / (float) stepY
                    },
                    {
                            //左上角
                            buttomLeftX + currentStepX * eachSquareWidth,
                            buttomLeftY + (currentStepY + 1) * eachSquareHeight,
                            maxU * currentStepX / (float) stepX,
                            maxV * (stepY - currentStepY - 1) / (float) stepY
                    }

            };

            for (int j = 0; j < indexes.length; j++) {
                int index = indexes[j];

                vertices[elementIndex++] = data[index][0];   //x
                vertices[elementIndex++] = data[index][1];   //y

                vertices[elementIndex++] = data[index][2];  //texX
                vertices[elementIndex++] = data[index][3];  //texY
            }

        }

        vertexData.setVertices(vertices);
        return vertexData;
    }

    public Grid(float width, float height) {
        this(width, height, true);
    }

    public Grid(float width, float height, boolean initVertex) {
        this.width = width;
        this.height = height;

        if (initVertex)
            setUpVertex();
    }

    public void setUpVertex() {
        setUpVertex(1, 1);
    }

    public void setUpVertex(float maxU, float maxV) {
        setUpVertex(maxU, maxV, true);
    }

    public void setUpVertex(float maxU, float maxV, boolean vboMode) {
        setUpVertex(1, 1, maxU, maxV, true);
    }

    public void setUpVertex(int stepX, int stepY, float maxU, float maxV, boolean vboMode) {

        this.stepX = stepX;
        this.stepY = stepY;

        VertexData vertexData = createVertexData(width, height, stepX, stepY, maxU, maxV, vboMode);

        setVertexData(vertexData);
    }

    public int getStepX() {
        return stepX;
    }

    public int getStepY() {
        return stepY;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    @Override
    public boolean hitTest(float x, float y) {
        Point3F absolutePos = getAbsolutePos();
        float offsetX = x - absolutePos.x;
        float offsetY = y - absolutePos.y;

        float halfWidth = width / 2;
        float halfHeight = height / 2;

        return offsetX >= -halfWidth
                && offsetX <= halfWidth
                && offsetY >= -halfHeight
                && offsetY <= halfHeight;
    }

    private int stepX, stepY;
    protected float width, height;
}
