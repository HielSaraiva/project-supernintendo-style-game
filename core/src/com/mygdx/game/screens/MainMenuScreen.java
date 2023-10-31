package com.mygdx.game.screens;

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
import com.mygdx.game.SpaceInvaders;

public class MainMenuScreen implements Screen {
    final SpaceInvaders game;
    private OrthographicCamera camera;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont fontBitmap;
    private Music musicMenu;
    private Sound soundMenu;
    private Texture textureMenu;

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

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/font3.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 40;
        fontParameter.borderWidth = 2.0f;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        fontBitmap = fontGenerator.generateFont(fontParameter);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(textureMenu,0, 0);

        fontBitmap.draw(game.batch, "Welcome to Space Invaders AHP!\n[1] - Singleplayer\n[2] - Doubles Multiplayer\n[3] - Credits\n[4] - Quit", 20, 200);
        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            game.setScreen(new InvadersScreen(game));
            musicMenu.stop();
            soundMenu.play();
            dispose();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
            System.exit(1);
            soundMenu.play();
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
