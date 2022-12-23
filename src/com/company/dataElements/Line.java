package com.company.dataElements;

import java.awt.*;
import java.io.Serializable;

public class Line implements Serializable {
    protected int x;
    protected int y;
//    protected int x2;
//    protected int y2;

    private Color color;

    public Line(int x, int y) {
        this.x = x;
        this.y = y;
//        this.x2 = 10;
//        this.y2 = 10;
        this.color = Color.BLUE;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

//    public int getX2() {
//        return x2;
//    }
//
//    public void setX2(int x2) {
//        this.x2 = x2;
//    }
//
//    public int getY2() {
//        return y2;
//    }
//
//    public void setY2(int y2) {
//        this.y2 = y2;
//    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

//    public boolean isMouseOver(int mx, int my){
//        return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;
//    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawLine(x, y, 10, 10);
    }

    @Override
    public String toString(){
        return ("(" + x +", " + y + ")");
    }

}
