/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package program;

import control.EI.BBListener;
/**
 *
 * @author SzabóRoland(SZOFT_20
 */
public class CsigaversenyKonzol implements BBListener {
    static CsigaversenyKonzol k = new CsigaversenyKonzol();
    static boolean jatekmegy = true;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AuxiliaryFunctions.bekeres("Üdvözöllek!\n"
                + "Ez itt a csigaverseny.\n"
                + "Az a csiga győz, amelyik a legnagyobb távot tette meg %d kör alatt.\nENTER".formatted(CsigaversenyLogic.getMaxKor()));
        AuxiliaryFunctions.KonzolraKiir("A csigák:");
        String[] csigak = CsigaversenyLogic.getNevezettCsigak();
        for (int i = 0; i < csigak.length; i++) {
            AuxiliaryFunctions.KonzolraKiir("%d. %s".formatted(i+1, csigak[i]));
        }
        String bem = "";
        do {
            bem = AuxiliaryFunctions.bekeres("Hanyas csigára fogadsz? ");
        } while (!CsigaversenyLogic.Restart(AuxiliaryFunctions.tryParse(bem) -1));
        CsigaversenyLogic.addListener(k);
        while(jatekmegy){
            CsigaversenyLogic.leptet();
            AuxiliaryFunctions.bekeres("Nyomj ENTER-t...");
        }
    }
    
    
    
    @Override
    public void actionValueChanged() {
        String[] csigakszinei = CsigaversenyLogic.getNevezettCsigak();
        int[] csigakpozicioi = CsigaversenyLogic.getCsigaPoziciok();
        int kieagyorsito = CsigaversenyLogic.getKieAGyorsito();
        AuxiliaryFunctions.KonzolraKiir("%d. kör: ".formatted(CsigaversenyLogic.getJelenlegiKor()));
        for (int i = 0; i < AuxiliaryFunctions.min(new int[]{csigakszinei.length, csigakpozicioi.length}); i++) {
            AuxiliaryFunctions.KonzolraKiir("%s: \t|%s %s".formatted(csigakszinei[i], "*".repeat(csigakpozicioi[i]), kieagyorsito == i ? "<<<" : ""));
        }
    }

    @Override
    public void actionJatekVege() {
        jatekmegy = false;
        String[] csigakszinei = CsigaversenyLogic.getNevezettCsigak();
        int nyert = CsigaversenyLogic.getNyert();
        AuxiliaryFunctions.KonzolraKiir("\nA %s csiga nyert. A fogadást %s.".formatted(csigakszinei[nyert].toLowerCase(), nyert == CsigaversenyLogic.getFogadott() ? "megnyerted" : "elvesztetted"));
    }
}
