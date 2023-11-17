package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    private static final float FRAME_LENGTH = 0.15f;
    private static final int OFFSET = 8;
    private int SIZE;
    private static Animation<TextureRegion> anim = null;
    private float x, y;
    private float statetime;
    private boolean remove = false;

    public Explosion(float x, float y, int SIZE, String texture) {
        this.SIZE = SIZE;
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        statetime = 0.0f;

        if (anim == null) {
            anim = new Animation<TextureRegion>(FRAME_LENGTH, TextureRegion.split(new Texture(texture), SIZE, SIZE)[0]);
        }
    }

    public void update(float deltatime) {
        statetime += deltatime;
        if (anim.isAnimationFinished(statetime)) {
            this.remove = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(anim.getKeyFrame(statetime), x, y);
    }

    public int getSIZE() {
        return SIZE;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public static Animation<TextureRegion> getAnim() {
        return anim;
    }

    public static void setAnim(Animation<TextureRegion> anim) {
        Explosion.anim = anim;
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

    public float getStatetime() {
        return statetime;
    }

    public void setStatetime(float statetime) {
        this.statetime = statetime;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isRemove() {
        return remove;
    }
}
