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

public class TCPServer2 {

    public static void main(String args[]) {
        try {
            int serverPort = 7892; // the server port
            String caminho = "C:\\arquivos_chorare\\server";
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket, caminho);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}


