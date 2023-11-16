package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class Eye {
    private final static float TIME_OUT = 2.5f;
    private Spaceship ship;
    private Texture texture;
    private Sprite sprite;
    private Bullet bullet;
    private boolean attack;
    private float time;
    private ArrayList<Explosion> explosions1, explosions2;
    private Sound sound1, sound2;

    public Eye(String texturePathEye, Spaceship ship) {
        this.ship = ship;

        texture = new Texture(Gdx.files.internal(texturePathEye));
        sprite = new Sprite(texture);

        attack = false;
        time = 0.0f;

        bullet = new Bullet("pictures/inGame/bullet/bullet3.png", "audio/bullets/bullet8.wav");

        sprite.setX(Gdx.graphics.getWidth());
        sprite.setY(MathUtils.random(0, Gdx.graphics.getHeight() - sprite.getHeight()));

        this.bullet.setX(sprite.getX() + 5);
        this.bullet.setY(sprite.getY() + 10);

        explosions1 = new ArrayList<>();
        explosions2 = new ArrayList<>();
        sound1 = Gdx.audio.newSound(Gdx.files.internal("audio/explosions/explosion1.wav"));
        sound2 = Gdx.audio.newSound(Gdx.files.internal("audio/explosions/explosion2.mp3"));
    }

    public void move() {
        sprite.setX(sprite.getX() - 100 * Gdx.graphics.getDeltaTime());
    }

    public void moveBullet() {
        time += Gdx.graphics.getDeltaTime();
        if (time > TIME_OUT && !isAttack()) {
            setAttack(true);
            bullet.setY((sprite.getY() + sprite.getHeight() / 2 - 10));
            bullet.getSound().play();
            time = 0;
        }

        if (isAttack()) {
            if (bullet.getX() < Gdx.graphics.getWidth() && bullet.getX() > 0 && bullet.getY() < Gdx.graphics.getHeight() && bullet.getY() > 0) {
                float deltaX, deltaY;
                deltaX = ship.getX() - sprite.getX();
                deltaY = ship.getY() - sprite.getY();

                bullet.setX(bullet.getX() + deltaX * Gdx.graphics.getDeltaTime());
                bullet.setY(bullet.getY() + deltaY * Gdx.graphics.getDeltaTime());
            } else {
                bullet.setX(sprite.getX() + sprite.getWidth() / 2 - 15);
                bullet.setY(sprite.getY());
                setAttack(false);
            }
        } else {
            bullet.setX(sprite.getX() + sprite.getWidth() / 2 - 15);
            bullet.setY(sprite.getY() + sprite.getHeight() / 2 - 10);
        }
    }

    public boolean eyeBulletCollision() {
        // Ship Bullet x Enemy
        if(Collision.collide(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), ship.getBullet1().getX(), ship.getBullet1().getY(), ship.getBullet1().getSprite().getWidth(), ship.getBullet1().getSprite().getHeight()) && ship.isAttack()) {
            ship.setScore(ship.getScore() + 300);
            sound1.play();
            explosions1.add(new Explosion(sprite.getX(), sprite.getY(), 64,"pictures/inGame/explosion/explosion2.png"));
            ship.setAttack(false);

            ArrayList<Explosion> explosionsToRemove1 = new ArrayList<>();
            for(Explosion explosion : explosions1) {
                explosion.update(Gdx.graphics.getDeltaTime());
                if(explosion.isRemove()) {
                    explosionsToRemove1.add(explosion);
                }
            }
            explosions1.removeAll(explosionsToRemove1);
            return true;
            // Ship x Enemy
        } else if(Collision.collide(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), ship.getX(), ship.getY(), (float)Spaceship.SHIP_WIDTH, (float)Spaceship.SHIP_HEIGTH) && !ship.isGameover()) {
            ship.setLife(ship.getLife() - 1);
            if(ship.getLife() <= 0 ) {
                ship.setFinalScore(ship.getScore());
                ship.setGameover(true);
            }
            sound2.play();
            explosions2.add(new Explosion(sprite.getX(), sprite.getY(), 64,"pictures/inGame/explosion/explosion2.png"));

            ArrayList<Explosion> explosionsToRemove2 = new ArrayList<>();
            for(Explosion explosion : explosions2) {
                explosion.update(Gdx.graphics.getDeltaTime());
                if(explosion.isRemove()) {
                    explosionsToRemove2.add(explosion);
                }
            }
            explosions2.removeAll(explosionsToRemove2);
            return true;
        }
        return false;
    }

    public Spaceship getShip() {
        return ship;
    }

    public void setShip(Spaceship ship) {
        this.ship = ship;
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

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

}
