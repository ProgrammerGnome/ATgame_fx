package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FSMEditor extends JFrame {

    public static void driver() {
        SwingUtilities.invokeLater(() -> {
            FSMEditor fsmEditor = new FSMEditor();
            fsmEditor.setVisible(true);
        });
    }

    private enum Mode {
        STATE, TRANSITION, DELETE, MOVE
    }

    private Mode currentMode = Mode.STATE;
    private Map<String, Point> statePositions;
    private ArrayList<Transition> transitions;

    private String movingState; // Az állapot neve, amelyet mozgatunk

    public FSMEditor() {
        statePositions = new HashMap<>();
        transitions = new ArrayList<>();

        initializeUI();
    }

    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int arrowSize) {
        g.drawLine(x1, y1, x2, y2);

        double angle = Math.atan2(y2 - y1, x2 - x1);
        int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
        int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x2, y2, x4, y4);
    }

    private void initializeUI() {
        setTitle("FSM Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolBar = new JToolBar();
        JButton stateButton = new JButton("State");
        JButton transitionButton = new JButton("Transition");
        JButton deleteButton = new JButton("Delete");
        JButton moveButton = new JButton("Move");

        stateButton.addActionListener(e -> currentMode = Mode.STATE);
        transitionButton.addActionListener(e -> currentMode = Mode.TRANSITION);
        deleteButton.addActionListener(e -> currentMode = Mode.DELETE);
        moveButton.addActionListener(e -> currentMode = Mode.MOVE);

        toolBar.add(stateButton);
        toolBar.add(transitionButton);
        toolBar.add(deleteButton);
        toolBar.add(moveButton);

        add(toolBar, BorderLayout.NORTH);

        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawStates(g);
                drawTransitions(g);
            }
        };

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCanvasClick(e.getPoint());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (currentMode == Mode.MOVE) {
                    movingState = findStateAtPoint(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentMode == Mode.MOVE) {
                    movingState = null;
                }
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentMode == Mode.MOVE && movingState != null) {
                    moveState(movingState, e.getPoint());
                    repaint();
                }
            }
        });

        add(canvas, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(e -> exportFSM());
        fileMenu.add(exportItem);
    }

    private void exportFSM() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

                // Write states
                for (Map.Entry<String, Point> entry : statePositions.entrySet()) {
                    writer.write("State: " + entry.getKey() + " at (" + entry.getValue().x + ", " + entry.getValue().y + ")\n");
                }

                // Write transitions
                for (Transition transition : transitions) {
                    writer.write("Transition: " + transition.getStartState() + " -> " +
                            transition.getEndState() + " with " + transition.getTransitionChar() + "\n");
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error exporting FSM", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void drawStates(Graphics g) {
        for (Map.Entry<String, Point> entry : statePositions.entrySet()) {
            String state = entry.getKey();
            Point position = entry.getValue();

            g.setColor(Color.WHITE);
            g.fillOval(position.x, position.y, 50, 50);
            g.setColor(Color.BLACK);
            g.drawString(state, position.x + 20, position.y + 30);
        }
    }

    private void drawTransitions(Graphics g) {
        for (Transition transition : transitions) {
            Point start = statePositions.get(transition.getStartState());
            Point end = statePositions.get(transition.getEndState());

            if (start != null && end != null) {
                g.setColor(Color.BLUE);
                drawArrowLine(g, start.x + 25, start.y + 25, end.x + 25, end.y + 25, 10);
                g.drawString(transition.getTransitionChar(), (start.x + end.x) / 2, (start.y + end.y) / 2);
            }
        }
    }

    private void handleCanvasClick(Point point) {
        switch (currentMode) {
            case STATE:
                String stateName = JOptionPane.showInputDialog(this, "Enter state name:");
                statePositions.put(stateName, point);
                break;
            case TRANSITION:
                String startState = JOptionPane.showInputDialog(this, "Enter start state:");
                String endState = JOptionPane.showInputDialog(this, "Enter end state:");
                String transitionChar = JOptionPane.showInputDialog(this, "Enter transition character:");

                Transition transition = new Transition(startState, endState, transitionChar);
                transitions.add(transition);
                break;
            case DELETE:
                handleDeleteClick(point);
                break;
        }

        repaint();
    }

    private void handleDeleteClick(Point point) {
        // Töröljük az állapotot vagy a nyilat a kattintás helyén
        // implementáld a saját logikádat a törléshez
        // például, ha a kattintás egy állapoton belül van, töröld az állapotot
        // ha a kattintás egy nyílon van belül, töröld a nyilat

        // Példa állapot törlésre:
        for (Map.Entry<String, Point> entry : statePositions.entrySet()) {
            if (isPointInState(point, entry.getKey(), entry.getValue())) {
                statePositions.remove(entry.getKey());
                // További logika, például a kapcsolódó nyilak törlése
                break;
            }
        }

        // Példa nyíl törlésre:
        ArrayList<Transition> transitionsToRemove = new ArrayList<>();
        for (Transition transition : transitions) {
            Point start = statePositions.get(transition.getStartState());
            Point end = statePositions.get(transition.getEndState());

            if (isPointOnArrow(point, start.x + 25, start.y + 25, end.x + 25, end.y + 25)) {
                transitionsToRemove.add(transition);
            }
        }

        transitions.removeAll(transitionsToRemove);
    }

    private String findStateAtPoint(Point point) {
        for (Map.Entry<String, Point> entry : statePositions.entrySet()) {
            if (isPointInState(point, entry.getKey(), entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void moveState(String state, Point newLocation) {
        statePositions.put(state, newLocation);
    }

    private boolean isPointInState(Point point, String state, Point statePosition) {
        // Implementáld a saját logikádat a pont ellenőrzéséhez az állapoton belül
        // Például, nézd meg, hogy a pont a körön belül van-e
        double distance = Math.sqrt(Math.pow(point.x - statePosition.x - 25, 2) + Math.pow(point.y - statePosition.y - 25, 2));
        return distance <= 25; // A kör sugara 25 (ezt igényeid szerint módosíthatod)
    }

    private boolean isPointOnArrow(Point point, int x1, int y1, int x2, int y2) {
        // Implementáld a saját logikádat a pont ellenőrzéséhez a nyílra
        // Például, használhatod a pont és egyenes távolság ellenőrzést
        double distance = Math.abs((x2 - x1) * (y1 - point.y) - (x1 - point.x) * (y2 - y1)) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        return distance <= 5; // Példa távolság 5 pixel (ezt igényeid szerint módosíthatod)
    }

    private static class Transition {
        private String startState;
        private String endState;
        private String transitionChar;

        public Transition(String startState, String endState, String transitionChar) {
            this.startState = startState;
            this.endState = endState;
            this.transitionChar = transitionChar;
        }

        public String getStartState() {
            return startState;
        }

        public String getEndState() {
            return endState;
        }

        public String getTransitionChar() {
            return transitionChar;
        }
    }
}
