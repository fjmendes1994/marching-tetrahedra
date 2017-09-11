/*
 * Trabalho Computação Grafica - UFF - 2017.1
 * Implementação do Algoritimo Marching Tetrahedra para superficies do tipo x² + y² + z² = 1
 * Fernando Mendes
 * 
 */
package cubo;

import java.util.ArrayList;
import static util.math.FastMath.sqrt;
import util.math.Vector3f;
import util.math.Vector4f;

/**
 *
 * @author fjmendes1994
 */
public class Tetrahedron {
    
    private ArrayList<Vector3f> vertex;
    private ArrayList<Float> value;
    private ArrayList<Boolean> flags;
    private int nverts = 4;
    

    
    
    public Tetrahedron(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3){
        this.vertex = new ArrayList<>(4);
        this.flags = new ArrayList<>(4);
        this.value = new ArrayList<Float>(4);
        
       
        
        // Fill the vertices
        this.vertex.add(0, v0);
        this.vertex.add(1, v1);
        this.vertex.add(2, v2);
        this.vertex.add(3, v3);
        
        
        for (int i = 0; i < 4; i++) {
            float x  = this.vertex.get(i).x;
            float y  = this.vertex.get(i).y;
            float z  = this.vertex.get(i).z;
            this.value.add(i, getSphereValueAt(x,y,z));
            
        }
        
        

    }
    
    public void printVertex(){
        System.out.println("V0 = ( " + this.vertex.get(0).x + ", " + this.vertex.get(0).y + ", " + this.vertex.get(0).z + " )");
        System.out.println("V1 = ( " + this.vertex.get(1).x + ", " + this.vertex.get(1).y + ", " + this.vertex.get(1).z + " )");
        System.out.println("V2 = ( " + this.vertex.get(2).x + ", " + this.vertex.get(2).y + ", " + this.vertex.get(2).z + " )");
        System.out.println("V3 = ( " + this.vertex.get(3).x + ", " + this.vertex.get(3).y + ", " + this.vertex.get(3).z + " )");
        System.out.println("");
    }
    
     public void printVAlues(){
        System.out.println("Val0 = ( " + this.value.get(0));
        System.out.println("Val1 = ( " + this.value.get(1));
        System.out.println("Val2 = ( " + this.value.get(2));
        System.out.println("Val3 = ( " + this.value.get(3));
        System.out.println("");
    }
    
    public float getValue(int n){
        return this.value.get(n);
    }
    
    public Vector3f getV0(){
        return this.vertex.get(0);
    }
    
    public Vector3f getV1(){
        return this.vertex.get(1);
    }
     
    public Vector3f getV2(){
        return this.vertex.get(2);
    }
    
     public Vector3f getV3(){
        return this.vertex.get(3);
    }
     
    // Função que define a superficie a ser desenhada
     public float getSphereValueAt(float x, float y, float z){
        return (float) sqrt((x*x) + (y*y) + (z*z));
     }
}
