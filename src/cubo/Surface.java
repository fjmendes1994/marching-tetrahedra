/*
 * Trabalho Computação Grafica - UFF - 2017.1
 * Implementação do Algoritimo Marching Tetrahedra para superficies do tipo x² + y² + z² = 1
 * Fernando Mendes
 * 
 */
package cubo;

import cubo.Grid;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import static util.math.FastMath.sqrt;
import util.math.Matrix4f;
import util.math.Vector3f;
import util.shader.ObjectGL;
import util.shader.ShaderProgram;

/**
 *
 * @author fjmendes1994
 */
class Surface implements ObjectGL{
    
    //Cria um novo Grid de dimensão 64
    Grid grid = new Grid(52, 52, 52);
    
    private float fTarget = 1.0f;
    // Vertex Array Object Id
    private int vaoHandle;
    // Shader Program
    private ShaderProgram shader;
 
    // Buffer with the Positions
    private FloatBuffer positionBuffer;
    
    // Buffer with the Positions
    private FloatBuffer normalBuffer;
 
    // Buffer with the Colors
    private FloatBuffer colorBuffer;
    
    //
    int ePolygonMode =  GL11.GL_FILL;
    
    
    
    public Surface(){
        super();
}

    @Override
    public void fillVBOs() {
        grid.buildCubes();
        grid.buildTetrahedrons();
        grid.marchingTetrahedrons(fTarget);
        
        // convert vertex array to buffer
        positionBuffer = BufferUtils.createFloatBuffer(grid.getTriangles().size() * 4 * 3); // numero de triangulos * 3
        // convert vertex array to buffer
        normalBuffer = BufferUtils.createFloatBuffer(grid.getTriangles().size() * 4 * 3); //4(coordinates)*3(vertices)*12(triangles)
        // convert color array to buffer
        colorBuffer = BufferUtils.createFloatBuffer(grid.getTriangles().size() * 4 * 3); //4(coordinates)*3(vertices)*12(triangles)
        
        
  //    for (int i = 0; i < grid.getTriangles().size(); i++) {
  //        System.out.println("Triangle #"+i);
  //        grid.getTriangles().get(i).printVertex();
  //        System.out.println("");
  //    }
        
        
        for (int i = 0; i < grid.getTriangles().size(); i++) {
            buildTriangle(grid.getTriangles().get(i));
        }
        positionBuffer.flip();
        normalBuffer.flip();
        colorBuffer.flip();
    }

    @Override
    public void fillVAOs() {
        // fills the VBOs
        fillVBOs();

        // create vertex byffer object (VBO) for vertices
        int positionBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);

        // create vertex byffer object (VBO) for normals
        int normalBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);

        // create VBO for color values
        int colorBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // create vertex array object (VAO)
        vaoHandle = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // assign vertex VBO to slot 0 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);

        // assign vertex VBO to slot 1 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferHandle);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);

        // assign vertex VBO to slot 2 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL20.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, 0, 0);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void loadShaders() {
        // compile and link vertex and fragment shaders into
        // a "program" that resides in the OpenGL driver
        shader = new ShaderProgram();

        // do the heavy lifting of loading, compiling and linking
        // the two shaders into a usable shader program
        shader.init("shaders/phong.vert", "shaders/phong.frag");
 
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());
    }
    public void setFloat(String nameFloat, float dataFloat){        
        // defines the uniform variable
        int fltId = GL20.glGetUniformLocation(shader.getProgramId(), nameFloat);
        GL20.glUniform1f(fltId, dataFloat);   
    }
    

    public void setVector(String nameVector, Vector3f dataVector) {
        // Buffer with the Model Matrix
        FloatBuffer vecBuff = BufferUtils.createFloatBuffer(3);

        // converts from matrix to FloatBuffer
        vecBuff.clear();
        dataVector.store(vecBuff);
        vecBuff.flip();
        
        // defines the uniform variable
        int vecId = GL20.glGetUniformLocation(shader.getProgramId(), nameVector);
        GL20.glUniform3(vecId, vecBuff);
    }
    
    
    public void setMatrix(String nameMatrix, Matrix4f dataMatrix) {
        // Buffer with the Model Matrix
        FloatBuffer matBuff = BufferUtils.createFloatBuffer(16);

        // converts from matrix to FloatBuffer
        matBuff.clear();
        dataMatrix.store(matBuff);
        matBuff.flip();
        
        // defines the uniform variable
        int matrixId = GL20.glGetUniformLocation(shader.getProgramId(), nameMatrix);
        GL20.glUniformMatrix4(matrixId, false, matBuff);
    }

    @Override
    public void render() {
         // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());

        // bind vertex and color data
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0); // VertexPosition
        GL20.glEnableVertexAttribArray(1); // VertexNormal
        GL20.glEnableVertexAttribArray(2); // VertexColor
        
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, ePolygonMode);
        
        // draw VAO
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3 *  grid.getTriangles().size());
       
        
    }
    
        public void buildTriangle(Triangle triangle){
        triangle.positions.get(0).store(positionBuffer);
        triangle.positions.get(1).store(positionBuffer);
        triangle.positions.get(2).store(positionBuffer);
        
        triangle.colors.get(0).store(colorBuffer);
        triangle.colors.get(1).store(colorBuffer);
        triangle.colors.get(2).store(colorBuffer);
        
        triangle.positions.get(0).store(normalBuffer);
        triangle.positions.get(1).store(normalBuffer);
        triangle.positions.get(2).store(normalBuffer);
        
          
      
    }
    


}
