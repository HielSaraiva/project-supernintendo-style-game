package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Eye {
    private Spaceship ship;
    private Texture texture;
    private Sprite sprite;
    private Bullet bullet;
    private float x, y;
    public Eye(String texturePathEye, Spaceship ship) {
        this.ship = ship;

        texture = new Texture(Gdx.files.internal(texturePathEye));
        sprite = new Sprite(texture);

    }

    public void spawn() {
        x = Gdx.graphics.getWidth();
        y = MathUtils.random();
    }
}
