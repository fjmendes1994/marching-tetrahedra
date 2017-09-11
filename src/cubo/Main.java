/*
 * Trabalho Computação Grafica - UFF - 2017.1
 * Implementação do Algoritimo Marching Tetrahedra para superficies do tipo x² + y² + z² = 1
 * Fernando Mendes
 * 
 */
package cubo;

/**
 *
 * @author marcoslage
 * 
 */
import java.util.Scanner;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import util.camera.Camera;
import util.math.FastMath;
import util.math.Matrix4f;
import util.math.Vector3f;
import util.projection.Orthographic;
import util.projection.Projection;

public class Main {

    Scanner teclado = new Scanner(System.in);
    // Creates a new cube
    private final Surface sphere = new Surface();

    // Animation:
    private float currentAngleY = 0.5f;
    private float currentAngleX = 0.5f;

    // Projection Matrix
    private final Projection proj = new Projection(45, 1.3333f, 0.0f, 100f);
    private final Orthographic ort = new Orthographic(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
    //-2,2,-2,2,-20,20

    // View Matrix
    private final Vector3f eye = new Vector3f(0.0f, 2.0f, 2.0f);
    private final Vector3f at = new Vector3f(0.0f, 0.0f, 0.0f);
    private final Vector3f up = new Vector3f(0.0f, 1.0f, 2.0f);

    // Camera
    private final Camera cam = new Camera(eye, at, up);

    // Light
    private final Vector3f lightPos = new Vector3f(0.0f, 2.0f, -2.0f);
    private final Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f speclarColor = new Vector3f(1.0f, 1.0f, 1.0f);

    private final float kA = 0.4f;
    private final float kD = 0.1f;
    private final float kS = 1.5f;
    private final float sN = 1.0f;

    // Model Matrix:
    private final Matrix4f rotationMatrixY = new Matrix4f();
    private final Matrix4f rotationMatrixX = new Matrix4f();
    private final Matrix4f scaleMatrix = new Matrix4f();

    // Final Matrix
    private final Matrix4f modelMatrix = new Matrix4f();
    private final Matrix4f viewMatrix = new Matrix4f();
    private final Matrix4f projMatrix = new Matrix4f();

    /**
     * General initialization stuff for OpenGL
     *
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {

        // width and height of window and view port
        int width = 1080;
        int height = 720;

        // set up window and display
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setVSyncEnabled(true);
        Display.setTitle("Shader OpenGL Hello");

        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        Display.create(pixelFormat, contextAtrributes);

        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));

        // initialize basic OpenGL stuff
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void run() {
        // Creates the vertex array object. 
        // Must be performed before shaders compilation.
        sphere.fillVAOs();
        sphere.loadShaders();

        // Model Matrix setup
        scaleMatrix.m11 = 0.5f;
        scaleMatrix.m22 = 0.5f;
        scaleMatrix.m33 = 0.5f;

        // light setup
        sphere.setVector("viewPos", eye);
        sphere.setVector("lightPos", lightPos);
        sphere.setVector("ambientColor", ambientColor);
        sphere.setVector("diffuseColor", diffuseColor);
        sphere.setVector("speclarColor", speclarColor);

        sphere.setFloat("kA", kA);
        sphere.setFloat("kD", kD);
        sphere.setFloat("kS", kS);
        sphere.setFloat("sN", sN);

        while (Display.isCloseRequested() == false) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);

            // Projection and View Matrix Setup
            if(Keyboard.isKeyDown(Keyboard.KEY_O)){
                projMatrix.setTo(ort.matrix());
               
            }else{
                projMatrix.setTo(proj.perspective());
            }
            
            
            viewMatrix.setTo(cam.viewMatrix());

            //rotação
            if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
                currentAngleY += 0.01f;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
                currentAngleY -= 0.01f;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
                currentAngleX += 0.01f;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                currentAngleX -= 0.01f;
            }

            //currentAngle += 0.01f;
            float cY = FastMath.cos(currentAngleY);
            float sY = FastMath.sin(currentAngleY);

            float cX = FastMath.cos(currentAngleX);
            float sX = FastMath.sin(currentAngleX);

            //matrixY
            rotationMatrixY.m22 = cY;
            rotationMatrixY.m32 = -sY;
            rotationMatrixY.m23 = sY;
            rotationMatrixY.m33 = cY;

            //matrixX
            rotationMatrixX.m11 = cX;
            rotationMatrixX.m13 = sX;
            rotationMatrixX.m31 = -sX;
            rotationMatrixX.m33 = cX;

            modelMatrix.setToIdentity();

            modelMatrix.multiply(scaleMatrix);
            modelMatrix.multiply(rotationMatrixY);
            modelMatrix.multiply(rotationMatrixX);

            sphere.setMatrix("modelmatrix", modelMatrix);
            sphere.setMatrix("viewmatrix", viewMatrix);
            sphere.setMatrix("projection", projMatrix);
            sphere.render();

            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            Display.update();
            Display.sync(60);
        }

        Display.destroy();
    }

    /**
     * main method to run the example
     *
     * @param args
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        Main example = new Main();
        example.initGl();
        example.run();
    }
}
