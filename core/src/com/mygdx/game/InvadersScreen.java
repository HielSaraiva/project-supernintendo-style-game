package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class InvadersScreen implements Screen {
    final SpaceInvaders game;
    public SpriteBatch batch;
    public Texture wallpaperScreen;
    public Texture textureSpaceship1;
    public Texture textureBulletSpaceship1;
    public Music backgroundMusic;
    public Sound soundShot1;
    public OrthographicCamera camera;
    private Sprite spaceship1;
    private float posXShip1, posYShip1;
    private Sprite bullet1;
    private float posXBullet1, posYBullet1;
    private boolean attackShip1;

    private final float VELOCITY = 200 * Gdx.graphics.getDeltaTime();;


    public InvadersScreen(final SpaceInvaders game) {
        this.game = game;
        // Load the image for the spaceship of player 1
        textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/base.png"));
        spaceship1 = new Sprite(textureSpaceship1);
        posXShip1 = 20;
        posYShip1 = (Gdx.graphics.getHeight() - spaceship1.getHeight())/2;
        // Load the image for the bullet of spaceship 1
        textureBulletSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/bullet/bullet1.png"));
        bullet1 = new Sprite(textureBulletSpaceship1);
        posXBullet1 = posXShip1;
        posYBullet1 = posYShip1;
        attackShip1 = false;

        // Load the background picture
        wallpaperScreen = new Texture(Gdx.files.internal("pictures/outGame/background.jpg"));

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
        batch.draw(wallpaperScreen,0, 0);
        batch.draw(bullet1, posXBullet1 + spaceship1.getWidth()/2 + 10, posYBullet1 + spaceship1.getHeight()/2 - 5);
        batch.draw(spaceship1, posXShip1, posYShip1);
        //batch.draw();
        batch.end();

        // Process user input
        // Making the spaceship 1 moves and add sound shot
        Sound soundShot1 = Gdx.audio.newSound(Gdx.files.internal("audio/bullets/bullet1.mp3"));
        this.moveSpaceship1();
        this.moveBullet1();

        // Make sure our spaceship 1 stays within the screen limits

    }

    public void moveSpaceship1() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if(posXShip1 > 0){
                posXShip1 -= VELOCITY;
                textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/base.png"));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(posXShip1 < Gdx.graphics.getWidth() - spaceship1.getWidth()){
                posXShip1 += VELOCITY;
            textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/accelerating.png"));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(posYShip1 > 0){
                posYShip1 -= VELOCITY;
            textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/accelerating.png"));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(posYShip1 < Gdx.graphics.getHeight() - spaceship1.getHeight()){
                posYShip1 += VELOCITY;
            textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/accelerating.png"));
            }
        }
    }

    public void moveBullet1() {
        if(Gdx.input.isKeyPressed(Keys.SPACE) && !attackShip1) {
            attackShip1 = true;
            posYBullet1 = posYShip1;
        }

        if(attackShip1) {
            if(posXBullet1 < Gdx.graphics.getWidth()){
                posXBullet1 += 8.0f * VELOCITY;
            } else {
                posXBullet1 = posXShip1;
                posYBullet1 = posYShip1;
                attackShip1 = false;
            }
        } else {
            posXBullet1 = posXShip1;
            posYBullet1 = posYShip1;
        }
    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        backgroundMusic.setVolume(0.5f);
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
        soundShot1.dispose();
        textureSpaceship1.dispose();
        batch.dispose();
    }
}
