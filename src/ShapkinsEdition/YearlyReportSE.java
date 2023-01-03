package ShapkinsEdition;

import java.util.HashMap;
import java.util.List;

public class YearlyReportSE extends ReportSE {

    private static boolean wasCreated;

    public YearlyReportSE(String fromFile, HashMap<Integer, List<String>> reportData) {
        super(fromFile, reportData);
        wasCreated = true;
    }

    public static boolean getWasCreated() {
        return wasCreated;
    }

    public void printProfit() {
        double buffer;
        for (int i = 2; i < reportData.size(); i = i + 2) {
            if (reportData.get(i).get(0).equals(reportData.get(i - 1).get(0))) {
                if ((boolean) parse(IE, i - 1)) {
                    buffer = -(double) parse(A, i - 1);
                } else {
                    buffer = (double) parse(A, i - 1);
                }
                if ((boolean) parse(IE, i)) {
                    buffer -= (double) parse(A, i);
                } else {
                    buffer += (double) parse(A, i);
                }
                System.out.println("Месяц " + MONTH_NAMES[(int) parse(M, i) - 1]
                        + ". Ваша прибыль составила "
                        + df.format(buffer) + " рублей.");

            } else {
                System.out.println("Несоответствие строк по месяцам. Проверьте предоставленные данные.");
            }
        }
    }

    public static void compareYrs(YearlyReportSE yr, YearlyReportSE autoYr) {
        boolean isOK = true;
        for (int i = 2; i < yr.reportData.size(); i += 2) {
            if (((int)yr.parse(M, i)==(int)yr.parse(M, i - 1)) &&
                    ((int)yr.parse(M, i)==(int)autoYr.parse(M, i)) &&
                    ((int)autoYr.parse(M, i - 1)==(int)autoYr.parse(M, i))) {
                double income = yrLinesExpenseProfitDefiner(false, yr, i);
                double outcome = yrLinesExpenseProfitDefiner(true, yr, i);
                double in = yrLinesExpenseProfitDefiner(false, autoYr, i);
                double out = yrLinesExpenseProfitDefiner(true, autoYr, i);
                if (!(outcome == out && income == in)) {
                    System.out.println("Несоответствие данных за " +
                            MONTH_NAMES[(int) yr.parse(M, i) - 1] + " " + yr.getYearInt() +
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
        for (int i = 1; i < reportData.size(); i++) {
            if ((boolean) parse(IE, i)) {
                expense += (double) parse(A, i);
            } else {
                profit += (double) parse(A, i);
            }
        }
        System.out.println("Средний расход за все месяцы составил " +
                df.format(expense / ((reportData.size() - 1) / 2.0)) + " рублей.");
        System.out.println("Средний доход за все месяцы составил " +
                df.format(profit / ((reportData.size() - 1) / 2.0)) + " рублей.");
    }

    static double yrLinesExpenseProfitDefiner(boolean isExpense,
                                              YearlyReportSE yr, int i) {
        double income;
        double outcome;
        if ((boolean) yr.parse(IE, i - 1)) {
            outcome = (double) yr.parse(A, i - 1);
            income = (double) yr.parse(A, i);
        } else {
            outcome = (double) yr.parse(A, i);
            income = (double) yr.parse(A, i - 1);
        }
        if (isExpense) {
            return outcome;
        } else {
            return income;
        }
    }
}
