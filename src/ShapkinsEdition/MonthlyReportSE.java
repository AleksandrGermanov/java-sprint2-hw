package ShapkinsEdition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonthlyReportSE extends ReportSE {
    private static boolean wasCreated;
    final int monthID;
    HashMap<Integer, MonthLineData> monthData;

    public MonthlyReportSE(String fromFile, HashMap<Integer, List<String>> reportData) {
        super(fromFile, reportData);
        wasCreated = true;
        monthID = Integer.parseInt(fromFile.substring(6, 8));
        monthData = mdCreator();
    }

    public static boolean getWasCreated() {
        return wasCreated;
    }

    HashMap<Integer, MonthLineData> mdCreator(){
        HashMap<Integer, MonthLineData> md = new HashMap<>();
        for (int i = 1; i<reportData.size(); i++){
        md.put(i-1, new MonthLineData(reportData.get(i).get(0), reportData.get(i).get(1),
                reportData.get(i).get(2), reportData.get(i).get(3)));
        }
        return md;
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
        HashMap<Integer, List<String>> reportData = new HashMap<>();
        mrsShuttleSorter(mrs);
        List<String> titles = new ArrayList<>(3);
        List<String> lineOne;
        List<String> lineTwo;
        int counter = 0;
        titles.add("month");
        titles.add("amount");
        titles.add("isExpense");
        reportData.put(counter++, titles);
        for (MonthlyReportSE monthly : mrs) {
            Double income = 0.0;
            Double outcome = 0.0;
            String month = monthly.fromFile.substring(6, 8);
            for (int j = 0; j < monthly.monthData.size(); j++) {
                if (monthly.monthData.get(j).isExpense) {
                    outcome += monthly.monthData.get(j).quantity * monthly.monthData.get(j).sumOfOne;
                } else {
                    income += monthly.monthData.get(j).quantity * monthly.monthData.get(j).sumOfOne;
                }
            }
            lineOne = new ArrayList<>(3);
            lineOne.add(month);
            lineOne.add(outcome.toString());
            lineOne.add("true");
            reportData.put(counter++, lineOne);
            lineTwo = new ArrayList<>(3);
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
            for (int i = 0; i < mr.monthData.size(); i++) {//0 строка - с заголовками
                if (mr.monthData.get(i).isExpense) {
                    double expense = mr.monthData.get(i).sumOfOne *
                            mr.monthData.get(i).quantity;
                    if (expense > maxExpense) {
                        maxExpense = expense;
                        maxExpenseIndex = i;
                    }
                } else {
                    double profit = mr.monthData.get(i).sumOfOne *
                            mr.monthData.get(i).quantity;
                    if (profit > maxProfit) {
                        maxProfit = profit;
                        maxProfitIndex = i;
                    }
                }
            }
            System.out.println("Позиция \"" + mr.monthData.get(maxProfitIndex).itemName +
                    "\" принесла вам больше всего средств с доходом " + df.format(maxProfit) + " рублей!");
            System.out.println("Больше всего вы потратили на позицию \"" +
                    mr.monthData.get(maxExpenseIndex).itemName +
                    "\". Пришлось отдать " + df.format(maxExpense) + " рублей!");
        }
    }

    static class MonthLineData {
        String itemName;
        boolean isExpense;
        int quantity;
        double sumOfOne;

        MonthLineData(String itemName, String isExpense, String quantity, String sumOfOne) {
            this.itemName = itemName;
            this.isExpense = Boolean.parseBoolean(isExpense);
            this.quantity = Integer.parseInt(quantity);
            this.sumOfOne = Double.parseDouble(sumOfOne);
        }
     }
}

