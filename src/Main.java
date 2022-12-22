import java.io.FileNotFoundException;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        StatesList listOfStates = new StatesList();
        StatesListDivided dividedClass = new StatesListDivided();

        //1. ÚKOL: Načti ze souboru všechna data do vhodné datové struktury (vytvoř třídu pro uložení dat).
        try {
            listOfStates.readFromFile(Settings.getFile());
        } catch (FileNotFoundException ex) {
            System.err.println("Soubor nemohl být otevřen: "+ex.getLocalizedMessage()+". Program bude ukončen");
            System.exit(0);
        } catch (StatesExceptionReadingFile ex) {
            ex.writeOnScreen();
        }

        //2. ÚKOL: Vypiš seznam všech států a u každého uveď základní sazbu daně z přidané hodnoty ve formátu podle vzorU.
        System.out.println("Výpis všech načtených států:");
        listOfStates.writeOnScreenAll();

        //7a ÚKOL: Doplň možnost, aby uživatel z klávesnice zadal výši sazby DPH/VAT, podle které se má filtrovat.
        UniversalMethods.readFromKeyboard();

        //3. ÚKOL: rozdělení států na dvě skupiny (podle výše základní daně a bez daňové vyjímky)
        dividedClass.divideThem(listOfStates.getStatesList() , Settings.getGtsLimit() , false);

        //3b. ÚKOL: výpis skupiny
        System.out.println("\n"+"Výpis vybraných států:");
        dividedClass.writeOnScreenOverLimit();

        //4. ÚKOL: Výpis z bodu 3. seřaď podle výše základní sazby DPH/VAT sestupně (nejprve státy s nejvyšší sazbou).
        Collections.sort(dividedClass.getOverLimit() , new SortingStatesByGST().reversed());

        //5Úkol: Výpis z bodu 3 seřazený a doplňěný o řádek s rovnítky pro oddělení a poté seznam zkratek států, které ve výpisu nefigurují.
        System.out.println("\n"+"Sežazený seznam podle daně:");
        dividedClass.writeOnScreenOverLimit();
        System.out.println("====================");
        System.out.printf("Sazba VAT "+Settings.getGtsLimit()+"%% nebo nižší nebo používají speciální sazbu: ");
        dividedClass.getUnderLimit().forEach(tmp -> System.out.printf(tmp.getSt()+" "));

        //6 a 7b ÚKOL: výsledný výpis zapiš také do souboru aby reflektoval zadanou sazbu daně.
        //Například pro zadanou sazbu 17 % se vygeneruje soubor vat-over-17.txt a pro sazbu 25 % se vygeneruje soubor vat-over-25.txt.
        UniversalMethods.writeToFile(Settings.getFileOut() , dividedClass.getOverLimit() , dividedClass.getUnderLimit());
    }
}