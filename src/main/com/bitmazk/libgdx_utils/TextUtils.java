package com.bitmazk.libgdx_utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.bitmazk.libgdx_utils.CommonGame;


/**
 * Collection of methods that simplify writing text on the screen.
 */
public class TextUtils {
    final CommonGame game;

    /**
     * Instantiates this helper class.
     *
     * @param game  The `CommonGame` instance
     */
    public TextUtils(CommonGame game) {
        this.game = game;
    }

    /**
     * Renders text centered horizontically.
     *
     * @param text      The string that you want to render
     * @param height    The distance from the top of the screen where the text
     *                  should be rendered
     */
    public void draw_text_center(CharSequence text, int height) {
        TextBounds bounds = this.game.font.getBounds(text);
        float center = this.game.width / 2 - bounds.width / 2;
        game.font.draw(this.game.batch, text, center, height);
    }
}
