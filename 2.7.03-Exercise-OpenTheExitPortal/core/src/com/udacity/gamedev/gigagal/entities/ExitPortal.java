package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;
import com.udacity.gamedev.gigagal.util.Utils;

public class ExitPortal {

    public final static String TAG = ExitPortal.class.getName();

    // TODO: Add a position
    private Vector2 position;

    // TODO: Add a long called startTime
    private long startTime;

    public ExitPortal(Vector2 position) {

        // TODO: Set the position
        this.position = position;

        // TODO: Set the startTime using TimeUtils.nanoTime()
        this.startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {
        // TODO: Get the time elapsed since startTime
        // Use Utils.secondsSince()
        float elapsedTime = Utils.secondsSince(startTime);

        // TODO: Get the right texture region from the exitPortalAssets
        TextureRegion region = Assets.instance.exitPortalAssets.animation.getKeyFrame(elapsedTime);

        // TODO: Draw that texture region
        Utils.drawTextureRegion(batch, region, position, Constants.EXIT_PORTAL_CENTER);
    }
}

