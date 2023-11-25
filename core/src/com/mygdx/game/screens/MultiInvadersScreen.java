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

public class MultiInvadersScreen implements Screen {
    private final SpaceInvaders game;
    private Spaceship ship1, ship2;
    private Texture shipLife;
    private BlueAlien blueAlien;
    private Meteor meteor;
    private Eye eye1, eye2;
    private Life life1, life2;
    private BulletMode bulletMode1, bulletMode2;
    private Texture shipBurst;
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


    public MultiInvadersScreen(SpaceInvaders game) {
        this.game = game;
        record = false;

        // Creating Spaceship and BlueAlien and Explosions
        ship1 = new Spaceship("pictures/inGame/player1/ship.png", new Bullet("pictures/inGame/bullet/bullet1.png", "audio/bullets/bullet1.mp3"));
        ship2 = new Spaceship("pictures/inGame/player2/ship.png", new Bullet("pictures/inGame/bullet/bullet2.png", "audio/bullets/bullet1.mp3"));
        blueAlien = new BlueAlien("pictures/inGame/enemies/alien1.png");
        meteor = new Meteor("pictures/inGame/enemies/meteor.png");
        eye1 = new Eye("pictures/inGame/enemies/eye.png");
        eye2 = new Eye("pictures/inGame/enemies/eye.png");
        life1 = new Life();

        bulletMode1 = new BulletMode();

        boss = new Boss("pictures/inGame/enemies/belligol.png", "pictures/inGame/bullet/bullet4.png");
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
        shipLife = new Texture(Gdx.files.internal("pictures/inGame/consumables/life.png"));

        //Ships bursts
        shipBurst = new Texture(Gdx.files.internal("pictures/inGame/consumables/bullet_mode_small.png"));

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

        if (!ship1.isGameover() || !ship2.isGameover()) {
            if (!ship1.isGameover()) {
                if (ship1.isAttack()) {
                    game.batch.draw(ship1.getBullet1().getSprite(), ship1.getBullet1().getX(), ship1.getBullet1().getY());
                }
                game.batch.draw((TextureRegion) ship1.rolls[ship1.roll].getKeyFrame(ship1.getStateTime(), true), ship1.getX(), ship1.getY(), Spaceship.SHIP_WIDTH, Spaceship.SHIP_HEIGTH);
            }

            if (!ship2.isGameover()) {
                if (ship2.isAttack()) {
                    game.batch.draw(ship2.getBullet1().getSprite(), ship2.getBullet1().getX(), ship2.getBullet1().getY());
                }
                game.batch.draw((TextureRegion) ship2.rolls[ship2.roll].getKeyFrame(ship1.getStateTime(), true), ship2.getX(), ship2.getY(), Spaceship.SHIP_WIDTH, Spaceship.SHIP_HEIGTH);
            }

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

            game.batch.draw(life1.getSprite(), life1.getSprite().getX(), life1.getSprite().getY());
            game.batch.draw(bulletMode1.getSprite(), bulletMode1.getSprite().getX(), bulletMode1.getSprite().getY());
            game.batch.draw(boss.getSprite(), boss.getSprite().getX(), boss.getSprite().getY());

            bitmap.draw(game.batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: ", 20, Gdx.graphics.getHeight() - 20);
            if (ship1.getLife() == 5) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 210, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 245, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 280, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 4) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 210, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 245, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 3) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 210, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 2) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 175, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 1) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
            }

            bitmap.draw(game.batch, "Player 2\nScore: " + ship2.getScore() + "\nLife: ", Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 20);
            if (ship2.getLife() == 5) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 195, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 160, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 125, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 90, Gdx.graphics.getHeight() - 167);
            } else if (ship2.getLife() == 4) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 195, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 160, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 125, Gdx.graphics.getHeight() - 167);
            } else if (ship2.getLife() == 3) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 195, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 160, Gdx.graphics.getHeight() - 167);
            } else if (ship2.getLife() == 2) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 195, Gdx.graphics.getHeight() - 167);
            } else if (ship2.getLife() == 1) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
            }
        } else {
            life1.getMusic1().stop();
            bulletMode1.getMusic1().stop();
            backgroundMusic.stop();
            boss.stopMusic();
            game.setScreen(new MultiGameoverScreen(game, ship1.getFinalScore() + ship2.getFinalScore()));
        }

        // Collision between Bomb and Ship:
        if (boss.shipBombCollision(ship1)) {
            ship1.setLife(ship1.getLife() - 1);
            if (ship1.getLife() <= 0) {
                ship1.setFinalScore(ship1.getScore());
                ship1.setGameover(true);
            }
        }
        if (boss.shipBombCollision(ship2)) {
            ship2.setLife(ship2.getLife() - 1);
            if (ship2.getLife() <= 0) {
                ship2.setFinalScore(ship2.getScore());
                ship2.setGameover(true);
            }
        }

        // Collision between Bullet and Boss:
        if (boss.bulletBossCollision(ship1)) {
            boss.setLife(boss.getLife() - 1);
            if (boss.getLife() == 0) {
                boss.stopMusic();
                ship1.setFinalScore(ship1.getScore());
                ship1.setGameover(true);
                ship2.setFinalScore(ship2.getScore());
                ship2.setGameover(true);
                backgroundMusic.stop();
                game.setScreen(new WinnerScreen(game, ship1.getFinalScore() + ship2.getFinalScore()));
            }
        }
        if (boss.bulletBossCollision(ship2)) {
            boss.setLife(boss.getLife() - 1);
            if (boss.getLife() == 0) {
                boss.stopMusic();
                ship2.setFinalScore(ship2.getScore());
                ship2.setGameover(true);
                ship1.setFinalScore(ship1.getScore());
                ship1.setGameover(true);
                backgroundMusic.stop();
                game.setScreen(new WinnerScreen(game, ship2.getFinalScore() + ship1.getFinalScore()));
            }
        }

        if (eye1.getSprite().getX() + eye1.getSprite().getWidth() < 0 ^ eye1.ShipBulletCollision(ship1) ^ eye1.ShipBulletCollision(ship2)) {
            game.batch.draw(eye1.getTexExplo1(), eye1.getSprite().getX(), eye1.getSprite().getY());
            game.batch.draw(eye1.getTexExplo2(), eye1.getSprite().getX(), eye1.getSprite().getY());
            game.batch.draw(eye1.getTexExplo3(), eye1.getSprite().getX(), eye1.getSprite().getY());

            if (allTime < 100.0f) {
                eye1 = new Eye("pictures/inGame/enemies/eye.png");
            } else {
                eye1.getSprite().setX(-100);
                eye1.getSprite().setY(-100);
            }
        }
        if (eye1.BulletAlienCollision(ship1)) {
            game.batch.draw(eye1.getTexExplo1(), ship1.getX(), ship1.getY());
            game.batch.draw(eye1.getTexExplo2(), ship1.getX(), ship1.getY());
            game.batch.draw(eye1.getTexExplo3(), ship1.getX(), ship1.getY());
        }
        if (eye1.BulletAlienCollision(ship2)) {
            game.batch.draw(eye1.getTexExplo1(), ship2.getX(), ship2.getY());
            game.batch.draw(eye1.getTexExplo2(), ship2.getX(), ship2.getY());
            game.batch.draw(eye1.getTexExplo3(), ship2.getX(), ship2.getY());
        }

        if (eye2.getSprite().getX() + eye2.getSprite().getWidth() < 0 ^ eye2.ShipBulletCollision(ship1) ^ eye2.ShipBulletCollision(ship2)) {
            game.batch.draw(eye2.getTexExplo1(), eye2.getSprite().getX(), eye2.getSprite().getY());
            game.batch.draw(eye2.getTexExplo2(), eye2.getSprite().getX(), eye2.getSprite().getY());
            game.batch.draw(eye2.getTexExplo3(), eye2.getSprite().getX(), eye2.getSprite().getY());

            if (allTime < 100.0f) {
                eye2 = new Eye("pictures/inGame/enemies/eye.png");
            } else {
                eye2.getSprite().setX(-100);
                eye2.getSprite().setY(-100);
            }
        }
        if (eye2.BulletAlienCollision(ship1)) {
            game.batch.draw(eye2.getTexExplo1(), ship1.getX(), ship1.getY());
            game.batch.draw(eye2.getTexExplo2(), ship1.getX(), ship1.getY());
            game.batch.draw(eye2.getTexExplo3(), ship1.getX(), ship1.getY());
        }
        if (eye2.BulletAlienCollision(ship2)) {
            game.batch.draw(eye2.getTexExplo1(), ship2.getX(), ship2.getY());
            game.batch.draw(eye2.getTexExplo2(), ship2.getX(), ship2.getY());
            game.batch.draw(eye2.getTexExplo3(), ship2.getX(), ship2.getY());
        }

        if (life1.lifeCollision(ship1)) {
            life1.getSprite().setX(Gdx.graphics.getWidth());
        }
        if (life1.lifeCollision(ship2)) {
            life1.getSprite().setX(Gdx.graphics.getWidth());
        }
        if (life1.getSprite().getX() >= Gdx.graphics.getWidth() && life1.getTime() >= 20.0f) {
            life1 = new Life();
        }

        if (bulletMode1.lifeCollision(ship1)) {
            bulletMode1.getSprite().setX(Gdx.graphics.getWidth());
            ship2.setFactor(12.0f);
        }
        if (bulletMode1.lifeCollision(ship2)) {
            bulletMode1.getSprite().setX(Gdx.graphics.getWidth());
            ship1.setFactor(12.0f);
        }
        if (bulletMode1.isCollision() && bulletMode1.getTime() <= 20.0f) {
            game.batch.draw(shipBurst, 20, Gdx.graphics.getHeight() - 200);
            game.batch.draw(shipBurst, Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 200);
        }
        if (bulletMode1.getSprite().getX() >= Gdx.graphics.getWidth() && bulletMode1.getTime() >= 20.0f) {
            ship1.setFactor(8.0f);
            ship2.setFactor(8.0f);
            Spaceship.setTimeOut(1.0f);
        }
        if (bulletMode1.getSprite().getX() >= Gdx.graphics.getWidth() && bulletMode1.getTime() >= 30.0f) {
            bulletMode1 = new BulletMode();
        }

        Preferences prefs = Gdx.app.getPreferences("MultiSpaceInvaders");
        this.highscore = prefs.getInteger("highscore2", 0);

        if (ship1.getScore() + ship2.getScore() > highscore) {
            prefs.putInteger("highscore2", ship1.getScore() + ship2.getScore());
            prefs.flush();
            record = true;
        }
        if (allTime > 100.0f) {
            bitmap.draw(game.batch, "Boss : " + boss.getLife(), Gdx.graphics.getWidth() / 2 - 100, 40);
        }
        bitmap.draw(game.batch, "Best Score: " + MultiInvadersScreen.getHighscore(), (Gdx.graphics.getWidth() - 450) / 2, Gdx.graphics.getHeight() - 20);

        if (paused) {
            game.batch.setColor(0.7f, 0.7f, 0.7f, 0.7f);
            bitmap.setColor(0.7f, 0.7f, 0.7f, 0.7f);
            bitmap.draw(game.batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: ", 20, Gdx.graphics.getHeight() - 20);
            if (ship1.getLife() == 5) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 210, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 245, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 280, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 4) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 210, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 245, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 3) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 175, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 210, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 2) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, 175, Gdx.graphics.getHeight() - 167);
            } else if (ship1.getLife() == 1) {
                game.batch.draw(shipLife, 140, Gdx.graphics.getHeight() - 167);
            }

            bitmap.draw(game.batch, "Player 2\nScore: " + ship2.getScore() + "\nLife: ", Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 20);
            if (ship2.getLife() == 5) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 195, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 160, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 125, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 90, Gdx.graphics.getHeight() - 167);
            } else if (ship2.getLife() == 4) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 195, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 160, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 125, Gdx.graphics.getHeight() - 167);
            } else if (ship2.getLife() == 3) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 195, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 160, Gdx.graphics.getHeight() - 167);
            } else if (ship2.getLife() == 2) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 195, Gdx.graphics.getHeight() - 167);
            } else if (ship2.getLife() == 1) {
                game.batch.draw(shipLife, Gdx.graphics.getWidth() - 230, Gdx.graphics.getHeight() - 167);
            }
            backgroundMusic.pause();
            life1.getMusic1().pause();
            bulletMode1.getMusic1().pause();
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
            this.ship1.moveBullet1();
            this.ship1.moveSpaceship1();
            this.ship2.moveBullet2();
            this.ship2.moveSpaceship2();

            this.blueAlien.move(ship1, ship2);

            this.life1.move(ship1, ship2);
            this.bulletMode1.move(ship1, ship2);


            if (allTime > 30.0f) {
                this.meteor.move(ship1, ship2);
            }

            if (allTime > 60.0f) {
                this.eye1.move();
                this.eye1.moveBullet(ship1);
            }

            if (allTime > 60.0f) {
                this.eye2.move();
                this.eye2.moveBullet(ship2);
            }

            if (allTime > 100.0f) {
                if (allTime < 101.0f) {
                    if (ship1.getLife() <= 5 || ship2.getLife() <= 5) {
                        ship1.setLife(5);
                        ship1.setGameover(false);
                        ship2.setLife(5);
                        ship2.setGameover(false);
                    }
                    boss.getEntrence().play();
                }

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
        if (Gdx.input.getX() < (Gdx.graphics.getWidth() + quitButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2 && Gdx.graphics.getHeight() - Gdx.input.getY() + 200 < (float) (Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 + quitButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 200 > (float) (Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2) {
            game.batch.draw(quitButtonActive, (float) (Gdx.graphics.getWidth() - quitButtonActive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - quitButtonActive.getHeight()) / 2 - 200);
            if (Gdx.input.justTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(quitButtonInactive, (float) (Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 - 200);
        }
        if (Gdx.input.getX() < (Gdx.graphics.getWidth() + resumeButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - resumeButtonInactive.getWidth()) / 2 && Gdx.graphics.getHeight() - Gdx.input.getY() < (float) (Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2 + resumeButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() > (float) (Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2) {
            game.batch.draw(resumeButtonActive, (float) (Gdx.graphics.getWidth() - resumeButtonActive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - resumeButtonActive.getHeight()) / 2);
            if (Gdx.input.justTouched()) {
                bitmap.setColor(1f, 1f, 1f, 1f);
                paused = false;
                game.batch.setColor(1f, 1f, 1f, 1f);
            }
        } else {
            game.batch.draw(resumeButtonInactive, (float) (Gdx.graphics.getWidth() - resumeButtonInactive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - resumeButtonInactive.getHeight()) / 2);
        }

        if (Gdx.input.getX() < (Gdx.graphics.getWidth() + menuButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2 && Gdx.graphics.getHeight() - Gdx.input.getY() + 100 < (float) (Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 + menuButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 100 > (float) (Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2) {
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