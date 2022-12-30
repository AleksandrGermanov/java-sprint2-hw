import java.util.ArrayList;

public class MonthlyECounter extends ECounter {
    private static boolean wasCreated;
    public static int columnQuantity = 4;
    ArrayList<String> itemName;
    ArrayList<Boolean> isExpense;
    ArrayList<Integer> quantity;
    ArrayList<Double> sumOfOne;

    public MonthlyECounter(String fromFile, ArrayList<String> reportData) {
        super(fromFile, reportData);
        super.columnQuantity = columnQuantity;
        wasCreated = true;
        columnsToClassFields();
    }

    public static boolean getWasCreated() {
        return wasCreated;
    }

    public int getMonthNum() {
        return Integer.parseInt(fromFile.substring(6, 8));
    }

    @Override
    public void columnsToClassFields() {
        itemName = new ArrayList<>(dataToColumns().get(0).length);
        for (String str : dataToColumns().get(0)) {
            itemName.add(str);
        }
        isExpense = new ArrayList<>(dataToColumns().get(1).length);
        for (String str : dataToColumns().get(1)) {
            isExpense.add(Boolean.parseBoolean(str));
        }
        quantity = new ArrayList<>(dataToColumns().get(2).length);
        for (String str : dataToColumns().get(2)) {
            quantity.add(Integer.parseInt(str));
        }
        sumOfOne = new ArrayList<>(dataToColumns().get(3).length);
        for (String str : dataToColumns().get(3)) {
            sumOfOne.add(Double.parseDouble(str));
        }
    }

    public static void mecShuttleSorter(ArrayList<MonthlyECounter> mec) { //челночная сортировка
        MonthlyECounter buffer;
        for (int i = 1; i < mec.size(); i++) {
            if (mec.get(i - 1).getMonthNum() > mec.get(i).getMonthNum()) {
                buffer = mec.get(i);
                mec.remove(i);
                mec.add(i - 1, buffer);
                for (int j = i - 1; (j - 1) > -1; j--) {
                    if (mec.get(j - 1).getMonthNum() > mec.get(j).getMonthNum()) {
                        buffer = mec.get(j);
                        mec.remove(j);
                        mec.add(j - 1, buffer);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static YearlyECounter yecFromMecs(ArrayList<MonthlyECounter> mecs) {
        mecShuttleSorter(mecs);
        ArrayList<String> reportData = new ArrayList<>();
        reportData.add("Dummy");// конструктор отбросит первые 3 значения,
        reportData.add("Dummy");// т.к. в csv файлах это названия колонок
        reportData.add("Dummy");
        for (int i = 0; i < mecs.size(); i++) {
            MonthlyECounter monthly = mecs.get(i);
            Double income = 0.0;
            Double outcome = 0.0;
            String month = monthly.fromFile.substring(6, 8);
            for (int j = 0; j < monthly.itemName.size(); j++) {
                if (monthly.isExpense.get(j)) {
                    outcome += monthly.quantity.get(j) * monthly.sumOfOne.get(j);
                } else {
                    income += monthly.quantity.get(j) * monthly.sumOfOne.get(j);
                }
            }
            reportData.add(month);
            reportData.add(outcome.toString());
            reportData.add("true");
            reportData.add(month);
            reportData.add(income.toString());
            reportData.add("false");
        }
        return new YearlyECounter(("ec" + mecs.get(0).getYearInt()), reportData);
    }

    public static void printMonthlyStats(ArrayList<MonthlyECounter> mecs) {
        for (MonthlyECounter mec : mecs) {
            System.out.println("Отчет за " + monthNames[mec.getMonthNum() - 1] + ".");
            double maxExpense = 0.0;
            int maxExpenseIndex = 0;
            double maxProfit = 0.0;
            int maxProfitIndex = 0;
            for (int i = 0; i < mec.itemName.size(); i++) {
                if (mec.isExpense.get(i)) {
                    double expense = mec.quantity.get(i) * mec.sumOfOne.get(i);
                    if (expense > maxExpense) {
                        maxExpense = expense;
                        maxExpenseIndex = i;
                    }
                } else {
                    double profit = mec.quantity.get(i) * mec.sumOfOne.get(i);
                    if (profit > maxProfit) {
                        maxProfit = profit;
                        maxProfitIndex = i;
                    }
                }
            }
            System.out.println("Позиция \"" + mec.itemName.get(maxProfitIndex) +
                    "\" принесла вам больше всего средств с доходом " + df.format(maxProfit) + " рублей!");
            System.out.println("Больше всего вы потратили на позицию \"" + mec.itemName.get(maxExpenseIndex) +
                    "\". Пришлось отдать " + df.format(maxExpense) + " рублей!");
        }
    }
}


