package com.udacity.gamedev.gigagal.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.udacity.gamedev.gigagal.entities.GigaGal;

public class ChaseCam {

    private Camera camera;
    private GigaGal target;

    // TODO: Add following flag
    private boolean following;

    public ChaseCam(Camera camera, GigaGal target) {
        this.camera = camera;
        this.target = target;

        // TODO: Initialize following flag
        this.following = true;
    }

    // TODO: Accept a float time delta
    public void update(float delta) {

        // TODO: Toggle the following flag when spacebar is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            following = !following;
        }

        // TODO: If following, update the camera's position to match the target
        if (following) {
            camera.position.x = target.position.x;
            camera.position.y = target.position.y;
        } else {

            // TODO: If not following, check if Gdx.input.isKeyPressed(Keys.A)
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                // TODO: If so, move the camera in the -x direction by delta * camera move speed
                camera.position.x -= delta * Constants.CHASE_CAM_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                // TODO: Move the camera right if D is pressed
                camera.position.x += delta * Constants.CHASE_CAM_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                // TODO: Move the camera up if W is pressed
                camera.position.y += delta * Constants.CHASE_CAM_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                // TODO: Move the camera down if S is pressed
                camera.position.y -= delta * Constants.CHASE_CAM_SPEED;
            }
        }

    }
}
