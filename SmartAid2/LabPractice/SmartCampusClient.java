import java.io.*;
import java.net.*;
import java.util.*;

public class SmartCampusClient {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("localhost", 5000);

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter pw =
                    new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            String msg;

            while (true) {

                System.out.print("Client Message: ");
                msg = sc.nextLine();

                pw.println(msg);

                String reply = br.readLine();

                System.out.println("Server: " + reply);
            }

        }

        catch (Exception e) {
            System.out.println(e);
        }
    }
}