package com.mygdx.game;

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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class InvadersScreen implements Screen {
    final SpaceInvaders game;
    private SpriteBatch batch;
    private Texture wallpaperScreen;
    private Texture textureSpaceship1;
    private Texture textureBulletSpaceship1;
    private Texture textureEnemy1;
    private Array<Rectangle> enemies1;
    private long lastEnemy1Time;
    private Music backgroundMusic;
    private Sound soundShot1;
    private OrthographicCamera camera;
    private Sprite spaceship1;
    private float posXShip1, posYShip1;
    private Sprite bullet1;
    private float posXBullet1, posYBullet1;
    private boolean attackShip1;
    private boolean gameoverShip1;
    private final float VELOCITY = 300 * Gdx.graphics.getDeltaTime();
    private int scorePlayer1,scoreFinalPlayer1, lifePlayer1, numEnemies1;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;


    public InvadersScreen(final SpaceInvaders game) {
        this.game = game;

        // Load the image for the spaceship of player 1 and set his initial position
        textureSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/player1/base.png"));
        spaceship1 = new Sprite(textureSpaceship1);
        posXShip1 = 20;
        posYShip1 = (Gdx.graphics.getHeight() - spaceship1.getHeight())/2;

        // Load the image for the bullet of spaceship 1, set his initial position and his attack boolean
        textureBulletSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/bullet/bullet1.png"));
        bullet1 = new Sprite(textureBulletSpaceship1);
        posXBullet1 = posXShip1;
        posYBullet1 = posYShip1;
        attackShip1 = false;

        // Load the image for the enemies type 1
        textureEnemy1 = new Texture(Gdx.files.internal("pictures/inGame/enemies/aliens/alien1.png"));
        enemies1 = new Array<Rectangle>();
        lastEnemy1Time = 0;

        // Set the pre-configs of Player1
        scorePlayer1 = 0;
        lifePlayer1 = 3;
        gameoverShip1 = false;

        // Set the time appear for enemies1
        numEnemies1 = 799999999;

        // Load the font of the game text screen
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font3.ttf"));
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
        soundShot1 = Gdx.audio.newSound(Gdx.files.internal("audio/bullets/bullet1.mp3"));

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
        if(!gameoverShip1) {
            if(attackShip1) {
                batch.draw(bullet1, posXBullet1, posYBullet1);
            }
            batch.draw(spaceship1, posXShip1, posYShip1);

            for(Rectangle enemy : enemies1) {
                batch.draw(textureEnemy1, enemy.x, enemy.y);
            }
            bitmap.draw(batch, "Player 1\nScore: " + scorePlayer1 + "\nLife: " + lifePlayer1, 20, Gdx.graphics.getHeight() - 20);
        } else {
            bitmap.draw(batch, "Player 1\nFinal Score: " + scoreFinalPlayer1 + "\nGAMEOVER PLAYER1", 20, Gdx.graphics.getHeight() - 20);
            soundShot1.pause();
            backgroundMusic.stop();

            // Reinitiate the game when ENTER is pressed
            if(Gdx.input.isKeyPressed(Keys.ENTER)) {
                scoreFinalPlayer1 = 0;
                gameoverShip1 = false;
                scorePlayer1 = 0;
                lifePlayer1 = 3;
                enemies1.clear();
                posXShip1 = 20;
                posYShip1 = (Gdx.graphics.getHeight() - spaceship1.getHeight())/2;
                backgroundMusic.play();
                backgroundMusic.setLooping(true);
            }
        }

        // Making the spaceship 1 moves and add sound shot
        Sound soundShot1 = Gdx.audio.newSound(Gdx.files.internal("audio/bullets/bullet1.mp3"));
        this.moveBullet1();
        this.moveEnemies1();

        this.moveSpaceship1();

        batch.end();
    }

    public void moveSpaceship1() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if(posXShip1 > 0){
                posXShip1 -= VELOCITY;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(posXShip1 < Gdx.graphics.getWidth() - spaceship1.getWidth()){
                posXShip1 += VELOCITY;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(posYShip1 > 0){
                posYShip1 -= VELOCITY;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(posYShip1 < Gdx.graphics.getHeight() - spaceship1.getHeight()){
                posYShip1 += VELOCITY;
            }
        }
    }

    public void moveBullet1() {
        if(Gdx.input.isKeyJustPressed(Keys.SPACE) && !attackShip1) {
            attackShip1 = true;
            posYBullet1 = posYShip1 + spaceship1.getHeight()/2 - 5;
            soundShot1.play();
        }

        if(attackShip1) {
            if(posXBullet1 < Gdx.graphics.getWidth()){
                posXBullet1 += 8.0f * VELOCITY;
            } else {
                posXBullet1 = posXShip1 + spaceship1.getWidth() / 2;
                posYBullet1 = posYShip1;
                attackShip1 = false;
            }
        } else {
            posXBullet1 = posXShip1 + spaceship1.getWidth() / 2;
            posYBullet1 = posYShip1 + spaceship1.getHeight() / 2 - 5;
        }
    }

    public void spawnEnemies1() {
        Rectangle enemy = new Rectangle(Gdx.graphics.getWidth(), MathUtils.random(0, Gdx.graphics.getHeight() - textureEnemy1.getHeight()), textureEnemy1.getWidth(), textureEnemy1.getHeight());
        enemies1.add(enemy);
        lastEnemy1Time = TimeUtils.nanoTime();
    }

    public void moveEnemies1() {
        if(TimeUtils.nanoTime() - lastEnemy1Time > numEnemies1){
            this.spawnEnemies1();
        }

        for(Iterator<Rectangle> iter = enemies1.iterator(); iter.hasNext();) {
            Rectangle enemy = iter.next();
            enemy.x -= 200 * Gdx.graphics.getDeltaTime();

            // Colisão do inimigo com o missel
            if(collide(enemy.x, enemy.y, enemy.width, enemy.height, posXBullet1, posYBullet1, bullet1.getWidth(), bullet1.getHeight()) && attackShip1) {
                ++scorePlayer1;
                if(scorePlayer1 % 10 == 0) {
                    numEnemies1 -= 10000;
                }
                attackShip1 = false;
                iter.remove();
            // Colisao do inimigo com a spaceship 1
            } else if( collide(enemy.x, enemy.y, enemy.width, enemy.height, posXShip1, posYShip1, spaceship1.getWidth(), spaceship1.getHeight()) && !gameoverShip1) {
                --lifePlayer1;
                if(lifePlayer1 <= 0 ) {
                    scoreFinalPlayer1 = scorePlayer1;
                    gameoverShip1 = true;
                }
                iter.remove();
            }
            if(enemy.x + textureEnemy1.getWidth() < 0) {
                iter.remove();
            }
        }
    }

    public boolean collide(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return (x1 + w1 > x2 && x1 < x2 + w2 && y1 + h1 > y2 && y1 < y2 + h2);
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