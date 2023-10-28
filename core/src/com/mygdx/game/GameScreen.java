package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen  implements Screen {
    final SpaceInvaders game;

    public Texture textureSpaceship1;
    public Music backgroundMusic;
    public OrthographicCamera camera;
    public SpriteBatch batch;
    public Rectangle rectangleSpaceship1;

    public GameScreen(final SpaceInvaders game) {
        this.game = game;

        // Load the image for the spaceship of player 1
        textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/base.png"));

        // Load the background sound of the game
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/trail/trail2.mp3"));
        // Start the playback of the background music immediately
        backgroundMusic.play();
        backgroundMusic.setLooping(true);

        // Creating camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        // Creating the batch
        batch = new SpriteBatch();

        // Instantiate the Rectangle of spaceship 1 and specify its initial values.
        // The x/y coordinates of the bucket define the bottom left corner of the bucket,
        // the origin for drawing is located in the bottom left corner of the screen.
        rectangleSpaceship1 = new Rectangle();
        rectangleSpaceship1.x = 20;
        rectangleSpaceship1.y = (int)(720 / 2 - 60 / 2);
        rectangleSpaceship1.width = 60;
        rectangleSpaceship1.height = 64;
    }

    @Override
    public void render (float delta) {
        // Clearing the screen with a dark blue color (RGB alpha)
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Tell the camera to update its matrices
        camera.update();

        // Tell the SpriteBatch to render in the
        // coordinate system specified by the camera
        batch.setProjectionMatrix(camera.combined);

        // Begin a new batch and draw the spaceship 1
        batch.begin();
        batch.draw(textureSpaceship1, rectangleSpaceship1.x, rectangleSpaceship1.y);
        batch.end();

        // Process user input
        // Making the spaceship 1 moves
        if(Gdx.input.isKeyPressed(Keys.ANY_KEY)){
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                rectangleSpaceship1.x -= 200 * Gdx.graphics.getDeltaTime();
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/base.png"));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Keys.SPACE)) {
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/baseShooting.png"));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                rectangleSpaceship1.x += 200 * Gdx.graphics.getDeltaTime();
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/accelerating.png"));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Keys.SPACE)) {
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/acceleratingShooting.png"));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                rectangleSpaceship1.y -= 200 * Gdx.graphics.getDeltaTime();
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/accelerating.png"));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Keys.SPACE)) {
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/acceleratingShooting.png"));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                rectangleSpaceship1.y += 200 * Gdx.graphics.getDeltaTime();
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/accelerating.png"));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Keys.SPACE)) {
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/acceleratingShooting.png"));
            }
            if(!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Keys.SPACE)) {
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/baseShooting.png"));
            }
        } else {
            textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/base.png"));
        }

        // Make sure our spaceship 1 stays within the screen limits
        if(rectangleSpaceship1.x < 0) {
            rectangleSpaceship1.x = 0;
        }
        if(rectangleSpaceship1.x > 1280 - 64) {
            rectangleSpaceship1.x = 1280 - 64;
        }
        if(rectangleSpaceship1.y < 0) {
            rectangleSpaceship1.y = 0;
        }
        if(rectangleSpaceship1.y > 720 - 60) {
            rectangleSpaceship1.y = 720 - 60;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        backgroundMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose () {
        // Cleaning Up (textures, sounds, musics, batch)
        backgroundMusic.dispose();
        textureSpaceship1.dispose();
        batch.dispose();
    }
}
