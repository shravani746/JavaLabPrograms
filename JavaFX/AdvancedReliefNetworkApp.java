import java.io.*;
import java.net.*;

public class AdvancedReliefNetworkApp {

    public static void main(String[] args) {

        try {
            // ------------------ INETADDRESS PART ------------------
            InetAddress reliefServer = InetAddress.getByName("www.google.com");

            System.out.println("Relief Server Details");
            System.out.println("---------------------");
            System.out.println("Host Name : " + reliefServer.getHostName());
            System.out.println("IP Address: " + reliefServer.getHostAddress());
            System.out.println("Reachable : " + reliefServer.isReachable(5000));
            System.out.println("Local Host: " + InetAddress.getLocalHost());

            // ------------------ URL CONNECTION PART ------------------
            URL url = new URL("https://www.wikipedia.org");
            URLConnection connection = url.openConnection();

            // Set request properties
            connection.setRequestProperty("User-Agent", "ReliefSystem/1.0");
            connection.connect();

            System.out.println("\nConnection Information");
            System.out.println("----------------------");
            System.out.println("URL            : " + url);
            System.out.println("Content Type   : " + connection.getContentType());
            System.out.println("Content Length : " + connection.getContentLength());
            System.out.println("Last Modified  : " + connection.getLastModified());
            System.out.println("Response Time  : " + connection.getDate());

            // ------------------ READING LIVE DATA ------------------
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line;
            System.out.println("\n---- Relief Website Live Data (First 20 lines) ----");

            int count = 0;
            while ((line = reader.readLine()) != null && count < 20) {
                System.out.println(line);
                count++;
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Network Error: " + e.getMessage());
        }
    }
}
