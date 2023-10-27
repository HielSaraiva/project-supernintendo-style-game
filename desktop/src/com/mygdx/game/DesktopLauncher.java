package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("Space Invaders - by AHP");
		config.setWindowIcon("pictures/out_game_pictures/game_icon.jpg");
		new Lwjgl3Application(new MyGame(), config);
	}
}
