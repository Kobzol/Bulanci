package cz.kobzol.bulanci.server;

public class ServerLauncher {
    public static void main(String[] args) {
        BulanciServer server = new BulanciServer(16543, 16543);
        server.start();

        System.out.println("start server");
    }
}
