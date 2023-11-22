package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
public class Boss {
    private Texture texture;
    private Sprite sprite;
    private Spaceship ship;
    private float velocity = 240 * Gdx.graphics.getDeltaTime();

    public Boss(String texturePathEye, Spaceship ship){
        this.ship = ship;
        texture = new Texture(Gdx.files.internal(texturePathEye));
        sprite = new Sprite(texture);

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

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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
}
