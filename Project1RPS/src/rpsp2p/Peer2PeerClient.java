/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpsp2p;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import project1rps.Choice;
import rpsgui.Window;

/**
 * Threaded class, runs the listening socket to all potential incoming requests
 * @author Simon Cathebras, Zoe Bellot
 */
public class Peer2PeerClient extends Thread {

    private Integer localPort;
    private ArrayList<Peer> peerList;
    private ArrayList<Choice> choiceList;
    private Choice playerChoice;
    private Window rpsWindow;
    private ServerSocket serverSocket;

    /**
     * Generate a PeerToPeerClient.
     * @param port Local port where the server socket will listen.
     * @param window GUI Window.
     */
    public Peer2PeerClient(
            Integer port,
            Window window) {
        this.localPort = port;
        this.peerList = new ArrayList<Peer>();
        this.choiceList = new ArrayList<Choice>();
        this.playerChoice = null;
        this.rpsWindow = window;
    }

    /**
     * Generate a Peer2PeerClient as a copy of peer.
     * @param peer P2Pclient to be copied.
     */
    public Peer2PeerClient(Peer2PeerClient peer) {
        this.localPort = peer.localPort;
        this.peerList = peer.peerList;
        this.rpsWindow = peer.rpsWindow;
        this.serverSocket = peer.serverSocket;
    }

    /**
     * Add a new peer corresponding to Ip Adress and port in parameters to peerList.
     * @param ipAddress
     * @param remotePort 
     */
    public void addPeer(InetAddress ipAddress, Integer remotePort) {
        this.peerList.add(new Peer(ipAddress, remotePort));
    }

    /**
     *
     * Connect the calling peer to an existing ring
     *
     * Procedure: Send "Join" message to a peer Send the Id of LocalHost (IP +
     * Port) Wait for the list of peers in reply Send to all other peer his
     * LocalHost ID
     *
     * @since peerList is correctly initialised with remote port and remote Ip
     * adress
     *
     */
    private Integer connectToRing() {

        System.out.println("connectToRing");
        if (peerList.isEmpty()) {
            /*
             * if the list is empty, this is the first peer in the system
             */
            return 0;
        }
        InetAddress localAdress = null;
        try {
            localAdress = java.net.InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.err.println("Could not get the local Host adress");
        }

        Peer localHost = new Peer(localAdress, this.localPort);


        Peer target = peerList.get(0);
        Socket clientSocket = null;

        try {
            clientSocket = new Socket(target.ipAdress, target.port);
        } catch (IOException ex) {
            System.err.println("unable to connect "
                    + target.ipAdress.toString()
                    + ":"
                    + target.port);
        }

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
        }

