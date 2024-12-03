//By Caleb Martin
import java.io.*;
import java.net.*;

public class RSAClient
{
    public static void main(String[] args)
    {
        //Require Server Loc and Port
        if(args.length != 2)
        {
            System.out.println("usage: java OneWayMesgClient <server name> <server port>");
			System.exit(1);
        }

        String serverName = args[0];
		int serverPort = Integer.parseInt(args[1]);

        //Connect to server
        try (Socket socket = new Socket(serverName, serverPort))
        {
            System.out.println("Connected to server at ["+serverName+", "+serverPort+"]");

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Recieve Cryptographic data
            long n = Long.parseLong(fromServer.readLine());
            long e = Long.parseLong(fromServer.readLine());

            //Loop Resources
            String message, decrypted;
            long m, c;
            //Message loop
            while(true)
            {
                //Read user input
                message = fromUser.readLine();

                //Break Case
                if(message.equals("Q"))
                {
                    System.out.println("Closing Connection.");
                    writer.println(message);
                    break;
                }

                //Encrypt
                m = Long.parseLong(message);
                c = RSA.modPow(m, e, n);

                System.out.println("Message " + m + " Encrypted to " + c);

                //Send
                writer.println(c);

                //Read returned plaintext
                decrypted = fromServer.readLine();
                System.out.println("Returned: " + decrypted);
            }

            //Cleanup
            writer.close();
            fromServer.close();
            fromUser.close();
        }
        catch(Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }
    }
}
