package com.deemo.typing.Util;

/**
 * y=kx+b
 * 计算k,b
 */
public class LinearFunctionCoefficient {
    private float k;
    private float b;

    //默认直线y=0
    public LinearFunctionCoefficient(){
        this.k=0;
        this.b=0;
    }

    public float getK() {
        return k;
    }

    public void setK(float k) {
        this.k = k;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void computeKAndB(float x1, float y1, float x2, float y2){
        //最好是让x1-x2<epsilon
        if(x1==x2){
            return;
        }
        else{
            this.k = (y2-y1)/(x2-x1);
            this.b = y1 - k*x1;
        }
    }
}