        /*
         * send JOIN to its first Peer
         */
        System.out.println("send JOIN");
        String toTarget = "JOIN";
        try {
            out.writeObject(toTarget);
            out.flush();
            out.writeObject(localHost);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Peer2PeerClient.class.getName()).log(Level.SEVERE, null, ex);
        }


        try {
            /*
             * receive a reponse of the first peer with the list of new peers
             */
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }
        Object newPeerList = null;
        try {
            newPeerList = in.readObject();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.toString());
            return 1;
        }

        /*
         * Integer nextRound; try { nextRound = in.readInt(); } catch
         * (OptionalDataException ode) { System.out.println(ode.toString());
         * return 1; }
         */

        if (!(newPeerList instanceof ArrayList)) {
            System.err.println("Transmission error");
            System.exit(1);
        }

        /*
         * add the other peers
         */
        for (Iterator<Peer> it = ((ArrayList) newPeerList).iterator();
                it.hasNext();) {
            Peer peer = it.next();
            peerList.add(peer);
        }

        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }

        /*
         * ask other adds to add it
         */
        for (Iterator<Peer> it = ((ArrayList) newPeerList).iterator(); it.hasNext();) {
            Peer peer = it.next();
            /*
             * create socket
             */
            Socket newClientSocket = null;
            try {
                newClientSocket = new Socket(peer.ipAdress, peer.port);
            } catch (IOException ex) {
                System.err.println("unable to connect "
                        + peer.ipAdress.toString()
                        + ":"
                        + peer.port);
            }

            try {
                out = new ObjectOutputStream(newClientSocket.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.toString());
                System.exit(1);
            }

            /*
             * send ADD ME (for the game number "game")
             */
            toTarget = "ADDME";

            try {
                out.writeObject(toTarget);
                out.flush();
                out.writeObject(localHost);
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try {
                /*
                 * Close socket
                 */
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Peer2PeerClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                out.close();
                newClientSocket.close();

            } catch (IOException ex) {
                System.err.println(ex.toString());
            }
        }
        return 0;
    }

    /**
     *
     * add the new peer for the current game or the next game
     *
     * @param clientSocket
     * @param peer
     */
    private void doAdd(ObjectInputStream in, ObjectOutputStream out) {

        Object peer = null;
        try {
            peer = in.readObject();
        } catch (IOException ex) {
            System.err.println("peer could not be read " + ex.toString());
        } catch (ClassNotFoundException ex) {
            System.err.println("Error in reading peer " + ex.toString());
        }

        if (peer instanceof Peer) {
            peerList.add((Peer) peer);
        } else {
            System.err.println("misreading of peer");
            System.exit(1);
        }

    }

    /**
     * do the required job to insert peer into the p2p ring Send the actual
     * peerList to this peer, using clientSocket.
     *
     * @param clientSocket
     * @param peer
     */
    private void doJoin(ObjectInputStream in, ObjectOutputStream out) {

        /*
         * Send the peerList to the new host
         */
        Object peer = null;
        try {
            peer = in.readObject();
        } catch (IOException ex) {
            System.err.println("peer could not be read " + ex.toString());
        } catch (ClassNotFoundException ex) {
            System.err.println("Error in reading peer " + ex.toString());
        }

        try {
            out.writeObject(peerList);
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }

        /*
         * ADD THE RECEIVED PEER TO THE LIST
         */
        if (peer instanceof Peer) {
            peerList.add((Peer) peer);
        } else {
            System.err.println("misreading of peer !");
            System.exit(1);
        }


    }

    /**
     * Remove peer from the peerList
     *
     * @param peer
     */
    private void doQuit(ObjectInputStream in, ObjectOutputStream out) {
        Object peer = null;
        try {
            peer = in.readObject();
        } catch (IOException ex) {
            System.err.println("peer could not be read " + ex.toString());
        } catch (ClassNotFoundException ex) {
            System.err.println("Error in reading peer " + ex.toString());
        }
        if(peer instanceof Peer){
           // boolean bool = peerList.get(0).equals(peer);
            peerList.remove(peerList.indexOf((Peer) peer));
        }else{
            System.err.println("misreading of peer !");
            System.exit(1);
        }
    }

    /**
     * Compute the result sended by peer
     *
     * @param peer
     */
    private void doPlay(ObjectInputStream in, ObjectOutputStream out) {

        /*
         * Receive the choie of the other peers
         */

        Object choice = null;
        try {
            choice = in.readObject();
        } catch (IOException ex) {
            System.err.println("error in reading. " + ex.toString());
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.toString());
        }

        if (!(choice instanceof Choice)) {
            System.err.println("Transmission error");
            System.exit(1);
        }

        choiceList.add((Choice) choice);
        if ((choiceList.size() == peerList.size()) && (playerChoice != null)) {
            endOfGame();
        }
    }

    /**
     *
     * Compute the game score
     *
     */
    private void endOfGame() {
        /*
         * Compute the game score
         */
        Integer score = 0;
        for (Iterator<Choice> it = choiceList.iterator(); it.hasNext();) {
            if (win(playerChoice, it.next())) {
                score = score + 1;
            }
        }
        /*
         * Update choices for the next game
         */
        choiceList.clear();
        playerChoice = null;
        /*
         * Update score
         */
        rpsWindow.setScore(score);
    }

    /**
     *
     * Return if p1 win or not
     *
     * @param p1 : choice of the first player
     * @param p2 : choice of the second player
     * @return boolean that indiates if p1 win or no
     */
    private boolean win(Choice p1, Choice p2) {
        if (p1.equals(Choice.ROCK)) {
            if (p2.equals(Choice.ROCK)) {
                return false;
            }
            if (p2.equals(Choice.SCISSORS)) {
                return true;
            }
            if (p2.equals(Choice.PAPER)) {
                return false;
            }
        }
        if (p1.equals(Choice.SCISSORS)) {
            if (p2.equals(Choice.ROCK)) {
                return false;
            }
            if (p2.equals(Choice.SCISSORS)) {
                return false;
            }
            if (p2.equals(Choice.PAPER)) {
                return true;
            }
        }
        if (p1.equals(Choice.PAPER)) {
            if (p2.equals(Choice.ROCK)) {
                return true;
            }
            if (p2.equals(Choice.SCISSORS)) {
                return false;
            }
            if (p2.equals(Choice.PAPER)) {
                return false;
            }
        } else {
            System.err.println("Choice error");
            System.exit(1);
        }
        return false;
    }

    /**
     * Handle all incoming request on this peer
     *
     * @param clientSocket
     * @return
     */
    private Integer handleConnection(Socket clientSocket) {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        Object input = null;
        try {
            input = in.readObject();
        } catch (IOException ex) {
            System.err.println("error in reading");
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.toString());
        }

        if (!(input instanceof String)) {
            System.err.println("Transmission error");
            System.exit(1);
        }

        if (input.equals("JOIN")) {
            doJoin(in, out);
            System.out.println("Joined !");
        }
        if (input.equals("QUIT")) {
            doQuit(in, out);
        }
        if (input.equals("PLAY")) {
            doPlay(in, out);
        }
        if (input.equals("ADDME")) {
            doAdd(in, out);
        }
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
            System.err.println("error while closing the input stream");
        }
        return 0;
    }

    /**
     * Broadcast "QUIT" message to all other peers.
     * 
     * @throws IOException 
     */
    public void disconnect() throws IOException {
        InetAddress localAdress = java.net.InetAddress.getLocalHost();
        
        /*
         * broadcast to all peers that we are leaving
         */
        
        Peer localHost = new Peer(localAdress, this.localPort);
        

        for (Iterator<Peer> it = peerList.iterator(); it.hasNext();) {
            Peer peer = it.next();
            Socket clientSocket = null;
            try {
                clientSocket = new Socket(peer.ipAdress, peer.port);
            } catch (IOException ex) {
                System.err.println("unable to connect "
                        + peer.ipAdress.toString()
                        + ":"
                        + peer.port);
            }

            ObjectOutputStream out = null;

            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.toString());
                System.exit(1);
            }

            /*
             * send bye
             */
            String toTarget = "QUIT";
            try {
                out.writeObject(toTarget);
                out.flush();
                out.writeObject(localHost);
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Peer2PeerClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.close();
            clientSocket.close();

        }

    }

    /**
     * Set the current player's choice.
     * 
     * @param choice Choice to be choosed
     */
    public void setChoice(Choice choice) {
        playerChoice = choice;
        if ((choiceList.size() == peerList.size())) {
            endOfGame();
        } 
    }

 
    /**
     * Sends to all peers the choice of the local user.
     * @param choice The choice to be send
     * @return error code
     * @throws IOException 
     */
    public Integer sendToPeers(Choice choice) throws IOException {
        for (Iterator<Peer> it = peerList.iterator(); it.hasNext();) {
            Peer peer = it.next();
            /*
             * create socket
             */
            Socket clientSocket = null;
            try {
                clientSocket = new Socket(peer.ipAdress, peer.port);
            } catch (IOException ex) {
                System.err.println("unable to connect "
                        + peer.ipAdress.toString()
                        + ":"
                        + peer.port);
            }

            ObjectOutputStream out = null;

            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.toString());
                System.exit(1);
            }

            /*
             * send the choice
             */
            String toTarget = "PLAY";

            try {
                out.writeObject(toTarget);
                out.flush();
                out.writeObject(choice);
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try {
                /*
                 * close streams and socket
                 */
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                break;
            }
            out.close();
            clientSocket.close();
        }
        return 0;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        try {
            this.serverSocket.close();
        } catch (IOException ex) {
            System.err.println("erreur while closing");
        }
    }

    /*
     *
     */
    @Override
    public void run() {
        this.serverSocket = null;

        /*
         * connection to the ring
         */
        connectToRing();

        try {
            serverSocket = new ServerSocket(localPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + localPort);
            System.exit(1);
        }


        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                handleConnection(clientSocket);
                clientSocket.close();
            } catch (InterruptedIOException ex) {
                Thread.currentThread().interrupt();
                break;
            } catch (IOException ex) {
                if (!isInterrupted()) {
                    System.err.print("Connection error on port: " + localPort);
                } else {
                    break;
                }
            }
        }

    }
}
