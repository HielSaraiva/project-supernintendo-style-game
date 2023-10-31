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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.SpaceInvaders;
import com.mygdx.game.entities.Spaceship;
import sun.jvm.hotspot.gc.shared.Space;

import java.util.Iterator;

public class InvadersScreen implements Screen {
    final SpaceInvaders game;
    private Spaceship ship1;
    private SpriteBatch batch;
    private Texture wallpaperScreen;
    private Texture textureBulletSpaceship1;
    private Texture textureEnemy1;
    private Array<Rectangle> enemies1;
    private long lastEnemy1Time;
    private Music backgroundMusic;
    private Sound soundShot1;
    private OrthographicCamera camera;
    private Sprite bullet1;
    private float posXBullet1, posYBullet1;
    private int numEnemies1;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;


    public InvadersScreen(final SpaceInvaders game) {
        this.game = game;

        // Creating spaceship 1
        ship1 = new Spaceship("pictures/inGame/player1/base.png");

        // Load the image for the bullet of spaceship 1, set his initial position and his attack boolean
        textureBulletSpaceship1 = new Texture(Gdx.files.internal("pictures/inGame/bullet/bullet1.png"));
        bullet1 = new Sprite(textureBulletSpaceship1);
        posXBullet1 = ship1.getX();
        posYBullet1 = ship1.getY();
        ship1.setAttack(false);

        // Load the image for the enemies type 1
        textureEnemy1 = new Texture(Gdx.files.internal("pictures/inGame/enemies/aliens/alien1.png"));
        enemies1 = new Array<Rectangle>();
        lastEnemy1Time = 0;

        // Set the pre-configs of Player1
        ship1.setScore(0);
        ship1.setLife(3);
        ship1.setGameover(false);

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
        if(!ship1.isGameover()) {
            if(ship1.isAttack()) {
                batch.draw(bullet1, posXBullet1, posYBullet1);
            }
            batch.draw(ship1.getSprite(), ship1.getX(), ship1.getY());

            for(Rectangle enemy : enemies1) {
                batch.draw(textureEnemy1, enemy.x, enemy.y);
            }
            bitmap.draw(batch, "Player 1\nScore: " + ship1.getScore() + "\nLife: " + ship1.getLife(), 20, Gdx.graphics.getHeight() - 20);
        } else {
            bitmap.draw(batch, "Player 1\nFinal Score: " + ship1.getFinalScore() + "\nGAMEOVER PLAYER1", 20, Gdx.graphics.getHeight() - 20);
            soundShot1.pause();
            backgroundMusic.stop();

            // Reinitiate the game when ENTER is pressed
            if(Gdx.input.isKeyPressed(Keys.ENTER)) {
                ship1.setFinalScore(0);
                ship1.setGameover(false);
                ship1.setScore(0);
                ship1.setLife(3);
                enemies1.clear();
                ship1.setX(20);
                ship1.setY((Gdx.graphics.getHeight() - ship1.getSprite().getHeight())/2);
                backgroundMusic.play();
                backgroundMusic.setLooping(true);
            }
        }

        // Making the spaceship 1 moves and add sound shot
        Sound soundShot1 = Gdx.audio.newSound(Gdx.files.internal("audio/bullets/bullet1.mp3"));
        this.moveBullet1();
        this.moveEnemies1();
        this.ship1.moveSpaceship();
        batch.end();
    }

    public void moveBullet1() {
        if(Gdx.input.isKeyJustPressed(Keys.SPACE) && !ship1.isAttack()) {
            ship1.setAttack(true);
            posYBullet1 = ship1.getY() + ship1.getSprite().getHeight()/2 - 5;
            soundShot1.play();
        }

        if(ship1.isAttack()) {
            if(posXBullet1 < Gdx.graphics.getWidth()){
                posXBullet1 += 8.0f * ship1.getVELOCITY();
            } else {
                posXBullet1 = ship1.getX() + ship1.getSprite().getWidth() / 2;
                posYBullet1 = ship1.getY();
                ship1.setAttack(false);
            }
        } else {
            posXBullet1 = ship1.getX() + ship1.getSprite().getWidth() / 2;
            posYBullet1 = ship1.getY() + ship1.getSprite().getHeight() / 2 - 5;
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
            if(collide(enemy.x, enemy.y, enemy.width, enemy.height, posXBullet1, posYBullet1, bullet1.getWidth(), bullet1.getHeight()) && ship1.isAttack()) {
                ship1.setScore(ship1.getScore() + 1);
                if(ship1.getScore() % 10 == 0) {
                    numEnemies1 += 10000;
                }
                ship1.setAttack(false);
                iter.remove();
            // Colisao do inimigo com a spaceship 1
            } else if( collide(enemy.x, enemy.y, enemy.width, enemy.height, ship1.getX(), ship1.getY(), ship1.getSprite().getWidth(), ship1.getSprite().getHeight()) && !ship1.isGameover()) {
                ship1.setLife(ship1.getLife() - 1);
                if(ship1.getLife() <= 0 ) {
                    ship1.setFinalScore(ship1.getScore());
                    ship1.setGameover(true);
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
        soundShot1.dispose();
        batch.dispose();
    }
}