package sample;

import javafx.scene.image.Image;

public enum Entity {
    BRIDGE("bridge", "B"), WALL("wall", "#"), NONE("none", " "),
    PINWHEEL("pinwheel", "+"), ELEVATOR("elevator", "E"),
    RAMP("ramp", "R"), START("start", "S"), FINISH("finish", "F"),
    TELEPORTER("teleporter", "T"), LAVA("lava", "L");

    private final String image;
    private final String code;
    private Image cache = null;

    Entity(String image, String code) {
        this.image = image;
        this.code = code;
    }

    public Image getImage() {
        if (cache == null) {
            cache = new Image("sprites/" + this.image + ".png");
        }
        return cache;
    }

    public String getCode() {
        return code;
    }
}
