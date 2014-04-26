package com.bitmazk.libgdx_utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Abstract base class with commonly used attributes and methods.
 * <p>
 * When I wrote my first game it occurred to me that I would likely need a
 * SpriteBatch and at least one font for every game that I will ever write.
 * <p>
 * I also saw that each screen will need to know about it's width and height
 * and I thought it would be a good idea to centralize that information on the
 * game object.
 */
public abstract class CommonGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public int width = 800;
    public int height = 480;

    /**
     * Default implementation of the abstract method defined in `Game`.
     * <p>
     * You will likely want to override this and do more stuff. Make sure to
     * call `super` in that case.
     */
    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
    }
}
