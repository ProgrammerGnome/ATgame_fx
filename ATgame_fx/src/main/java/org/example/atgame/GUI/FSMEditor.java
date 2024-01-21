package org.example.atgame.GUI;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FSMEditor {

    private enum Mode {
        STATE, TRANSITION, DELETE, MOVE, INIT, FINAL
    }

    private Map<String, Boolean> selectedStates = new HashMap<>();
    private Map<String, Boolean> selectedStates2 = new HashMap<>();

    private Mode currentMode = Mode.STATE;
    private Map<String, javafx.geometry.Point2D> statePositions;
    private ArrayList<Transition> transitions;
    private String movingState;


    @FXML
    private Canvas canvas;

    public FSMEditor() {
        statePositions = new HashMap<>();
        transitions = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        // Az inicializáló kód itt helyezhető el, ha szükséges
    }

    @FXML
    private void stateButtonClicked() {
        currentMode = Mode.STATE;
    }

    @FXML
    private void initButtonClicked() {
        currentMode = Mode.INIT;
    }

    @FXML
    private void finalButtonClicked() {
        currentMode = Mode.FINAL;
    }

    @FXML
    private void transitionButtonClicked() {
        currentMode = Mode.TRANSITION;
    }

    @FXML
    private void deleteButtonClicked() {
        currentMode = Mode.DELETE;
    }

    @FXML
    private void moveButtonClicked() {
        currentMode = Mode.MOVE;
    }

    @FXML
    private void canvasMouseClicked(MouseEvent event) {
        if (currentMode == Mode.STATE || currentMode == Mode.TRANSITION) {
            handleCanvasClick(event.getX(), event.getY());
            drawStates();
            drawTransitions();
        }
        if (currentMode == Mode.FINAL) {
            drawFinalStates(event.getX(), event.getY());
        }
        if (currentMode == Mode.INIT) {
            drawInitStates(event.getX(), event.getY());
        }
        if (currentMode == Mode.DELETE) {
            handleDeleteClick(event.getX(), event.getY());
        }
    }

    @FXML
    private void canvasMousePressed(MouseEvent event) {
        if (currentMode == Mode.MOVE) {
            movingState = findStateAtPoint(event.getX(), event.getY());
        }
    }

    @FXML
    private void canvasMouseReleased(MouseEvent event) {
        if (currentMode == Mode.MOVE) {
            movingState = null;
        }
    }

    @FXML
    private void canvasMouseDragged(MouseEvent event) {
        if (currentMode == Mode.MOVE && movingState != null) {
            moveState(movingState, event.getX(), event.getY());
            drawStates();
            drawTransitions();
        }
    }

    @FXML
    private void exportFSM() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export FSM");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        java.io.File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("TRANSITIONS LIST: [");

                for (int i = 0; i < transitions.size(); i++) {
                    Transition transition = transitions.get(i);

                    writer.write(transition.getStartState() + " - " + transition.getTransitionChar() + " - " + transition.getEndState());

                    if (i < transitions.size() - 1) {
                        writer.write(", ");
                    }
                }

                writer.write("]");

                // Új rész a kiskörös állapotok exportálásához
                var startList = new ArrayList<>(selectedStates2.keySet());
                writer.write("\nINITIAL STATE: " + startList);

                // Új rész a kiskörös állapotok exportálásához
                var finalList = new ArrayList<>(selectedStates.keySet());
                writer.write("\nFINAL STATE(S): " + finalList +"\n");

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error exporting FSM", "Error");
            }
        }
    }


    private void showAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void drawStates() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Map.Entry<String, javafx.geometry.Point2D> entry : statePositions.entrySet()) {
            String state = entry.getKey();
            javafx.geometry.Point2D position = entry.getValue();

            gc.setFill(Color.WHITE);
            gc.fillOval(position.getX(), position.getY(), 50, 50);
            gc.setStroke(Color.BLACK);
            gc.strokeOval(position.getX(), position.getY(), 50, 50);

            // Rajzoljuk a kis kört, ha az állapot kijelölt
            if (selectedStates.containsKey(state) && selectedStates.get(state)) {
                gc.strokeOval(position.getX() + 10, position.getY() + 10, 30, 30);
                gc.setStroke(Color.BLACK);
            }
            if (selectedStates2.containsKey(state) && selectedStates2.get(state)) {
                gc.strokePolygon(new double[]{position.getX() + 0, position.getX() - 10, position.getX() - 10},
                        new double[]{position.getY() + 25, position.getY() + 35, position.getY() + 15}, 3);
                gc.setStroke(Color.BLACK);
            }

            gc.setFill(Color.BLACK);
            gc.fillText(state, position.getX() + 20, position.getY() + 30);
        }

        // Rajzoljuk újra a nyilakat
        drawTransitions();
    }

    private void drawInitStates(double mouseX, double mouseY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Keressük meg azt az állapotot, amelyre kattintottak
        for (Map.Entry<String, javafx.geometry.Point2D> entry : statePositions.entrySet()) {
            String state = entry.getKey();
            javafx.geometry.Point2D position = entry.getValue();

            // Ellenőrizzük, hogy a kattintás az adott állapot körlapjában van-e
            if (isPointInState(mouseX, mouseY, state, position)) {
                // Jelöljük az állapotot kijelöltként
                selectedStates2.put(state, true);
                break;
            }
        }

        // Rajzoljuk újra az állapotokat és a nyilakat
        drawStates();
        drawTransitions();
    }

    private void drawFinalStates(double mouseX, double mouseY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Keressük meg azt az állapotot, amelyre kattintottak
        for (Map.Entry<String, javafx.geometry.Point2D> entry : statePositions.entrySet()) {
            String state = entry.getKey();
            javafx.geometry.Point2D position = entry.getValue();

            // Ellenőrizzük, hogy a kattintás az adott állapot körlapjában van-e
            if (isPointInState(mouseX, mouseY, state, position)) {
                // Jelöljük az állapotot kijelöltként
                selectedStates.put(state, true);
                break;
            }
        }

        // Rajzoljuk újra az állapotokat és a nyilakat
        drawStates();
        drawTransitions();
    }


    private void drawTransitions() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Transition transition : transitions) {
            javafx.geometry.Point2D start = statePositions.get(transition.getStartState());
            javafx.geometry.Point2D end = statePositions.get(transition.getEndState());

            if (start != null && end != null) {
                gc.setStroke(Color.BLUE);
                drawArrowLine(gc, start.getX() + 50, start.getY() + 25, end.getX() + 0, end.getY() + 25, 10);
                gc.setFill(Color.BLACK);
                gc.fillText(transition.getTransitionChar(), (start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
            }
        }
    }

    private void drawArrowLine(GraphicsContext gc, double x1, double y1, double x2, double y2, double arrowSize) {
        gc.strokeLine(x1, y1, x2, y2);

        double angle = Math.atan2(y2 - y1, x2 - x1);
        double x3 = x2 - arrowSize * Math.cos(angle - Math.PI / 6);
        double y3 = y2 - arrowSize * Math.sin(angle - Math.PI / 6);
        double x4 = x2 - arrowSize * Math.cos(angle + Math.PI / 6);
        double y4 = y2 - arrowSize * Math.sin(angle + Math.PI / 6);

        gc.strokeLine(x2, y2, x3, y3);
        gc.strokeLine(x2, y2, x4, y4);
    }

    private void handleCanvasClick(double x, double y) {
        switch (currentMode) {
            case STATE:
                String stateName = showInputDialog("Enter state name:");
                statePositions.put(stateName, new javafx.geometry.Point2D(x, y));
                break;
            case TRANSITION:
                String startState = showInputDialog("Enter start state:");
                String endState = showInputDialog("Enter end state:");
                String transitionChar = showInputDialog("Enter transition character:");

                Transition transition = new Transition(startState, endState, transitionChar);
                transitions.add(transition);
                break;
            case DELETE:
                handleDeleteClick(x, y);
                break;
        }
    }

    private String showInputDialog(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        return dialog.showAndWait().orElse(null);
    }

    private void handleDeleteClick(double x, double y) {
        System.out.println("törlés...");

        // Példa állapot törlésre:
        String stateToRemove = findStateAtPoint(x, y);
        if (stateToRemove != null) {
            statePositions.remove(stateToRemove);
            // További logika, például a kapcsolódó nyilak törlése
            removeTransitionsRelatedToState(stateToRemove);
        }

        // Példa nyíl törlésre:
        Iterator<Transition> iterator = transitions.iterator();
        while (iterator.hasNext()) {
            Transition transition = iterator.next();
            javafx.geometry.Point2D start = statePositions.get(transition.getStartState());
            javafx.geometry.Point2D end = statePositions.get(transition.getEndState());

            if (isPointOnArrow(x, y, start.getX() + 50, start.getY() + 25, end.getX() + 0, end.getY() + 25)) {
                iterator.remove(); // Az iterator segítségével biztonságosan töröljük az elemet
            }
        }

        // Rajzoljuk újra az állapotokat és a nyilakat
        drawStates();
        drawTransitions();
    }

    private void removeTransitionsRelatedToState(String stateToRemove) {
        Iterator<Transition> iterator = transitions.iterator();
        while (iterator.hasNext()) {
            Transition transition = iterator.next();
            if (transition.getStartState().equals(stateToRemove) || transition.getEndState().equals(stateToRemove)) {
                iterator.remove();
            }
        }
    }


    private String findStateAtPoint(double x, double y) {
        for (Map.Entry<String, javafx.geometry.Point2D> entry : statePositions.entrySet()) {
            if (isPointInState(x, y, entry.getKey(), entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void moveState(String state, double newX, double newY) {
        statePositions.put(state, new javafx.geometry.Point2D(newX, newY));
    }

    private boolean isPointInState(double x, double y, String state, javafx.geometry.Point2D statePosition) {
        double distance = Math.sqrt(Math.pow(x - statePosition.getX() - 25, 2) + Math.pow(y - statePosition.getY() - 25, 2));
        return distance <= 25;
    }

    private boolean isPointOnArrow(double x, double y, double x1, double y1, double x2, double y2) {
        double distance = Math.abs((x2 - x1) * (y1 - y) - (x1 - x) * (y2 - y1)) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distance <= 5;
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
