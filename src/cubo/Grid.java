/*
 * Trabalho Computação Grafica - UFF - 2017.1
 * Implementação do Algoritimo Marching Tetrahedra para superficies do tipo x² + y² + z² = 1
 * Fernando Mendes
 * 
 */
package cubo;

import cubo.CubeT;
import java.util.ArrayList;
import util.math.Vector3f;
import util.math.Vector4f;

/**
 *
 * @author fjmendes1994
 */
public class Grid {

    private int dimX;
    private int dimY;
    private int dimZ;
    private float offset;
    private ArrayList<CubeT> cubes;
    private ArrayList<Tetrahedron> tetrahedrons;
    private ArrayList<Triangle> triangles;
    private int numCubes;
    private int numTetrahedrons;

    public Grid(int dimX, int dimY, int dimZ) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.dimZ = dimZ;
        this.numCubes = (this.dimX * this.dimY * this.dimZ) * 8;
        this.numTetrahedrons = numCubes * 6;
        this.offset = (1.0f / (float) dimX) / 2;
        cubes = new ArrayList<>(numCubes);
        tetrahedrons = new ArrayList<>(numTetrahedrons);
        triangles = new ArrayList<>();
    }

    // Divide a tela em cubos de acordo com a dimensão passada. 
    public void buildCubes() {

        float x = offset, y = offset, z = offset;
        System.out.println("Offset => " + offset);
        CubeT cubo;
        int contP = 0;
        // Cria os cubos do grid
        for (x = -1 + offset; x < 1; x += 2.0f * (offset)) {
            for (y = -1 + offset; y < 1; y += 2.0f * (offset)) {
                for (z = -1 + offset; z < 1; z += 2.0f * (offset)) {
                    cubo = new CubeT(x, y, z, offset);
                    cubes.add(contP, cubo);
                    contP++;

                }
            }
        }

        System.out.println("Cubos => " + contP);
        System.out.println("");

    }

    // Para cada cubo que foi gerado pelo buildCubes(), vão ser gerados mais 6 tetraedros
    public void buildTetrahedrons() {
        for (int i = 0; i < this.numCubes; i++) {
            cubes.get(i).buildTetrahedrons2();
        }
        for (int i = 0; i < this.numCubes; i++) {
            tetrahedrons.addAll(cubes.get(i).getTetrahedrons());
        }

    }

    // Retorna a Lista com todos os Tetraedros do grid
    public ArrayList<Tetrahedron> getTetrahedrons() {
        return tetrahedrons;
    }

    // Retorna a Array com todos os cubos do grid
    public ArrayList<CubeT> getCubes() {
        return cubes;
    }

    //  Aqui é implementdo o algoritmo de Marching Tetrahedrons, o valor da função é comparado com o falor de cada vertice,
    // apos essa etapa é calculado em qual dos 16 casos se encaixa o tetraedro para que seja desenhado os trinagulos de forma correta.
    public void marchingTetrahedrons(float fTarget) {

        Vector3f v0;
        Vector3f v1;
        Vector3f v2;
        Vector3f v3;

        for (int i = 0; i < tetrahedrons.size(); i++) {
            v0 = tetrahedrons.get(i).getV0();
            v1 = tetrahedrons.get(i).getV1();
            v2 = tetrahedrons.get(i).getV2();
            v3 = tetrahedrons.get(i).getV3();
            byte index = 0;
            for (int j = 0; j < 4; ++j) {
                if (tetrahedrons.get(i).getValue(j) < fTarget) {
                    index |= (1 << j);
                }
            }

            switch (index) {

                // we don't do anything if everyone is inside or outside
                case (byte) 0x00:
                case (byte) 0x0F:
                    break;

                // only vert 0 is inside
                case (byte) 0x01:
                    //desenha o vertice entra os vertices p[0] e p[1]
                    triangles.add(new Triangle(midleVertex(v0, v1),
                            midleVertex(v0, v3),
                            midleVertex(v0, v2)));
                    break;

                // only vert 1 is inside
                case (byte) 0x02:
                    triangles.add(new Triangle(midleVertex(v1, v0),
                            midleVertex(v1, v2),
                            midleVertex(v1, v3)));
                    break;

                // only vert 2 is inside
                case (byte) 0x04:
                    triangles.add(new Triangle(midleVertex(v2, v0),
                            midleVertex(v2, v3),
                            midleVertex(v2, v1)));
                    break;

                // only vert 3 is inside
                case (byte) 0x08:
                    triangles.add(new Triangle(midleVertex(v3, v1),
                            midleVertex(v3, v2),
                            midleVertex(v3, v0)));
                    break;

                // verts 0, 1 are inside
                case (byte) 0x03:
                    triangles.add(new Triangle(midleVertex(v3, v0),
                            midleVertex(v2, v0),
                            midleVertex(v1, v3)));

                    triangles.add(new Triangle(midleVertex(v2, v0),
                            midleVertex(v2, v1),
                            midleVertex(v1, v3)));
                    break;

                // verts 0, 2 are inside
                case (byte) 0x05:
                    triangles.add(new Triangle(midleVertex(v3, v0),
                            midleVertex(v1, v2),
                            midleVertex(v1, v0)));

                    triangles.add(new Triangle(midleVertex(v1, v2),
                            midleVertex(v3, v0),
                            midleVertex(v2, v3)));
                    break;

                // verts 0, 3 are inside
                case (byte) 0x09:
                    triangles.add(new Triangle(midleVertex(v0, v1),
                            midleVertex(v1, v3),
                            midleVertex(v0, v2)));

                    triangles.add(new Triangle(midleVertex(v1, v3),
                            midleVertex(v3, v2),
                            midleVertex(v0, v2)));
                    break;

                // verts 1, 2 are inside
                case (byte) 0x06:
                    triangles.add(new Triangle(midleVertex(v0, v1),
                            midleVertex(v0, v2),
                            midleVertex(v1, v3)));

                    triangles.add(new Triangle(midleVertex(v1, v3),
                            midleVertex(v0, v2),
                            midleVertex(v3, v2)));
                    break;

                // verts 2, 3 are inside
                case (byte) 0x0C:
                    triangles.add(new Triangle(midleVertex(v1, v3),
                            midleVertex(v2, v0),
                            midleVertex(v3, v0)));

                    triangles.add(new Triangle(midleVertex(v2, v0),
                            midleVertex(v1, v3),
                            midleVertex(v2, v1)));
                    break;

                // verts 1, 3 are inside
                case (byte) 0x0A:
                    triangles.add(new Triangle(midleVertex(v3, v0),
                            midleVertex(v1, v0),
                            midleVertex(v1, v2)));

                    triangles.add(new Triangle(midleVertex(v1, v2),
                            midleVertex(v2, v3),
                            midleVertex(v3, v0)));
                    break;

                // verts 0, 1, 2 are inside
                case (byte) 0x07:
                    triangles.add(new Triangle(midleVertex(v3, v0),
                            midleVertex(v3, v2),
                            midleVertex(v3, v1)));
                    break;

                // verts 0, 1, 3 are inside
                case (byte) 0x0B:
                    triangles.add(new Triangle(midleVertex(v2, v1),
                            midleVertex(v2, v3),
                            midleVertex(v2, v0)));
                    break;

                // verts 0, 2, 3 are inside
                case (byte) 0x0D:
                    triangles.add(new Triangle(midleVertex(v1, v0),
                            midleVertex(v1, v3),
                            midleVertex(v1, v2)));
                    break;

                // verts 1, 2, 3 are inside
                case (byte) 0x0E:
                    triangles.add(new Triangle(midleVertex(v0, v1),
                            midleVertex(v0, v2),
                            midleVertex(v0, v3)));
                    break;

                default:
                    assert (false);

            }
        }
        System.out.println("NUM TRI =>"+getTriangles().size());

    }

    // Retorna a Lista com os triangulos a serem desenhados na tela
    public ArrayList<Triangle> getTriangles() {
        return this.triangles;
    }

    // Calcula o Ponto medio entre os vertices
    private Vector3f midleVertex(Vector3f v1, Vector3f v2) {
        Vector3f result = new Vector3f((v1.x + v2.x) / 2.0f, (v1.y + v2.y) / 2.0f, (v1.z + v2.z) / 2.0f);
        return result;
    }

}
