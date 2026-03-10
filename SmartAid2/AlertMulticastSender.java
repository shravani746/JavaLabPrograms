import java.net.*;
import java.util.Scanner;

public class AlertMulticastSender {

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName("230.0.0.0");
            int port = 4446;

            DatagramSocket socket = new DatagramSocket();
            Scanner sc = new Scanner(System.in);

            System.out.println("=== Disaster Alert Sender Started ===");
            System.out.println("Type message and press ENTER to send.");
            System.out.println("Type 'stop' to end.\n");

            while (true) {
                System.out.print("Enter Alert: ");
                String alert = sc.nextLine();

                // Stop condition
                if (alert.equalsIgnoreCase("stop")) {
                    System.out.println("Sender stopped.");
                    break;
                }

                byte[] buffer = alert.getBytes();

                DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length, group, port);

                socket.send(packet);

                System.out.println("Alert sent to all clients!\n");
            }

            socket.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
