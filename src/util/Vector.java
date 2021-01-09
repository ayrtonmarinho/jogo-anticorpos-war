/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author iTuhh Z
 */
public class Vector {

    public double x;
    public double y;

    public Vector() {
        this.set(0, 0);
    }

    public Vector(double x, double y) {
        this.set(x, y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void multiply(double m) {
        this.x *= m;
        this.y *= m;
    }

    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void setLength(double L) {
        double currentLength = this.getLength();
        //Se a divisão for por zero retorna.
        if (currentLength == 0) {
            this.set(L, 0);
        } else {
            //escala o vetor para ter tamanho 1;
            this.multiply(1 / currentLength);
            //escale o vetor para ter tamanho L
            this.multiply(L);
        }

    }

    public double getAngle() {
        return Math.toDegrees(Math.atan2(this.y, this.x));
    }

    public void setAngle(double angleDegrees) {
        double L = this.getLength();
        double angleRadians = Math.toRadians(angleDegrees);
        this.x = L * Math.cos(angleRadians);
        this.y = L * Math.sin(angleRadians);
    }
}
