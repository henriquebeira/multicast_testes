/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testemulticast;

/**
 *
 * @author Henrique
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient2 {

    public static void main(String args[]) {
        // arguments supply message and hostname
        Socket socket = null;
        try {
            //Concetar com o Processo 1
            int serverPort = 7891;
            //s = new Socket(args[1], serverPort);
            socket = new Socket("localhost", serverPort);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //out.writeUTF(args[0]);      	// UTF is a string encoding see Sn. 4.4
            //String arquivodesejado = "oi.txt";
            Scanner entrada = new Scanner (System.in);
            System.out.print("Digite o nome do arquivo desejado: ");
            String arquivodesejado = entrada.nextLine();
            System.out.println("O nome digitado foi: " + arquivodesejado);
            out.writeUTF(arquivodesejado);      	// UTF is a string encoding see Sn. 4.4
            
            String data = in.readUTF();	    // read a line of data from the stream
            System.out.println("Received: " + data);
            
            if (!data.endsWith("--1")) {
                FileOutputStream fos = new FileOutputStream(new File("C:\\arquivos_chorare\\download\\2\\" + arquivodesejado));
                byte[] buf = new byte[4096];
                int i = 1;
                while (true) {
                    int len = in.read(buf);
                    if (len == -1) {
                        break;
                    }
                    fos.write(buf, 0, len);
                }
            }
            
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
}
