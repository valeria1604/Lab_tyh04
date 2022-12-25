/*
 *  Program: Prosty edytor grafu
 *     Plik: GraphPanel.java
 *
 *  Klasa GraphPanel implementuje podstawowe funkcjonalno�ci
 *  graficznego edytora grafu nieskierowanego.
 *  Klasa umo�liwia:
 *     - rysowanie grafu w oknie,
 *     - obs�ug� zdarze� generowanych przez myszk�,
 *     - tworzenie i obs�ug� menu kontekstowych
 *       umo�liwiaj�cych wykonywanie operacji edycyjnych.
 *
 *    Autor: Pawel Rogalinski
 *     Data:  listopad 2021 r.
 */

package com.company.gui;

import com.company.data.GraphForNodes;
import com.company.data.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class GraphPanel extends JPanel
        implements MouseListener, MouseMotionListener, KeyListener {

    private static final long serialVersionUID = 1L;

    protected GraphForNodes graphForNodes;


    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mouseButtonLeft = false;
    @SuppressWarnings("unused")
    private boolean mouseButtonRigth = false;
    protected int mouseCursor = Cursor.DEFAULT_CURSOR;

    protected Node nodeUnderCursor = null;

    private ArrayList<Node> toConnect = new ArrayList<>();
    private ArrayList<Node> toDelete = new ArrayList<>();


    public GraphPanel() {
        this.addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }

    public GraphForNodes getGraph() {
        return graphForNodes;
    }

    public void setGraph(GraphForNodes graphForNodes) {
        this.graphForNodes = graphForNodes;
    }


    private Node findNode(int mx, int my) {
        for (Node node : graphForNodes.getNodes()) {
            if (node.isMouseOver(mx, my)) {
                return node;
            }
        }
        return null;
    }

    private Node findNode(MouseEvent event) {
        return findNode(event.getX(), event.getY());
    }

    protected void setMouseCursor(MouseEvent event) {
        nodeUnderCursor = findNode(event);
        if (nodeUnderCursor != null) {
            mouseCursor = Cursor.HAND_CURSOR;
        } else if (mouseButtonLeft) {
            mouseCursor = Cursor.MOVE_CURSOR;
        } else {
            mouseCursor = Cursor.DEFAULT_CURSOR;
        }
        setCursor(Cursor.getPredefinedCursor(mouseCursor));
        mouseX = event.getX();
        mouseY = event.getY();
    }

    protected void setMouseCursor() {
        nodeUnderCursor = findNode(mouseX, mouseY);
        if (nodeUnderCursor != null) {
            mouseCursor = Cursor.HAND_CURSOR;
        } else if (mouseButtonLeft) {
            mouseCursor = Cursor.MOVE_CURSOR;
        } else {
            mouseCursor = Cursor.DEFAULT_CURSOR;
        }
        setCursor(Cursor.getPredefinedCursor(mouseCursor));
    }


    private void moveNode(int dx, int dy, Node node) {
        node.setX(node.getX() + dx);
        node.setY(node.getY() + dy);
    }

    // Przenie�� do klasy GraphForNodes
    private void moveAllNodes(int dx, int dy) {
        for (Node node : graphForNodes.getNodes()) {
            moveNode(dx, dy, node);
        }
    }

    /*
     * UWAGA: ta metoda b�dzie wywo�ywana automatycznie przy ka�dej potrzebie
     * odrysowania na ekranie zawarto�ci panelu
     *
     * W tej metodzie NIE WOLNO !!! wywo�ywa� metody repaint()
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graphForNodes == null) return;
        graphForNodes.draw(g);

    }


    /*
     * Implementacja interfejsu MouseListener - obs�uga zdarze� generowanych przez myszk�
     * gdy kursor myszki jest na tym panelu
     */
    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == 1) mouseButtonLeft = true;
        if (event.getButton() == 3) mouseButtonRigth = true;
        setMouseCursor(event);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == 1)
            mouseButtonLeft = false;
        if (event.getButton() == 3)
            mouseButtonRigth = false;
        setMouseCursor(event);
        if (event.getButton() == 3) {
            if (nodeUnderCursor != null) {
                createPopupMenu(event, nodeUnderCursor);
            } else {
                createPopupMenu(event);
            }
        }
    }


    /*
     * Implementacja interfejsu MouseMotionListener
     *  - obs�uga zdarze� generowanych przez ruch myszki
     * gdy kursor myszki jest na tym panelu
     */
    @Override
    public void mouseDragged(MouseEvent event) {
        if (mouseButtonLeft) {
            if (nodeUnderCursor != null) {
                moveNode(event.getX() - mouseX, event.getY() - mouseY, nodeUnderCursor);
            } else {
                moveAllNodes(event.getX() - mouseX, event.getY() - mouseY);
            }
        }
        mouseX = event.getX();
        mouseY = event.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        setMouseCursor(event);
    }


    /*
     *  Impelentacja interfejsu KeyListener - obs�uga zdarze� generowanych
     *  przez klawiatur� gdy focus jest ustawiony na ten obiekt.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        {
            int dist;
            if (event.isShiftDown()) dist = 10;
            else dist = 1;
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveAllNodes(-dist, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    moveAllNodes(dist, 0);
                    break;
                case KeyEvent.VK_UP:
                    moveAllNodes(0, -dist);
                    break;
                case KeyEvent.VK_DOWN:
                    moveAllNodes(0, dist);
                    break;
                case KeyEvent.VK_DELETE:
                    if (nodeUnderCursor != null) {
                        graphForNodes.removeNode(nodeUnderCursor);
                        nodeUnderCursor = null;
                    }
                    break;
            }
        }
        repaint();
        setMouseCursor();
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    @Override
    public void keyTyped(KeyEvent event) {
        char znak = event.getKeyChar();
        if (nodeUnderCursor != null) {
            switch (znak) {
                case 'r':
                    nodeUnderCursor.setColor(Color.RED);
                    break;
                case 'g':
                    nodeUnderCursor.setColor(Color.GREEN);
                    break;
                case 'b':
                    nodeUnderCursor.setColor(Color.BLUE);
                    break;
                case '+':
                    int r = nodeUnderCursor.getR() + 10;
                    nodeUnderCursor.setR(r);
                    break;
                case '-':
                    r = nodeUnderCursor.getR() - 10;
                    if (r >= 10) nodeUnderCursor.setR(r);
                    break;
            }
            repaint();
            setMouseCursor();
        }
    }


    /*
     *  Tworzenie i obs�uga menu kontekstowego uruchanianego
     *  poprzez klikni�cie prawym przyciskiem myszki
     */
    protected void createPopupMenu(MouseEvent event) {
        JMenuItem menuItem;

        //Create the popup menu.
        JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem("Create new node");

        // Implementacja s�uchacza zdarze� za pomoc� wyra�enia Lambda  
        menuItem.addActionListener((action) -> {
            graphForNodes.addNode(new Node(event.getX(), event.getY()));

            repaint();
        });
		/*
		// Implementacja s�uchacza zdarze� za pomoc� klasy anonimowej 
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent action) {
				graphForNodes.addNode(new Node(event.getX(), event.getY()));
				repaint();
			}
		});
		*/
        popup.add(menuItem);
        popup.show(event.getComponent(), event.getX(), event.getY());
    }


    protected void createPopupMenu(MouseEvent event, Node node) {
        JMenuItem menuItem;
        JMenuItem menuCreateLine;
        JMenuItem menuCreateLineWith;
        JMenuItem menuDeleteLine;
        JMenuItem menuDeleteLineWith;

        // Create the popup menu.
        JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem("Change node color");
        menuCreateLine = new JMenuItem("Create edge (first node)");
        menuCreateLineWith = new JMenuItem("Create edge with (second node)");
        menuDeleteLine = new JMenuItem("Delete edge (first node)");
        menuDeleteLineWith = new JMenuItem("Delete edge with (second node)");


        menuCreateLine.addActionListener((a) -> {
            toConnect.add(nodeUnderCursor);
        });


        menuCreateLineWith.addActionListener((a) -> {
            toConnect.add(nodeUnderCursor);
            if (toConnect.size()>1) {
				int size = toConnect.size();
                node.getLinkedNodes().add(toConnect.get(size - 2));
				repaint();
				toConnect.clear();
            }
			else{
				JOptionPane.showMessageDialog(null, "You need to choose the first node before you connect");
			}
        });

        menuDeleteLine.addActionListener((a) -> {
            toDelete.add(nodeUnderCursor);
        });

        menuDeleteLineWith.addActionListener((a) -> {
            toDelete.add(nodeUnderCursor);
            if (toDelete.size()>1) {
                int size = toDelete.size();
                node.getLinkedNodes().remove(toDelete.get(size - 2));
                node.getLinkedNodes().remove(toDelete.get(size - 1));
                repaint();
                toDelete.clear();
            }
            else{
                JOptionPane.showMessageDialog(null, "You need to choose the first node before you connect");
            }
        });



        // Implementacja s�uchacza zdarze� za pomoc� wyra�enia Lambda
        menuItem.addActionListener((a) -> {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Background Color",
                    node.getColor());
            if (newColor != null) {
                node.setColor(newColor);
            }
            repaint();
        });
		

        popup.add(menuItem);
        popup.add(menuCreateLine);
        popup.add(menuCreateLineWith);
        popup.add(menuDeleteLine);
        popup.add(menuDeleteLineWith);
        menuItem = new JMenuItem("Remove this node");

        // Implementacja s�uchacza zdarze� za pomoc� wyra�enia Lambda
        menuItem.addActionListener((action) -> {
            graphForNodes.removeNode(node);
            repaint();
        });
		
		/*
		// Implementacja s�uchacza zdarze� za pomoc� klasy anonimowej
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent action) {
				graphForNodes.removeNode(node);
				repaint();
			}
		};
		*/
        popup.add(menuItem);
        popup.show(event.getComponent(), event.getX(), event.getY());
    }

}
