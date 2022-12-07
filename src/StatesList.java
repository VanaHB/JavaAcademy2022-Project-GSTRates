import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StatesList {
    private List<State> statesArray = new ArrayList<>();

    public List<State> getStatesArray() {
        return new ArrayList<>(statesArray);
    }

    public void readFromFile(String file) {
        try (Scanner sc = new Scanner(new FileReader(file))) {
            int cisloRadku = 0;
            String separator = "\t";
            String lineOfFile;
            String[] lineParsed;
            BigDecimal gst = BigDecimal.valueOf(0);
            BigDecimal gstR = BigDecimal.valueOf(0);
            List<String> lineErrors = new ArrayList<>();
            while (sc.hasNextLine()) {
                cisloRadku++;
                lineOfFile = sc.nextLine();
                lineParsed = lineOfFile.split(separator);

                //možné problémy co je třeba očetřit:
                //- daň se nedá převést na číslo (chyba ve znacích)
                //- daň je náporné číslo
                //- daňová vyjímka neodpovídá true nebo false
                try {
                    gst = procesBigdecimal(lineParsed[2]);
                } catch (Exception ex) {
                    lineErrors.add("Řádek " + cisloRadku + ": cbyba v gst: "+ex.getLocalizedMessage()+".");
                }
                try {
                    gstR = procesBigdecimal(lineParsed[3]);
                } catch (Exception ex) {
                    lineErrors.add("Řádek " + cisloRadku + ": chyba v gstReduced: "+ex.getLocalizedMessage()+".");
                }
                if ((!lineParsed[4].equals("true")) && (!lineParsed[4].equals("false")))
                    lineErrors.add("Řádek " + cisloRadku + ": chyba v parametru gtsException: musí se rovnat true nebo false.");

                statesArray.add(new State(lineParsed[0], lineParsed[1], gst, gstR, Boolean.valueOf(lineParsed[4])));
            }

            lineErrors.forEach(tmp -> System.err.println(tmp));

        } catch (FileNotFoundException e) {     //chyba při otevření souboru Scannerem
            System.err.println(e.getLocalizedMessage());
        }
    }

    //metoda převede String na BigDecimal; v případě záporného čísla, nebo chyby v řetězci vrátí vyjímku
    private BigDecimal procesBigdecimal(String bigDecimalString) throws Exception{
        bigDecimalString = bigDecimalString.replace("," , ".");
        BigDecimal returnValue;
        try {
            returnValue = new BigDecimal(bigDecimalString);
        } catch (NumberFormatException ex) {
            throw new Exception("špatný tvar zápisu");
        }
        if ( returnValue.compareTo(BigDecimal.valueOf(0)) < 0) throw new Exception("daň nemůže být záporná");
        return returnValue;
    }

    public void writeToFile(String file) {
        //will be coded later
    }

    public void writeOnScreenAll() {
        statesArray.forEach(tmp -> System.out.println(tmp.getState()+" ("+tmp.getSt()+"): "+tmp.getGst()+"%"));
    }
}
