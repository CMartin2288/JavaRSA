//By Caleb Martin
import java.io.*;
import java.net.*;
import java.util.Random;

public class RSAServer
{
    static Random rand = new Random();
    public static void main(String[] args)
    {
        try (ServerSocket socket = new ServerSocket(0))
        {
            //Timeout after 30 seconds
            socket.setSoTimeout(30000);

            //Loop for multiple connections
            while (true)
            {
                //Accept client
                System.out.println("Hosting on port " + socket.getLocalPort());
                
                Socket activeSocket = socket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(activeSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(activeSocket.getOutputStream(), true);

                System.out.println("Client Connected.");

                //Generate Primes p and q (Miller-Rabin).
                int p = RSA.getPrime();
                int q;
                do
                {
                    q = RSA.getPrime();
                } 
                while(q == p);

                long n = p*q;

                //Generate e st. GCD(e, (p-1)(q-1)) = 1 (Brute Force with e's of (2^k)+1)
                long e = RSA.getEncrypt(p, q);

                //Calculate d from the ExtendedGCD(e, (p-1)(q-1)) (d is y)
                long d = RSA.getDecrypt(p, q, e);
                
                //Send Cryptographic Requirements (N, e)
                writer.println(n);
                writer.println(e);


                String message;
                long c, m;
                //Message Loop
                while(true)
                {
                    //Read for encrypted message
                    message = reader.readLine();

                    //Break Case
                    if(message.equals("Q"))
                    {
                        System.out.println("Client Closed Connection");
                        break;
                    }

                    c = Long.parseLong(message);
                    System.out.println("Recieved " + c);

                    //Decrypt the message
                    m = RSA.modPow(c, d, n);
                    System.out.println("Decrypted " + m);

                    //Send initial value back
                    writer.println(m);
                }

                //Cleanup
                reader.close();
                writer.close();
            }
        }
        catch (Exception e) 
        {
            System.err.println(e);
            System.exit(1);
        }
    }
}