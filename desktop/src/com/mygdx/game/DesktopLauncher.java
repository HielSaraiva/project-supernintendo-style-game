package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		 config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		 config.setResizable(true);

//		config.setWindowedMode(1280, 720);
//		config.setResizable(false);

		config.setTitle("Space Invaders - by AHP");
		config.setWindowIcon("pictures/outGame/gameIcon.jpg");
		new Lwjgl3Application(new SpaceInvaders(), config);
	}
}
