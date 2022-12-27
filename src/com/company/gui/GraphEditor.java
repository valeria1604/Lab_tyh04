/**
 * Nazwa: Graph editor
 * Autor: Valeriia Tykhoniuk (266319)
 * Data utworzenia: 13.12.2022
 */

package com.company.gui;

import com.company.data.Graph;
import com.company.data.Node;
import com.company.data.NodeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;


public class GraphEditor extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String APP_AUTHOR = "Author: Valeriia Tykhoniuk\n  Data: grudzien 2021";
    private static final String APP_TITLE = "Graph editor";

    private static final String APP_INSTRUCTION =
            "                 D E S C R I P T I O N \n\n" +
                    "   arrows -> moving all nodes\n" +
                    "   SHIFT + arrows -> fast moving all nodes\n\n" +

                    "   DEL -> delete node\n" +
                    "   +, -   -> change the size of node\n" +
                    "   r,g,b -> color change of node\n\n" +

                    "   Left mouse on node -> move of node \n" +
                    "   Left mouse on node -> moving all nodes \n" +
                    "   Right mouse on panel -> POPUP MENU (create node)\n" +
                    "   Right mouse on node -> POPUP MENU (edit node)\n";


    public static void main(String[] args) {
        new GraphEditor();
    }

    private static final String LIST_OF_GRAPH = "Graph.BIN";

    private JMenuBar menuBar = new JMenuBar();

    private JMenu menuGraph = new JMenu("Graph");
    private JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
    private JMenuItem menuShowExample = new JMenuItem("Example", KeyEvent.VK_X);
    private JMenuItem menuListOfNodes = new JMenuItem("List of Nodes", KeyEvent.VK_N);
    private JMenuItem menuExit = new JMenuItem("Exit", KeyEvent.VK_E);

    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem menuAuthor = new JMenuItem("Author", KeyEvent.VK_A);
    private JMenuItem menuInstruction = new JMenuItem("Instruction", KeyEvent.VK_I);

    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuLoadFromDocument = new JMenuItem("Load from document", KeyEvent.VK_L);
    private JMenuItem menuSaveToDocument = new JMenuItem("Save to document", KeyEvent.VK_S);

    private GraphPanel panel = new GraphPanel();


    public GraphEditor() {
        super(APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setContentPane(panel);
        createMenu();
        showBuildingExample();
        setVisible(true);
    }

    private void showListOfNodes(Graph graph) {
        Node array[] = graph.getNodes();
        int i = 0;
        StringBuilder message = new StringBuilder("Amount of nodes: " + array.length + "\n");
        for (Node node : array) {
            message.append(node + "    ");
            if (++i % 5 == 0)
                message.append("\n");
        }
        JOptionPane.showMessageDialog(this, message, APP_TITLE + " - Amount of nodes", JOptionPane.PLAIN_MESSAGE);
    }

    private void createMenu() {
        menuNew.addActionListener(this);
        menuShowExample.addActionListener(this);
        menuExit.addActionListener(this);
        menuListOfNodes.addActionListener(this);
        menuAuthor.addActionListener(this);
        menuInstruction.addActionListener(this);
        menuLoadFromDocument.addActionListener(this);
        menuSaveToDocument.addActionListener(this);

        menuGraph.setMnemonic(KeyEvent.VK_G);
        menuGraph.add(menuNew);
        menuGraph.add(menuShowExample);
        menuGraph.addSeparator();
        menuGraph.add(menuListOfNodes);
        menuGraph.addSeparator();
        menuGraph.add(menuExit);

        menuHelp.setMnemonic(KeyEvent.VK_H);
        menuHelp.add(menuInstruction);
        menuHelp.add(menuAuthor);

        menuFile.setMnemonic(KeyEvent.VK_F);
        menuFile.add(menuLoadFromDocument);
        menuFile.add(menuSaveToDocument);

        menuBar.add(menuGraph);
        menuBar.add(menuHelp);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == menuNew) {
            panel.setGraph(new Graph());
        }
        if (source == menuShowExample) {
            showBuildingExample();
        }
        if (source == menuListOfNodes) {
            showListOfNodes(panel.getGraph());
        }
        if (source == menuAuthor) {
            JOptionPane.showMessageDialog(this, APP_AUTHOR, APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
        }
        if (source == menuInstruction) {
            JOptionPane.showMessageDialog(this, APP_INSTRUCTION, APP_TITLE, JOptionPane.PLAIN_MESSAGE);
        }
        if (source == menuExit) {
            System.exit(0);
        }

        if (source == menuLoadFromDocument) {
            try {
                panel.setGraph(loadGroupListFromFile(LIST_OF_GRAPH));
            } catch (NodeException e) {
                e.printStackTrace();
            }
            repaint();
        }

        if (source == menuSaveToDocument) {
            try {
                saveGroupListToFile(LIST_OF_GRAPH);
            } catch (NodeException e) {
                e.printStackTrace();
            }
        }
    }

    private void showBuildingExample() {
        Graph graph = new Graph();

        Node node1 = new Node(130, 40);
        node1.setColor(Color.RED);
        node1.setR(30);
        Node node2 = new Node(300, 100);
        node2.setColor(Color.MAGENTA);
        node2.setR(35);
        Node node3 = new Node(50, 200);
        node3.setColor(Color.YELLOW);
        node3.setR(20);
        Node node4 = new Node(200, 250);
        node4.setColor(Color.GREEN);
        node4.setR(30);

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        panel.setGraph(graph);
    }

    void saveGroupListToFile(String fileName) throws NodeException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(panel.getGraph());
        } catch (FileNotFoundException e) {
            throw new NodeException("File wasn't found: " + fileName);
        } catch (IOException e) {
            throw new NodeException("Error");
        }

    }

    public static Graph loadGroupListFromFile(String fileName) throws NodeException {
        Graph loadedGraph = new Graph();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            loadedGraph = (Graph) in.readObject();
        } catch (FileNotFoundException e) {
            throw new NodeException("File wasn't found: " + fileName);
        } catch (IOException e) {
            throw new NodeException("Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedGraph;
    }
}



