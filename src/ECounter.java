import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class ECounter {
    String fromFile;
    ArrayList<String> reportData;
    int columnQuantity;
    public static DecimalFormat df = new DecimalFormat("###,###.00");
    public static String[] monthNames = {"Январь", "Февраль", "Март", "Апрель",
            "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public ECounter(String fromFile, ArrayList<String> reportData) {
        this.fromFile = fromFile;
        this.reportData = reportData;
    }

    int getYearInt() {
        return Integer.parseInt(this.fromFile.substring(2, 6));
    }

    ArrayList<String[]> dataToColumns() {
        ArrayList<String[]> columns = new ArrayList<>(columnQuantity);
        int columnSize = reportData.size() / columnQuantity;
        String[] column;
        for (int j = 0; j < columnQuantity; j++) {
            column = new String[columnSize - 1];//без первой строки
            for (int i = columnQuantity + j; i < reportData.size(); i = i + columnQuantity) {
                column[(i - j) / columnQuantity - 1] = reportData.get(i);
            }
            columns.add(column);
        }
        return columns;
    }
    public abstract void columnsToClassFields();
}


