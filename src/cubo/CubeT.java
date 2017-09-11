/*
 * Trabalho Computação Grafica - UFF - 2017.1
 * Implementação do Algoritimo Marching Tetrahedra para superficies do tipo x² + y² + z² = 1
 * Fernando Mendes
 * 
 */
package cubo;

import java.util.ArrayList;
import util.math.Vector3f;

/**
 *
 * @author fjmendes1994
 */
class CubeT {
    private float x;
    private float y;
    private float z;
    private float offset;
    private ArrayList<Vector3f> vertex;
    private ArrayList<Tetrahedron> tetrahedrons;
    private int nVertex = 8;
    private int nTetrahedrons = 6;
   
    
    public CubeT(float x, float y, float z, float offset){
        this.x = x;
        this.y = y;
        this.z = z;
        this.offset = offset;
        this.tetrahedrons = new ArrayList<Tetrahedron>(this.nTetrahedrons);
        this.vertex = new ArrayList<Vector3f>(this.nVertex);
        
        this.vertex.add(0, new Vector3f( (-1.0f) * this.offset + x, (-1.0f) * this.offset + y,  (-1.0f) * this.offset + z));
        this.vertex.add(1, new Vector3f( (1.0f) * this.offset + x, (-1.0f) * this.offset + y,  (-1.0f) * this.offset + z));
        this.vertex.add(2, new Vector3f( (1.0f) * this.offset + x, (1.0f) * this.offset + y,  (-1.0f) * this.offset + z));
        this.vertex.add(3, new Vector3f( (-1.0f) * this.offset + x, (1.0f) * this.offset + y,  (-1.0f) * this.offset + z));
        this.vertex.add(4, new Vector3f( (-1.0f) * this.offset + x, (-1.0f) * this.offset + y,  (1.0f) * this.offset + z));
        this.vertex.add(5, new Vector3f( (1.0f) * this.offset + x, (-1.0f) * this.offset + y,  (1.0f) * this.offset + z));
        this.vertex.add(6, new Vector3f( (1.0f) * this.offset + x, (1.0f) * this.offset + y,  (1.0f) * this.offset + z));
        this.vertex.add(7, new Vector3f( (-1.0f) * this.offset + x, (1.0f) * this.offset + y,  (1.0f) * this.offset + z));


    }
    
    public void buildTetrahedrons2(){
        
      //    { v[0], v[7], v[3], v[2] },
      //    { v[0], v[7], v[2], v[6] },
      //    { v[0], v[4], v[7], v[6] },
      //    { v[0], v[1], v[6], v[2] },
      //    { v[0], v[4], v[6], v[1] },
      //    { v[5], v[1], v[6], v[4] }
      this.tetrahedrons.add(0, new Tetrahedron(vertex.get(0), vertex.get(7), vertex.get(3), vertex.get(2)));
      this.tetrahedrons.add(1, new Tetrahedron(vertex.get(0), vertex.get(7), vertex.get(2), vertex.get(6)));
      this.tetrahedrons.add(2, new Tetrahedron(vertex.get(0), vertex.get(4), vertex.get(7), vertex.get(6)));
      this.tetrahedrons.add(3, new Tetrahedron(vertex.get(0), vertex.get(1), vertex.get(6), vertex.get(2)));
      this.tetrahedrons.add(4, new Tetrahedron(vertex.get(0), vertex.get(4), vertex.get(6), vertex.get(1)));
      this.tetrahedrons.add(5, new Tetrahedron(vertex.get(5), vertex.get(1), vertex.get(6), vertex.get(4)));
      
    }
    
    public ArrayList<Tetrahedron> getTetrahedrons(){
        return this.tetrahedrons;
    }
    
    public void printVertex(){
            System.out.println("V0 = ( " + this.vertex.get(0).x + ", " + this.vertex.get(0).y + ", " + this.vertex.get(0).z + " )");
            System.out.println("V1 = ( " + this.vertex.get(1).x + ", " + this.vertex.get(1).y + ", " + this.vertex.get(1).z + " )");
            System.out.println("V2 = ( " + this.vertex.get(2).x + ", " + this.vertex.get(2).y + ", " + this.vertex.get(2).z + " )");
            System.out.println("V3 = ( " + this.vertex.get(3).x + ", " + this.vertex.get(3).y + ", " + this.vertex.get(3).z + " )");
            System.out.println("V4 = ( " + this.vertex.get(4).x + ", " + this.vertex.get(4).y + ", " + this.vertex.get(4).z + " )");
            System.out.println("V5 = ( " + this.vertex.get(5).x + ", " + this.vertex.get(5).y + ", " + this.vertex.get(5).z + " )");
            System.out.println("V6 = ( " + this.vertex.get(6).x + ", " + this.vertex.get(6).y + ", " + this.vertex.get(6).z + " )");
            System.out.println("V7 = ( " + this.vertex.get(7).x + ", " + this.vertex.get(7).y + ", " + this.vertex.get(7).z + " )");
            System.out.println("");
        }
    
    
}
