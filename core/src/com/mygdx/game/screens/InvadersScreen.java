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
import com.mygdx.game.entities.Explosion;
import com.mygdx.game.entities.Spaceship;
import sun.jvm.hotspot.gc.shared.Space;

import java.util.ArrayList;
import java.util.Iterator;

public class InvadersScreen implements Screen {
    private final SpaceInvaders game;
    private Spaceship ship1;
    private BlueAlien blueAlien;
    private Texture wallpaperScreen;
    private OrthographicCamera camera;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;
    private Music backgroundMusic;
    private boolean paused = false;
    private Texture resumeButtonActive, resumeButtonInactive,quitButtonActive, quitButtonInactive, menuButtonActive, menuButtonInactive;
    private Music backgroundPauseMusic;

    public InvadersScreen(SpaceInvaders game) {
        this.game = game;

        // Creating Spaceship and BlueAlien and Explosions
        ship1 = new Spaceship("pictures/inGame/player1/ship.png", new Bullet("pictures/inGame/bullet/bullet1.png", "audio/bullets/bullet1.mp3"));
        blueAlien = new BlueAlien("pictures/inGame/enemies/aliens/alien1.png", ship1);

        // Load the font of the game text screen
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font4.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.borderWidth = 2f;
        parameter.borderColor = Color.BLACK;
        parameter.color = Color.WHITE;
        bitmap = generator.generateFont(parameter);

        // Load the background picture and background music
        wallpaperScreen = new Texture(Gdx.files.internal("pictures/outGame/background.jpg"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/trail/trail2.mp3"));

        // Textures when paused:
        resumeButtonActive = new Texture(Gdx.files.internal("pictures/outGame/resume_blue.png"));
        resumeButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/resume_orange.png"));
        quitButtonActive = new Texture(Gdx.files.internal("pictures/outGame/quit_blue.png"));
        quitButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/quit_orange.png"));
        menuButtonActive = new Texture(Gdx.files.internal("pictures/outGame/back_blue.png"));
        menuButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/back_orange.png"));

        // Music when paused :
        backgroundPauseMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/trail/trail3.mp3"));
        backgroundPauseMusic.setVolume(0.4f);

        // Start the playback of the background music immediately and put him at loop
        backgroundMusic.play();
        backgroundMusic.setLooping(true);

        // Creating camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render (float delta) {
        if (paused) {
            backgroundMusic.pause();
            if (Gdx.input.isKeyJustPressed((Input.Keys.ESCAPE))) {
                paused = false;
                game.batch.setColor(1f,1f,1f,1f);
            }
        } else {
            backgroundPauseMusic.pause();
            generalUpdate(delta);
            if(!backgroundMusic.isPlaying()){
                backgroundMusic.play();
            }
        }
        // Clearing the screen with a dark blue color (RGB alpha)
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Tell the camera to update its matrices
        camera.update();

        // Tell the SpriteBatch to render in the
        // coordinate system specified by the camera
        game.batch.setProjectionMatrix(camera.combined);

        // Begin a batch and draw the background wallpaper
        game.batch.begin();
        game.batch.draw(wallpaperScreen,0, 0);

        if(paused) {
            game.batch.setColor(0.7f, 0.7f, 0.7f, 0.7f);
            backgroundMusic.pause();
            backgroundPauseMusic.play();
            backgroundPauseMusic.setLooping(true);

            // Drawing the options/buttons in the Menu when paused :
            optionsMenuPaused();
        }

        // Draw the bullet system, the spaceship and the text on game screen
        if(!ship1.isGameover()) {
            if(ship1.isAttack()) {
                game.batch.draw(ship1.getBullet().getSprite(), ship1.getBullet().getX(), ship1.getBullet().getY());
            }
            game.batch.draw((TextureRegion) ship1.rolls[ship1.roll].getKeyFrame(ship1.getStateTime(), true), ship1.getX(), ship1.getY(), Spaceship.SHIP_WIDTH, Spaceship.SHIP_HEIGTH);

            for(Rectangle enemy : blueAlien.getRectangles()) {
                game.batch.draw(blueAlien.getTexture(), enemy.x, enemy.y);
            }
            bitmap.draw(game.batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: " + ship1.getLife(), 20, Gdx.graphics.getHeight() - 20);
        } else {
            backgroundMusic.stop();
            game.setScreen(new GameoverScreen(game, ship1.getFinalScore()));
        }

        // Make the spaceship moves, the bullet moves, the alien moves
        Sound soundShot1 = Gdx.audio.newSound(Gdx.files.internal("audio/bullets/bullet1.mp3"));
        for(Explosion explosion : blueAlien.getExplosions1()) {
            explosion.render(game.batch);
        }
        for(Explosion explosion : blueAlien.getExplosions2()) {
            explosion.render(game.batch);
        }
        game.batch.end();
    }

    public void generalUpdate (float delta){
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
            paused = true;
        } else {
            this.ship1.moveBullet();
            this.blueAlien.move();
            this.ship1.moveSpaceship();
            this.ship1.setStateTime(ship1.getStateTime() + delta);
        }
    }

    public void optionsMenuPaused (){
        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + quitButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() + 200 < (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 + quitButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 200 > (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2) {
            game.batch.draw(quitButtonActive, (float)(Gdx.graphics.getWidth() - quitButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - quitButtonActive.getHeight()) / 2 - 200);
            if(Gdx.input.isTouched()) {
                Gdx.app.exit();
                //this.dispose();
            }
        } else {
            game.batch.draw(quitButtonInactive, (float)(Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 - 200);
        }
        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + resumeButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - resumeButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY()  < (float)(Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2 + resumeButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY()  > (float)(Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2) {
            game.batch.draw(resumeButtonActive, (float)(Gdx.graphics.getWidth() - resumeButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - resumeButtonActive.getHeight()) / 2 );
            if(Gdx.input.isTouched()) {
                paused = false;
                game.batch.setColor(1f,1f,1f,1f);
            }
        } else {
            game.batch.draw(resumeButtonInactive, (float)(Gdx.graphics.getWidth() - resumeButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2);
        }

        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + menuButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() + 100 < (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 + menuButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 100 > (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2) {
            game.batch.draw(menuButtonActive, (float)(Gdx.graphics.getWidth() - menuButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - menuButtonActive.getHeight()) / 2 - 100);
            if(Gdx.input.isTouched()) {
                game.batch.setColor(1f,1f,1f,1f);
                backgroundPauseMusic.stop();
                game.setScreen(new MainMenuScreen(game));
            }
        } else {
            game.batch.draw(menuButtonInactive, (float)(Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 - 100);
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
        game.batch.dispose();
        backgroundPauseMusic.dispose();
    }
}