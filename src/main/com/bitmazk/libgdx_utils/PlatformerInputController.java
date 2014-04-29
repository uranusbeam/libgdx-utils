package com.bitmazk.libgdx_utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Processes user input and renders HUD with controller buttons on the screen.
 *
 * This is optimized for typical platformer games where you would hold your
 * mobile device like a gamepad. You can move left/right with your left thumb
 * and you can jump and shoot with your right thumb.
 * <p>
 * The buttons are invisible rectangles that take the whole height of the
 * screen and a certain width, this allows for quite precise and forgiving
 * gameplay - you don't really have to press the actual button image (which is
 * tiny ayways), you just have to press anywhere above the button.
 * <p>
 * TODO: This is a work in progress. One or two action buttons still need to
 * be implemented and special functionality like long jump and double jump
 * and sprinting (pressing left or right twice). Assets should probably be
 * configurable via constructor as well.
 */
public class PlatformerInputController {
    final int width;
    final int height;
    final OrthographicCamera camera;

    Rectangle btnLeft;
    Texture btnLeftImage;

    Rectangle btnRight;
    Texture btnRightImage;

    Rectangle btnUp;
    Texture btnUpImage;

    public boolean isLeftPressed;
    public boolean isRightPressed;
    public boolean isUpPressed;
    public boolean isAction1Pressed;

    /**
     * Constructor takes the game width, height and camera.
     *
     * @param width     Width of the screen. Needed to place the button
     *                  rectangles on the screen
     * @param height    Height of the screen. Needed to make the button
     *                  rectangles as high as the screen is
     * @param camera    Needed to unproject touch events into the camera's
     *                  coordinate system.
     */
    public PlatformerInputController(int width,
                                     int height,
                                     OrthographicCamera camera) {
        // We will set these so that we can access them in the other methods
        this.width = width;
        this.height = height;
        this.camera = camera;

        // Let's load the images. Make sure you have assets with these names
        this.btnLeftImage = new Texture(
            Gdx.files.internal("btn_arrow_left.png"));
        this.btnRightImage = new Texture(
            Gdx.files.internal("btn_arrow_right.png"));
        this.btnUpImage = new Texture(
            Gdx.files.internal("btn_arrow_up.png"));

        // Creating the Rectangles for the invisible onscreen buttons
        this.btnLeft = new Rectangle();
        this.btnLeft.width = 100;
        this.btnLeft.height = height;
        this.btnLeft.x = 0;
        this.btnLeft.y = 0;

        this.btnRight = new Rectangle();
        this.btnRight.width = 100;
        this.btnRight.height = height;
        this.btnRight.x = this.btnLeft.width;
        this.btnRight.y = 0;

        this.btnUp = new Rectangle();
        this.btnUp.width = 100;
        this.btnUp.height = height;
        this.btnUp.x = width - this.btnUp.width;
        this.btnUp.y = 0;
    }

    /**
     * Processes the user input and saves the states into class attributes.
     *
     * Other parts of your game can then simply ask for
     * `controller.isLeftPressed` and then manipulate the world or player
     * object accordingly.
     *
     * This abstracts away the complexity of dealing with different input
     * devices. Your game engine doesn't want to care if the input was made
     * with keyboard, mouse or touch. It just wants to know if some kind of
     * left button was pressed.
     */
    public void processInput() {
        this.resetInputs();
        if (Gdx.input.isTouched(0)) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(0), Gdx.input.getY(0), 0);
            this.camera.unproject(touchPos);
            if (this.btnLeft.contains(touchPos.x, touchPos.y))
                this.isLeftPressed = true;
            if (this.btnRight.contains(touchPos.x, touchPos.y))
                this.isRightPressed = true;
            if (this.btnUp.contains(touchPos.x, touchPos.y))
                this.isUpPressed = true;
        }
        if (Gdx.input.isTouched(1)) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(1), Gdx.input.getY(1), 0);
            this.camera.unproject(touchPos);
            if (this.btnLeft.contains(touchPos.x, touchPos.y))
                this.isLeftPressed = true;
            if (this.btnRight.contains(touchPos.x, touchPos.y))
                this.isRightPressed = true;
            if (this.btnUp.contains(touchPos.x, touchPos.y))
                this.isUpPressed = true;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.isLeftPressed = true;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.isRightPressed = true;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            isUpPressed = true;
        }
    }

    /**
     * Helper method for internal use, resets inputs captured during last loop.
     */
    public void resetInputs() {
        this.isAction1Pressed = false;
        this.isLeftPressed = false;
        this.isRightPressed = false;
        this.isUpPressed = false;
    }

    /**
     * Renders the HUD that makes up this controller.
     *
     * Useful for mobile devices in order to give the user a hint where they
     * should press in order to navigate.
     *
     * TODO: Should probably be hidden on desktop devices.
     *
     * @param batch     SpriteBatch instance that takes care of rendering your
     *                  stuff.
     */
    public void render(SpriteBatch batch) {
        int heightFromGround = 10;

        // The invisible button rectangle is 100 pixels wide but the tiny image
        // is much smaller. We want the image to be centered in those 100
        // pixels.
        float btnLeftXPosition = (
            this.btnLeft.width - this.btnLeftImage.getWidth()) / 2;
        batch.draw(this.btnLeftImage, btnLeftXPosition, heightFromGround);

        batch.draw(
            this.btnRightImage, btnLeftXPosition + this.btnLeft.width,
            heightFromGround);

        float btnUpPosition = (
            this.width - btnLeftXPosition - this.btnUpImage.getWidth());
        batch.draw(this.btnUpImage, btnUpPosition, heightFromGround);
    }

    /**
     * Disposes all disposable objects (for example button images).
     *
     * Make sure to call this in the `dispose()` method of your Screen
     * instance.
     */
    public void dispose() {
        this.btnLeftImage.dispose();
        this.btnRightImage.dispose();
        this.btnUpImage.dispose();
    }
}
