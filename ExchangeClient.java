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
        
        final Data data = new Data();
        
        new Thread(()->{
            String line;
            try{
                System.out.println("starting thread");
                while ((line = bin.readLine()) != null) {
                    System.out.println("Received {" + line + "}");
                    Scanner sc = new Scanner(line);
                    String type = sc.next();
                    if(type.equals("STATUS_OUT") || type.equals("SCAN_OUT")){
                        data.clear();
                        
                        data.updateCurrentPlayer(sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),sc.nextDouble());
                        
                        sc.next(); //MINES
                        
                        int numMines = sc.nextInt();
                        for(int i=0;i<numMines; i++){
                            data.addMine(sc.nextInt(), sc.nextDouble(), sc.nextDouble());
                        }
                        
                        sc.next(); // PLAYERS
                        
                        int numPlayers = sc.nextInt();
                        for(int i=0;i<numPlayers; i++){
                            data.addPlayer(sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),sc.nextDouble());
                        }
                        
                        sc.next(); // BOMBS
                        
                        int numBombs = sc.nextInt();
                        for(int i=0;i<numBombs; i++){
                            data.addBomb(sc.nextDouble(), sc.nextDouble());
                        }
                        
                        System.out.println(data);
                    }
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