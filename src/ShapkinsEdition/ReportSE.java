package ShapkinsEdition;

import java.text.DecimalFormat;
import java.util.List;
import java.util.HashMap;

public abstract class ReportSE {

    static final String IE = "is_expense";
    static final String SOO = "sum_of_one";
    static final String Q = "quantity";
    static final String A = "amount";
    static final String M = "month";
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

    public Object parse(String column, int line) {
        if (column.equals(Q)||column.equals(M)) {
            return iParse(line, column);
        } else if (column.equals(A) || column.equals(SOO)) {
            return dParse(line,column);
        } else if (column.equals(IE)) {
            return bParse(line, column);
        } else {
            System.out.println("Не удалось привести строку к необходимому значению!");
            return null;
        }
    }

     Integer iParse(int line, String column) {
        int colNo = columnSearcher(column);
        return Integer.parseInt(reportData.get(line).get(colNo));
    }

   Double dParse( int line, String column) {
        int colNo = columnSearcher(column);
        return Double.parseDouble(reportData.get(line).get(colNo));
    }

     Boolean bParse(int line, String column) {
        int colNo = columnSearcher(column);
        return Boolean.parseBoolean(reportData.get(line).get(colNo));
    }

     int columnSearcher (String column){
        int colNo = -1;
        for(int i  = 0; i<reportData.get(0).size(); i++){
            if (reportData.get(0).get(i).equals(column)){
                colNo = i;
                break;
            }
        }
        return colNo;
    }
}
