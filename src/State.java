import java.math.BigDecimal;

public class State {
    private String st;
    private String state;
    private BigDecimal gst;
    private BigDecimal gstReduced;
    private boolean gstException;

    public State(String st , String state , BigDecimal gts , BigDecimal gstReduced , boolean gstException) {
        this.st = st;
        this.state = state;
        this.gst = gts;
        this.gstReduced = gstReduced;
        this.gstException = gstException;
    }

    public String getSt() {
        return st;
    }

    public String getState() {
        return state;
    }

    public BigDecimal getGst() {
        return gst;
    }

    public BigDecimal getGstReduced() {
        return gstReduced;
    }

    public boolean getGstException() {
        return gstException;
    }
}
