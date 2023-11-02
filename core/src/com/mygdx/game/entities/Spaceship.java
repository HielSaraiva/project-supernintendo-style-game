package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spaceship {
    private static int totalSpaceships = 0;
    public static final float VELOCITY = 300 * Gdx.graphics.getDeltaTime();
    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL= 32;
    public static final int SHIP_HEIGTH_PIXEL = 18;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGTH = SHIP_HEIGTH_PIXEL * 3;
    public static final float ROLL_TIMER_SWITCH_TIME = 0.15f;
    private Bullet bullet;
    private float x, y;
    private boolean attack;
    private boolean gameover;
    private int score, finalScore;
    private int life;
    public Animation[] rolls;
    private TextureRegion[][] rollSpriteSheet;
    public int roll;
    private float stateTime;
    private float rollTimer;

    public Spaceship(String texturePathSpaceShip, Bullet bullet) {
        this.bullet = bullet;
        if(totalSpaceships == 0){
            x = 20;
            y = (float)(Gdx.graphics.getHeight() - SHIP_HEIGTH_PIXEL) / 2;

            roll = 2;
            rollTimer = 0.0f;
            rolls = new Animation[5];

            rollSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal(texturePathSpaceShip)), SHIP_WIDTH_PIXEL, SHIP_HEIGTH_PIXEL);

            rolls[0] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]); // All up
            rolls[1] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);
            rolls[2] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]); // No tilt
            rolls[3] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);
            rolls[4] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]); // All down

            attack = false;
            score = 0;
            life = 3;
            gameover = false;
            ++totalSpaceships;

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
            if(x < Gdx.graphics.getWidth() - (float)SHIP_WIDTH){
                x += VELOCITY;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(y > 0){
                y -= VELOCITY;

                // Update roll if button just clicked
                if(Gdx.input.isKeyJustPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.W) && roll < 4) {
                    rollTimer = 0;
                    roll++;
                }

                //Update roll
                rollTimer += Gdx.graphics.getDeltaTime();
                if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer = 0.0f;
                    roll++;
                }
            }
        } else {
            if(roll > 2) {
                //Update roll to go back to center
                rollTimer -= Gdx.graphics.getDeltaTime();
                if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0.0f;
                    roll--;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(y < Gdx.graphics.getHeight() - (float)SHIP_HEIGTH){
                y += VELOCITY;
                //Update roll if button just clicked
                if(Gdx.input.isKeyJustPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S) && roll > 0) {
                    rollTimer = 0;
                    roll--;
                }

                //Update roll
                rollTimer -= Gdx.graphics.getDeltaTime();
                if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0.0f;
                    roll--;
                }
            }
        } else {
            if(roll < 2) {
                //Update roll to go back to center
                rollTimer += Gdx.graphics.getDeltaTime();
                if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer = 0.0f;
                    roll++;
                }
            }
        }
    }

    public void moveBullet() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isAttack()) {
            setAttack(true);
            bullet.setY((getY() + (float) SHIP_HEIGTH / 2 - 5));
            bullet.getSound().play();
        }

        if(isAttack()) {
            if(bullet.getX() < Gdx.graphics.getWidth()){
                bullet.setX(bullet.getX() + 8.0f * VELOCITY);
            } else {
                bullet.setX(getX() + (float)SHIP_WIDTH / 2);
                bullet.setY(getY());
                setAttack(false);
            }
        } else {
            bullet.setX(getX() + (float)SHIP_WIDTH / 2);
            bullet.setY(getY() + (float) SHIP_HEIGTH / 2 - 5);
        }
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
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

    public Animation[] getRolls() {
        return rolls;
    }

    public void setRolls(Animation[] rolls) {
        this.rolls = rolls;
    }

    public TextureRegion[][] getRollSpriteSheet() {
        return rollSpriteSheet;
    }

    public void setRollSpriteSheet(TextureRegion[][] rollSpriteSheet) {
        this.rollSpriteSheet = rollSpriteSheet;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
