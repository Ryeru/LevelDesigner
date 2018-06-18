package sample;

class BoardInfo {

    static int horizontalCells = 16;
    static int verticalCells = 12;

    static int getSlotWidth() {
        int width = 800;
        return width / horizontalCells;
    }

    static int getSlotHeight() {
        int height = 600;
        return height / verticalCells;
    }

    static int getX(double x) {
        return (int) (x / getSlotWidth());
    }

    static int getY(double y) {
        return (int) (y / getSlotHeight());
    }
}
