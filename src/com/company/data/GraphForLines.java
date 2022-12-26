package com.company.data;

import com.company.dataElements.Edge;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphForLines {
    private static final long serialVersionUID = 1L;

    private List<Edge> edges;

    public GraphForLines() {
        this.edges = new ArrayList<Edge>();
    }

    public void addLine(Edge edge){
        edges.add(edge);
    }

    public void drawLine(Graphics g){
        for(Edge edge : edges){
            edge.draw(g);
        }
    }


}
