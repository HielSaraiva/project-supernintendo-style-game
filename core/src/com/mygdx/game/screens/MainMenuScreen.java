package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceInvaders;

public class MainMenuScreen implements Screen {
    private final SpaceInvaders game;
    private OrthographicCamera camera;s
    private Music musicMenu;
    private Sound soundMenu;
    private Texture textureMenu;
    private Texture title1;
    private Texture title2;
    private Texture singleButtonActive;
    private Texture singleButtonInactive;
    private Texture multiButtonActive;
    private Texture multiButtonInactive;
    private Texture creditsButtonActive;
    private Texture creditsButtonInactive;
    private Texture quitButtonActive;
    private Texture quitButtonInactive;


    public MainMenuScreen(final SpaceInvaders game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        musicMenu = Gdx.audio.newMusic(Gdx.files.internal("audio/menu/menu1.mp3"));
        soundMenu = Gdx.audio.newSound(Gdx.files.internal("audio/button_menu/button2.wav"));
        musicMenu.play();
        musicMenu.setVolume(0.5f);
        musicMenu.setLooping(true);

        textureMenu = new Texture(Gdx.files.internal("pictures/outGame/menu.png"));
        title1 = new Texture(Gdx.files.internal("pictures/outGame/title1.png"));
        title2 = new Texture(Gdx.files.internal("pictures/outGame/title2.png"));
        singleButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/singleplayer_orange.png"));
        singleButtonActive = new Texture(Gdx.files.internal("pictures/outGame/singleplayer_blue.png"));
        multiButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/multiplayer_orange.png"));
        multiButtonActive = new Texture(Gdx.files.internal("pictures/outGame/multiplayer_blue.png"));
        creditsButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/credits_orange.png"));
        creditsButtonActive = new Texture(Gdx.files.internal("pictures/outGame/credits_blue.png"));
        quitButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/quit_orange.png"));
        quitButtonActive = new Texture(Gdx.files.internal("pictures/outGame/quit_blue.png"));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(textureMenu,0, 0);

        game.batch.draw(title1, (float)(Gdx.graphics.getWidth() - title1.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - title1.getHeight()) / 2 + 450);
        game.batch.draw(title2, (float)(Gdx.graphics.getWidth() - title2.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - title2.getHeight()) / 2 + 350);

        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + singleButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - singleButtonInactive.getWidth()) / 2 && Gdx.graphics.getHeight() - Gdx.input.getY() < (float)(Gdx.graphics.getHeight() - singleButtonInactive.getHeight()) / 2 + singleButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() > (float)(Gdx.graphics.getHeight() - singleButtonInactive.getHeight()) / 2) {
            game.batch.draw(singleButtonActive, (float)(Gdx.graphics.getWidth() - singleButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - singleButtonActive.getHeight()) / 2);
            if(Gdx.input.isTouched()) {
                game.setScreen(new InvadersScreen(game));
                musicMenu.stop();
                soundMenu.play();
                dispose();
            }
        } else {
            game.batch.draw(singleButtonInactive, (float)(Gdx.graphics.getWidth() - singleButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - singleButtonInactive.getHeight()) / 2);
        }

        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + multiButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - multiButtonInactive.getWidth()) / 2 && Gdx.graphics.getHeight() - Gdx.input.getY() + 100 < (float)(Gdx.graphics.getHeight() - multiButtonInactive.getHeight()) / 2 + multiButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 100 > (float)(Gdx.graphics.getHeight() - multiButtonInactive.getHeight()) / 2) {
            game.batch.draw(multiButtonActive, (float)(Gdx.graphics.getWidth() - multiButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - multiButtonActive.getHeight()) / 2 - 100);
            if(Gdx.input.isTouched()) {
                soundMenu.play();
            }
        } else {
            game.batch.draw(multiButtonInactive, (float)(Gdx.graphics.getWidth() - multiButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - multiButtonInactive.getHeight()) / 2 - 100);
        }

        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + creditsButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - creditsButtonInactive.getWidth()) / 2 && Gdx.graphics.getHeight() - Gdx.input.getY() + 200 < (float)(Gdx.graphics.getHeight() - creditsButtonInactive.getHeight()) / 2 + creditsButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 200 > (float)(Gdx.graphics.getHeight() - creditsButtonInactive.getHeight()) / 2) {
            game.batch.draw(creditsButtonActive, (float)(Gdx.graphics.getWidth() - creditsButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - creditsButtonActive.getHeight()) / 2 - 200);
            if(Gdx.input.isTouched()) {
                soundMenu.play();
            }
        } else {
            game.batch.draw(creditsButtonInactive, (float)(Gdx.graphics.getWidth() - creditsButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - creditsButtonInactive.getHeight()) / 2 - 200);
        }

        if(Gdx.input.getX() < (Gdx.graphics.getWidth() + quitButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2 && Gdx.graphics.getHeight() - Gdx.input.getY() + 300 < (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 + quitButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 300 > (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2) {
            game.batch.draw(quitButtonActive, (float)(Gdx.graphics.getWidth() - quitButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - quitButtonActive.getHeight()) / 2 - 300);
            if(Gdx.input.isTouched()) {
                Gdx.app.exit();
                soundMenu.play();
            }
        } else {
            game.batch.draw(quitButtonInactive, (float)(Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 - 300);
        }

        game.batch.end();
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
