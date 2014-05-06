/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testemulticast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Henrique
 */
public class RSA {
    private static byte[] input;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, 
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        // TODO code application logic here
        SecureRandom random = new SecureRandom(); //gera um numero aleatório seguro

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048, random); //chaves de 2048 bits
        KeyPair pair = generator.generateKeyPair(); //Gera as chaves pública e privada
        PublicKey pubKey = pair.getPublic(); //chave pública
        PrivateKey privKey = pair.getPrivate(); //chave privada

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey, random); //cifra com a chave pública

        byte[] mensagemBytes = ("oi" + ";" + "oi" + ";").getBytes("UTF-8");
        String mensagem = new String(mensagemBytes, "UTF-8");
        System.out.println("mensagem original: "+mensagem);
        
        byte[] cipherText = cipher.doFinal(mensagem.getBytes()); //mensagem cifrada
        mensagem = new String(cipherText, "UTF-8");
        System.out.println("mensagem cifrada: "+mensagem);
        
        cipher.init(Cipher.DECRYPT_MODE, privKey); //decifra com a chave privada
        byte[] plainText = cipher.doFinal(cipherText); //mensagem decifrada
        mensagem = new String(plainText, "UTF-8");
        System.out.println("mensagem decifrada: "+mensagem);
    }
    
}
