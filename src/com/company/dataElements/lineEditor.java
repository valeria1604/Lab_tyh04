package com.company.dataElements;

// Java program to draw a lineEditor in Applet

import com.company.data.Graph;
import com.company.gui.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyCanvas extends JComponent implements MouseListener, MouseMotionListener, KeyListener{

    protected Graph graph;


    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mouseButtonLeft = false;
    @SuppressWarnings("unused")
    private boolean mouseButtonRigth = false;
    protected int mouseCursor = Cursor.DEFAULT_CURSOR;

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    protected void setMouseCursor(MouseEvent event) {
        mouseCursor = Cursor.DEFAULT_CURSOR;
        setCursor(Cursor.getPredefinedCursor(mouseCursor));
        mouseX = event.getX();
        mouseY = event.getY();
    }

    public MyCanvas() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }

    protected void createPopupMenu(MouseEvent event) {
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem("Create new line");
        menuItem.addActionListener((action) -> {
            graph.addLine(new line(event.getX(), event.getY()));
        });
        popup.add(menuItem);
        popup.show(event.getComponent(), event.getX(), event.getY());

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph==null) return;
        graph.drawLine(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == 1)
            mouseButtonLeft = false;
        if (event.getButton() == 3)
            mouseButtonRigth = false;
        setMouseCursor(event);
        if (event.getButton() == 3) {
                createPopupMenu(event);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

public class lineEditor {

    public static void main(String[] a) {

        // creating object of JFrame(Window popup)
        JFrame window = new JFrame();

        // setting closing operation
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setting size of the pop window
        window.setBounds(30, 30, 400, 400);
        window.setLocationRelativeTo(null);
        // setting canvas for draw
        window.getContentPane().add(new MyCanvas());
        // set visibility
        window.setVisible(true);


    }
}

