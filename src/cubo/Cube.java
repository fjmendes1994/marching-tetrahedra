/*
 * Trabalho Computação Grafica - UFF - 2017.1
 * Implementação do Algoritimo Marching Tetrahedra para superficies do tipo x² + y² + z² = 1
 * Fernando Mendes
 * 
 */
package cubo;

import java.util.ArrayList;
import util.math.Vector4f;

/**
 *
 * @author marcoslage
 */
class Cube {
    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;
    
    protected int nverts = 8;
    protected int nfaces = 12;
    
    public Cube(){
        positions = new ArrayList<>(8);
        colors    = new ArrayList<>(8);
        
        // Fill the vertices
        positions.add( new Vector4f(-0.5f,-0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f, 0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f, 0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f,-0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f,-0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f, 0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f, 0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f,-0.5f,-0.5f, 1.0f) );

        // Fill the colors
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
    }
}
