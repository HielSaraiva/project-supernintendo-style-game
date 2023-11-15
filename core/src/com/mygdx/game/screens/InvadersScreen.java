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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceInvaders;
import com.mygdx.game.entities.*;

public class InvadersScreen implements Screen {
    private final SpaceInvaders game;
    private Spaceship ship1;
    private BlueAlien blueAlien;
    private Meteor meteor;
    private Eye eye1, eye2, eye3;
    private Texture wallpaperScreen;
    private OrthographicCamera camera;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;
    private Music backgroundMusic;
    private boolean paused = false;
    private Texture resumeButtonActive, resumeButtonInactive,quitButtonActive, quitButtonInactive, menuButtonActive, menuButtonInactive;
    private Music backgroundPauseMusic;
    private Sound soundScreen;
    private float allTime;

    public InvadersScreen(SpaceInvaders game) {
        this.game = game;

        // Creating Spaceship and BlueAlien and Explosions
        ship1 = new Spaceship("pictures/inGame/player1/ship.png", new Bullet("pictures/inGame/bullet/bullet1.png", "audio/bullets/bullet1.mp3"));
        blueAlien = new BlueAlien("pictures/inGame/enemies/alien1.png", ship1);
        meteor = new Meteor("pictures/inGame/enemies/meteor.png", ship1);
        eye1 = new Eye("pictures/inGame/enemies/eye.png", ship1);
        eye2 = new Eye("pictures/inGame/enemies/eye.png", ship1);
        eye3 = new Eye("pictures/inGame/enemies/eye.png", ship1);

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
        soundScreen = Gdx.audio.newSound(Gdx.files.internal("audio/button_menu/button2.wav"));

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
        allTime += Gdx.graphics.getDeltaTime();
        if (paused) {
            backgroundMusic.pause();
            if (Gdx.input.isKeyJustPressed((Input.Keys.ESCAPE))) {
                paused = false;
                game.batch.setColor(1f,1f,1f,1f);
                bitmap.setColor(1f, 1f, 1f, 1f);
            }
        } else {
            backgroundPauseMusic.pause();
            generalUpdate(delta);
            if(!backgroundMusic.isPlaying()){
                backgroundMusic.play();
                soundScreen.play();
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
        // Draw the bullet system, the spaceship and the text on game screen

        if(!ship1.isGameover()) {
            if(ship1.isAttack()) {
                game.batch.draw(ship1.getBullet1().getSprite(), ship1.getBullet1().getX(), ship1.getBullet1().getY());
            }
            game.batch.draw((TextureRegion) ship1.rolls[ship1.roll].getKeyFrame(ship1.getStateTime(), true), ship1.getX(), ship1.getY(), Spaceship.SHIP_WIDTH, Spaceship.SHIP_HEIGTH);

            for(Rectangle enemy : blueAlien.getRectangles()) {
                game.batch.draw(blueAlien.getTexture(), enemy.x, enemy.y);
            }

            for(Rectangle enemy : meteor.getRectangles()) {
                game.batch.draw(meteor.getTexture(), enemy.x, enemy.y);
            }

            if(eye1.isAttack()) {
                game.batch.draw(eye1.getBullet().getSprite(), eye1.getBullet().getX(), eye1.getBullet().getY());
            }
            game.batch.draw(eye1.getSprite(), eye1.getSprite().getX(), eye1.getSprite().getY());

            if(eye2.isAttack()) {
                game.batch.draw(eye2.getBullet().getSprite(), eye2.getBullet().getX(), eye2.getBullet().getY());
            }
            game.batch.draw(eye2.getSprite(), eye2.getSprite().getX(), eye2.getSprite().getY());

            if(eye3.isAttack()) {
                game.batch.draw(eye3.getBullet().getSprite(), eye3.getBullet().getX(), eye3.getBullet().getY());
            }
            game.batch.draw(eye3.getSprite(), eye3.getSprite().getX(), eye3.getSprite().getY());

            bitmap.draw(game.batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: " + ship1.getLife(), 20, Gdx.graphics.getHeight() - 20);
        } else {
            backgroundMusic.stop();
            game.setScreen(new GameoverScreen(game, ship1.getFinalScore()));
        }

        if(eye1.getSprite().getX() + eye1.getSprite().getWidth()< 0) {
            eye1 = new Eye("pictures/inGame/enemies/eye.png", ship1);
        }

        if(eye2.getSprite().getX() + eye2.getSprite().getWidth()< 0) {
            eye2 = new Eye("pictures/inGame/enemies/eye.png", ship1);
        }

        if(eye3.getSprite().getX() + eye3.getSprite().getWidth()< 0) {
            eye3 = new Eye("pictures/inGame/enemies/eye.png", ship1);
        }

        if(paused) {
            game.batch.setColor(0.7f, 0.7f, 0.7f, 0.7f);
            bitmap.setColor(0.7f, 0.7f, 0.7f, 0.7f);
            bitmap.draw(game.batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: " + ship1.getLife(), 20, Gdx.graphics.getHeight() - 20);
            backgroundMusic.pause();
            backgroundPauseMusic.play();
            backgroundPauseMusic.setLooping(true);

            // Drawing the options/buttons in the Menu when paused :
            optionsMenuPaused();
        }

        // Make the spaceship moves, the bullet moves, the alien moves
        Sound soundShot1 = Gdx.audio.newSound(Gdx.files.internal("audio/bullets/bullet1.mp3"));
        for(Explosion explosion : blueAlien.getExplosions1()) {
            explosion.render(game.batch);
        }
        for(Explosion explosion : blueAlien.getExplosions2()) {
            explosion.render(game.batch);
        }
        for(Explosion explosion : meteor.getExplosions1()) {
            explosion.render(game.batch);
        }
        for(Explosion explosion : meteor.getExplosions2()) {
            explosion.render(game.batch);
        }

        game.batch.end();
    }

    public void generalUpdate (float delta){
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
            soundScreen.play();
            paused = true;
        } else {
            this.ship1.moveBullet();
            this.ship1.moveSpaceship();
            this.blueAlien.move();

            if(allTime > 60.0f){
                this.meteor.move();
            }

            if(allTime > 120.0f){
                this.eye1.move();
                this.eye1.moveBullet();
            }

            if(allTime > 122.5f){
                this.eye2.move();
                this.eye2.moveBullet();
            }

            if(allTime > 124.5f){
                this.eye3.move();
                this.eye3.moveBullet();
            }

            this.ship1.setStateTime(ship1.getStateTime() + delta);
        }
    }

    public void optionsMenuPaused (){
        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + quitButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() + 200 < (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 + quitButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 200 > (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2) {
            game.batch.draw(quitButtonActive, (float)(Gdx.graphics.getWidth() - quitButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - quitButtonActive.getHeight()) / 2 - 200);
            if(Gdx.input.justTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(quitButtonInactive, (float)(Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 - 200);
        }
        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + resumeButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - resumeButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY()  < (float)(Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2 + resumeButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY()  > (float)(Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2) {
            game.batch.draw(resumeButtonActive, (float)(Gdx.graphics.getWidth() - resumeButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - resumeButtonActive.getHeight()) / 2 );
            if(Gdx.input.justTouched()) {
                bitmap.setColor(1f, 1f, 1f, 1f);
                paused = false;
                game.batch.setColor(1f,1f,1f,1f);
            }
        } else {
            game.batch.draw(resumeButtonInactive, (float)(Gdx.graphics.getWidth() - resumeButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2);
        }

        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + menuButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() + 100 < (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 + menuButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 100 > (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2) {
            game.batch.draw(menuButtonActive, (float)(Gdx.graphics.getWidth() - menuButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - menuButtonActive.getHeight()) / 2 - 100);
            if(Gdx.input.justTouched()) {
                game.batch.setColor(1f,1f,1f,1f);
                bitmap.setColor(1f, 1f, 1f, 1f);
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
        ship1.getBullet1().getSound().dispose();
        game.batch.dispose();
        backgroundPauseMusic.dispose();
    }
}