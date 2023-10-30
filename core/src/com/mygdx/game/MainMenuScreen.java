package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final SpaceInvaders game;
    private OrthographicCamera camera;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;
    private Music menuMusic;
    private Sound menuSound;
    private Texture wallpaperMainMenu;

    public MainMenuScreen(final SpaceInvaders game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/menu/menu1.mp3"));
        menuSound = Gdx.audio.newSound(Gdx.files.internal("audio/button_menu/button2.wav"));

        menuMusic.play();
        menuMusic.setVolume(0.5f);
        menuMusic.setLooping(true);
        wallpaperMainMenu = new Texture(Gdx.files.internal("pictures/outGame/menu.png"));

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font3.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK;
        parameter.color = Color.WHITE;
        bitmap = generator.generateFont(parameter);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(wallpaperMainMenu,0, 0);

        bitmap.draw(game.batch, "Welcome to Space Invaders by AHP!\n[1] - Singleplayer\n[2] - Doubles Multiplayer\n[3] - Credits\n[4] - Quit", 20, 200);
        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            game.setScreen(new InvadersScreen(game));
            menuMusic.stop();
            menuSound.play();
            dispose();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
            System.exit(1);
            menuSound.play();
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
