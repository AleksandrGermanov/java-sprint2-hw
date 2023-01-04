package ShapkinsEdition;

import java.text.DecimalFormat;
import java.util.List;
import java.util.HashMap;

public abstract class ReportSE {

    String fromFile;
    HashMap<Integer, List<String>> reportData;
    public static DecimalFormat df = new DecimalFormat("###,###.00"); //немного эстетики
    public static final String[] MONTH_NAMES = {"Январь", "Февраль", "Март", "Апрель",
            "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public ReportSE(String fromFile, HashMap<Integer, List<String>> reportData) {
        this.fromFile = fromFile;
        this.reportData = reportData;
    }

    int getYearInt() {
        return Integer.parseInt(this.fromFile.substring(2, 6));
    }
}
