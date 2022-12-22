import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class UniversalMethods {

    public static void writeToFile(String file , List<State> listOfStatesOver , List<State> listOfStatesUnder) {
        try(PrintWriter printWritertoFile = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            listOfStatesOver.forEach(tmpState-> {
                printWritertoFile.write(tmpState.getState()+Settings.getSeparator()+tmpState.getSt()+Settings.getSeparator()+tmpState.getGst()+"%\n");
            });
            printWritertoFile.write("====================\n");
            printWritertoFile.write("Sazba VAT "+Settings.gtsLimit +"% nebo nižší nebo používají speciální sazbu: ");
            listOfStatesUnder.forEach(tmp -> printWritertoFile.write(tmp.getSt()+" "));
            printWritertoFile.write("\n");
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public static void readFromKeyboard() {
        Scanner sc = new Scanner(System.in);
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //šlo by použít v kombinaci s String scInput = br.readLine();
        BigDecimal scInputBD;
        System.out.println("Zadej základní sazbu DPH podle které se bude filtrovat, nebo Enter pro výchozích 20%");
        try {
            scInputBD = new BigDecimal(sc.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Zadaná hodnota není číslo (nebo nemá správný formát) - pro filtrování se použije výchozích "+Settings.gtsLimitDefault +"%");
            scInputBD = Settings.getGtsLimitDefault();
        }
        System.out.println("Bude se filtrovat podle základní daně "+scInputBD+"%");
        Settings.setGtsLimit(scInputBD);
        Settings.setFileOut("src\\vat-over-"+scInputBD+".txt");
    }
}
