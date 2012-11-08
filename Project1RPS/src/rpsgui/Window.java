/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpsgui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import project1rps.Choice;
import project1rps.Rps;
import rpsp2p.Peer2PeerClient;
import rpsp2p.Peer2PeerSendChoice;
import rpsp2p.Peer2PeerSendDisconnect;

/**
 * GUI Class, Contains the connexion control system, and the game control system.
 * 
 * @author Simon Cathébras, Zoé Bellot
 */
public class Window extends javax.swing.JFrame {

    /**
     * Creates new form Window
     */
    Peer2PeerClient p2pClient;
    Thread threadP2p;
    Integer totalScore;

    public Window() {
        initComponents();
        setGameVisible(false);
        setConnectionVisible(true);
        this.totalScore = 0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRock = new javax.swing.JButton();
        btnPaper = new javax.swing.JButton();
        btnScissors = new javax.swing.JButton();
        labLastScore = new javax.swing.JLabel();
        labTotalScore = new javax.swing.JLabel();
        labPlayerName = new javax.swing.JLabel();
        btnDisconnect = new javax.swing.JButton();
        txtPort = new javax.swing.JTextField();
        txtIpAdress = new javax.swing.JTextField();
        txtPlayerName = new javax.swing.JTextField();
        btnConnect = new javax.swing.JButton();
        labIpAdress = new javax.swing.JLabel();
        labRemotePort = new javax.swing.JLabel();
        labChangePlayerName = new javax.swing.JLabel();
        labLocalPort = new javax.swing.JLabel();
        txtLocalPort = new javax.swing.JTextField();
        labScore = new javax.swing.JLabel();
        labTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rock Paper Scissors");

        btnRock.setText("Rock");
        btnRock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRockActionPerformed(evt);
            }
        });

        btnPaper.setText("Paper");
        btnPaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaperActionPerformed(evt);
            }
        });

        btnScissors.setText("Scissors");
        btnScissors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScissorsActionPerformed(evt);
            }
        });

        labLastScore.setText("Last score:");

        labTotalScore.setText("Total:");

        labPlayerName.setText("Player");

        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectActionPerformed(evt);
            }
        });

        txtIpAdress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIpAdressActionPerformed(evt);
            }
        });

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        labIpAdress.setText("Ip address");

        labRemotePort.setText("Port");

        labChangePlayerName.setText("Player Name");

        labLocalPort.setText("Local Port:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labPlayerName)
                            .addComponent(btnRock)
                            .addComponent(btnScissors)
                            .addComponent(btnPaper))
                        .addGap(184, 184, 184)
                        .addComponent(labLastScore))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labTotalScore)
                        .addGap(4, 4, 4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(labScore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labLocalPort))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(labTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labIpAdress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labRemotePort, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labChangePlayerName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnConnect, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtPlayerName, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtPort, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtIpAdress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDisconnect)
                    .addComponent(txtLocalPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtIpAdress, txtLocalPort, txtPlayerName, txtPort});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labPlayerName)
                        .addGap(41, 41, 41)
                        .addComponent(btnRock)
                        .addGap(8, 8, 8)
                        .addComponent(btnPaper)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnScissors))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labLastScore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labScore))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labLocalPort)
                                .addComponent(txtLocalPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIpAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labIpAdress))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labRemotePort))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labChangePlayerName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnDisconnect)
                                    .addComponent(btnConnect)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labTotalScore)
                                .addComponent(labTotal)))))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIpAdressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIpAdressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIpAdressActionPerformed

    /**
     * Set the new score and the total score in the GUI.
     * Enable also the game's selection buttons.
     * @param newScore Score of the last played game.
     */
    public void setScore(Integer newScore) {
        //set le score + pop-up continuer/quitter + eventuellement degriser
        totalScore = totalScore + newScore;
        labScore.setText(newScore.toString());
        labTotal.setText(totalScore.toString());
        setBtnEnable(true);
    }
    
    private void setGameVisible(Boolean visible){
        btnPaper.setVisible(visible);
        btnRock.setVisible(visible);
        btnScissors.setVisible(visible);
        labScore.setVisible(visible);
        labTotal.setVisible(visible);
        labLastScore.setVisible(visible);
        labPlayerName.setVisible(visible);
        labTotalScore.setVisible(visible);
    }

    private void setConnectionVisible(Boolean visible){
        btnConnect.setVisible(visible);
        btnDisconnect.setVisible(!visible);
        txtIpAdress.setVisible(visible);
        txtPort.setVisible(visible);
        txtLocalPort.setVisible(visible);
        txtPlayerName.setVisible(visible);
        labLocalPort.setVisible(visible);
        labRemotePort.setVisible(visible);
        labChangePlayerName.setVisible(visible);
        labIpAdress.setVisible(visible);
    }
    
    private void setBtnEnable(boolean enable){
        btnPaper.setEnabled(enable);
        btnRock.setEnabled(enable);
        btnScissors.setEnabled(enable);
    }
    
    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        p2pClient = new Peer2PeerClient(
                        Integer.parseInt(txtLocalPort.getText()),
                        this);
        if (!(txtIpAdress.getText().equals(""))) {
            try {
                p2pClient.addPeer(
                        InetAddress.getByName(txtIpAdress.getText()),
                        Integer.parseInt(txtPort.getText()));
            } catch (UnknownHostException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setConnectionVisible(false);
        setGameVisible(true);
        this.totalScore = 0;
        this.labTotal.setText(this.totalScore.toString());
        labPlayerName.setText(txtPlayerName.getText());
        threadP2p = new Thread(p2pClient);
        threadP2p.start();
   }//GEN-LAST:event_btnConnectActionPerformed

    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconnectActionPerformed
        Thread threadDeconnection = new Thread(new Peer2PeerSendDisconnect(p2pClient));
        threadDeconnection.start();
        threadP2p.interrupt();
        this.setGameVisible(false);
        this.setConnectionVisible(true);
    }//GEN-LAST:event_btnDisconnectActionPerformed

    private void btnRockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRockActionPerformed
        this.setBtnEnable(false);
        p2pClient.setChoice(Choice.ROCK);        
        Thread threadSendPeer = new Thread(new Peer2PeerSendChoice(p2pClient, Choice.ROCK));
        threadSendPeer.start();
    }//GEN-LAST:event_btnRockActionPerformed

    private void btnPaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaperActionPerformed
        this.setBtnEnable(false);
        p2pClient.setChoice(Choice.PAPER);        
        Thread threadSendPeer = new Thread(new Peer2PeerSendChoice(p2pClient, Choice.PAPER));
        threadSendPeer.start();
    }//GEN-LAST:event_btnPaperActionPerformed

    private void btnScissorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScissorsActionPerformed
        this.setBtnEnable(false);
        p2pClient.setChoice(Choice.SCISSORS);
        Thread threadSendPeer = new Thread(new Peer2PeerSendChoice(p2pClient, Choice.SCISSORS));
        threadSendPeer.start();
    }//GEN-LAST:event_btnScissorsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Window().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnDisconnect;
    private javax.swing.JButton btnPaper;
    private javax.swing.JButton btnRock;
    private javax.swing.JButton btnScissors;
    private javax.swing.JLabel labChangePlayerName;
    private javax.swing.JLabel labIpAdress;
    private javax.swing.JLabel labLastScore;
    private javax.swing.JLabel labLocalPort;
    private javax.swing.JLabel labPlayerName;
    private javax.swing.JLabel labRemotePort;
    private javax.swing.JLabel labScore;
    private javax.swing.JLabel labTotal;
    private javax.swing.JLabel labTotalScore;
    private javax.swing.JTextField txtIpAdress;
    private javax.swing.JTextField txtLocalPort;
    private javax.swing.JTextField txtPlayerName;
    private javax.swing.JTextField txtPort;
    // End of variables declaration//GEN-END:variables
}
