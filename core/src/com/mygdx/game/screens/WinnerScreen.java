package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceInvaders;

public class WinnerScreen implements Screen {
    private final SpaceInvaders game;
    private int score;
    Texture background, menuButtonActive, menuButtonInactive;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter, parameter2, parameter3;
    private BitmapFont bitmap, bitmap2, greenFont;
    private Music backgroundMusic;
    private Sound soundScreen;

    public WinnerScreen(SpaceInvaders game, int score) {
        this.game = game;
        this.score = score;

        background = new Texture("pictures/outGame/winner.jpg");
        menuButtonActive = new Texture("pictures/outGame/back_orange.png");
        menuButtonInactive = new Texture("pictures/outGame/back_blue.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font4.ttf"));

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        parameter.color = Color.GREEN;
        bitmap = generator.generateFont(parameter);

        parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 100;
        parameter2.borderWidth = 2;
        parameter2.borderColor = Color.BLACK;
        parameter2.color = Color.BLUE;
        bitmap2 = generator.generateFont(parameter2);

        parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 100;
        parameter3.borderWidth = 2;
        parameter3.borderColor = Color.BLACK;
        parameter3.color = Color.GREEN;
        greenFont = generator.generateFont(parameter3);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/win/win1.mp3"));
        soundScreen = Gdx.audio.newSound(Gdx.files.internal("audio/button_menu/button2.wav"));
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        backgroundMusic.setLooping(true);

    }

    @Override
    public void show() {
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        backgroundMusic.setLooping(true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        bitmap.draw(game.batch, "YOU", ((float) Gdx.graphics.getWidth() / 2 - 170), (float) Gdx.graphics.getHeight() / 2 + 400);
        bitmap.draw(game.batch, "WIN", ((float) Gdx.graphics.getWidth() / 2 - 130), (float) Gdx.graphics.getHeight() / 2 + 200);
        bitmap2.draw(game.batch, "Total score: " + this.score, ((float) Gdx.graphics.getWidth() - 800) / 2, (float) Gdx.graphics.getHeight() / 2 - 200);

        if (SingleInvadersScreen.getRecord()) {
            greenFont.draw(game.batch, "!!!NEW RECORD!!!", ((float) Gdx.graphics.getWidth() - 700) / 2, (float) Gdx.graphics.getHeight() / 2 - 50);
        }


        if (Gdx.input.getX() < (Gdx.graphics.getWidth() + menuButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() + 400 < (float) (Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 + menuButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 400 > (float) (Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2) {
            game.batch.draw(menuButtonActive, (float) (Gdx.graphics.getWidth() - menuButtonActive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - menuButtonActive.getHeight()) / 2 - 400);
            if (Gdx.input.justTouched()) {
                game.setScreen(new MainMenuScreen(game));
                soundScreen.play(2.0f);
                this.dispose();
            }
        } else {
            game.batch.draw(menuButtonInactive, (float) (Gdx.graphics.getWidth() - menuButtonActive.getWidth()) / 2, (float) (Gdx.graphics.getHeight() - menuButtonActive.getHeight()) / 2 - 400);
        }

        game.batch.end();
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
        backgroundMusic.dispose();
    }
}
