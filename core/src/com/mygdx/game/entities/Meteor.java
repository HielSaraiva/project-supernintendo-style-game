package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class Meteor {
    private Texture texture;
    private Array<Rectangle> rectangles;
    private long lastTime, time;
    private ArrayList<Explosion> explosions1, explosions2, explosions3;
    private Sound sound1, sound2;

    public Meteor(String texturePathAlien) {
        texture = new Texture(Gdx.files.internal(texturePathAlien));
        rectangles = new Array<Rectangle>();
        lastTime = 0;
        time = 2000000000;
        explosions1 = new ArrayList<>();
        explosions2 = new ArrayList<>();
        sound2 = Gdx.audio.newSound(Gdx.files.internal("audio/explosions/explosion2.mp3"));
    }

    public void spawn() {
        Rectangle enemy = new Rectangle(Gdx.graphics.getWidth(), MathUtils.random(0, Gdx.graphics.getHeight() - texture.getHeight()), texture.getWidth(), texture.getHeight());
        rectangles.add(enemy);
        lastTime = TimeUtils.nanoTime();
    }

    public void move(Spaceship ship1) {
        if (TimeUtils.nanoTime() - lastTime > time) {
            this.spawn();
        }

        for (Iterator<Rectangle> iter = rectangles.iterator(); iter.hasNext(); ) {
            Rectangle enemy = iter.next();
            enemy.x -= 150 * Gdx.graphics.getDeltaTime();
            enemy.y -= 150 * Gdx.graphics.getDeltaTime();

            if (enemy.y + enemy.getHeight() <= 0) {
                enemy.y = Gdx.graphics.getHeight();
            }

            // Collision Enemy x Bullet
            if (Collision.collide(enemy.x, enemy.y, enemy.width, enemy.height, ship1.getX(), ship1.getY(), (float) Spaceship.SHIP_WIDTH, (float) Spaceship.SHIP_HEIGTH) && !ship1.isGameover()) {
                ship1.setLife(ship1.getLife() - 1);
                if (ship1.getLife() <= 0) {
                    ship1.setFinalScore(ship1.getScore());
                    ship1.setGameover(true);
                }
                sound2.play(2.0f);
                explosions2.add(new Explosion(enemy.x, enemy.y, 32, "pictures/inGame/explosion/explosion1.png"));
                iter.remove();
            }

            if (enemy.x + texture.getWidth() < 0) {
                iter.remove();
            }

            ArrayList<Explosion> explosionsToRemove1 = new ArrayList<>();
            for (Explosion explosion : explosions1) {
                explosion.update(Gdx.graphics.getDeltaTime());
                if (explosion.isRemove()) {
                    explosionsToRemove1.add(explosion);
                }
            }
            explosions1.removeAll(explosionsToRemove1);

            ArrayList<Explosion> explosionsToRemove2 = new ArrayList<>();
            for (Explosion explosion : explosions2) {
                explosion.update(Gdx.graphics.getDeltaTime());
                if (explosion.isRemove()) {
                    explosionsToRemove2.add(explosion);
                }
            }
            explosions2.removeAll(explosionsToRemove2);
        }
    }

    public void move(Spaceship ship1, Spaceship ship2) {
        if (TimeUtils.nanoTime() - lastTime > time) {
            this.spawn();
        }

        for (Iterator<Rectangle> iter = rectangles.iterator(); iter.hasNext(); ) {
            Rectangle enemy = iter.next();
            enemy.x -= 150 * Gdx.graphics.getDeltaTime();
            enemy.y -= 150 * Gdx.graphics.getDeltaTime();

            if (enemy.y + enemy.getHeight() <= 0) {
                enemy.y = Gdx.graphics.getHeight();
            }

            // Collision Enemy x Bullet
            if (Collision.collide(enemy.x, enemy.y, enemy.width, enemy.height, ship1.getX(), ship1.getY(), (float) Spaceship.SHIP_WIDTH, (float) Spaceship.SHIP_HEIGTH) && !ship1.isGameover()) {
                ship1.setLife(ship1.getLife() - 1);
                if (ship1.getLife() <= 0) {
                    ship1.setFinalScore(ship1.getScore());
                    ship1.setGameover(true);
                }
                sound2.play(2.0f);
                explosions2.add(new Explosion(enemy.x, enemy.y, 32, "pictures/inGame/explosion/explosion1.png"));
                iter.remove();
            }

            // Collision Enemy x Bullet
            if (Collision.collide(enemy.x, enemy.y, enemy.width, enemy.height, ship2.getX(), ship2.getY(), (float) Spaceship.SHIP_WIDTH, (float) Spaceship.SHIP_HEIGTH) && !ship2.isGameover()) {
                ship2.setLife(ship2.getLife() - 1);
                if (ship2.getLife() <= 0) {
                    ship2.setFinalScore(ship2.getScore());
                    ship2.setGameover(true);
                }
                sound2.play(2.0f);
                explosions2.add(new Explosion(enemy.x, enemy.y, 32, "pictures/inGame/explosion/explosion1.png"));
                iter.remove();
            }

            if (enemy.x + texture.getWidth() < 0) {
                iter.remove();
            }

            ArrayList<Explosion> explosionsToRemove1 = new ArrayList<>();
            for (Explosion explosion : explosions1) {
                explosion.update(Gdx.graphics.getDeltaTime());
                if (explosion.isRemove()) {
                    explosionsToRemove1.add(explosion);
                }
            }
            explosions1.removeAll(explosionsToRemove1);

            ArrayList<Explosion> explosionsToRemove2 = new ArrayList<>();
            for (Explosion explosion : explosions2) {
                explosion.update(Gdx.graphics.getDeltaTime());
                if (explosion.isRemove()) {
                    explosionsToRemove2.add(explosion);
                }
            }
            explosions2.removeAll(explosionsToRemove2);
        }
    }

    public ArrayList<Explosion> getExplosions1() {
        return explosions1;
    }

    public ArrayList<Explosion> getExplosions2() {
        return explosions2;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Array<Rectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(Array<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public ArrayList<Explosion> getExplosions3() {
        return explosions3;
    }

    public void setExplosions3(ArrayList<Explosion> explosions3) {
        this.explosions3 = explosions3;
    }
}
