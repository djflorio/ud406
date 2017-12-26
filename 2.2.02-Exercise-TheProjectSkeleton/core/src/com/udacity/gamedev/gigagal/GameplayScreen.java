package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by dflo on 12/24/17.
 */

public class GameplayScreen extends ScreenAdapter {

    FitViewport viewport;
    SpriteBatch batch;

    public GameplayScreen() {

        this.viewport = new FitViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        this.batch = new SpriteBatch();

    }

}
