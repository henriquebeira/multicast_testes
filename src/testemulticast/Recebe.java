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

/**
 *
 * @author Henrique
 */
public class Recebe implements Runnable{

    private int num;

    Recebe(int s) {
        num = s;
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
            while (true) {
                byte[] buffer = new byte[1000];
                for (int i = 0; i < 3; i++) {		// get messages from others in group
                    DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                    s.receive(messageIn);
                    System.out.println("Processo "+num+" recebeu: " + new String(messageIn.getData()));
                }
                break;
            }
            //Após eleição, cria lista com número do Processo, porta, arquivo.
            
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
    
}
