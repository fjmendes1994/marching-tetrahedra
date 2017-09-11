/*
 * Trabalho Computação Grafica - UFF - 2017.1
 * Implementação do Algoritimo Marching Tetrahedra para superficies do tipo x² + y² + z² = 1
 * Fernando Mendes
 * 
 */
package cubo;

import java.util.ArrayList;
import util.math.Vector3f;
import util.math.Vector4f;

/**
 *
 * @author fjmendes1994
 */
class Triangle {

    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;

    

    protected int nverts = 3;
    protected int nfaces = 1;

    public Triangle(Vector3f v0, Vector3f v1, Vector3f v2) {
        positions = new ArrayList<>(3);
        colors = new ArrayList<>(3);
     
        

        // Fill the vertices
        positions.add(0, new Vector4f(v0, 1.0f));
        positions.add(1, new Vector4f(v1, 1.0f));
        positions.add(2, new Vector4f(v2, 1.0f));

        // Fill the colors
        colors.add(0, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
        colors.add(1, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
        colors.add(2, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
        
    
        
    }
    
    public void printVertex(){
        System.out.println(" V0 => " + positions.get(0));
        System.out.println(" V1 => " + positions.get(1));
        System.out.println(" V2 => " + positions.get(2));
        
    }
}
