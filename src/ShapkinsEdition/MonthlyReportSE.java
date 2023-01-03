package ShapkinsEdition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonthlyReportSE extends ReportSE {
    private static boolean wasCreated;
    final int monthID;

    public MonthlyReportSE(String fromFile, HashMap<Integer, List<String>> reportData) {
        super(fromFile, reportData);
        wasCreated = true;
        monthID = Integer.parseInt(fromFile.substring(6, 8));
    }

    public static boolean getWasCreated() {
        return wasCreated;
    }

    public static void mrsShuttleSorter(ArrayList<MonthlyReportSE> mrs) { //челночная сортировка
        MonthlyReportSE buffer;
        for (int i = 1; i < mrs.size(); i++) {
            if (mrs.get(i - 1).monthID > mrs.get(i).monthID) {
                buffer = mrs.get(i);
                mrs.remove(i);
                mrs.add(i - 1, buffer);
                for (int j = i - 1; (j - 1) > -1; j--) {
                    if (mrs.get(j - 1).monthID > mrs.get(j).monthID) {
                        buffer = mrs.get(j);
                        mrs.remove(j);
                        mrs.add(j - 1, buffer);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    static public YearlyReportSE autoYrCreator(ArrayList<MonthlyReportSE> mrs) {
        HashMap<Integer,List<String>> reportData = new HashMap<>();
        mrsShuttleSorter(mrs);
        List<String> titles = new ArrayList<>(3);
        List<String> lineOne = new ArrayList<>(3);
        List<String> lineTwo = new ArrayList<>(3);
        int counter = 0;
        titles.add(M);
        titles.add(A);
        titles.add(IE);
        reportData.put(counter++, titles);
        for (int i = 0; i < mrs.size(); i++) {
            MonthlyReportSE monthly = mrs.get(i);
            Double income = 0.0;
            Double outcome = 0.0;
            String month = monthly.fromFile.substring(6, 8);
            for (int j = 1; j < monthly.reportData.size(); j++) {
                if ((boolean)monthly.parse(IE, j)) {
                    outcome += (int)monthly.parse(Q, j) * (double)monthly.parse(SOO, j);
                } else {
                    income += (int)monthly.parse(Q, j) * (double)monthly.parse(SOO, j);
                }
        }
            lineOne=new ArrayList<>();
            lineOne.add(month);
            lineOne.add(outcome.toString());
            lineOne.add("true");
            reportData.put(counter++, lineOne);
            lineTwo = new ArrayList<>();
            lineTwo.add(month);
            lineTwo.add(income.toString());
            lineTwo.add("false");
            reportData.put(counter++, lineTwo);
                    }
        return new YearlyReportSE(("au" + mrs.get(0).getYearInt()), reportData);
    }

    public static void printMonthlyStats(ArrayList<MonthlyReportSE> mrs) {
        for (MonthlyReportSE mr : mrs) {
            System.out.println("Отчет за " + MONTH_NAMES[mr.monthID - 1] + ".");
            double maxExpense = 0.0;
            int maxExpenseIndex = 0;
            double maxProfit = 0.0;
            int maxProfitIndex = 0;
            for (int i = 1; i < mr.reportData.size(); i++) {//0 строка - с заголовками
                if ((boolean) mr.parse("is_expense", i)) {
                    double expense = (double) mr.parse("sum_of_one", i) *
                            (int) mr.parse("quantity", i);
                    if (expense > maxExpense) {
                        maxExpense = expense;
                        maxExpenseIndex = i;
                    }
                } else {
                    double profit = (double) mr.parse("sum_of_one", i) *
                            (int) mr.parse("quantity", i);
                    if (profit > maxProfit) {
                        maxProfit = profit;
                        maxProfitIndex = i;
                    }
                }
            }
            System.out.println("Позиция \"" + mr.reportData.get(maxProfitIndex).get(0) +
                    "\" принесла вам больше всего средств с доходом " + df.format(maxProfit) + " рублей!");
            System.out.println("Больше всего вы потратили на позицию \"" +
                    mr.reportData.get(maxExpenseIndex).get(0) +
                    "\". Пришлось отдать " + df.format(maxExpense) + " рублей!");
        }
    }
}

