package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spaceship {
    public static final float VELOCITY = 500 * Gdx.graphics.getDeltaTime();
    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 32;
    public static final int SHIP_HEIGTH_PIXEL = 18;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGTH = SHIP_HEIGTH_PIXEL * 3;
    public static final float ROLL_TIMER_SWITCH_TIME = 0.10f;
    public static int numSpaceships = 0;
    private Bullet bullet1;
    private float x, y, factor;
    private boolean attack;
    private boolean gameover;
    private int score, finalScore;
    private int life;
    public Animation[] rolls;
    private TextureRegion[][] rollSpriteSheet;
    public int roll;
    private float stateTime;
    private float rollTimer;
    private float time;
    private static float TIME_OUT = 1.00f;

    public Spaceship(String texturePathSpaceShip, Bullet bullet1) {

        this.bullet1 = bullet1;
        numSpaceships += 1;

        //Setting the initial coordinates of spaceship
        x = 20;
        y = (float) (Gdx.graphics.getHeight() - SHIP_HEIGTH_PIXEL) / 2;
        factor = 8.0f;

        //Setting the initial animation configs of spaceship
        roll = 2;
        rollTimer = 0.0f;
        rolls = new Animation[5];

        //Catching all the textures of spaceship png
        rollSpriteSheet = TextureRegion.split(new Texture(Gdx.files.internal(texturePathSpaceShip)), SHIP_WIDTH_PIXEL, SHIP_HEIGTH_PIXEL);

        //Setting all the possibilities animations configs
        rolls[0] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]); // All up
        rolls[1] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);
        rolls[2] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]); // No tilt
        rolls[3] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);
        rolls[4] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]); // All down

        //Setting the initial conditions of spaceship
        attack = false;
        score = 0;
        life = 5;
        gameover = false;
        time = 1.25f;

        //Setting the position of bullet
        this.bullet1.setX(x);
        this.bullet1.setY(y);

    }

    public void moveSpaceship1() {
        time += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (x > 0) {
                x -= VELOCITY;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (x < Gdx.graphics.getWidth() - (float) SHIP_WIDTH) {
                x += VELOCITY;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (y > 0) {
                y -= VELOCITY;

                // Update roll if button is just clicked
                if (Gdx.input.isKeyJustPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.W) && roll < 4) {
                    rollTimer = 0.0f;
                    roll++;
                }

                //Update roll
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer = 0.0f;
                    roll++;
                }
            }
        } else {
            if (roll > 2) {
                //Update roll to go back to center
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0.0f;
                    roll--;
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (y < Gdx.graphics.getHeight() - (float) SHIP_HEIGTH) {
                y += VELOCITY;

                //Update roll if button just clicked
                if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S) && roll > 0) {
                    rollTimer = 0.0f;
                    roll--;
                }

                //Update roll
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0.0f;
                    roll--;
                }
            }
        } else {
            if (roll < 2) {
                //Update roll to go back to center
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer = 0.0f;
                    roll++;
                }
            }
        }
    }

    public void moveBullet1() {
        if (time > TIME_OUT && Gdx.input.isKeyPressed(Input.Keys.SPACE) && !isAttack()) {
            time = 0;
            setAttack(true);
            bullet1.setY((getY() + (float) SHIP_HEIGTH / 2 - 5));
            if (life != 0) {
                bullet1.getSound().play(2.0f);
            }
        }

        if (isAttack()) {
            if (bullet1.getX() < Gdx.graphics.getWidth()) {
                bullet1.setX(bullet1.getX() + factor * VELOCITY);
            } else {
                bullet1.setX(getX() + (float) SHIP_WIDTH / 2);
                bullet1.setY(getY());
                setAttack(false);
            }
        } else {
            bullet1.setX(getX() + (float) SHIP_WIDTH / 2);
            bullet1.setY(getY() + (float) SHIP_HEIGTH / 2 - 5);
        }
    }

    public void moveSpaceship2() {
        time += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (x > 0) {
                x -= VELOCITY;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (x < Gdx.graphics.getWidth() - (float) SHIP_WIDTH) {
                x += VELOCITY;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (y > 0) {
                y -= VELOCITY;

                // Update roll if button is just clicked
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP) && roll < 4) {
                    rollTimer = 0.0f;
                    roll++;
                }

                //Update roll
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer = 0.0f;
                    roll++;
                }
            }
        } else {
            if (roll > 2) {
                //Update roll to go back to center
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0.0f;
                    roll--;
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (y < Gdx.graphics.getHeight() - (float) SHIP_HEIGTH) {
                y += VELOCITY;

                //Update roll if button just clicked
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && roll > 0) {
                    rollTimer = 0.0f;
                    roll--;
                }

                //Update roll
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0.0f;
                    roll--;
                }
            }
        } else {
            if (roll < 2) {
                //Update roll to go back to center
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer = 0.0f;
                    roll++;
                }
            }
        }
    }

    public void moveBullet2() {
        if (time > TIME_OUT && Gdx.input.isKeyPressed(Input.Keys.NUMPAD_ENTER) && !isAttack()) {
            time = 0;
            setAttack(true);
            bullet1.setY((getY() + (float) SHIP_HEIGTH / 2 - 5));
            if (life != 0) {
                bullet1.getSound().play(2.0f);
            }
        }

        if (isAttack()) {
            if (bullet1.getX() < Gdx.graphics.getWidth()) {
                bullet1.setX(bullet1.getX() + factor * VELOCITY);
            } else {
                bullet1.setX(getX() + (float) SHIP_WIDTH / 2);
                bullet1.setY(getY());
                setAttack(false);
            }
        } else {
            bullet1.setX(getX() + (float) SHIP_WIDTH / 2);
            bullet1.setY(getY() + (float) SHIP_HEIGTH / 2 - 5);
        }
    }

    public static float getTimeOut() {
        return TIME_OUT;
    }

    public static void setTimeOut(float timeOut) {
        TIME_OUT = timeOut;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public static int getNumSpaceships() {
        return numSpaceships;
    }

    public static void setNumSpaceships(int numSpaceships) {
        Spaceship.numSpaceships = numSpaceships;
    }

    public Bullet getBullet1() {
        return bullet1;
    }

    public void setBullet1(Bullet bullet1) {
        this.bullet1 = bullet1;
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
