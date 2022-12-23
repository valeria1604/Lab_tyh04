/* 
 *  Program: Prosty edytor grafu
 *     Plik: Graph.java
 *           
 *  Klasa Graph reprezentuje graf na p�aszczy�nie. 
 *  Klasa mo�e by� klas� bazow� dla klas reprezentuj�cych 
 *  grafy modeluj�ce wybrane zagadnienia np.: 
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
import java.util.ArrayList;
import java.util.List;


public class Graph implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// Lista w�z��w grafu
	private List<Node> nodes;

	
	public Graph() {
		this.nodes = new ArrayList<Node>();
	}
	
	public void addNode(Node node){
		nodes.add(node);
	}


	public void removeNode(Node node){
		nodes.remove(node);
	}
	
	public Node[] getNodes(){
		Node [] array = new Node[0];
		return nodes.toArray(array);
	}

	public List<Node> getNodesList(){
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public void draw(Graphics g){
		for(Node node : nodes){
			node.draw(g);
		}
	}

}
