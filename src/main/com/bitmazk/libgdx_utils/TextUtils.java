package com.bitmazk.libgdx_utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.bitmazk.libgdx_utils.CommonGame;


public class TextUtils {
    public void draw_text_center(CommonGame game, CharSequence text,
                                 int height) {
        TextBounds bounds = game.font.getBounds(text);
        float center = game.width / 2 - bounds.width / 2;
        game.font.draw(game.batch, text, center, height);
    }
}
