/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package program;

import control.EI.BBListener;
import control.EI;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
/**
 *
 * @author SzabóRoland(SZOFT_20
 */
public class CsigaversenyKonzol implements EI.BBListener {
    static CsigaversenyKonzol k = new CsigaversenyKonzol();
    static boolean jatekmegy = true;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        bekeres("Üdvözöllek!\n"
                + "Ez itt a csigaverseny.\n"
                + "Az a csiga győz, amelyik a legnagyobb távot tette meg %d kör alatt.\nENTER".formatted(CsigaversenyLogic.getMaxKor()));
        KonzolraKiir("A csigák:");
        String[] csigak = CsigaversenyLogic.getNevezettCsigak();
        for (int i = 0; i < csigak.length; i++) {
            KonzolraKiir("%d. %s".formatted(i+1, csigak[i]));
        }
        String bem = "";
        do {
            bem = bekeres("Hanyas csigára fogadsz? ");
        } while (!CsigaversenyLogic.Restart(tryParse(bem) -1));
        CsigaversenyLogic.addListener(k);
        while(jatekmegy){
            CsigaversenyLogic.leptet();
            bekeres("Nyomj ENTER-t...");
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Közös">
    public static int intBekeres(String inputText){
        String bem;
        int a;
        do {
            bem = bekeres(inputText);
            a = tryParse(bem);
        } while (!CanITryParse(bem));
        return a;
    }
    
    public static String bekeres(String inputText){
        KonzolraKiir(inputText, "");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    
    public static void KonzolraKiir(String bem){
        System.out.println(bem);
    }
    
    public static void KonzolraKiir(String bem, String end){
        System.out.print(bem+end);
    }
    
    public static <T> String tombKiir(T[] tomb){
        return tombKiir(tomb,  "\n");
    }
     
    public static <T> String tombKiir(T[] tomb, String elvalaszt){
        String s = "";
        for(Object item : tomb){
            s += String.valueOf(item) + elvalaszt;
        }
        return s;
    }
     
    public static <T> String tombKiir(T[] tomb, String elvalaszt, int egysorbanmennyi){
        String s = "";
        int valaszt = 1;
        for(T item : tomb){
            s += String.valueOf(item) + elvalaszt;
            if(valaszt == egysorbanmennyi){
                s += "\n";
                valaszt =1;
            }
        }
        return s;
    }
    
    public static void fajlbair(String bem, String filename) throws FileNotFoundException{
        //File f = new File(filename);
        PrintWriter pn = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8));
        pn.print(bem);
        pn.flush();
    }
    
    public static int tryParse(String bem){
        try {
            return Integer.parseInt(bem);
        } catch (Exception e) {
            return -1;
        }
    }
    public static boolean CanITryParse(String bem){
        try {
            int a = Integer.parseInt(bem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static int sum(int[] szamok){
        int sum = 0;
        for(int szam : szamok){
            sum += szam;
        }
        return sum;
    }
    
    public static int min(int[] szamok){
        int min = Integer.MAX_VALUE;
        for(int szam : szamok){
            min = szam < min ? szam : min;
        }
        return min;
    }

    public static int max(int[] szamok){
        int max = Integer.MIN_VALUE;
        for(int szam : szamok){
            max = szam > max ? szam : max;
        }
        return max;
    }
    
    public void add(){
    
    }
    // </editor-fold>
    
    @Override
    public void actionValueChanged() {
        String[] csigakszinei = CsigaversenyLogic.getNevezettCsigak();
        int[] csigakpozicioi = CsigaversenyLogic.getCsigaPoziciok();
        int kieagyorsito = CsigaversenyLogic.getKieAGyorsito();
        KonzolraKiir("%d. kör: ".formatted(CsigaversenyLogic.getJelenlegiKor()));
        for (int i = 0; i < min(new int[]{csigakszinei.length, csigakpozicioi.length}); i++) {
            KonzolraKiir("%s: \t|%s %s".formatted(csigakszinei[i], "*".repeat(csigakpozicioi[i]), kieagyorsito == i ? "<<<" : ""));
        }
    }

    @Override
    public void actionJatekVege() {
        jatekmegy = false;
        String[] csigakszinei = CsigaversenyLogic.getNevezettCsigak();
        int nyert = CsigaversenyLogic.getNyert();
        KonzolraKiir("\nA %s csiga nyert. A fogadást %s.".formatted(csigakszinei[nyert].toLowerCase(), nyert == CsigaversenyLogic.getFogadott() ? "megnyerted" : "elvesztetted"));
    }
}
