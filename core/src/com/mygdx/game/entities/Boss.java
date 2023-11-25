package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.audio.Sound;


public class Boss {
    private final static float TIME_OUT = 1.75f;
    private Texture texture, bomb;
    private Sprite sprite;
    private Sprite bombSprite, bombSprite1, bombSprite2, bombSprite3;
    private float velocity = 240 * Gdx.graphics.getDeltaTime();
    private float time;
    private Sound explosionBomb, scream, soundBullet;
    private Music entrence;
    private int life = 30;


    public Boss(String texturePathEye, String TexturePathBomb) {
        texture = new Texture(Gdx.files.internal(texturePathEye));
        sprite = new Sprite(texture);


        bomb = new Texture(Gdx.files.internal(TexturePathBomb));
        bombSprite = new Sprite(bomb);
        bombSprite1 = new Sprite(bomb);
        bombSprite2 = new Sprite(bomb);
        bombSprite3 = new Sprite(bomb);

        bombSprite.setX(-100);
        bombSprite.setY(-100);
        bombSprite1.setX(-100);
        bombSprite1.setY(-100);
        bombSprite2.setX(-100);
        bombSprite2.setY(-100);
        bombSprite3.setX(-100);
        bombSprite3.setY(-100);

        explosionBomb = Gdx.audio.newSound(Gdx.files.internal("audio/explosions/explosion3.mp3"));
        soundBullet = Gdx.audio.newSound(Gdx.files.internal("audio/explosions/explosion1.wav"));
        scream = Gdx.audio.newSound(Gdx.files.internal("audio/scream/scream1.wav"));
        entrence = Gdx.audio.newMusic(Gdx.files.internal("audio/belligol.mp3"));

        sprite.setX(-sprite.getWidth() - texture.getWidth());
        sprite.setY(0.0f);
    }

    public void move() {
        if (sprite.getX() + sprite.getWidth() + 10 < Gdx.graphics.getWidth()) {
            sprite.setX(sprite.getX() + 500 * Gdx.graphics.getDeltaTime());
        } else {
            sprite.setY(sprite.getY() + velocity);
            if (sprite.getY() + sprite.getHeight() > Gdx.graphics.getHeight() || sprite.getY() < 0.0f) {
                velocity = velocity * (-1);
            }
        }
    }

    public void moveBomb() {
        time += Gdx.graphics.getDeltaTime();
        if (time > TIME_OUT) {
            bombSprite.setX(MathUtils.random(0.0f + bombSprite.getWidth() - 15.0f, Gdx.graphics.getWidth() - bombSprite.getWidth() - sprite.getWidth()));
            bombSprite.setY(MathUtils.random(0.0f + bombSprite.getHeight() - 30.0f, Gdx.graphics.getHeight() - bombSprite.getHeight()));
            bombSprite1.setX(MathUtils.random(0.0f + bombSprite.getWidth() - 15.0f, Gdx.graphics.getWidth() - bombSprite1.getWidth() - sprite.getWidth()));
            bombSprite1.setY(MathUtils.random(0.0f + bombSprite.getHeight() - 30.0f, Gdx.graphics.getHeight() - bombSprite1.getHeight()));
            bombSprite2.setX(MathUtils.random(0.0f + bombSprite.getWidth() - 15.0f, Gdx.graphics.getWidth() - bombSprite2.getWidth() - sprite.getWidth()));
            bombSprite2.setY(MathUtils.random(0.0f + bombSprite.getHeight() - 30.0f, Gdx.graphics.getHeight() - bombSprite2.getHeight()));
            bombSprite3.setX(MathUtils.random(0.0f + bombSprite.getWidth() - 15.0f, Gdx.graphics.getWidth() - bombSprite3.getWidth() - sprite.getWidth()));
            bombSprite3.setY(MathUtils.random(0.0f + bombSprite.getHeight() - 30.0f, Gdx.graphics.getHeight() - bombSprite3.getHeight()));
            time = 0;
        }
    }

