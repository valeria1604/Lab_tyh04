/*
 *  Program: Prosty edytor grafu
 *     Plik: GraphEditor.java
 *
 *  Klasa GraphEditor implementuje okno g³ówne
 *  dla prostego graficznego edytora grafu.
 *
 *    Autor: Pawel Rogalinski
 *     Data:  listopad 2021 r.
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
import java.util.ArrayList;
import java.util.List;


public class GraphEditor extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String APP_AUTHOR = "Autor: Pawe³ Rogaliñski\n  Data: listopad 2021";
    private static final String APP_TITLE = "Prosty edytor grafów";

    private static final String APP_INSTRUCTION =
            "                  O P I S   P R O G R A M U \n\n" +
                    "Aktywna klawisze:\n" +
                    "   strza³ki ==> przesuwanie wszystkich kó³\n" +
                    "   SHIFT + strza³ki ==> szybkie przesuwanie wszystkich kó³\n\n" +
                    "ponadto gdy kursor wskazuje ko³o:\n" +
                    "   DEL   ==> kasowanie ko³a\n" +
                    "   +, -   ==> powiêkszanie, pomniejszanie ko³a\n" +
                    "   r,g,b ==> zmiana koloru ko³a\n\n" +
                    "Operacje myszka:\n" +
                    "   przeci¹ganie ==> przesuwanie wszystkich kó³\n" +
                    "   PPM ==> tworzenie nowego ko³a w niejscu kursora\n" +
                    "ponadto gdy kursor wskazuje ko³o:\n" +
                    "   przeci¹ganie ==> przesuwanie ko³a\n" +
                    "   PPM ==> zmiana koloru ko³a\n" +
                    "                   lub usuwanie ko³a\n";


    public static void main(String[] args) {
        new GraphEditor();
    }

    private static final String LIST_OF_NODES = "Nodes.BIN";

    // private GraphBase graph;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuLoadFromDocument = new JMenuItem("Load from document", KeyEvent.VK_L);
    private JMenuItem menuSaveToDocument = new JMenuItem("Save to document", KeyEvent.VK_S);

    private JMenu menuGraph = new JMenu("Graph");
    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
    private JMenuItem menuShowExample = new JMenuItem("Example", KeyEvent.VK_X);
    private JMenuItem menuExit = new JMenuItem("Exit", KeyEvent.VK_E);
    private JMenuItem menuListOfNodes = new JMenuItem("List of Nodes", KeyEvent.VK_N);
    private JMenuItem menuAuthor = new JMenuItem("Author", KeyEvent.VK_A);
    private JMenuItem menuInstruction = new JMenuItem("Instruction", KeyEvent.VK_I);

    private GraphPanel panel = new GraphPanel();


    public GraphEditor() {
        super(APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setContentPane(panel);
        createMenu();
        showBuildinExample();
        setVisible(true);
    }

    private void showListOfNodes(Graph graph) {
        Node array[] = graph.getNodes();
        int i = 0;
        StringBuilder message = new StringBuilder("Liczba wêz³ów: " + array.length + "\n");
        for (Node node : array) {
            message.append(node + "    ");
            if (++i % 5 == 0)
                message.append("\n");
        }
        JOptionPane.showMessageDialog(this, message, APP_TITLE + " - Lista wêz³ów", JOptionPane.PLAIN_MESSAGE);
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
            showBuildinExample();
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
                panel.getGraph().setNodes(loadGroupListFromFile(LIST_OF_NODES));
            } catch (NodeException e) {
                e.printStackTrace();
            }
            repaint();
        }

        if (source == menuSaveToDocument) {
            try {
                saveGroupListToFile(LIST_OF_NODES);
            } catch (NodeException e) {
                e.printStackTrace();
            }
        }
    }

    private void showBuildinExample() {
        Graph graph = new Graph();

        Node n1 = new Node(100, 100);
        Node n2 = new Node(100, 200);
        n2.setColor(Color.CYAN);
        Node n3 = new Node(200, 100);
        n3.setR(20);
        Node n4 = new Node(200, 250);
        n4.setColor(Color.GREEN);
        n4.setR(30);

        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        panel.setGraph(graph);
    }

    void saveGroupListToFile(String fileName) throws NodeException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(panel.getGraph().getNodesList());
        } catch (FileNotFoundException e) {
            throw new NodeException("File wasn't found: " + fileName);
        } catch (IOException e) {
            throw new NodeException("Error");
        }

    }

    public static List<Node> loadGroupListFromFile(String fileName) throws NodeException {
        List<Node> listOfNodes = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            listOfNodes = (List<Node>) in.readObject();
        } catch (FileNotFoundException e) {
            throw new NodeException("File wasn't found: " + fileName);
        } catch (IOException e) {
            throw new NodeException("Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listOfNodes;
    }
    }



