package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final SpaceInvaders game;
    OrthographicCamera camera;
    public Music menuMusic;

    public MainMenuScreen(final SpaceInvaders game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/menu/menu1.mp3"));

        menuMusic.play();
        menuMusic.setVolume(0.5f);
        menuMusic.setLooping(true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Space Invaders!", 540, 360);
        game.font.draw(game.batch, "Tap anywhere to begin!", 540, 300);
        game.batch.end();

        if(Gdx.input.isTouched()) {
            game.setScreen(new InvadersScreen(game));
            menuMusic.stop();
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
