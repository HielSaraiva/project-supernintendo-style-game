package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class BulletMode {
    private Texture texture;
    private Sprite sprite;
    private Sound sound1;
    private Music music1;
    private float time;
    private boolean collision;

    public BulletMode() {
        collision = false;
        time = 0;
        texture = new Texture(Gdx.files.internal("pictures/inGame/consumables/bullet_mode.png"));
        sprite = new Sprite(texture);

        sprite.setX(0 - sprite.getWidth());
        sprite.setY(MathUtils.random(0, Gdx.graphics.getHeight() - sprite.getHeight()));

        music1 = Gdx.audio.newMusic(Gdx.files.internal("audio/moments/moments4.mp3"));
        sound1 = Gdx.audio.newSound(Gdx.files.internal("audio/got/got6.mp3"));
    }

    public void move(Spaceship ship) {
        if (ship.getLife() <= 5) {
            music1.setVolume(8.0f);
            music1.play();
            music1.setLooping(true);
            sprite.setX(sprite.getX() + 300 * Gdx.graphics.getDeltaTime());
            sprite.setY(sprite.getY() - 300 * Gdx.graphics.getDeltaTime());
            if (sprite.getY() + sprite.getHeight() < 0) {
                sprite.setY(Gdx.graphics.getHeight());
            }
            if (sprite.getX() >= Gdx.graphics.getWidth()) {
                time += Gdx.graphics.getDeltaTime();
                music1.stop();
                sprite.setX(Gdx.graphics.getWidth());
            }
        }
    }

    public void move(Spaceship ship, Spaceship ship2) {
        if (ship.getLife() <= 5 || ship2.getLife() <= 5) {
            music1.setVolume(8.0f);
            music1.play();
            music1.setLooping(true);
            sprite.setX(sprite.getX() + 300 * Gdx.graphics.getDeltaTime());
            sprite.setY(sprite.getY() - 300 * Gdx.graphics.getDeltaTime());
            if (sprite.getY() + sprite.getHeight() < 0) {
                sprite.setY(Gdx.graphics.getHeight());
            }
            if (sprite.getX() >= Gdx.graphics.getWidth()) {
                time += Gdx.graphics.getDeltaTime();
                music1.stop();
                sprite.setX(Gdx.graphics.getWidth());
            }
        }
    }

    public boolean lifeCollision(Spaceship ship) {
        // Ship Bullet x Life
        if (Collision.collide(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), ship.getBullet1().getX(), ship.getBullet1().getY(), ship.getBullet1().getSprite().getWidth(), ship.getBullet1().getSprite().getHeight()) && ship.isAttack()) {
            if (sprite.getX() != Gdx.graphics.getWidth()) {
                collision = true;
                ship.setFactor(12.0f);
                Spaceship.setTimeOut(0.35f);
                music1.stop();
                sound1.play(2.0f);
                ship.setAttack(false);
            }
            return true;
            // Ship x Life
        } else if (Collision.collide(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), ship.getX(), ship.getY(), (float) Spaceship.SHIP_WIDTH, (float) Spaceship.SHIP_HEIGTH) && !ship.isGameover()) {
            if (sprite.getX() != Gdx.graphics.getWidth()) {
                collision = true;
                ship.setFactor(12.0f);
                Spaceship.setTimeOut(0.35f);
                music1.stop();
                sound1.play(2.0f);
                ship.setAttack(false);
            }
            return true;
        }
        return false;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sound getSound1() {
        return sound1;
    }

    public void setSound1(Sound sound1) {
        this.sound1 = sound1;
    }

    public Music getMusic1() {
        return music1;
    }

    public void setMusic1(Music music1) {
        this.music1 = music1;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
