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
public class Bitwise {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(int i = 0; i < 20; i++){
            System.out.println("número "+i+" -> "+expo(i));
        }
        
    }
    
    public static boolean expo(int n){
        return ((n & n-1) == 0) && (n!=0);
    }
    
}
