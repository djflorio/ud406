package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Platform {

    // TODO: Add members for the platform top, bottom, left edge, right edge, width, and height
    private float top;
    private float bottom;
    private float left;
    private float right;
    private float width;
    private float height;

    public Platform(float left, float top, float width, float height) {
        // TODO: Populate the member variables
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.right = left + width;
        this.bottom = top - height;

    }

    public void render(ShapeRenderer renderer) {

        // TODO: Draw a box representing the platform
        renderer.rect(left, bottom, width, height);
    }

}