    public boolean shipBombCollision(Spaceship ship) {
        if (Collision.collide(bombSprite.getX(), bombSprite.getY(), bombSprite.getWidth(), bombSprite.getHeight(), ship.getX(), ship.getY(), (float) Spaceship.SHIP_WIDTH, (float) Spaceship.SHIP_HEIGTH)) {
            explosionBomb.play(2.0f);
            bombSprite.setX(MathUtils.random(0.0f + bombSprite.getWidth() - 15.0f, Gdx.graphics.getWidth() - bombSprite.getWidth() - sprite.getWidth()));
            bombSprite.setY(MathUtils.random(0.0f + bombSprite.getHeight() - 30.0f, Gdx.graphics.getHeight() - bombSprite.getHeight()));
            return true;
        }
        if (Collision.collide(bombSprite1.getX(), bombSprite1.getY(), bombSprite1.getWidth(), bombSprite1.getHeight(), ship.getX(), ship.getY(), (float) Spaceship.SHIP_WIDTH, (float) Spaceship.SHIP_HEIGTH)) {
            explosionBomb.play(2.0f);
            bombSprite1.setX(MathUtils.random(0.0f + bombSprite.getWidth() - 15.0f, Gdx.graphics.getWidth() - bombSprite1.getWidth() - sprite.getWidth()));
            bombSprite1.setY(MathUtils.random(0.0f + bombSprite.getHeight() - 30.0f, Gdx.graphics.getHeight() - bombSprite1.getHeight()));
            return true;
        }
        if (Collision.collide(bombSprite2.getX(), bombSprite2.getY(), bombSprite2.getWidth(), bombSprite2.getHeight(), ship.getX(), ship.getY(), (float) Spaceship.SHIP_WIDTH, (float) Spaceship.SHIP_HEIGTH)) {
            explosionBomb.play(2.0f);
            bombSprite2.setX(MathUtils.random(0.0f + bombSprite.getWidth() - 15.0f, Gdx.graphics.getWidth() - bombSprite2.getWidth() - sprite.getWidth()));
            bombSprite2.setY(MathUtils.random(0.0f + bombSprite.getHeight() - 30.0f, Gdx.graphics.getHeight() - bombSprite2.getHeight()));
            return true;
        }
        if (Collision.collide(bombSprite3.getX(), bombSprite3.getY(), bombSprite3.getWidth(), bombSprite3.getHeight(), ship.getX(), ship.getY(), (float) Spaceship.SHIP_WIDTH, (float) Spaceship.SHIP_HEIGTH)) {
            explosionBomb.play(2.0f);
            bombSprite3.setX(MathUtils.random(0.0f + bombSprite.getWidth() - 15.0f, Gdx.graphics.getWidth() - bombSprite3.getWidth() - sprite.getWidth()));
            bombSprite3.setY(MathUtils.random(0.0f + bombSprite.getHeight() - 30.0f, Gdx.graphics.getHeight() - bombSprite3.getHeight()));
            return true;
        }
        return false;
    }

    public boolean bulletBossCollision(Spaceship ship) {
        if (Collision.collide(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), ship.getBullet1().getX(), ship.getBullet1().getY(), ship.getBullet1().getSprite().getWidth(), ship.getBullet1().getSprite().getHeight()) && ship.isAttack()) {
            if (sprite.getX() != Gdx.graphics.getWidth()) {
                ship.setScore(ship.getScore() + 550);
                ship.setAttack(false);
                scream.play(0.5f);
                soundBullet.play();
            }
            return true;
        }
        return false;
    }

    public Music getEntrence() {
        return entrence;
    }

    public void setEntrence(Music entrence) {
        this.entrence = entrence;
    }

    public void stopMusic() {
        entrence.stop();
    }

    public void pauseMusic() {
        entrence.pause();
    }

    public void playMusic() {
        entrence.play();
    }


    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Sprite getBombSprite() {
        return bombSprite;
    }

    public Sprite getBombSprite1() {
        return bombSprite1;
    }

    public Sprite getBombSprite2() {
        return bombSprite2;
    }

    public void setBombSprite2(Sprite bombSprite2) {
        this.bombSprite2 = bombSprite2;
    }

    public Sprite getBombSprite3() {
        return bombSprite3;
    }

    public void setBombSprite3(Sprite bombSprite3) {
        this.bombSprite3 = bombSprite3;
    }

    public void setBombSprite1(Sprite bombSprite1) {
        this.bombSprite1 = bombSprite1;
    }

    public void setBombSprite(Sprite bombSprite) {
        this.bombSprite = bombSprite;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
