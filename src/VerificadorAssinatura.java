

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
import java.security.spec.*;

class VerificadorAssinatura {

    private final String caminhoPublicKey;
    private final String caminhoAssinatura;
    private final String caminhoArquivo;
    private boolean verifies;

    public VerificadorAssinatura(String caminhoDiretorioControle) {
        this.caminhoPublicKey = caminhoDiretorioControle + File.separator + "public_key";
        this.caminhoAssinatura = caminhoDiretorioControle + File.separator + "assinatura";
        this.caminhoArquivo = caminhoDiretorioControle + File.separator + "quem_tem.txt";
    }
    
    public boolean verificar() {
            try {

                /* import encoded public key */
                FileInputStream keyfis = new FileInputStream(caminhoPublicKey);
                byte[] encKey = new byte[keyfis.available()];
                keyfis.read(encKey);
                keyfis.close();

                X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);

                KeyFactory keyFactory = KeyFactory.getInstance("DSA");
                PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

                /* input the signature bytes */
                FileInputStream sigfis = new FileInputStream(caminhoAssinatura);
                byte[] sigToVerify = new byte[sigfis.available()];
                sigfis.read(sigToVerify);

                sigfis.close();

                /* create a Signature object and initialize it with the public key */
                Signature sig = Signature.getInstance("DSA");
                sig.initVerify(pubKey);

                /* Update and verify the data */
                FileInputStream datafis = new FileInputStream(caminhoArquivo);
                BufferedInputStream bufin = new BufferedInputStream(datafis);

                byte[] buffer = new byte[1024];
                int len;
                while (bufin.available() != 0) {
                    len = bufin.read(buffer);
                    sig.update(buffer, 0, len);
                };

                bufin.close();

                verifies = sig.verify(sigToVerify);
                return verifies;

            } catch (Exception e) {
                System.err.println("Caught exception " + e.toString());
            } finally {
                return verifies;
            }

    }

}
