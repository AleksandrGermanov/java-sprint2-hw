import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class ECounter {
    String fromFile;
    ArrayList<String> reportData;
    int columnQuantity;
    public static DecimalFormat df = new DecimalFormat("###,###.00"); //немного эстетики
    public static String[] monthNames = {"Январь", "Февраль", "Март", "Апрель",
            "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public ECounter(String fromFile, ArrayList<String> reportData) {
        this.fromFile = fromFile;
        this.reportData = reportData;
    }

    int getYearInt() {
        return Integer.parseInt(this.fromFile.substring(2, 6));
    }

    ArrayList<String[]> dataToColumns() { //разбиваем массив из конструктора по колонкам
        ArrayList<String[]> columns = new ArrayList<>(columnQuantity);
        int columnSize = reportData.size() / columnQuantity;
        String[] column;
        for (int j = 0; j < columnQuantity; j++) {//создаем колонки от 0 до 3
            column = new String[columnSize - 1];
            //дальше для каждой колонки, без значения первой строки
            for (int i = columnQuantity + j; i < reportData.size(); i += columnQuantity) {//считываем значения 4,8,12,...
                // или 3,6,9,...
                column[(i - j) / columnQuantity - 1] = reportData.get(i);//при i=1, зачение [индекса] будет 0
            }
            columns.add(column);
        }
        return columns;
    }

    public abstract void columnsToClassFields();
}


