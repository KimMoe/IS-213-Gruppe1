/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kortspill;

/**
 *
 * @author vegar
 */
public class Kort {
    String valor;
    int verdi;
    
    public Kort(String valor, int verdi){
        this.valor = valor;
        this.verdi = verdi;
    }

    public String getValor() {
        return valor;
    }

    public int getVerdi() {
        return verdi;
    }
    
}
