
import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.util.logging.*;
import database.*;
import java.util.Arrays;
import java.util.List;



public class server2 implements Runnable {
    private ServerSocket serverSocket = null;
    private static int numConnectedClients = 0;


    // maybe change name from MyLog?
    private Logger logger = Logger.getLogger("MyLog");
    FileHandler fh;

    // Database, what will path be? Import from utility?
    private Database db = new Database();
    private API api = new API(db);

    public server2(ServerSocket ss) throws IOException {
        serverSocket = ss;
        newListener();
        setUpLog();
        logger.info("Connection up.");
    }

    public void run() {
        try {
            SSLSocket socket=(SSLSocket)serverSocket.accept();
            newListener();
            SSLSession session = socket.getSession();
            X509Certificate cert = (X509Certificate)session.getPeerCertificateChain()[0];
            String subject = cert.getSubjectDN().getName();
            String issuer = cert.getIssuerDN().getName();
            String serial = cert.getSerialNumber().toString();
    	      numConnectedClients++;
            // System.out.println("client connected");
            // System.out.println("client name (cert subject DN field): " + subject);
            // System.out.println(cert.getIssuerDN());
            // System.out.println("the issuer is: " + issuer);
            // System.out.println("the serial is: " + serial);

   	    System.out.println("Connection created");
   	    Person person = db.getPerson(issuer, serial);
   		if(person == null) {
   			// Person is not in database, interrupt connection?
   			throw new IOException("Access denied");
   		} else {
            // Person is in database
            System.out.println(numConnectedClients + " concurrent connection(s)\n");
            PrintWriter out = null;
            BufferedReader in = null;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Welcome message
            out.println("Welcome "+ person.getName() + "\n");
            out.flush();

            // Start reading comands
            String clientMsg = null;
            while ((clientMsg = in.readLine()) != null) {
                /* EX how commands can be run:
   			  	// cmd "I want to read journal for patient "P02"
					String res = db.readJournal(person, "P02");
					System.out.println(res); // = "Broken arm"

					// cmd "I want to write "better now" to journal for patient "P02"
					res = db.writeJournal(person, "P02", "better now");
					System.out.println(res); // = access denied

   			    */
                clientMsg.replaceAll("\r", "");
			    String rev = new StringBuilder(clientMsg).toString();
                System.out.println("received '" + clientMsg + "' from client");
                List<String> cmd = Arrays.asList(clientMsg.split(" "));
                String result = api.executeCmd(cmd, person);
                //String result = cmd.get(0);
                System.out.print("sending '" + result + "' to client...");
				out.println(result + "\r");
				out.flush();
                System.out.println("done\n");
			}
			in.close();
			out.close();
			socket.close();
    	    numConnectedClients--;
            System.out.println("client disconnected");
            System.out.println(numConnectedClients + " concurrent connection(s)\n");
   		}
		} catch (IOException e) {
            System.out.println("Client died: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    private void newListener() { (new Thread(this)).start(); } // calls run()

    public static void main(String args[]) {
        System.out.println("\nServer Started\n");
        int port = -1;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        String type = "TLS";
        try {
            ServerSocketFactory ssf = getServerSocketFactory(type);
            ServerSocket ss = ssf.createServerSocket(port);
            ((SSLServerSocket)ss).setNeedClientAuth(true); // enables client authentication
            new server2(ss);
        } catch (IOException e) {
            System.out.println("Unable to start Server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static ServerSocketFactory getServerSocketFactory(String type) {
        if (type.equals("TLS")) {
            SSLServerSocketFactory ssf = null;
            try { // set up key manager to perform server authentication
                SSLContext ctx = SSLContext.getInstance("TLS");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                KeyStore ks = KeyStore.getInstance("JKS");
				KeyStore ts = KeyStore.getInstance("JKS");
                char[] password = "password".toCharArray();

                ks.load(new FileInputStream("serverkeystore"), password);  // keystore password (storepass)
                ts.load(new FileInputStream("servertruststore"), password); // truststore password (storepass)
                kmf.init(ks, password); // certificate password (keypass)
                tmf.init(ts);  // possible to use keystore as truststore here
                ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                ssf = ctx.getServerSocketFactory();
                return ssf;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ServerSocketFactory.getDefault();
        }
        return null;
    }

    private void setUpLog() {
        try {

            // Remove all handlers so that we can add desired ones later (no print to terminal)
            logger.setUseParentHandlers(false);
            // This block configure the logger with handler and formatter
            fh = new FileHandler("TestLogger.txt", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("Log created.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
