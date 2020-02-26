package ru.phantomhunter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.phantomhunter.MyGameGalaxyX;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 580;
		config.resizable = false;

		new LwjglApplication(new MyGameGalaxyX(), config);
	}
}
