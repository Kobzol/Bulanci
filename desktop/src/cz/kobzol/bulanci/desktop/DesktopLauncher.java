package cz.kobzol.bulanci.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import cz.kobzol.bulanci.BulanciGame;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.DataPackage;

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
                    com.esotericsoftware.kryonet.Client client = new com.esotericsoftware.kryonet.Client();
                    client.getKryo().register(DataPackage.class);
                    client.start();

                    client.addListener(new Listener() {
                        @Override
                        public void connected(Connection connection) {
                            final ConnectionSide cc = new ConnectionSide();
                            cc.setConnection(connection);
                            cc.send("Ahoj jsem tu správně?", new ConnectionSide.Request() {
                                @Override
                                public Object received(Connection connection, Object object) {
                                    form.addLog("Server: " + object.toString());
                                    cc.send("I love tree (a ty, " + object + ", to víš a mlč!)");
                                    return null;
                                }
                            });
                        }

                    });
                    client.connect(5000, address, port, port);
                    startGame();
                } catch (IOException e) {
                    form.addLog("Nepodařilo se navázat spojení");
                    e.printStackTrace();
                }
            }
        });
        form.setVisible(true);
    }

    public void startGame() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new BulanciGame(), config);
    }
}
