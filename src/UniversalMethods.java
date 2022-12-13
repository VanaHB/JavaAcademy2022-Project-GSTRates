import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class UniversalMethods {

    public static void writeToFile(String file , List<State> listOfStates) {
        try(PrintWriter printWritertoFile = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            String separator = "\t";
            listOfStates.forEach(tmpState-> {
                printWritertoFile.write(tmpState.getSt()+separator+tmpState.getState()+separator+tmpState.getGst()+separator+tmpState.getGstReduced()+separator+tmpState.getGstException()+"\n");
            });
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
            System.out.println("Zadaná hodnota není číslo - pro filtrování se použije výchozích "+Settings.GTSLIMITDEFAULT+"%");
            scInputBD = Settings.GTSLIMITDEFAULT;
        }
        System.out.println("Bude se filtrovat podle základní daně "+scInputBD+"%");
        Settings.GSTLIMIT = scInputBD;
        Settings.FILEOUT = "src\\vat-over-"+scInputBD+".txt";
    }
}
