package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.SpaceInvaders;
import com.mygdx.game.entities.BlueAlien;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.Spaceship;
import sun.jvm.hotspot.gc.shared.Space;

import java.util.Iterator;

public class InvadersScreen implements Screen {
    private final SpaceInvaders game;
    private Spaceship ship1;
    private BlueAlien blueAlien;
    private SpriteBatch batch;
    private Texture wallpaperScreen;
    private Music backgroundMusic;
    private OrthographicCamera camera;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;


    public InvadersScreen(final SpaceInvaders game) {
        this.game = game;

        // Creating spaceship 1
        ship1 = new Spaceship("pictures/inGame/player2/ship.png", new Bullet("pictures/inGame/bullet/bullet1.png", "audio/bullets/bullet1.mp3"));

        blueAlien = new BlueAlien("pictures/inGame/enemies/aliens/alien1.png", ship1);

        // Load the font of the game text screen
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font4.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK;
        parameter.color = Color.WHITE;
        bitmap = generator.generateFont(parameter);

        // Load the background picture
        wallpaperScreen = new Texture(Gdx.files.internal("pictures/outGame/background.jpg"));

        // Load the background sound of the game and the shot of the spaceship 1
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/trail/trail2.mp3"));

        // Start the playback of the background music immediately and put him at loop
        backgroundMusic.play();
        backgroundMusic.setLooping(true);

        // Creating camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

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

        // Begin a new batch and draw the background wallpaper
        batch.begin();
        batch.draw(wallpaperScreen,0, 0);

        // Draw the bullet system, the spaceship1 and the text on game screen
        if(!ship1.isGameover()) {
            if(ship1.isAttack()) {
                batch.draw(ship1.getBullet().getSprite(), ship1.getBullet().getX(), ship1.getBullet().getY());
            }
            batch.draw((TextureRegion) ship1.rolls[ship1.roll].getKeyFrame(ship1.getStateTime(), true), ship1.getX(), ship1.getY(), Spaceship.SHIP_WIDTH, Spaceship.SHIP_HEIGTH);

            for(Rectangle enemy : blueAlien.getRectangles()) {
                batch.draw(blueAlien.getTexture(), enemy.x, enemy.y);
            }
            bitmap.draw(batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: " + ship1.getLife(), 20, Gdx.graphics.getHeight() - 20);
        } else {
            bitmap.draw(batch, "Player 1\nFinal Score: " + ship1.getFinalScore() + "\nGAMEOVER PLAYER1", 20, Gdx.graphics.getHeight() - 20);
            ship1.getBullet().getSound().pause();
            backgroundMusic.stop();

            // Reinitiate the game when ENTER is pressed
            if(Gdx.input.isKeyPressed(Keys.ENTER)) {
                ship1.setFinalScore(0);
                ship1.setGameover(false);
                ship1.setScore(0);
                ship1.setLife(3);
                blueAlien.getRectangles().clear();
                ship1.setX(20);
                ship1.setY((float)(Gdx.graphics.getHeight() - Spaceship.SHIP_HEIGTH_PIXEL)/2); //
                backgroundMusic.play();
                backgroundMusic.setLooping(true);
            }
        }

        // Making the spaceship 1 moves and add sound shot
        Sound soundShot1 = Gdx.audio.newSound(Gdx.files.internal("audio/bullets/bullet1.mp3"));
        this.ship1.moveBullet();
        this.blueAlien.move();
        this.ship1.moveSpaceship();
        this.ship1.setStateTime(ship1.getStateTime() + delta);
        batch.end();
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
        backgroundMusic.setLooping(true);
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
        ship1.getBullet().getSound().dispose();
        batch.dispose();
    }
}