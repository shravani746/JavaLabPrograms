import java.io.*;
import java.net.*;

public class DisasterReliefClient {

    public static void main(String[] args) {
        try {
            // 1. Connect to Server (TCP)
            Socket socket = new Socket("localhost", 5000);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String unitName = "RescueUnit-" + (int)(Math.random() * 100);
            dos.writeUTF(unitName);

            System.out.println(unitName + " registered with Disaster Relief Server");

            // 2. Join Multicast Group
            InetAddress group = InetAddress.getByName("230.0.0.0");
            MulticastSocket multicastSocket = new MulticastSocket(4446);
            multicastSocket.joinGroup(group);

            System.out.println("Listening for Disaster Alerts... (Type CTRL+C to stop)");

            byte[] buffer = new byte[1024];

            // 3. CONTINUOUSLY RECEIVE MESSAGES
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);   // waits for message

                String alert = new String(packet.getData(), 0, packet.getLength());

                // If sender sends "stop", client will exit
                if (alert.equalsIgnoreCase("stop")) {
                    System.out.println("Received STOP signal. Client shutting down...");
                    break;
                }

                System.out.println("🚨 ALERT RECEIVED: " + alert);
            }

            // 4. Cleanup
            multicastSocket.leaveGroup(group);
            multicastSocket.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
