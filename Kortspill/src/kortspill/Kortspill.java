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
public class Kortspill {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Kortstokk kortstokk;
        kortstokk = new Kortstokk();
        kortstokk.lagKortstokk();
        kortstokk.skrivKort(); 
        kortstokk.stokkKort();
        kortstokk.skrivKort();
        
    }
    
}
