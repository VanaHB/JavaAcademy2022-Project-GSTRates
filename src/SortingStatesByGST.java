import java.util.Comparator;

public class SortingStatesByGST implements Comparator<State> {
    @Override
    public int compare(State one , State two) {
        if (one.getGst().compareTo(two.getGst()) == 0) {
            return one.getState().compareTo(two.getState());
        }
        return one.getGst().compareTo(two.getGst());
    }
}
