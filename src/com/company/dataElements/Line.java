package com.company.dataElements;

import java.awt.*;
import java.io.Serializable;

public class Line implements Serializable {
    protected int x;
    protected int y;

    private Color color;

    public Line(int x, int y) {
        this.x = x;
        this.y = y;
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


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawLine(x, y, 10, 10);
    }

    @Override
    public String toString(){
        return ("(" + x +", " + y + ")");
    }

}
