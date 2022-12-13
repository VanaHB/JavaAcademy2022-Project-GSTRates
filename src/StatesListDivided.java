import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatesListDivided {
    private List<State> underLimit = new ArrayList<>();
    private List<State> overLimit = new ArrayList<>();

    public void divideThem(List<State> wholeList, BigDecimal gstLimit , boolean isGstException) {
        wholeList.forEach(tmp -> {
            if ( (tmp.getGst().compareTo(gstLimit) > 0) && ((tmp.getGstException() == isGstException) ) ) overLimit.add(tmp);
            else underLimit.add(tmp);
        });
    }

    public List<State> getUnderLimit() {
        return underLimit;
    }

    public List<State> getOverLimit() {
        return overLimit;
    }

    public void sortUnderLimit() {
        sortList(underLimit);
    }

    public void sortOverLimit() {
        sortList(overLimit);
    }

    private void sortList(List<State> listtoSort) {
        Collections.sort(listtoSort, new SortingStatesByGST());
    }

    public void writeOnScreenOverLimit() {
        overLimit.forEach(tmp -> System.out.println(tmp.getState()+" ("+tmp.getSt()+"): "+tmp.getGst()+"%"));
    }

    public void writeOnScreenUnderLimit() {
        underLimit.forEach(tmp -> System.out.println(tmp.getState()+" ("+tmp.getSt()+"): "+tmp.getGst()+"%"));
    }
}
