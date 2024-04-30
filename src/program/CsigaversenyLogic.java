/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package program;

import control.EI;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author SzabóRoland(SZOFT_20
 */
public class CsigaversenyLogic {
    // <editor-fold defaultstate="collapsed" desc="Event implmentation">
    private static Set<EI.BBListener> listeners = new HashSet();
    
    public static void addListener(EI.BBListener listener) {
        listeners.add(listener);
    }
    
    public static void removeListener(EI.BBListener listener) {
        listeners.remove(listener);
    }

    private static void broadcast(Broad sw) {
        if(listeners.stream().count() > 0){ 
            switch(sw) {
                case adatvaltozas:
                    listeners.forEach(x -> x.actionValueChanged());
                    break;
                case jatekvege:
                    listeners.forEach(x -> x.actionJatekVege());
                    break;
            }
            
            
        }
    }
    
    private static enum Broad{
        adatvaltozas,
        jatekvege
    }
    // </editor-fold>
    
    private static String[] csigakszinei = {"Piros", "Zöld", "Kék"};
    private static int[] csigahely = {0,0,0};
    private static int[] csigalepes = {0,0,0};
    
    private static int kirefogad = -1;
    private static int jelenlegikor = 1;
    private static int maxkor = 15;
    private static int kieagyorsito = -1;
    private static int nyert = -1; 
    
    // <editor-fold defaultstate="collapsed" desc="control">
    public static boolean Restart(int kirefogadsz){
        boolean both = false;
        if(kirefogadsz >= 0 && kirefogadsz < min(new int[]{csigakszinei.length, csigahely.length, csigalepes.length})){
            jelenlegikor = 0;
            kirefogad = kirefogadsz;
            both = true;
            broadcast(Broad.adatvaltozas);
        }
        return both;
    }
    
    public static boolean leptet(){
        boolean both = false;
        if(kirefogad != -1 && jelenlegikor < maxkor){
            kieagyorsito = (int)(Math.random() * 10) < 2 ? (int)((Math.random() * min(new int[]{csigakszinei.length, csigahely.length, csigalepes.length}))) : -1;
            for(int j = 0; j < min(new int[]{csigakszinei.length, csigahely.length, csigalepes.length}); j++){
                csigalepes[j] = kieagyorsito == j ? (int)(Math.random() * 3) * 2 : (int)((Math.random() * 3));
                csigahely[j] += csigalepes[j];
            }
            jelenlegikor++;
            both = true;
            broadcast(Broad.adatvaltozas);
        }
        if(jelenlegikor >= maxkor) broadcast(Broad.jatekvege);
        return both;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="segédfüggvények">
    
    
    private static int min(int[] szamok){
        int min = Integer.MAX_VALUE;
        for(int szam : szamok){
            min = szam < min ? szam : min;
        }
        return min;
    }

    private static int max(int[] szamok){
        int max = Integer.MIN_VALUE;
        for(int szam : szamok){
            max = szam > max ? szam : max;
        }
        return max;
    }
    
    private static int kivalasztas(int[] szamok) {
        int index = -1;
        boolean both = false;
        int max = max(csigahely);
        for (int i = 0; i < szamok.length && !both; i++) {
            both = szamok[i] == max;
            if(both) index = i;
        }
        return index;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="get függvények">
    public static String[] getNevezettCsigak(){
        return csigakszinei;
    }
    
    public static int[] getCsigaPoziciok(){
        return csigahely;
    }
    
    public static int[] getCsigaLepesek(){
        return csigalepes;
    }
    
    public static int getKieAGyorsito(){
        return kieagyorsito;
    }
    
    public static int getNyert(){
        return jelenlegikor >= maxkor ? kivalasztas(csigahely) : -1;
    }
    
    public static int getFogadott(){
        return kirefogad;
    }
    
    public static int getJelenlegiKor(){
        return jelenlegikor;
    }
    
    public static int getMaxKor(){
        return maxkor;
    }
    // </editor-fold>
}