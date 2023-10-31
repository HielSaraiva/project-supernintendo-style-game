package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Spaceship {
    private static int totalSpaceships = 0;
    private final float VELOCITY = 300 * Gdx.graphics.getDeltaTime();
    private Texture texture;
    private Sprite sprite;
    private Bullet bullet;
    private float x, y;
    private boolean attack;
    private boolean gameover;
    private int score, finalScore;
    private int life;

    public Spaceship(String texturePathSpaceShip, Bullet bullet) {
        this.bullet = bullet;
        if(totalSpaceships == 0){
            texture = new Texture(Gdx.files.internal(texturePathSpaceShip));
            sprite = new Sprite(texture);
            x = 20;
            y = (Gdx.graphics.getHeight() - sprite.getHeight()) / 2;
            attack = false;
            score = 0;
            life = 3;
            gameover = false;
            ++totalSpaceships;

            this.bullet.setX(x);
            this.bullet.setY(y);
        } else {
            texture = new Texture(Gdx.files.internal(texturePathSpaceShip));
            sprite = new Sprite(texture);
            x = 100;
            y = (Gdx.graphics.getHeight() - sprite.getHeight()) / 2;
            attack = false;
            score = 0;
            life = 3;
            gameover = false;

            this.bullet.setX(x);
            this.bullet.setY(y);
        }
    }

    public void moveSpaceship() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if(x > 0){
                x -= VELOCITY;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(x < Gdx.graphics.getWidth() - sprite.getWidth()){
                x += VELOCITY;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(y > 0){
                y -= VELOCITY;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(y < Gdx.graphics.getHeight() - sprite.getHeight()){
                y += VELOCITY;
            }
        }
    }

    public void moveBullet() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isAttack()) {
            setAttack(true);
            bullet.setY(getY() + getSprite().getHeight()/2 - 5);
            bullet.getSound().play();
        }

        if(isAttack()) {
            if(bullet.getX() < Gdx.graphics.getWidth()){
                bullet.setX(bullet.getX() + 8.0f * getVELOCITY());
            } else {
                bullet.setX(getX() + getSprite().getWidth() / 2);
                bullet.setY(getY());
                setAttack(false);
            }
        } else {
            bullet.setX(getX() + getSprite().getWidth() / 2);
            bullet.setY(getY() + getSprite().getHeight() / 2 - 5);
        }
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public float getVELOCITY() {
        return VELOCITY;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean isGameover() {
        return gameover;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
