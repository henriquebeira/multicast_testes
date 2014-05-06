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

public class TCPEnviatListaTeste implements Runnable {

    //porta do server, número do processo do cliente
    private int porta=7892, numeroProcesso=1;

    TCPEnviatListaTeste(int porta, int numeroProcesso) {
        this.porta = porta;
        this.numeroProcesso = numeroProcesso;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            //Concetar com o Processo "server"
            int portaEnviaLista = porta;
            socket = new Socket("localhost", portaEnviaLista);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //out.writeUTF(args[0]);      	// UTF is a string encoding see Sn. 4.4
            //String arquivodesejado = "oi.txt";
            for (File f : new File("C:\\arquivos_chorare\\upload\\" + numeroProcesso).listFiles()) {
                    //Se o arquivo existir, logo é enviado por unicast
                    out.writeUTF(f.getName());
                    System.out.println("Respondeu que tem os arquivos: " + f.getName());
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
