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
class FilaCircular {
    public Noh inicio = null;
    public Noh fim = null;
    public Noh auxiliar = null;

    public void inserir(String dado) {
        auxiliar = new Noh();
        auxiliar.dado = dado;

        if (inicio == null) {
            inicio = auxiliar;
            fim = auxiliar;
        } else {
            fim.prox = auxiliar;
            fim = auxiliar;
            fim.prox = inicio;
        }
    }

    public String remover() {
        String auxiliar_dado;
        auxiliar_dado = inicio.dado;
        inicio = inicio.prox;
        fim.prox = inicio;
        return auxiliar_dado;
    }

    public boolean vazio() {
        return inicio == fim;
    }
    
    public void print(){
        auxiliar = inicio;
        do {
            System.out.println(auxiliar.dado);
            auxiliar = auxiliar.prox;
        } while (auxiliar != inicio);
    }
}
