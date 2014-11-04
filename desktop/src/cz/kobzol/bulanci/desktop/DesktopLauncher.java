package cz.kobzol.bulanci.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cz.kobzol.bulanci.BulanciGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
        StartupForm f = new StartupForm();
        f.setVisible(true);
	}

    public static void startGame()
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new BulanciGame(), config);
    }
}
