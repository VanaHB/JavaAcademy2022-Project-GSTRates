import java.util.List;

public class StatesExceptionReadingFile extends Exception {
    private String description;
    private List<String> listOfErrors;

    public StatesExceptionReadingFile(String description, List<String> listOfErrors) {
        super(description);
        this.description = description;
        this.listOfErrors = listOfErrors;
    }

    public List<String> getListOfErrors() {
        return listOfErrors;
    }

    public void writeOnScreen() {
        System.err.println(description);
        listOfErrors.forEach(tmp -> System.err.println(tmp));
    }
}
