/*
 *  Program: Prosty edytor grafu
 *     Plik: Node.java
 *
 *  Klasa Node reprezentuje w�z�y grafu na p�aszczy�nie.
 *  Klasa mo�e by� klas� bazow� dla klas reprezentuj�cych
 *  w�z�y graf�w modeluj�ch  wybrane zagadnienia np.:
 *     - schemat komunikacji miejskiej,
 *     - drzewo genealogiczne,
 *     - schemat obwodu elektronicznego typu RLC,
 *     - schemat topologi sieci komputerowej
 *
 *    Autor: Pawel Rogalinski
 *     Data:  listopad 2021 r.
 */

package com.company.data;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;


public class Node implements Serializable {

    protected int x;
    protected int y;

    protected int r;

    private Color color;

    private LinkedList<Node> linkedNodes = new LinkedList<>();

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 10;
        this.color = Color.WHITE;
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

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMouseOver(int mx, int my) {
        return (x - mx) * (x - mx) + (y - my) * (y - my) <= r * r;
    }

    void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
        g.setColor(Color.BLACK);
        g.drawOval(x - r, y - r, 2 * r, 2 * r);
    }

    public LinkedList<Node> getLinkedNodes() {
        return linkedNodes;
    }

    public void setLinkedNodes(LinkedList<Node> linkedNodes) {
        this.linkedNodes = linkedNodes;
    }

    @Override
    public String toString() {
        return ("(" + x + ", " + y + ", " + r + ")");
    }

}
