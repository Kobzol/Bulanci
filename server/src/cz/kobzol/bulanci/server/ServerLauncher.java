package cz.kobzol.bulanci.server;

public class ServerLauncher {
    public static void main(String[] args) {
        BulanciServer server = new BulanciServer(1338, 1338);
        server.start();

        System.out.println("start server");
    }
}
