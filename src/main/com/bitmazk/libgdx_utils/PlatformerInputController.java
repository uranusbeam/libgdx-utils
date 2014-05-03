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
 * Usage:
 * <ul>
 *  <li>
 *      Somewhere in your game engine, instantiate the controller and bind it to
 *      a class attribute. I do this in a `WorldRenderer` class because that
 *      class knows about the camera width and height and pixel-per-unit.
 *  </li>
 *  <li>
 *      Whereever you load all your textures, call `loadTextures()`.
 *  </li>
 *  <li>
 *      Just before you render your screen, call
 *      `this.controller.processInput()`.
 *  </li>
 *  <li>
 *      Now you can access `this.controller.isLeftPressed` and other booleans
 *      in order to find out what inputs have been pressed.
 *  </li>
 *  <li>
 *      In your `SpriteBatch` begin-end-block, call
 *      `this.controller.render(spriteBatch, ppuX, ppuY)` in order to render
 *      the HUD.
 *  </li>
 *  <li>
 *      In your main `dispose()` method call `this.controller.dispose()`
 *      in order to dispose the Textures that are used by this controller.
 *  </li>
 * </ul>
 */
public class PlatformerInputController {
    final OrthographicCamera camera;

    public float width;
    public float height;

    Rectangle btnLeft;
    Texture btnLeftTexture;
    Rectangle btnRight;
    Texture btnRightTexture;
    Rectangle btnUp;
    Texture btnUpTexture;

    public boolean isLeftPressed;
    public boolean isRightPressed;
    public boolean isUpPressed;
    public boolean isAction1Pressed;

    /**
     * Constructor takes the game width, height and camera.
     *
     * @param width     Width of the camera. Needed to place the button
     *                  rectangles on the screen. These are not pixels but
     *                  whatever unit you chose for your camera.
     * @param height    Height of the screen. Needed to make the button
     *                  rectangles as high as the screen is. These are not
     *                  pixels but whatever unit you chose for your camera.
     * @param camera    Needed to unproject touch events into the camera's
     *                  coordinate system.
     */
    public PlatformerInputController(float width,
                                     float height,
                                     OrthographicCamera camera) {
        // We will set these so that we can access them in the other methods
        this.width = width;
        this.height = height;
        this.camera = camera;

        // Creating the Rectangles for the invisible onscreen buttons
        // Dividin the device width by 6 might turn out to bee too wide for
        // larger devices (i.e 10" tablets) - I need to find a smarter solution
        // for this.
        this.btnLeft = new Rectangle();
        this.btnLeft.width = this.width / 6;
        this.btnLeft.height = this.height;
        this.btnLeft.x = 0;
        this.btnLeft.y = 0;

        this.btnRight = new Rectangle();
        this.btnRight.width = this.width / 6;
        this.btnRight.height = this.height;
        this.btnRight.x = this.btnLeft.width;
        this.btnRight.y = 0;

        this.btnUp = new Rectangle();
        this.btnUp.width = this.width / 6;
        this.btnUp.height = this.height;
        this.btnUp.x = this.width - this.btnUp.width;
        this.btnUp.y = 0;
    }

    /**
     * Loads the Texture objects for this controller.
     *
     * Don't forget to call `dispose()` in your Sreen's main dispose method.
     */
    public void loadTextures() {
        this.btnLeftTexture = new Texture(
            Gdx.files.internal("btn_arrow_left.png"));
        this.btnRightTexture = new Texture(
            Gdx.files.internal("btn_arrow_right.png"));
        this.btnUpTexture = new Texture(
            Gdx.files.internal("btn_arrow_up.png"));
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
     * Note that we need to pass in the pixel-per-unit values here in order to
     * re-arrange everything if someone re-sizes the screen.
     *
     * TODO: Should probably be hidden on desktop devices.
     *
     * @param batch SpriteBatch instance that takes care of rendering your
     *              stuff.
     * @param ppuX  Pixel per unit for the x-axis.
     * @param ppuY  Pixel per unit for the y-axis.
     */
    public void render(SpriteBatch batch, float ppuX, float ppuY) {
        int heightFromGround = 10;
        int btnSize = 32;

        // The invisible button rectangle is 100 pixels wide but the tiny image
        // is much smaller. We want the image to be centered in those 100
        // pixels.
        float btnLeftXPosition = (
            this.btnLeft.width * ppuX - this.btnLeftTexture.getWidth()) / 2;
        batch.draw(
            this.btnLeftTexture,
            btnLeftXPosition, heightFromGround, btnSize, btnSize);

        float btnRightXPosition = btnLeftXPosition + this.btnLeft.width * ppuX;
        batch.draw(
            this.btnRightTexture,
            btnRightXPosition, heightFromGround, btnSize, btnSize);

        float btnUpPosition = (
            this.width * ppuX - btnLeftXPosition - this.btnUpTexture.getWidth());
        batch.draw(
            this.btnUpTexture,
            btnUpPosition, heightFromGround, btnSize, btnSize);
    }

    /**
     * Disposes the HashMap of Textures that `loadTexures()` returned.
     */
    public void dispose() {
        this.btnLeftTexture.dispose();
        this.btnRightTexture.dispose();
        this.btnUpTexture.dispose();
    }
}
