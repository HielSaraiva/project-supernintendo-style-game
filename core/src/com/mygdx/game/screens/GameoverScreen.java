package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceInvaders;

public class GameoverScreen implements Screen {
    private final SpaceInvaders game;
    private int score;
    private Texture background;
    private Texture tryButtonActive, tryButtonInactive;
    private Texture menuButtonActive, menuButtonInactive;
    private Music music;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;
    private Sound soundScreen;

    public GameoverScreen(SpaceInvaders game, int score) {
        this.game = game;
        this.score = score;

        background = new Texture(Gdx.files.internal("pictures/outGame/gameover2.jpg"));
        tryButtonActive = new Texture(Gdx.files.internal("pictures/outGame/tryagain_orange.png"));
        tryButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/tryagain_blue.png"));
        menuButtonActive = new Texture(Gdx.files.internal("pictures/outGame/back_orange.png"));
        menuButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/back_blue.png"));

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/gameover/gameover1.mp3"));
        soundScreen = Gdx.audio.newSound(Gdx.files.internal("audio/button_menu/button2.wav"));

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font4.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        parameter.color = Color.WHITE;
        bitmap = generator.generateFont(parameter);

        music.play();
        music.setVolume(0.5f);
        music.setLooping(true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.batch.begin();
        game.batch.draw(background, 0 ,0);
        bitmap.draw(game.batch, "Total score: " + this.score, ((float)Gdx.graphics.getWidth() - 800) / 2, (float)Gdx.graphics.getHeight() / 2 + 200);

        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + tryButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - tryButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < (float)(Gdx.graphics.getHeight() - tryButtonInactive.getHeight()) / 2 + tryButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY()  > (float)(Gdx.graphics.getHeight() - tryButtonInactive.getHeight()) / 2) {
            game.batch.draw(tryButtonActive, (float)(Gdx.graphics.getWidth() - tryButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - tryButtonActive.getHeight()) / 2);
            if(Gdx.input.justTouched()) {
                game.setScreen(new InvadersScreen(game));
                soundScreen.play();
                this.dispose();
            }
        } else {
            game.batch.draw(tryButtonInactive, (float)(Gdx.graphics.getWidth() - tryButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - tryButtonInactive.getHeight()) / 2);
        }

        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + menuButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() + 100 < (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 + menuButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 100 > (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2) {
            game.batch.draw(menuButtonActive, (float)(Gdx.graphics.getWidth() - menuButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - menuButtonActive.getHeight()) / 2 - 100);
            if(Gdx.input.justTouched()) {
                game.setScreen(new MainMenuScreen(game));
                soundScreen.play();
                this.dispose();
            }
        } else {
            game.batch.draw(menuButtonInactive, (float)(Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 - 100);
        }
        game.batch.end();
    }

    @Override
    public void show() {
        music.play();
        music.setVolume(0.5f);
        music.setLooping(true);
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
