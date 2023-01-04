package ShapkinsEdition;

import java.util.HashMap;
import java.util.List;

public class YearlyReportSE extends ReportSE {

    private static boolean wasCreated;
    HashMap<Integer, YearLineData> yearData;

    public YearlyReportSE(String fromFile, HashMap<Integer, List<String>> reportData) {
        super(fromFile, reportData);
        wasCreated = true;
        yearData = ydCreator();
    }

    public static boolean getWasCreated() {
        return wasCreated;
    }

    HashMap<Integer, YearLineData> ydCreator(){
        HashMap<Integer, YearLineData> yd = new HashMap<>();
        for (int i = 1; i<reportData.size(); i++){
            yd.put(i-1, new YearLineData(reportData.get(i).get(0), reportData.get(i).get(1),
                    reportData.get(i).get(2)));
        }
        return yd;
    }

    public void printProfit() {
        double buffer;
        for (int i = 1; i < yearData.size(); i = i + 2) {
            if (yearData.get(i).month == yearData.get(i - 1).month) {
                if (yearData.get(i-1).isExpense) {
                    buffer = -yearData.get(i-1).amount;
                } else {
                    buffer = yearData.get(i-1).amount;
                }
                if (yearData.get(i).isExpense) {
                    buffer -= yearData.get(i).amount;
                } else {
                    buffer += yearData.get(i).amount;
                }
                System.out.println("Месяц " + MONTH_NAMES[yearData.get(i).month-1]
                        + ". Ваша прибыль составила "
                        + df.format(buffer) + " рублей.");

            } else {
                System.out.println("Несоответствие строк по месяцам. Проверьте предоставленные данные.");
            }
        }
    }

    public static void compareYrs(YearlyReportSE yr, YearlyReportSE autoYr) {
        boolean isOK = true;
        for (int i = 1; i < yr.yearData.size(); i += 2) {
            if (yr.yearData.get(i-1).month == yr.yearData.get(i).month &&
                    yr.yearData.get(i-1).month == autoYr.yearData.get(i).month &&
                    autoYr.yearData.get(i-1).month == autoYr.yearData.get(i).month) {
                double income = yrLinesExpenseProfitDefiner(false, yr, i);
                double outcome = yrLinesExpenseProfitDefiner(true, yr, i);
                double in = yrLinesExpenseProfitDefiner(false, autoYr, i);
                double out = yrLinesExpenseProfitDefiner(true, autoYr, i);
                if (!(outcome == out && income == in)) {
                    System.out.println("Несоответствие данных за " +
                            MONTH_NAMES[yr.yearData.get(i-1).month - 1] + " " + yr.getYearInt() +
                            " года!");
                    isOK = false;
                }
            } else {
                System.out.println("Не удалось сравнить месячные и годовой отчет. " +
                        "Несоответствие строк по месяцам. Проверьте предоставленные данные.");
                return;
            }
        }
        if (isOK) {
            System.out.println("Сверка успешно завершена. Данные месячных отчетов\n" +
                    "полностью соответствуют данным годового отчета.");
        }
    }

    public void printAvgProfitAndExpense() {
        double expense = 0.0;
        double profit = 0.0;
        for (int i = 0 ; i < yearData.size(); i++) {
            if (yearData.get(i).isExpense) {
                expense += yearData.get(i).amount;
            } else {
                profit += yearData.get(i).amount;
            }
        }
        System.out.println("Средний расход за все месяцы составил " +
                df.format(expense / (yearData.size() / 2.0)) + " рублей.");
        System.out.println("Средний доход за все месяцы составил " +
                df.format(profit / (yearData.size() / 2.0)) + " рублей.");
    }

    static double yrLinesExpenseProfitDefiner(boolean isExpense,
                                              YearlyReportSE yr, int i) {
        double income;
        double outcome;
        if (yr.yearData.get(i-1).isExpense) {
            outcome = yr.yearData.get(i-1).amount;
            income = yr.yearData.get(i).amount;
        } else {
            outcome = yr.yearData.get(i).amount;
            income = yr.yearData.get(i-1).amount;
        }
        if (isExpense) {
            return outcome;
        } else {
            return income;
        }
    }

    static class YearLineData {
        int month;
        boolean isExpense;
        double amount;

        YearLineData(String month, String amount, String isExpense) {
            this.month = Integer.parseInt(month);
            this.amount = Double.parseDouble(amount);
            this.isExpense = Boolean.parseBoolean(isExpense);
        }
    }
}
