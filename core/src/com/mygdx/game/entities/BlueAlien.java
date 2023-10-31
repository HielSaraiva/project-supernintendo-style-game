package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class BlueAlien {
    private Spaceship ship;
    private Texture texture;
    private Array<Rectangle> rectangles;
    private long lastTime, time;

    public BlueAlien(String texturePathAlien, Spaceship ship) {
        this.ship = ship;
        texture = new Texture(Gdx.files.internal(texturePathAlien));
        rectangles = new Array<Rectangle>();
        lastTime = 0;
        time = 799999999;
    }

    public void spawn() {
        Rectangle enemy = new Rectangle(Gdx.graphics.getWidth(), MathUtils.random(0, Gdx.graphics.getHeight() - texture.getHeight()), texture.getWidth(), texture.getHeight());
        rectangles.add(enemy);
        lastTime = TimeUtils.nanoTime();
    }

    public void move() {
        if(TimeUtils.nanoTime() - lastTime > time){
            this.spawn();
        }

        for(Iterator<Rectangle> iter = rectangles.iterator(); iter.hasNext();) {
            Rectangle enemy = iter.next();
            enemy.x -= 200 * Gdx.graphics.getDeltaTime();

            // Colisão do inimigo com o missel
            if(collide(enemy.x, enemy.y, enemy.width, enemy.height, ship.getBullet().getX(), ship.getBullet().getY(), ship.getBullet().getSprite().getWidth(), ship.getBullet().getSprite().getHeight()) && ship.isAttack()) {
                ship.setScore(ship.getScore() + 1);
                if(ship.getScore() % 10 == 0) {
                    time += 100000;
                }
                ship.setAttack(false);
                iter.remove();
                // Colisao do inimigo com a spaceship 1
            } else if( collide(enemy.x, enemy.y, enemy.width, enemy.height, ship.getX(), ship.getY(), ship.getSprite().getWidth(), ship.getSprite().getHeight()) && !ship.isGameover()) {
                ship.setLife(ship.getLife() - 1);
                if(ship.getLife() <= 0 ) {
                    ship.setFinalScore(ship.getScore());
                    ship.setGameover(true);
                }
                iter.remove();
            }
            if(enemy.x + texture.getWidth() < 0) {
                iter.remove();
            }
        }
    }

    public boolean collide(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return (x1 + w1 > x2 && x1 < x2 + w2 && y1 + h1 > y2 && y1 < y2 + h2);
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
}
