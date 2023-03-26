package com.damiskot;

import javafx.scene.shape.Rectangle;

public class Bounds {

    private Rectangle shape;
    private boolean collision;
    private int type;

     Bounds(Rectangle shape, boolean collision) {
        this.shape = shape;
        this.collision = collision;
    }

     Bounds(Rectangle shape, int type) {
        this.shape = shape;
        this.type = type;
    }

     Rectangle getShape() {
        return shape;
    }

     boolean isCollision() {
        return collision;
    }

     int getType() {
        return type;
    }
}
