import java.io.*;
import java.net.*;
import java.util.*;

public class SmartCampusServer {

    public static void main(String[] args) {

        try {

            // -------- INET ADDRESS --------
            InetAddress local = InetAddress.getLocalHost();
            System.out.println("Host Name: " + local.getHostName());
            System.out.println("IP Address: " + local.getHostAddress());

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter host (or localhost): ");
            String host = sc.nextLine();

            InetAddress remote = InetAddress.getByName(host);
            System.out.println("Remote Host: " + remote.getHostName());
            System.out.println("Remote IP: " + remote.getHostAddress());


            // -------- URL CONNECTION --------
            System.out.println("\nReading student_info.html");

            URL url = new URL("file:student_info.html");
            URLConnection con = url.openConnection();

            System.out.println("Content Type: " + con.getContentType());
            System.out.println("Content Length: " + con.getContentLength());

            BufferedReader fileReader =
                    new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;

            while ((line = fileReader.readLine()) != null) {
                System.out.println(line);
            }


            // -------- TCP SERVER --------
            ServerSocket server = new ServerSocket(5000);
            System.out.println("\nServer started...");

            ArrayList<String> clients = new ArrayList<>();
            int clientCount = 0;

            while (true) {

                Socket socket = server.accept();

                clientCount++;
                String clientName = "Client" + clientCount;

                clients.add(clientName);

                System.out.println(clientName + " connected");
                System.out.println("Connected Clients: " + clients);

                BufferedReader br =
                        new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter pw =
                        new PrintWriter(socket.getOutputStream(), true);

                BufferedReader serverInput =
                        new BufferedReader(new InputStreamReader(System.in));

                String clientMsg;

                while ((clientMsg = br.readLine()) != null) {

                    System.out.println(clientName + ": " + clientMsg);

                    System.out.print("Server Reply: ");
                    String reply = serverInput.readLine();

                    pw.println(reply);
                }

                socket.close();
            }

        }

        catch (Exception e) {
            System.out.println(e);
        }
    }
}