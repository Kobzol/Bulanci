package cz.kobzol.bulanci.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import cz.kobzol.bulanci.BulanciGame;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.DataPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DesktopLauncher {

	public static void main (String[] arg) {
        tryConnection();
        /*
        DesktopLauncher launcher = new DesktopLauncher();
        launcher.openGameLauncher();
        */
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


    public static void tryConnection() {
        try {
            final Server server = new Server();
            server.getKryo().register(DataPackage.class);
            server.start();

            server.addListener( new Listener() {
                @Override
                public void connected(Connection connection) {
                    ConnectionSide cs = new ConnectionSide(connection);
                    cs.addListener(new ConnectionSide.Response() {
                        @Override
                        public Object received(Connection connection, Object object) {
                            if (object instanceof String) {
                                System.out.println("Server dostal: " + object);
                                return "Ahoj " + (object);
                            }
                            return null;
                        }
                    });
                }
            } );
            server.bind(54555, 54555);

            createClient("Martin");
            createClient("Kuba");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createClient(final String name) throws IOException
    {
        com.esotericsoftware.kryonet.Client client = new com.esotericsoftware.kryonet.Client();
        client.getKryo().register(DataPackage.class);
        client.start();

        client.addListener( new Listener() {
            @Override
            public void connected(Connection connection) {
                final ConnectionSide cc = new ConnectionSide();
                cc.setConnection(connection);
                cc.send(name, new ConnectionSide.Response() {
                    @Override
                    public Object received(Connection connection, Object object) {
                        System.out.println("Klient dostal odpověd: " + object);
                        cc.send("I love tree (a ty, " + object + ", to víš a mlč!)");
                        return null;
                    }
                });
            }

        });
        client.connect(5000, "localhost", 54555, 54555);
    }
}
