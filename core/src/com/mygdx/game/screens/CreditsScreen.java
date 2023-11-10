package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.SpaceInvaders;
import com.badlogic.gdx.audio.Music;
public class CreditsScreen implements Screen {

        SpaceInvaders game;
        Texture hiel, alanis, pimenta, background, credits, quitButtonActive, quitButtonInactive, menuButtonActive, menuButtonInactive, hielnome, pimentanome, alanisnome;
        private Music backgroundMusic;

        public CreditsScreen(SpaceInvaders game){
            this.game = game;

            quitButtonActive = new Texture(Gdx.files.internal("pictures/outGame/quit_orange.png"));
            quitButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/quit_blue.png"));
            menuButtonActive = new Texture(Gdx.files.internal("pictures/outGame/back_orange.png"));
            menuButtonInactive = new Texture(Gdx.files.internal("pictures/outGame/back_blue.png"));
            background = new Texture(Gdx.files.internal("pictures/outGame/background2.png"));
            pimenta = new Texture(Gdx.files.internal("pictures/outGame/pimentaHead.png"));
            alanis = new Texture(Gdx.files.internal("pictures/outGame/alanisHead.png"));
            hiel = new Texture(Gdx.files.internal("pictures/outGame/hielHead.png"));
            credits = new Texture(Gdx.files.internal("pictures/outGame/credits_blue.png"));
            hielnome = new Texture(Gdx.files.internal("pictures/outGame/hielName.png"));
            pimentanome = new Texture(Gdx.files.internal("pictures/outGame/pimentaName.png"));
            alanisnome = new Texture(Gdx.files.internal("pictures/outGame/alanisName.png"));

            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/credits/credits1.mp3"));
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
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.batch.begin();

            game.batch.draw(background, 0, 0);
            game.batch.draw(pimenta, 100, Gdx.graphics.getHeight() - 3 * pimentanome.getHeight() - 65, pimenta.getWidth(), pimenta.getHeight());
            game.batch.draw(alanis, Gdx.graphics.getWidth() - alanis.getWidth() - 100, Gdx.graphics.getHeight() - 4 * alanisnome.getHeight() - 100, alanis.getWidth(), alanis.getHeight());
            game.batch.draw(hiel, Gdx.graphics.getWidth() - hiel.getWidth() - 100, Gdx.graphics.getHeight() - 2 * hielnome.getHeight() - 70, hiel.getWidth(), hiel.getHeight());
            game.batch.draw(credits, (float) Gdx.graphics.getWidth() / 2 - (float) credits.getWidth() / 2, Gdx.graphics.getHeight() - credits.getHeight() - 50, credits.getWidth(), credits.getHeight());
            game.batch.draw(hielnome,(float) Gdx.graphics.getWidth() / 2 - (float) hielnome.getWidth() / 2, Gdx.graphics.getHeight() - 2 * hielnome.getHeight(), hielnome.getWidth(), hielnome.getHeight());
            game.batch.draw(pimentanome, (float) Gdx.graphics.getWidth() / 2- (float) pimentanome.getWidth() / 2, Gdx.graphics.getHeight() - 3 * pimentanome.getHeight(), pimentanome.getWidth(), pimentanome.getHeight());
            game.batch.draw(alanisnome, (float) Gdx.graphics.getWidth() / 2 - (float) alanisnome.getWidth() / 2, Gdx.graphics.getHeight() - 4 * alanisnome.getHeight(), alanisnome.getWidth(), alanisnome.getHeight());

            if(Gdx.input.getX() < (Gdx.graphics.getWidth() + quitButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() + 450 < (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 + quitButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 450 > (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2) {
                game.batch.draw(quitButtonActive, (float)(Gdx.graphics.getWidth() - quitButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - quitButtonActive.getHeight()) / 2 - 450);
                if(Gdx.input.isTouched()) {
                    Gdx.app.exit();
                    this.dispose();
                }
            } else {
                game.batch.draw(quitButtonInactive, (float)(Gdx.graphics.getWidth() - quitButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - quitButtonInactive.getHeight()) / 2 - 450);
            }

            if(Gdx.input.getX() < (Gdx.graphics.getWidth() + menuButtonInactive.getWidth()) / 2 && Gdx.input.getX() > (Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() + 350 < (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 + menuButtonInactive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() + 350 > (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2) {
                game.batch.draw(menuButtonActive, (float)(Gdx.graphics.getWidth() - menuButtonActive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - menuButtonActive.getHeight()) / 2 - 350);
                if(Gdx.input.isTouched()) {
                    game.setScreen(new MainMenuScreen(game));
                    this.dispose();
                }
            } else {
                game.batch.draw(menuButtonInactive, (float)(Gdx.graphics.getWidth() - menuButtonInactive.getWidth()) / 2, (float)(Gdx.graphics.getHeight() - menuButtonInactive.getHeight()) / 2 - 350);
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


