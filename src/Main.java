import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StatesList listOfStates = new StatesList();
        //BigDecimal gstLimit = BigDecimal.valueOf(20);     //PROČ TO TADY NEMŮŽE BÝT?45
        BigDecimal gstLimit;
        List<State> underLimit = new ArrayList<>();
        List<State> overLimit = new ArrayList<>();

        //1 ÚKOL: Načti ze souboru všechna data do vhodné datové struktury (vytvoř třídu pro uložení dat).
        try {
            listOfStates.readFromFile(Settings.FILE);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);  //pokud nelze soubor otevřít tak chci ukončit program
        } catch (ExceptionStates ex) {
            ex.writeOnScreen();
        }

        //2 ÚKOL: Vypiš seznam všech států a u každého uveď základní sazbu daně z přidané hodnoty ve formátu podle vzorU.
        listOfStates.writeOnScreenAll();

        //7a ÚKOL: Doplň možnost, aby uživatel z klávesnice zadal výši sazby DPH/VAT, podle které se má filtrovat.
        gstLimit = readFromKeyboard();

        //3 ÚKOL: Vypiš ve stejném formátu pouze státy, které mají základní sazbu daně z přidané hodnoty vyšší než 20 % a přitom nepoužívají speciální sazbu daně.
        listOfStates.getStatesArray().forEach(tmp -> {
            if ( (tmp.getGst().compareTo(gstLimit) > 0) && (!tmp.getGstException()) ) overLimit.add(tmp);
            else underLimit.add(tmp);
        });
        overLimit.forEach(tmp -> System.out.println(tmp.getState()+" ("+tmp.getSt()+"): "+tmp.getGst()+"%"));
        System.out.println("");

        //4ÚKOL: Výpis z bodu 3. seřaď podle výše základní sazby DPH/VAT sestupně (nejprve státy s nejvyšší sazbou).
        Collections.sort(overLimit , new SortingStatesByGST());

        //5Úkol: Pod výpis z bodu 3. doplň řádek s rovnítky pro oddělení a poté seznam zkratek států, které ve výpisu nefigurují.
        overLimit.forEach(tmp -> System.out.println(tmp.getState()+" ("+tmp.getSt()+"): "+tmp.getGst()+"%"));
        System.out.println("====================");
        System.out.printf("Sazba VAT "+gstLimit+"%% nebo nižší nebo používají speciální sazbu: ");
        underLimit.forEach(tmp -> System.out.printf(tmp.getSt()+" "));


    }

    public static BigDecimal readFromKeyboard() {
        Scanner sc = new Scanner(System.in);
        BigDecimal scInputInt;
        System.out.println("Zadej základní sazbu DPH podle které se bude filtrovat, nebo Enter pro výchozích 20%");
        try {
            scInputInt = new BigDecimal(sc.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Zadaná hodnota není číslo - použije se výchozích 20%");
            return BigDecimal.valueOf(20);
        }
        System.out.println("Bude se filtrovat podle základní daně "+scInputInt+"%");
        return scInputInt;
    }
}