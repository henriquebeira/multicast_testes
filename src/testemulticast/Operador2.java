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

public class Operador2 {

    public static void main(String args[]) throws InterruptedException {
        Thread thread1 = new Thread(new Envio(2, 10000));
        Thread thread2 = new Thread(new Recebe(2));
        thread1.start();
        thread2.start();
    }
    
}
