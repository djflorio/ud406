package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

enum JumpState {
    JUMPING,
    FALLING,
    GROUNDED
}

enum Facing {
    LEFT,
    RIGHT
}

enum WalkState {
    STANDING,
    WALKING
}

public class GigaGal {

    public final static String TAG = GigaGal.class.getName();

    Vector2 position;

    // TODO: Add Vector2 to hold GigaGal's position last frame
    private Vector2 lastPosition;

    Vector2 velocity;

    Facing facing;
    JumpState jumpState;
    WalkState walkState;

    long walkStartTime;
    long jumpStartTime;

    public GigaGal() {
        position = new Vector2(20, 20);

        // TODO: Initialize a new Vector2 for lastFramePosition
        lastPosition = new Vector2(position);

        velocity = new Vector2();
        jumpState = JumpState.FALLING;
        facing = Facing.RIGHT;
        walkState = WalkState.STANDING;
    }

    // Note that we're now passing in the platform array to GigaGal's update method
    public void update(float delta, Array<Platform> platforms) {
        // TODO: Update lastFramePosition
        // You'll want to use Vector2.set()
        lastPosition.set(position);

        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        if (jumpState != JumpState.JUMPING) {
            jumpState = JumpState.FALLING;

            if (position.y - Constants.GIGAGAL_EYE_HEIGHT < 0) {
                jumpState = JumpState.GROUNDED;
                position.y = Constants.GIGAGAL_EYE_HEIGHT;
                velocity.y = 0;
            }

            // TODO: For each platform, call landedOnPlatform()
            for (Platform platform: platforms) {
                if (landedOnPlatform(platform)) {
                    // TODO: If true, set jumpState to GROUNDED
                    // TODO: Zero vertical velocity
                    // TODO: Make sure GigaGal's feet aren't sticking into the platform
                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = platform.top + Constants.GIGAGAL_EYE_HEIGHT;
                }

            }

        }

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            moveLeft(delta);
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            moveRight(delta);
        } else {
            walkState = WalkState.STANDING;
        }

        if (Gdx.input.isKeyPressed(Keys.Z)) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
            }
        } else {
            endJump();
        }
    }


    boolean landedOnPlatform(Platform platform) {

        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        // TODO: First check if GigaGal's feet were above the platform top last frame and below the platform top this frame
        if (lastPosition.y - Constants.GIGAGAL_EYE_HEIGHT >= platform.top &&
                position.y - Constants.GIGAGAL_EYE_HEIGHT < platform.top) {

            // TODO: If so, find the position of GigaGal's left and right toes
            float leftToe = position.x - (Constants.GIGAGAL_STANCE_WIDTH / 2);
            float rightToe = position.x + (Constants.GIGAGAL_STANCE_WIDTH / 2);

            // TODO: See if either of GigaGal's toes are on the platform
            leftFootIn = (platform.left < leftToe && platform.right > leftToe);
            rightFootIn = (platform.left < rightToe && platform.right > rightToe);
            straddle = (platform.left > leftToe && platform.right < rightToe);

            // TODO: See if GigaGal is straddling the platform

            // TODO: Return whether or not GigaGal had landed on the platform
        }
        return leftFootIn || rightFootIn || straddle;
    }


    private void moveLeft(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = WalkState.WALKING;
        facing = Facing.LEFT;
        position.x -= delta * Constants.GIGAGAL_MOVE_SPEED;
    }


    private void moveRight(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = WalkState.WALKING;
        facing = Facing.RIGHT;
        position.x += delta * Constants.GIGAGAL_MOVE_SPEED;
    }


    private void startJump() {
        jumpState = JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            if (jumpDuration < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.gigaGalAssets.standingRight;

        if (facing == Facing.RIGHT && jumpState != JumpState.GROUNDED) {
            region = Assets.instance.gigaGalAssets.jumpingRight;
        } else if (facing == Facing.RIGHT && walkState == WalkState.STANDING) {
            region = Assets.instance.gigaGalAssets.standingRight;
        } else if (facing == Facing.RIGHT && walkState == WalkState.WALKING) {
            float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
            region = Assets.instance.gigaGalAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        } else if (facing == Facing.LEFT && jumpState != JumpState.GROUNDED) {
            region = Assets.instance.gigaGalAssets.jumpingLeft;
        } else if (facing == Facing.LEFT && walkState == WalkState.STANDING) {
            region = Assets.instance.gigaGalAssets.standingLeft;
        } else if (facing == Facing.LEFT && walkState == WalkState.WALKING) {
            float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
            region = Assets.instance.gigaGalAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
        }

        batch.draw(
                region.getTexture(),
                position.x - Constants.GIGAGAL_EYE_POSITION.x,
                position.y - Constants.GIGAGAL_EYE_POSITION.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }
}
