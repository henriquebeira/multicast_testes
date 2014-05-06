/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.*;
import java.security.*;

class GenSig {

    public static void main(String[] args) {
        
        String args0 = "C:\\arquivos_chorare\\server\\suepk";
        String args1 = "C:\\arquivos_chorare\\server\\sig";
        String args2 = "C:\\arquivos_chorare\\server\\dados.txt";

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
;

}
