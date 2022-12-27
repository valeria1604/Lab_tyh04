/**
 * Nazwa: Graph editor
 * Autor: Valeriia Tykhoniuk (266319)
 * Data utworzenia: 13.12.2022
 */

package com.company.data;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Graph implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Node> nodes;

    private List<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<Node>();
        this.edges = new ArrayList<Edge>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public Node[] getNodes() {
        Node[] array = new Node[0];
        return nodes.toArray(array);
    }

    public List<Node> getNodesList() {
        return nodes;
    }

    public List<Edge> getEdgesList() {
        return edges;
    }


    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void draw(Graphics g) {
        for (Node node : nodes) {
            node.draw(g);
        }
        for (Edge edge : edges) {
            edge.draw(g);
        }
    }

}
