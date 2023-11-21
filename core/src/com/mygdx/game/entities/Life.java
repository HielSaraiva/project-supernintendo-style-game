package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Life {
    private Texture texture;
    private Sprite sprite;
    private Sound sound1;
    private Music music1;
    private float time;

    public Life() {
        time = 0;
        texture = new Texture(Gdx.files.internal("pictures/inGame/consumables/life.png"));
        sprite = new Sprite(texture);

        sprite.setX(0 - sprite.getWidth());
        sprite.setY(MathUtils.random(0, Gdx.graphics.getHeight() - sprite.getHeight()));

        music1 = Gdx.audio.newMusic(Gdx.files.internal("audio/moments/moments1.mp3"));
        sound1 = Gdx.audio.newSound(Gdx.files.internal("audio/got/got5.mp3"));
    }

    public void move(Spaceship ship) {
        if (ship.getLife() < 5) {
            music1.play();
            music1.setLooping(true);
            sprite.setX(sprite.getX() + 250 * Gdx.graphics.getDeltaTime());
            sprite.setY(sprite.getY() - 250 * Gdx.graphics.getDeltaTime());
            if (sprite.getY() + sprite.getHeight() < 0) {
                sprite.setY(Gdx.graphics.getHeight());
            }
            if (sprite.getX() > Gdx.graphics.getWidth()) {
                time += Gdx.graphics.getDeltaTime();
                music1.stop();
                sprite.setX(Gdx.graphics.getWidth() + 2.0f);
            }
        }
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
