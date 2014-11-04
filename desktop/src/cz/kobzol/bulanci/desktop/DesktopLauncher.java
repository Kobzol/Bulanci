package cz.kobzol.bulanci.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cz.kobzol.bulanci.BulanciGame;

public class DesktopLauncher {

	public static void main (String[] arg) {
        DesktopLauncher launcher = new DesktopLauncher();
        launcher.openGameLauncher();
	}

    public void openGameLauncher()
    {
        StartupForm form = new StartupForm();
        form.setActionListener(new StartupForm.ActionListener() {
            @Override
            public void connect(StartupForm form, String address, String port, String nickname) {
                form.addLog("Start hry");
                startGame();
            }
        });
        form.setVisible(true);
    }

    public void startGame()
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new BulanciGame(), config);
    }
}
