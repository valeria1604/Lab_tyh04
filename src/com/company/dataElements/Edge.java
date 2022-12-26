package com.company.dataElements;

import com.company.data.Node;

import java.awt.*;
import java.io.Serializable;

public class Edge implements Serializable {
    protected Node firstNode;
    protected Node secondNode;
    private Color color;

    public Edge(Node firstNode, Node secondNode, Color color) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.color = color;
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }

    public Node getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(Node secondNode) {
        this.secondNode = secondNode;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMouseOver(int mx, int my) {
        double dX = secondNode.getX() - firstNode.getX();
        double dY = secondNode.getY() - firstNode.getY();
        double eX = mx - secondNode.getX();
        double eY = my - secondNode.getY();
        double fX = mx - firstNode.getX();
        double fY = my - firstNode.getY();

        double sD = Math.pow(dX, 2) + Math.pow(dY, 2);
        double d = Math.sqrt(sD); // length of d (from point A to point B)

        double sE = Math.pow(eX, 2) + Math.pow(eY, 2);
        double e = Math.sqrt(sE); // length of e (from point B to point C)

        double sF = Math.pow(fX, 2) + Math.pow(fY, 2);
        double f = Math.sqrt(sF); // length of f (from point C to point A)

        double p = (d + e + f)/2;
        double area = Math.sqrt( p * (p-d) * (p-e) * (p-f) );
        double heightD = (2 * area) / d; //height of edge d

        return heightD<5;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(firstNode.getX(), firstNode.getY(), secondNode.getX(), secondNode.getY());
    }

    @Override
    public String toString() {
        return ("(" + firstNode.getX() + ", " + firstNode.getY() + ", " + secondNode.getX() + ", " + secondNode.getY() + ")");
    }

}
