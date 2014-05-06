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
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class TCPClient1 {

    public static void main(String args[]) {
        // arguments supply message and hostname
        Socket socket = null;
        try {
            String arquivodesejado = "dados.txt";
            // Porta do Processo 1
            int serverPort = 7892;
            
            //Transferir arquivo
            socket = new Socket("localhost", serverPort);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(arquivodesejado);      	// UTF is a string encoding see Sn. 4.4
            
            String data = in.readUTF();	    // read a line of data from the stream
            System.out.println("Received: " + data);
            
            if (!data.endsWith("--1")) {
                FileOutputStream fos = new FileOutputStream(new File("C:\\arquivos_chorare\\client\\" + arquivodesejado));
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
            in.close();
            out.close();
            socket.close();
            
            //Transferir signature
            socket = new Socket("localhost", serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("sig");
            
            data = in.readUTF();	    // read a line of data from the stream
            System.out.println("Received: " + data);

            if (!data.endsWith("--1")) {
                FileOutputStream fos = new FileOutputStream(new File("C:\\arquivos_chorare\\client\\sig"));
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
            in.close();
            out.close();
            socket.close();
            
            //Transferir public key
            socket = new Socket("localhost", serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("suepk");

            data = in.readUTF();	    // read a line of data from the stream
            System.out.println("Received: " + data);

            if (!data.endsWith("--1")) {
                FileOutputStream fos = new FileOutputStream(new File("C:\\arquivos_chorare\\client\\suepk"));
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
            in.close();
            out.close();
            socket.close();
            
            conferirAssinaturaDigital(arquivodesejado);
            
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

    private static void conferirAssinaturaDigital(String arquivodesejado) {
        String args0 = "C:\\arquivos_chorare\\client\\suepk";
        String args1 = "C:\\arquivos_chorare\\client\\sig";
        String args2 = "C:\\arquivos_chorare\\client\\"+arquivodesejado;

        try {

            /* import encoded public key */
            FileInputStream keyfis = new FileInputStream(args0);
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);

            keyfis.close();

            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);

            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

            /* input the signature bytes */
            FileInputStream sigfis = new FileInputStream(args1);
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify);

            sigfis.close();

            /* create a Signature object and initialize it with the public key */
            Signature sig = Signature.getInstance("DSA");
            sig.initVerify(pubKey);

            /* Update and verify the data */
            FileInputStream datafis = new FileInputStream(args2);
            BufferedInputStream bufin = new BufferedInputStream(datafis);

            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            };

            bufin.close();

            boolean verifies = sig.verify(sigToVerify);

            System.out.println("signature verifies: " + verifies);

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
}
