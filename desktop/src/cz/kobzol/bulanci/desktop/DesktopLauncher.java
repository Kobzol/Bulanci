package cz.kobzol.bulanci.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import cz.kobzol.bulanci.command.message.SetPlayerNameMessage;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.KryoFactory;
import cz.kobzol.bulanci.game.GameController;

import java.io.IOException;

public class DesktopLauncher {

    private StartupForm form;

    public static void main(String[] arg) {
        DesktopLauncher launcher = new DesktopLauncher();
        launcher.openGameLauncher();
        //launcher.startGame(new DummyConnectionSide());
    }

    public DesktopLauncher() {
        this.form = new StartupForm();
    }

    public void openGameLauncher() {
        this.form.setActionListener(new StartupForm.ActionListener() {
            @Override
            public void connect(final StartupForm form, String address, int port, final String nickname) {
                form.addLog("Start spojení...");
                try {
                    com.esotericsoftware.kryonet.Client client = KryoFactory.createClient();
                    client.start();

                    client.addListener(new Listener() {
                        @Override
                        public void connected(Connection connection) {
                            final ConnectionSide cc = new ConnectionSide(connection);
                            cc.send(new SetPlayerNameMessage(nickname), new ConnectionSide.Request() {
                                @Override
                                public Object received(Connection connection, Object object) {
                                    startGame(cc);

                                    return null;
                                }
                            });
                        }

                    });
                    client.connect(5000, address, port, port);
                } catch (IOException e) {
                    form.addLog("Nepodařilo se navázat spojení");
                    e.printStackTrace();
                }
            }
        });

        this.form.setVisible(true);
    }

    public void startGame(ConnectionSide connectionSide) {
        //this.form.setVisible(false);

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.foregroundFPS = 60;
        config.backgroundFPS = 60;
        config.resizable = false;
        config.width = 1000;
        config.height = 800;

        new LwjglApplication(new GameController(connectionSide), config);
    }
}
