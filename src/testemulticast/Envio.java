/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testemulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Henrique
 */
public class Envio implements Runnable {

    private int num, tempo;

    Envio(int s, int tempo) {
        num = s;
        this.tempo = tempo;
    }
    
    @Override
    public void run() {
        // args give message contents and destination multicast group (e.g. "228.5.6.7")
        MulticastSocket s = null;
        try {
            //InetAddress group = InetAddress.getByName(args[1]);
            InetAddress group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket(6789);
            s.joinGroup(group);
            //byte[] m = args[0].getBytes();
            while (true) {
                byte[] m = ("oi de "+num).getBytes();
                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                s.send(messageOut);
                //Thread.sleep(tempo);
                long tempo = (long) (0 + Math.random()*10000);
                System.out.println(tempo);
                Thread.sleep(tempo);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(Envio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
    
}
