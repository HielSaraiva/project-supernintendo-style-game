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

public class BlueAlien {

    private Spaceship ship;
    private Texture texture;
    private Array<Rectangle> rectangles;
    private long lastTime, time;
    private ArrayList<Explosion> explosions1, explosions2;
    private Sound sound1, sound2;

    public BlueAlien(String texturePathAlien, Spaceship ship) {
        this.ship = ship;
        texture = new Texture(Gdx.files.internal(texturePathAlien));
        rectangles = new Array<Rectangle>();
        lastTime = 0;
        time = 855555555;
        explosions1 = new ArrayList<>();
        explosions2 = new ArrayList<>();
        sound1 = Gdx.audio.newSound(Gdx.files.internal("audio/explosions/explosion1.wav"));
        sound2 = Gdx.audio.newSound(Gdx.files.internal("audio/explosions/explosion2.mp3"));

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

            // Collision Enemy x Bullet
            if(Collision.collide(enemy.x, enemy.y, enemy.width, enemy.height, ship.getBullet1().getX(), ship.getBullet1().getY(), ship.getBullet1().getSprite().getWidth(), ship.getBullet1().getSprite().getHeight()) && ship.isAttack()) {
                ship.setScore(ship.getScore() + 100);
                if(ship.getScore() % 100 == 0) {
                    time -= 200000;
                }
                sound1.play();
                explosions1.add(new Explosion(enemy.x, enemy.y, 64,"pictures/inGame/explosion/explosion2.png"));
                ship.setAttack(false);
                iter.remove();

            // Collision Enemy x Spaceship
            } else if(Collision.collide(enemy.x, enemy.y, enemy.width, enemy.height, ship.getX(), ship.getY(), (float)Spaceship.SHIP_WIDTH, (float)Spaceship.SHIP_HEIGTH) && !ship.isGameover()) {
                ship.setLife(ship.getLife() - 1);
                if(ship.getLife() <= 0 ) {
                    ship.setFinalScore(ship.getScore());
                    ship.setGameover(true);
                }
                sound2.play();
                explosions2.add(new Explosion(enemy.x, enemy.y, 64,"pictures/inGame/explosion/explosion2.png"));
                iter.remove();
            }
            if(enemy.x + texture.getWidth() < 0) {
                iter.remove();
            }
            ArrayList<Explosion> explosionsToRemove1 = new ArrayList<>();
            for(Explosion explosion : explosions1) {
                explosion.update(Gdx.graphics.getDeltaTime());
                if(explosion.isRemove()) {
                    explosionsToRemove1.add(explosion);
                }
            }
            explosions1.removeAll(explosionsToRemove1);

            ArrayList<Explosion> explosionsToRemove2 = new ArrayList<>();
            for(Explosion explosion : explosions2) {
                explosion.update(Gdx.graphics.getDeltaTime());
                if(explosion.isRemove()) {
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
