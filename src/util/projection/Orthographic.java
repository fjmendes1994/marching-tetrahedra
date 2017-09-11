/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.projection;

import util.math.Matrix4f;

/**
 *
 * @author fjmendes1994
 */
public class Orthographic {

    private float left = 0.0f;
    private float rigth = 0.0f;
    private float bottom = 0.0f;
    private float top = 0.0f;
    private float near = 0.0f;
    private float far = 0.0f;

    public Orthographic(float left, float rigth, float bottom, float top, float near, float far) {
        this.left = left;
        this.rigth = rigth;
        this.bottom = bottom;
        this.top = top;
        this.near = near;
        this.far = far;
    }
    public Matrix4f matrix() {
        Matrix4f tempMatrix = new Matrix4f();
        tempMatrix.m11 = 2 / (rigth - left);     tempMatrix.m21 = 0.0f;                 tempMatrix.m31 = 0.0f;                  tempMatrix.m41 = -( (left + rigth) / (rigth - left) );
        tempMatrix.m12 = 0.0f;                   tempMatrix.m22 = 2 / (top - bottom);   tempMatrix.m32 = 0.0f;                  tempMatrix.m42 = -( (top + bottom) / (top - bottom) );
        tempMatrix.m13 = 0.0f;                   tempMatrix.m23 = 0.0f;                 tempMatrix.m33 = -( 2 / (far - near) ); tempMatrix.m43 = -( (far + near) / (far - near) );
        tempMatrix.m14 = 0.0f;                   tempMatrix.m24 = 0.0f;                 tempMatrix.m34 = -1.0f;                 tempMatrix.m44 = 1.0f;

        return tempMatrix;
    }
}
