package ice.node.mesh;

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
                        VertexAttribute.Usage.Normal,
                        3
                ),

                new VertexAttribute(
                        VertexAttribute.Usage.TextureCoordinates,
                        2
                )
        };

        int squareNum = stepX * stepY;
        int verticesCount = squareNum * 2 * 3;

        VertexData vertexData = null;
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


        float[] vertices = new float[verticesCount * (2 + 2 + 3)];


        int[] indexes = new int[]{
                3, 0, 2,
                1, 2, 0
        };

        float buttomLeftX = 0;//-(width >> 1);
        float buttomLeftY = -height;//-(height >> 1);
        float eachSquareWidth = width / stepX;
        float eachSquareHeight = height / stepY;

        for (int square = 0, elementIndex = 0; square < squareNum; square++) {

            int currentStepX = square % stepX;
            int currentStepY = square / stepY;


            float[][] data = new float[][]{
                    {
                            buttomLeftX + currentStepX * eachSquareWidth,
                            buttomLeftY + currentStepY * eachSquareHeight,

                            0,
                            0,
                            1,

                            maxU * currentStepX / (float) stepX,
                            maxV * (stepY - currentStepY) / (float) stepY
                    },
                    {
                            buttomLeftX + (currentStepX + 1) * eachSquareWidth,
                            buttomLeftY + currentStepY * eachSquareHeight,
                            0,
                            0,
                            1,
                            maxU * (currentStepX + 1) / (float) stepX,
                            maxV * (stepY - currentStepY) / (float) stepY
                    },
                    {
                            buttomLeftX + (currentStepX + 1) * eachSquareWidth,
                            buttomLeftY + (currentStepY + 1) * eachSquareHeight,
                            0,
                            0,
                            1,
                            maxU * (currentStepX + 1) / (float) stepX,
                            maxV * (stepY - currentStepY - 1) / (float) stepY
                    },
                    {
                            buttomLeftX + currentStepX * eachSquareWidth,
                            buttomLeftY + (currentStepY + 1) * eachSquareHeight,
                            0,
                            0,
                            1,
                            maxU * currentStepX / (float) stepX,
                            maxV * (stepY - currentStepY - 1) / (float) stepY
                    }

            };

            for (int j = 0, index = 0; j < indexes.length; j++) {
                index = indexes[j];
                vertices[elementIndex++] = data[index][0];   //x
                vertices[elementIndex++] = data[index][1];   //y

                vertices[elementIndex++] = data[index][2];  //noramlX
                vertices[elementIndex++] = data[index][3];  //noramlY
                vertices[elementIndex++] = data[index][4];  //noramlX

                vertices[elementIndex++] = data[index][5];  //texX
                vertices[elementIndex++] = data[index][6];  //texY
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

    private int stepX, stepY;
}