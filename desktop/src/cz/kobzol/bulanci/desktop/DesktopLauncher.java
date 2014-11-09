package cz.kobzol.bulanci.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import cz.kobzol.bulanci.BulanciGame;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.KryoFactory;

import java.io.IOException;

public class DesktopLauncher {

    public static void main(String[] arg) {
        DesktopLauncher launcher = new DesktopLauncher();
        launcher.openGameLauncher();
    }

    public void openGameLauncher() {
        StartupForm form = new StartupForm();
        form.setActionListener(new StartupForm.ActionListener() {
            @Override
            public void connect(final StartupForm form, String address, int port, String nickname) {
                form.addLog("Start spojení...");
                try {
                    com.esotericsoftware.kryonet.Client client = KryoFactory.createClient();
                    client.start();

                    client.addListener(new Listener() {
                        @Override
                        public void connected(Connection connection) {
                            final ConnectionSide cc = new ConnectionSide();
                            cc.setConnection(connection);
                            startGame(cc);
                        }

                    });
                    client.connect(5000, address, port, port);
                } catch (IOException e) {
                    form.addLog("Nepodařilo se navázat spojení");
                    e.printStackTrace();
                }
            }
        });
        form.setVisible(true);
    }

    public void startGame(ConnectionSide connectionSide) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new BulanciGame(connectionSide), config);
    }
}
