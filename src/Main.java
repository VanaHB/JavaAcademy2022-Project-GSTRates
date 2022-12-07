import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StatesList listOfStates = new StatesList();
        BigDecimal gstLimit = BigDecimal.valueOf(20);
        List<State> underLimit = new ArrayList<>();
        List<State> overLimit = new ArrayList<>();

        listOfStates.readFromFile(Settings.FILE);
        listOfStates.writeOnScreenAll();

        listOfStates.getStatesArray().forEach(tmp -> {
            if ( (tmp.getGst().compareTo(gstLimit) > 0) && (!tmp.getGstException()) ) overLimit.add(tmp);
            else underLimit.add(tmp);
        });

        System.out.println("-----------------------");
        System.out.println("Goods Services Tax over "+gstLimit+"%");
        overLimit.forEach(tmp -> System.out.println(tmp.getState()+" ("+tmp.getSt()+"): "+tmp.getGst()+"%"));
        System.out.println("=======================");
        System.out.println("Sazba VAT 20% nebo nižší nebo používají speciální sazbu: ");
        underLimit.forEach(tmp -> System.out.printf(tmp.getSt()+", "));



        //System.out.println("Program ended.");
    }
}