/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.*;
import java.net.Socket;

/**
 *
 * @author atamarkin2
 */
public class ExchangeClient {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.out.println("Usage: \nclientTask <host> <port> <user> <password>");
            return;
        }
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        PrintStream pout = new PrintStream(socket.getOutputStream(), true);
        final BufferedReader bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pout.println(args[2] + " " + args[3]);
        
        new Thread(()->{
            String line;
            try{
                System.out.println("starting thread");
                while ((line = bin.readLine()) != null) {
                System.out.println("Received {" + line + "}");
                }
            } catch(IOException ex){
                System.err.println("IO error, exiting output thread");
            }
        }).start();

        
        Scanner stdin = new Scanner(System.in);
        while(stdin.hasNextLine()){
            pout.println(stdin.nextLine());
        }
        
        pout.println("CLOSE_CONNECTION");
        pout.flush();
        pout.close();
        bin.close();
    }

}