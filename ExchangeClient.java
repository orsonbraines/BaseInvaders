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

    //static Vector<Integer> oldScores, newScores;

static double mapwidth, mapheight,captureradius, visionradius, friction,
            bfriction, bombpr, bomber, bombdelay, bombpower, scanradius, scandelay;

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

        final String username = args[2];
        final Data data = new Data(username);
        Motion motion = new Motion(new V2d(0,0), data.currentPlayer, mapwidth, mapheight);
        pout.println("CONFIGURATIONS");

        new Thread(()->{
            String line;
            int counter = 0;
            try{
                System.out.println("starting thread");

                boolean drifting = false;
                while ((line = bin.readLine()) != null) {
                    //System.out.println("Received {" + line + "}");
                    Scanner sc = new Scanner(line);
                    String type = sc.next();
                    if(type.equals("BOMB_OUT")){
                      System.out.println("Bombs");
                    }

                    if(type.equals("STATUS_OUT") || type.equals("SCAN_OUT")){
                        //System.out.println("status");
                        data.clear();

                        data.updateCurrentPlayer(sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),sc.nextDouble());

                        sc.next(); //MINES

                        int numMines = sc.nextInt();
                        for(int i=0;i<numMines; i++){
                            data.addMine(sc.next(), sc.nextDouble(), sc.nextDouble());
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

                        if(counter % 200 == 0) System.out.println(data);
                        counter++;

                            //write(pout, Motion.moveTowards(data.currentPlayer.r, data.currentPlayer.v, new V2d(2500,2500)));
                           // System.out.println("v" + data.currentPlayer.v);


                        Mine closest = data.closestMine();
                        if(closest != null && !closest.r.equals(motion.target, mapwidth, mapheight)){
                            System.out.println("Changing target to closest mine");
                            motion.changeTarget(closest.r);
                        }

                        if(closest != null){
                            drifting = false;
                            String cmd = motion.move();
                            if(cmd.length() > 0) {
                                System.out.println(cmd);
                                System.out.println(V2d.sub(motion.target, data.currentPlayer.r));
                                write(pout,cmd);

                            }
                        }
                        else if(!drifting){
                            drifting = true;
                            System.out.println("starting drifting");
                            write(pout, "ACCELERATE 0.2 1");
                            write(pout, ("BOMB" + " " + data.currentPlayer.r.x + " " + data.currentPlayer.r.y));
                        }


                    }
                    else if(type.equals("SCOREBOARD_OUT")){
                        //System.out.println(line);
                        while(sc.hasNext()){
                            String user = sc.next();
                            sc.nextInt(); //score
                            if(user.equals(username)){
                                data.setMinesOwned(sc.nextInt());
                                break;
                            }
                            else{
                                sc.nextInt();
                            }
                        }
                    }

                    else if(type.equals("CONFIGURATIONS_OUT")){
                        String [] results = line.split(" ");
                        // System.out.println(Arrays.toString(results));

                        mapwidth = Double.parseDouble(results[2]);
                        mapheight = Double.parseDouble(results[4]);
                        captureradius = Double.parseDouble(results[6]);
                        visionradius = Double.parseDouble(results[8]);
                        friction = Double.parseDouble(results[10]);
                        bfriction = Double.parseDouble(results[12]);
                        bombpr = Double.parseDouble(results[14]);
                        bomber = Double.parseDouble(results[16]);
                        bombdelay = Double.parseDouble(results[18]);
                        bombpower = Double.parseDouble(results[20]);
                        scanradius = Double.parseDouble(results[22]);
                        scandelay = Double.parseDouble(results[24]);

                        System.out.println("scandelay " +scandelay);
                        data.setSize(mapwidth, mapheight);
                    }
                }
            } catch(IOException ex){
                System.err.println("IO error, exiting output thread");
            }
        }).start();

        // new Thread(()->{
        //   while(true){
        //     write(pout, motion.placeMoveBomb(data.currentPlayer.r, 0.5));
        //     try{
        //     Thread.sleep(1000);
        //     } catch(InterruptedException ex){}
        //   }
        // }).start();

        new Thread(()->{
            while(true){
                write(pout, "STATUS");
                try{
                Thread.sleep(25);
                } catch(InterruptedException ex){}
            }
        }).start();

        new Thread(()->{
            while(true){
                pout.println("SCOREBOARD");
                try{
                Thread.sleep(20);
                } catch(InterruptedException ex){}
            }
        }).start();


        Scanner stdin = new Scanner(System.in);
        while(stdin.hasNextLine()){
            write(pout,stdin.nextLine());
            //pout.println(Motion.moveTowards(data.currentPlayer.r, data.currentPlayer.v, new V2d(2500,2500)));
        }

        pout.println("CLOSE_CONNECTION");
        pout.flush();
        pout.close();
        bin.close();
    }

    static void write(PrintStream pout, String cmd){
        synchronized(pout){
            //System.out.println("outputting cmd : " + cmd);
            pout.println(cmd);
        }
    }
}
