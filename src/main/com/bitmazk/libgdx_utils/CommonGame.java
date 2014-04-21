package com.bitmazk.libgdx_utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class CommonGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public int width = 800;
    public int height = 480;

    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
    }
}
