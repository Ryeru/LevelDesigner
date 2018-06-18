package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static sample.Entity.*;

public class Controller {
    public Canvas canvas;
    private Entity[][] gameboard = new Entity[BoardInfo.horizontalCells][BoardInfo.verticalCells];
    private Entity selectedEntity = NONE;
    private int temporaryX = 0;
    private int temporaryY = 0;

    @FXML
    public void initialize() {
        for (int x = 0; x < BoardInfo.horizontalCells; x++) {
            for (int y = 0; y < BoardInfo.verticalCells; y++) {
                gameboard[x][y] = NONE;
                redrawEntity(x, y);
            }
        }
    }

    public void handleMove(MouseEvent mouseEvent) {
        clearTemporaryDraw();
        if (selectedEntity == NONE) {
            return;
        }

        int x = BoardInfo.getX(mouseEvent.getX());
        int y = BoardInfo.getY(mouseEvent.getY());

        temporaryDrawEntity(x, y);
    }

    public void handleClick(MouseEvent mouseEvent) {
        clearTemporaryDraw();
        if (selectedEntity == NONE) {
            return;
        }

        int x = BoardInfo.getX(mouseEvent.getX());
        int y = BoardInfo.getY(mouseEvent.getY());

        addEntityToBoard(x, y);
        redrawEntity(x, y);
    }

    private void clearTemporaryDraw() {
        if (temporaryX == -1 || temporaryY == -1) {
            return;
        }

        redrawEntity(temporaryX, temporaryY);
        temporaryX = -1;
        temporaryY = -1;
    }

    private void temporaryDrawEntity(int x, int y) {
        temporaryX = x;
        temporaryY = y;
        int xPos = x * BoardInfo.getSlotWidth();
        int yPos = y * BoardInfo.getSlotHeight();
        Image image = selectedEntity.getImage();

        canvas.getGraphicsContext2D().setGlobalAlpha(0.5);
        canvas.getGraphicsContext2D().clearRect(xPos + 1, yPos + 1, BoardInfo.getSlotWidth() - 1, BoardInfo.getSlotHeight() - 1);
        canvas.getGraphicsContext2D().drawImage(image, xPos, yPos, BoardInfo.getSlotWidth(), BoardInfo.getSlotHeight());
        canvas.getGraphicsContext2D().setGlobalAlpha(1);

    }

    private void addEntityToBoard(int x, int y) {
        gameboard[x][y] = selectedEntity;
        selectedEntity = NONE;
    }

    private void redrawEntity(int x, int y) {
        int xPos = x * BoardInfo.getSlotWidth();
        int yPos = y * BoardInfo.getSlotHeight();
        Image image = gameboard[x][y].getImage();

        canvas.getGraphicsContext2D().clearRect(xPos + 1, yPos + 1, BoardInfo.getSlotWidth() - 1, BoardInfo.getSlotHeight() - 1);
        canvas.getGraphicsContext2D().drawImage(image, xPos, yPos, BoardInfo.getSlotWidth(), BoardInfo.getSlotHeight());
    }

    public void wallAction(ActionEvent actionEvent) {
        selectedEntity = WALL;
    }

    public void lavaAction(ActionEvent actionEvent) {
        selectedEntity = LAVA;
    }

    public void pinwheelAction(ActionEvent actionEvent) {
        selectedEntity = PINWHEEL;
    }

    public void bridgeAction(ActionEvent actionEvent) {
        selectedEntity = BRIDGE;
    }

    public void elevatorAction(ActionEvent actionEvent) {
        selectedEntity = ELEVATOR;
    }

    public void teleporterAction(ActionEvent actionEvent) {
        selectedEntity = TELEPORTER;
    }

    public void rampAction(ActionEvent actionEvent) {
        selectedEntity = RAMP;
    }

    public void startAction(ActionEvent actionEvent) {
        selectedEntity = START;
    }

    public void finishAction(ActionEvent actionEvent) {
        selectedEntity = FINISH;
    }

    public void exportLevelAction(ActionEvent actionEvent) {
        try {
            FileWriter fw = new FileWriter("exportedLevel.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int y = 0; y < BoardInfo.verticalCells; y++) {
                for (int x = 0; x < BoardInfo.horizontalCells; x++) {
                    bw.write(gameboard[x][y].getCode());
                }
                bw.newLine();
            }
            bw.flush();
            fw.flush();

            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
