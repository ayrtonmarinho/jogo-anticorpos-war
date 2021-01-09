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
public class Rectangle {
    //(x-y) represents top-left corner rectangle;

    public double x;
    public double y;
    public double width;
    public double height;

    public Rectangle() {
        this.setPosition(0, 0);
        this.setSize(1, 1);
    }

    public Rectangle(double x, double y, double w, double h) {
        this.setPosition(x, y);
        this.setSize(w, h);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(double w, double h) {
        this.width = w;
        this.height = h;
    }

    public boolean overlaps(Rectangle other) {
        boolean noOverlap
                = this.x + this.width < other.x
                || other.x + other.width < this.x
                || this.y + this.height < other.y
                || other.y + other.height < this.y;
        return !noOverlap;
    }
}
