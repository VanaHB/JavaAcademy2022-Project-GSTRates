import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StatesList {
    private List<State> statesList = new ArrayList<>();

    public List<State> getStatesList() {
        return new ArrayList<>(statesList);
    }

    public void readFromFile(String file) throws StatesExceptionReadingFile, FileNotFoundException{
        try (Scanner sc = new Scanner(new FileReader(file))) {
            int cisloRadku = 0;
            int errorCount;
            String lineOfFile;
            String[] lineParsed;
            BigDecimal gst = BigDecimal.valueOf(0);
            BigDecimal gstR = BigDecimal.valueOf(0);
            List<String> lineErrors = new ArrayList<>();
            while (sc.hasNextLine()) {
                cisloRadku++;
                errorCount = 0;
                lineOfFile = sc.nextLine();

                //možné problémy co je třeba ošetřit:
                //- celý řádek má špatný zápis - nesedí bloky oddělné tabelátory
                //- daň se nedá převést na číslo (chyba ve znacích)
                //- daň je náporné číslo
                //- daňová vyjímka neodpovídá true nebo false
                if (lineOfFile.matches("^[^\t]+"+Settings.getSeparator()+"{1}[^\t]+"+Settings.getSeparator()+"{1}[^\t]+"+Settings.getSeparator()+"{1}[^\t]+"+Settings.getSeparator()+"{1}[^\t]+$")) {
                        lineParsed = lineOfFile.split(Settings.getSeparator());
                        try {
                            gst = procesBigdecimal(lineParsed[2]);
                        } catch (StatesException ex) {
                            lineErrors.add("Řádek "+cisloRadku+": chyba v gst: "+ex.getLocalizedMessage()+".");
                            errorCount++;
                        }
                        try {
                            gstR = procesBigdecimal(lineParsed[3]);
                        } catch (Exception ex) {
                            lineErrors.add("Řádek "+cisloRadku+": chyba v gstReduced: "+ex.getLocalizedMessage()+".");
                            errorCount++;
                        }
                        if ((!lineParsed[4].equals("true")) && (!lineParsed[4].equals("false"))) {
                            lineErrors.add("Řádek "+cisloRadku+": chyba v parametru gtsException: musí se rovnat true nebo false.");
                            errorCount++;
                        }
                        if (errorCount == 0) statesList.add(new State(lineParsed[0], lineParsed[1], gst, gstR, Boolean.valueOf(lineParsed[4])));
                        //statesList.add(new State(lineParsed[0], lineParsed[1], gst, gstR, Boolean.valueOf(lineParsed[4])));
                    }
                    else {
                        lineErrors.add("Řádek "+cisloRadku+": chyba v zápisu řádku.");
                    }
                }


            if (lineErrors.size() != 0) {
                throw new StatesExceptionReadingFile("Při načítání souboru vznikly chyby:" , lineErrors);
            }
        } catch (FileNotFoundException ex) {     //chyba při otevření souboru Scannerem
            throw ex;
        }
    }

    //metoda převede String na BigDecimal; v případě záporného čísla, nebo chyby v řetězci vrátí vyjímku
    private BigDecimal procesBigdecimal(String bigDecimalString) throws StatesException{
        bigDecimalString = bigDecimalString.replace("," , ".");
        BigDecimal returnValue;
        try {
            returnValue = new BigDecimal(bigDecimalString);
        } catch (NumberFormatException ex) {
            //throw new Exception("špatný tvar zápisu");
            throw new StatesException("vstupní hodnotu nelze převést na číslo");
        }
        if ( returnValue.compareTo(BigDecimal.valueOf(0)) < 0) throw new StatesException("daň nemůže být záporná");
        return returnValue;
        //poznámka: možná by šlo používat i Double.parseDouble(items[2].replace(",", "."));
    }

    //tuto metodu používám je jednou, možná by mohla být v main, nebo jí vůbec nedělat
    public void writeOnScreenAll() {
        statesList.forEach(tmp -> System.out.println(tmp.getState()+" ("+tmp.getSt()+"): "+tmp.getGst()+"%"));
    }
}
