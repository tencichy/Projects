package com.damiskot;

import javafx.scene.shape.Rectangle;

public class Food{

    private Rectangle rectangle;
    private int type;

    public Food(Rectangle rectangle, int type) {
        this.rectangle = rectangle;
        this.type = type;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getType() {
        return type;
    }
}
