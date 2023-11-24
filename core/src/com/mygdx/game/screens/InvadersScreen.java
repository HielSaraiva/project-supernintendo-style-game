package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
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
    private Texture ship1Life;
    private BlueAlien blueAlien;
    private Meteor meteor;
    private Eye eye1, eye2, eye3;
    private Life life;
    private BulletMode bulletMode;
    private Texture ship1Burst;
    private Texture wallpaperScreen;
    private OrthographicCamera camera;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;
    private Music backgroundMusic;
    private boolean paused = false;
    private Texture resumeButtonActive, resumeButtonInactive, quitButtonActive, quitButtonInactive, menuButtonActive, menuButtonInactive, player1, player2, space, enter, controls1, controls2;
    private Music backgroundPauseMusic;
    private Sound soundScreen;
    private float allTime;
    public static int highscore;
    public static boolean record;
    private Boss boss;


    public InvadersScreen(SpaceInvaders game) {
        this.game = game;
        record = false;

        // Creating Spaceship and BlueAlien and Explosions
        ship1 = new Spaceship("pictures/inGame/player1/ship.png", new Bullet("pictures/inGame/bullet/bullet1.png", "audio/bullets/bullet1.mp3"));
        blueAlien = new BlueAlien("pictures/inGame/enemies/alien1.png", ship1);
        meteor = new Meteor("pictures/inGame/enemies/meteor.png", ship1);
        eye1 = new Eye("pictures/inGame/enemies/eye.png", ship1);
        eye2 = new Eye("pictures/inGame/enemies/eye.png", ship1);
        eye3 = new Eye("pictures/inGame/enemies/eye.png", ship1);
        life = new Life();
        bulletMode = new BulletMode();
        boss = new Boss("pictures/inGame/enemies/belligol.png", "pictures/inGame/bullet/bullet4.png", ship1);
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
        player1 = new Texture(Gdx.files.internal("pictures/outGame/player1.png"));
        player2 = new Texture(Gdx.files.internal("pictures/outGame/player2.png"));
        enter = new Texture(Gdx.files.internal("pictures/outGame/enter.png"));
        space = new Texture(Gdx.files.internal("pictures/outGame/space.png"));
        controls1 = new Texture(Gdx.files.internal("pictures/outGame/wasd.png"));
        controls2 = new Texture(Gdx.files.internal("pictures/outGame/setas.png"));

        //Ships lifes:
        ship1Life = new Texture(Gdx.files.internal("pictures/inGame/consumables/life.png"));

        //Ships bursts
        ship1Burst = new Texture(Gdx.files.internal("pictures/inGame/consumables/bullet_mode_small.png"));

        // Music when paused :
        backgroundPauseMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/trail/trail3.mp3"));
        backgroundPauseMusic.setVolume(2f);

        // Start the playback of the background music immediately and put him at loop
        backgroundMusic.play();
        backgroundMusic.setLooping(true);

        // Creating camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        allTime += Gdx.graphics.getDeltaTime();
        if (paused) {
            boss.pauseMusic();
            backgroundMusic.pause();
            if (Gdx.input.isKeyJustPressed((Input.Keys.ESCAPE))) {
                paused = false;
                game.batch.setColor(1f, 1f, 1f, 1f);
                bitmap.setColor(1f, 1f, 1f, 1f);
            }
        } else {
            backgroundPauseMusic.pause();
            generalUpdate(delta);
            if (!backgroundMusic.isPlaying()) {
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
        game.batch.draw(wallpaperScreen, 0, 0);
        // Draw the bullet system, the spaceship and the text on game screen

        game.batch.draw(this.boss.getBombSprite(), this.boss.getBombSprite().getX(), this.boss.getBombSprite().getY());
        game.batch.draw(this.boss.getBombSprite1(), this.boss.getBombSprite1().getX(), this.boss.getBombSprite1().getY());
        game.batch.draw(this.boss.getBombSprite2(), this.boss.getBombSprite2().getX(), this.boss.getBombSprite2().getY());
        game.batch.draw(this.boss.getBombSprite3(), this.boss.getBombSprite3().getX(), this.boss.getBombSprite3().getY());

        if (!ship1.isGameover()) {
            if (ship1.isAttack()) {
                game.batch.draw(ship1.getBullet1().getSprite(), ship1.getBullet1().getX(), ship1.getBullet1().getY());
            }
            game.batch.draw((TextureRegion) ship1.rolls[ship1.roll].getKeyFrame(ship1.getStateTime(), true), ship1.getX(), ship1.getY(), Spaceship.SHIP_WIDTH, Spaceship.SHIP_HEIGTH);

            for (Rectangle enemy : blueAlien.getRectangles()) {
                game.batch.draw(blueAlien.getTexture(), enemy.x, enemy.y);
            }

            for (Rectangle enemy : meteor.getRectangles()) {
                game.batch.draw(meteor.getTexture(), enemy.x, enemy.y);
            }

            if (eye1.isAttack()) {
                game.batch.draw(eye1.getBullet().getSprite(), eye1.getBullet().getX(), eye1.getBullet().getY());
            }
            game.batch.draw(eye1.getSprite(), eye1.getSprite().getX(), eye1.getSprite().getY());

            if (eye2.isAttack()) {
                game.batch.draw(eye2.getBullet().getSprite(), eye2.getBullet().getX(), eye2.getBullet().getY());
            }
            game.batch.draw(eye2.getSprite(), eye2.getSprite().getX(), eye2.getSprite().getY());

            if (eye3.isAttack()) {
                game.batch.draw(eye3.getBullet().getSprite(), eye3.getBullet().getX(), eye3.getBullet().getY());
            }
            game.batch.draw(eye3.getSprite(), eye3.getSprite().getX(), eye3.getSprite().getY());

            game.batch.draw(life.getSprite(), life.getSprite().getX(), life.getSprite().getY());
            game.batch.draw(bulletMode.getSprite(), bulletMode.getSprite().getX(), bulletMode.getSprite().getY());
            game.batch.draw(boss.getSprite(), boss.getSprite().getX(), boss.getSprite().getY());

            bitmap.draw(game.batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: ", 20, Gdx.graphics.getHeight() - 20);
            if (ship1.getLife() == 5) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 210, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 245, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 280, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 4) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 210, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 245, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 3) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 210, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 2) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 175, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 1) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
            }
        } else {
            life.getMusic1().stop();
            bulletMode.getMusic1().stop();
            backgroundMusic.stop();
            boss.stopMusic();
            game.setScreen(new GameoverScreen(game, ship1.getFinalScore()));
        }

        // Collision between Bomb and Ship:
        if (boss.shipBombCollision()) {
            ship1.setLife(ship1.getLife() - 1);
            if (ship1.getLife() <= 0) {
                ship1.setFinalScore(ship1.getScore());
                ship1.setGameover(true);
            }
        }
        // Collision between Bullet and Boss:
        if (boss.bulletBossCollision()) {
            boss.setLife(boss.getLife() - 1);
            if (boss.getLife() == 0) {
                boss.stopMusic();
                ship1.setFinalScore(ship1.getScore());
                ship1.setGameover(true);
            }
        }

        if (eye1.getSprite().getX() + eye1.getSprite().getWidth() < 0 ^ eye1.ShipBulletCollision()) {
            game.batch.draw(eye1.getTexExplo1(), eye1.getSprite().getX(), eye1.getSprite().getY());
            game.batch.draw(eye1.getTexExplo2(), eye1.getSprite().getX(), eye1.getSprite().getY());
            game.batch.draw(eye1.getTexExplo3(), eye1.getSprite().getX(), eye1.getSprite().getY());

            if(allTime < 130.0f){
                eye1 = new Eye("pictures/inGame/enemies/eye.png", ship1);
            }else{
                eye1.getSprite().setX(-100);
                eye1.getSprite().setY(-100);
            }
        }
        if (eye1.BulletAlienCollision()) {
            game.batch.draw(eye1.getTexExplo1(), eye1.getShip().getX(), eye1.getShip().getY());
            game.batch.draw(eye1.getTexExplo2(), eye1.getShip().getX(), eye1.getShip().getY());
            game.batch.draw(eye1.getTexExplo3(), eye1.getShip().getX(), eye1.getShip().getY());
        }

        if (eye2.getSprite().getX() + eye2.getSprite().getWidth() < 0 ^ eye2.ShipBulletCollision()) {
            game.batch.draw(eye2.getTexExplo1(), eye2.getSprite().getX(), eye2.getShip().getY());
            game.batch.draw(eye2.getTexExplo2(), eye2.getSprite().getX(), eye2.getShip().getY());
            game.batch.draw(eye2.getTexExplo3(), eye2.getSprite().getX(), eye2.getShip().getY());

            if(allTime < 130.0f){
                eye2 = new Eye("pictures/inGame/enemies/eye.png", ship1);
            }else{
                eye2.getSprite().setX(-100);
                eye2.getSprite().setY(-100);
            }
        }
        if (eye2.BulletAlienCollision()) {
            game.batch.draw(eye2.getTexExplo1(), eye2.getShip().getX(), eye2.getShip().getY());
            game.batch.draw(eye2.getTexExplo2(), eye2.getShip().getX(), eye2.getShip().getY());
            game.batch.draw(eye2.getTexExplo3(), eye2.getShip().getX(), eye2.getShip().getY());
        }

        if (eye3.getSprite().getX() + eye3.getSprite().getWidth() < 0 ^ eye3.ShipBulletCollision()) {
            game.batch.draw(eye3.getTexExplo1(), eye3.getSprite().getX(), eye3.getShip().getY());
            game.batch.draw(eye3.getTexExplo2(), eye3.getSprite().getX(), eye3.getShip().getY());
            game.batch.draw(eye3.getTexExplo3(), eye3.getSprite().getX(), eye3.getShip().getY());
            if(allTime < 130.0f){
                eye3 = new Eye("pictures/inGame/enemies/eye.png", ship1);
            }else{
                eye3.getSprite().setX(-100);
                eye3.getSprite().setY(-100);
            }
        }
        if (eye3.BulletAlienCollision()) {
            game.batch.draw(eye3.getTexExplo1(), eye3.getShip().getX(), eye3.getShip().getY());
            game.batch.draw(eye3.getTexExplo2(), eye3.getShip().getX(), eye3.getShip().getY());
            game.batch.draw(eye3.getTexExplo3(), eye3.getShip().getX(), eye3.getShip().getY());
        }

        if (life.lifeCollision(ship1)) {
            life.getSprite().setX(Gdx.graphics.getWidth());
        }
        if (life.getSprite().getX() >= Gdx.graphics.getWidth() && life.getTime() >= 20.0f) {
            life = new Life();
        }

        if (bulletMode.lifeCollision(ship1)) {
            bulletMode.getSprite().setX(Gdx.graphics.getWidth());
        }
        if (bulletMode.isCollision() && bulletMode.getTime() <= 20.0f) {
            game.batch.draw(ship1Burst, 20, Gdx.graphics.getHeight() - 200);
        }
        if (bulletMode.getSprite().getX() >= Gdx.graphics.getWidth() && bulletMode.getTime() >= 20.0f) {
            ship1.setFactor(8.0f);
            Spaceship.setTimeOut(1.0f);
        }
        if (bulletMode.getSprite().getX() >= Gdx.graphics.getWidth() && bulletMode.getTime() >= 30.0f) {
            bulletMode = new BulletMode();
        }

        Preferences prefs = Gdx.app.getPreferences("SpaceInvaders");
        this.highscore = prefs.getInteger("highscore", 0);

        if (ship1.getScore() > highscore) {
            prefs.putInteger("highscore", ship1.getScore());
            prefs.flush();
            record = true;
        }
        if (allTime > 130.0f) {
            bitmap.draw(game.batch, "Boss : " + boss.getLife(), Gdx.graphics.getWidth() / 2 - 100, 40);
        }
        bitmap.draw(game.batch, "Best Score: " + InvadersScreen.getHighscore(), (Gdx.graphics.getWidth() - 450) / 2, Gdx.graphics.getHeight() - 20);

        if (paused) {
            game.batch.setColor(0.7f, 0.7f, 0.7f, 0.7f);
            bitmap.setColor(0.7f, 0.7f, 0.7f, 0.7f);
            bitmap.draw(game.batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: ", 20, Gdx.graphics.getHeight() - 20);
            if (ship1.getLife() == 5) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 210, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 245, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 280, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 4) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 210, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 245, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 3) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 210, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 2) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(ship1Life, 175, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 1) {
                game.batch.draw(ship1Life, 140, Gdx.graphics.getHeight() - 167);
            }
            backgroundMusic.pause();
            life.getMusic1().pause();
            bulletMode.getMusic1().pause();
            backgroundPauseMusic.play();
            backgroundPauseMusic.setLooping(true);

            // Drawing the options/buttons in the Menu when paused :
            optionsMenuPaused();
        }

        // Make the spaceship moves, the bullet moves, the alien moves
        Sound soundShot1 = Gdx.audio.newSound(Gdx.files.internal("audio/bullets/bullet1.mp3"));
        for (Explosion explosion : blueAlien.getExplosions1()) {
            explosion.render(game.batch);
        }
        for (Explosion explosion : blueAlien.getExplosions2()) {
            explosion.render(game.batch);
        }
        for (Explosion explosion : meteor.getExplosions1()) {
            explosion.render(game.batch);
        }
        for (Explosion explosion : meteor.getExplosions2()) {
            explosion.render(game.batch);
        }

        game.batch.end();
    }

    public void generalUpdate(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            soundScreen.play(2.0f);
            paused = true;
        } else {
            this.ship1.moveBullet();
            this.ship1.moveSpaceship();
            this.blueAlien.move();
            this.life.move(ship1);
            this.bulletMode.move(ship1);

            if (allTime > 30.0f) {
                this.meteor.move();
            }

            if (allTime > 60.0f) {
                this.eye1.move();
                this.eye1.moveBullet();
            }

            if (allTime > 80.0f) {
                this.eye2.move();
                this.eye2.moveBullet();
            }

            if (allTime > 100.0f) {
                this.eye3.move();
                this.eye3.moveBullet();
            }
            if (allTime > 130.0f) {
                // Boss Movement:
                this.boss.move();
                // Boss Firts Attack (Bombs):
                boss.moveBomb();
            }

            this.ship1.setStateTime(ship1.getStateTime() + delta);
        }
    }

    public void optionsMenuPaused() {
        controles();
        if (Gdx.input.getX() < (Gdx.graphics.getWidth() + quitButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() + 200 < (float) (Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 + quitButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 200 > (float) (Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2) {
            game.batch.draw(quitButtonActive, (float) (Gdx.graphics.getWidth() - quitButtonActive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - quitButtonActive.getHeight()) / 2 - 200);
            if (Gdx.input.justTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(quitButtonInactive, (float) (Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 - 200);
        }
        if (Gdx.input.getX() < (Gdx.graphics.getWidth() + resumeButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - resumeButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < (float) (Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2 + resumeButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() > (float) (Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2) {
            game.batch.draw(resumeButtonActive, (float) (Gdx.graphics.getWidth() - resumeButtonActive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - resumeButtonActive.getHeight()) / 2);
            if (Gdx.input.justTouched()) {
                bitmap.setColor(1f, 1f, 1f, 1f);
                paused = false;
                game.batch.setColor(1f, 1f, 1f, 1f);
            }
        } else {
            game.batch.draw(resumeButtonInactive, (float) (Gdx.graphics.getWidth() - resumeButtonInactive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2);
        }

        if (Gdx.input.getX() < (Gdx.graphics.getWidth() + menuButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() + 100 < (float) (Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 + menuButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 100 > (float) (Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2) {
            game.batch.draw(menuButtonActive, (float) (Gdx.graphics.getWidth() - menuButtonActive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - menuButtonActive.getHeight()) / 2 - 100);
            if (Gdx.input.justTouched()) {
                game.batch.setColor(1f, 1f, 1f, 1f);
                bitmap.setColor(1f, 1f, 1f, 1f);
                backgroundPauseMusic.stop();
                game.setScreen(new MainMenuScreen(game));
            }
        } else {
            game.batch.draw(menuButtonInactive, (float) (Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 - 100);
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

    public void controles() {
        game.batch.draw(player1, (float) ((Gdx.graphics.getWidth() / 5 - player1.getWidth() / 2) - 20), (float) (Gdx.graphics.getWidth() - player1.getWidth()) / 2);
        game.batch.draw(controls1, (float) ((Gdx.graphics.getWidth() / 5 - controls1.getWidth() / 2) - 20), (float) ((Gdx.graphics.getWidth() - controls1.getWidth()) / 2 - 300));
        game.batch.draw(space, (float) ((Gdx.graphics.getWidth() / 5 - space.getWidth() / 2) - 20), (float) ((Gdx.graphics.getWidth() - space.getWidth()) / 2 - 500));

        game.batch.draw(player2, (float) ((Gdx.graphics.getWidth() * 4 / 5 - player2.getWidth() / 2) + 20), (float) (Gdx.graphics.getWidth() - player2.getWidth()) / 2);
        game.batch.draw(controls2, (float) ((Gdx.graphics.getWidth() * 4 / 5 - controls2.getWidth() / 2) + 20), (float) ((Gdx.graphics.getWidth() - controls2.getWidth()) / 2 - 300));
        game.batch.draw(enter, (float) ((Gdx.graphics.getWidth() * 4 / 5 - enter.getWidth() / 2) + 35), (float) ((Gdx.graphics.getWidth() - enter.getWidth()) / 2 - 600));
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

    public static int getHighscore() {
        return highscore;
    }

    public static boolean getRecord() {
        return record;
    }

    @Override
    public void dispose() {
        // Cleaning Up (textures, sounds, musics, batch)
        backgroundMusic.dispose();
        ship1.getBullet1().getSound().dispose();
        game.batch.dispose();
        backgroundPauseMusic.dispose();
    }
}