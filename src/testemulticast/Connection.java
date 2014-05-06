/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testemulticast;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

/**
 *
 * @author Henrique
 */
class Connection extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    String caminho;

    public Connection(Socket aClientSocket, String caminho) {
        try {
            clientSocket = aClientSocket;
            this.caminho = caminho;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {			                 // an echo server
            String data = in.readUTF();	                  // read a line of data from the stream
            for (File f : new File(caminho).listFiles()) {
                if (f.getName().equals(data)) {
                    //Se o arquivo existir, logo é enviado por unicast
                    out.writeUTF(data);
                    FileInputStream fis = new FileInputStream(f);
                    byte[] buf = new byte[4096];
                    while (true) {
                        int len = fis.read(buf);
                        if (len == -1) {
                            break;
                        }
                        out.write(buf, 0, len);
                    }
                    if ((!data.equals("suepk"))&&(!data.equals("sig"))) {
                        criarChaveComAssinatura(data);
                    }
                    
                } 
            }
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/

            }
        }

    }

    private void criarChaveComAssinatura(String data) {
        String args0 = "C:\\arquivos_chorare\\server\\suepk";
        String args1 = "C:\\arquivos_chorare\\server\\sig";
        String args2 = "C:\\arquivos_chorare\\server\\"+data;

        /* Generate a DSA signature */
        /*if (args.length != 1) {
         System.out.println("Usage: GenSig nameOfFileToSign");
         } else {*/
        try {

            /* Generate a key pair */
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            SecureRandom random = new SecureRandom(); //gera um numero aleatório seguro

            keyGen.initialize(1024, random); //chaves de 1024 bits

            KeyPair pair = keyGen.generateKeyPair(); //Gera as chaves pública e privada
            PrivateKey priv = pair.getPrivate(); //chave privada
            PublicKey pub = pair.getPublic(); //chave pública

            /* Create a Signature object and initialize it with the private key */
            Signature rsa = Signature.getInstance("DSA");

            rsa.initSign(priv);

            /* Update and sign the data */
            //FileInputStream fis = new FileInputStream(args[0]);
            FileInputStream fis = new FileInputStream(args2);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                rsa.update(buffer, 0, len);
            };

            bufin.close();

            /* Now that all the data to be signed has been read in, 
             generate a signature for it */
            byte[] realSig = rsa.sign();

            /* Save the signature in a file */
            FileOutputStream sigfos = new FileOutputStream(args1);
            sigfos.write(realSig);

            sigfos.close();


            /* Save the public key in a file */
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream(args0);
            keyfos.write(key);

            keyfos.close();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
        //}
    }
}
