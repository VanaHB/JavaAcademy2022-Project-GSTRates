import java.math.BigDecimal;

public class Settings {
    public static String file = "src\\vat-eu.csv";
    public static String file2 = "src\\vat-eu-damaged-lines.csv";
    public static String separator = "\t";
    public static String fileOut;
    public static BigDecimal gtsLimitDefault = BigDecimal.valueOf(20);
    public static BigDecimal gtsLimit;

    public static String getFile() {
        return file;
    }

    public static void setFile(String file) {
        Settings.file = file;
    }

    public static String getFile2() {
        return file2;
    }

    public static void setFile2(String file2) {
        Settings.file2 = file2;
    }

    public static String getSeparator() {
        return separator;
    }

    public static void setSeparator(String separator) {
        Settings.separator = separator;
    }

    public static String getFileOut() {
        return fileOut;
    }

    public static void setFileOut(String fileOut) {
        Settings.fileOut = fileOut;
    }

    public static BigDecimal getGtsLimitDefault() {
        return gtsLimitDefault;
    }

    public static void setGtsLimitDefault(BigDecimal gtsLimitDefault) {
        Settings.gtsLimitDefault = gtsLimitDefault;
    }

    public static BigDecimal getGtsLimit() {
        return gtsLimit;
    }

    public static void setGtsLimit(BigDecimal gtsLimit) {
        Settings.gtsLimit = gtsLimit;
    }
}
