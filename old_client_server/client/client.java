import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.cert.*;
import java.util.Scanner;

public class client {

    private static String host = "localhost";
    private static int port = 9876;

    public static void main(String[] args) {

        String name = "";
        String passphrase = "";
        boolean fileNotFound = true;
        Scanner input = new Scanner(System.in);
        FileInputStream stream = null;

        while (fileNotFound) {
            try {
                System.out.print("Name: ");
                name = input.nextLine();
                System.out.print("Certificate Passphrase: "); // First step of 2FA
                passphrase = input.nextLine();

                stream = new FileInputStream("./keystores/" + name + "keystore");
                fileNotFound = false;
            } catch (FileNotFoundException e) {
                fileNotFound = true;
            }
        }

        /* set up a key manager for client authentication */
        try {
            SSLSocketFactory factory;

            KeyStore ks = KeyStore.getInstance("JKS");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            SSLContext ctx = SSLContext.getInstance("TLS");

            char[] password = passphrase.toCharArray();

            ks.load(stream, password);  // keystore password (storepass)
            kmf.init(ks, password); // user password (keypass)
            tmf.init(ks); // keystore can be used as truststore here
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            factory = ctx.getSocketFactory();

            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
            socket.setUseClientMode(true);
            socket.startHandshake();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;

            out.println(name); // Send username to server, second part of 2FA
            out.flush();

            boolean passwordCorrect = false;

            while (!passwordCorrect) {
                System.out.println("Enter your password: ");
                String serverPassword = input.nextLine();
                out.println(serverPassword);
                out.flush();

                passwordCorrect = in.readLine().equals("correct"); // If server sends "correct", terminate.
            }

            System.out.println("Successfully logged into server.");

            for (;;) {
                /* Here server should probably send a list of possible commands. */
                System.out.println("Which command do you want to do?");
                System.out.print(">");
                msg = input.nextLine();
                if (msg.equalsIgnoreCase("quit")) {
                    break;
                }

                switch (in.readLine()) {
                    case "readRecords": // Servers sends records, and we print them.
                        break;
                    case "writeRecords": // Server prompts us to choose patient, and then enter data.
                        break;
                    case "createRecords": // Server prompts us with different questions necessary to create a new record, like: Nurse, Patient, Divisions, Contents. Not sure, but should this create a new user in the server database?
                        break;
                    case "deleteRecords": // Server prompts us to enter which record to delete.
                        break;
                    default: // Error
                        break;
                }
            }

            in.close();
            out.close();
            input.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
