/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kortspill;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author vegar
 */
public class Kortstokk {
    
    HashMap<String, Integer> stokk;
    ArrayList<String> valorer;

public Kortstokk(){
    stokk = new HashMap<>();
    valorer = new ArrayList<>();
}

//Legger valører inn i ArrayList som skal brukes av lagKort() for å legge kort inn i HashMap
public void putValorer(){
    valorer.add("Hjerter");
    valorer.add("Ruter");
    valorer.add("Kløver");
    valorer.add("Spar");
}

//Legger valør og verdi inn i HashMap
public void lagKort(){
    putValorer();
    int i = 1;
    int t = 1;
    int index = 0;
    String valor = valorer.get(index);
    
    while (i<5){
        while(t<14){
            stokk.put(valor, t);
            t++;
        }
        index++;
        t=1;
        if (index<4){
        valor = valorer.get(index);
        }
        i++;        
    }
}

//Metde kun for testing
public void skrivKort(){
    for (String i:valorer){
        System.out.println(i);
    }
}

}
