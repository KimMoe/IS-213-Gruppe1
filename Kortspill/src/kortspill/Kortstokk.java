/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kortspill;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author vegar
 */
public class Kortstokk {
    
    private ArrayList<String> valorer;
    private ArrayList<Kort> kortstokk;

public Kortstokk(){
    valorer = new ArrayList<>();
    kortstokk = new ArrayList<>();
}

//Legger valører inn i ArrayList som skal brukes av lagKort() for å legge kort inn i HashMap
private void putValorer(){
    valorer.add("Hjerter");
    valorer.add("Ruter");
    valorer.add("Kløver");
    valorer.add("Spar");
}

public void lagKortstokk(){
    putValorer();
    int i = 1;
    int v = 0;
    String k;
    while (v<=3){
        k = valorer.get(v);
        kortstokk.add(new Kort(k, i));
        i++;
        if (i==14){
            i=0;
            v++;
        }
        }
    }


//Metde for å skrive ut kortstokken
public void skrivKort(){
    for (Kort k:kortstokk){
        System.out.println(k.getValor() + " " + k.getVerdi());
    }
}

public void stokkKort(){
    Collections.shuffle(kortstokk);
}

}
