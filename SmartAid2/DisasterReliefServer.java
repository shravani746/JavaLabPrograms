import java.io.*;
import java.net.*;
import java.util.*;

public class DisasterReliefServer {

    static List<ClientInfo> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Disaster Relief Server Started on Port 5000");

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }

    static class ClientHandler extends Thread {
        Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String clientName = dis.readUTF();

                ClientInfo info = new ClientInfo(
                        clientName,
                        socket.getInetAddress(),
                        socket.getPort()
                );
                clients.add(info);

                System.out.println("Registered: " + clientName + " | IP: " +
                        socket.getInetAddress());

            } catch (IOException e) {
                System.out.println("Client disconnected");
            }
        }
    }

    static class ClientInfo {
        String name;
        InetAddress address;
        int port;

        ClientInfo(String name, InetAddress address, int port) {
            this.name = name;
            this.address = address;
            this.port = port;
        }
    }
}
