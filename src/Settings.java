import java.math.BigDecimal;

public class Settings {
    public static String getFile() {
        return "src\\vat-eu.csv";
    }

    public static String getFile2() {
        return "src\\vat-eu-damaged-lines.csv";
    }

    public static String getSeparator() {
        return "\t";
    }

    public static BigDecimal getGtsLimitDefault() {
        return BigDecimal.valueOf(20);
    }

    public static String getFileOut(BigDecimal limit) {
        return "src\\vat-over-"+limit+".txt";
    }
}
