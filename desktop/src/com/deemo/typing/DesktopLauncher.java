package com.deemo.typing;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.deemo.typing.DeemoTypingGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static final int ScreenWidth = 1920;
	public static final int ScreenHeight = 1080;
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(ScreenWidth, ScreenHeight);
		config.useVsync(true);
		config.setTitle("Deemo Typing Game");
		new Lwjgl3Application(new DeemoTypingGame(), config);
	}
}
