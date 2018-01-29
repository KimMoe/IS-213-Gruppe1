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
    private ArrayList<Kort> hand1;
    private ArrayList<Kort> hand2;

public Kortstokk(){
    valorer = new ArrayList<>();
    kortstokk = new ArrayList<>();
    hand1 = new ArrayList<>();
    hand2 = new ArrayList<>();
}

//Legger valører inn i ArrayList som skal brukes av lagKort()
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
            i=1;
            v++;
        }
        }
    }


//Metde for å skrive ut kortstokken
public void skrivKort(){
    int i = 1;
    for (Kort k:kortstokk){
        System.out.println(i + ": " + k.getValor() + " " + k.getVerdi());
        i++;
    }
}

public void stokkKort(){
    Collections.shuffle(kortstokk);
}

public void delKort(){
int i = 0;
while (i<=4){
    hand1.add(kortstokk.get(0));
    kortstokk.remove(0);
    hand2.add(kortstokk.get(0));
    kortstokk.remove(0);
    i++;    
}
}

public void skrivHender(){
    int i = 1;
    System.out.println("Hånd 1:");
    for (Kort k:hand1){
        System.out.println(i + ": " + k.getValor() + " " + k.getVerdi());
        i++;
    }
    i = 1;
    System.out.println("------");
    System.out.println("Hånd 2:");
    for (Kort k:hand2){
        System.out.println(i + ": " + k.getValor() + " " + k.getVerdi());
        i++;
    }
    
}

}
