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
public class LinkedList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FilaCircular fila = new FilaCircular();
        fila.inserir("PRIMEIRO NUMERO");
        fila.inserir("SEGUNDO NUMERO");
        fila.inserir("TERCEIRO NUMERO");
        fila.inserir("QUARTO NUMERO");
        fila.inserir("QUINTO NUMERO");

        fila.print();
        
        while (!fila.vazio()) {
            System.out.println(fila.remover());
        }
        System.out.println(fila.remover());
    }
   
}
