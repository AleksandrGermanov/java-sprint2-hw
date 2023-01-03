package StandartEdition;

import java.util.ArrayList;

public class YearlyECounter extends ECounter {
    private static boolean wasCreated;
    public static int columnQuantity = 3;
    ArrayList<Integer> month;
    ArrayList<Double> amount;
    ArrayList<Boolean> isExpense;

    public YearlyECounter(String fromFile, ArrayList<String> reportData) {
        super(fromFile, reportData);
        super.columnQuantity = columnQuantity;
        wasCreated = true;
        columnsToClassFields();
    }

    public static boolean getWasCreated() {
        return wasCreated;
    }

    public void columnsToClassFields() {
        month = new ArrayList<>(dataToColumns().get(0).length);
        for (String str : dataToColumns().get(0)) {
            month.add(Integer.parseInt(str));
        }
        amount = new ArrayList<>(dataToColumns().get(1).length);
        for (String str : dataToColumns().get(1)) {
            amount.add(Double.parseDouble(str));
        }
        isExpense = new ArrayList<>(dataToColumns().get(2).length);
        for (String str : dataToColumns().get(2)) {
            isExpense.add(Boolean.parseBoolean(str));
        }
    }

    public void printProfit() {
        double buffer;
        for (int i = 1; i < month.size(); i = i + 2) {
            if (month.get(i).equals(month.get(i - 1))) {
                if (isExpense.get(i - 1)) {
                    buffer = -amount.get(i - 1);
                } else {
                    buffer = amount.get(i - 1);
                }
                if (isExpense.get(i)) {
                    buffer -= amount.get(i);
                } else {
                    buffer += amount.get(i);
                }
                System.out.println("Месяц " + monthNames[month.get(i) - 1] + ". Ваша прибыль составила "
                        + df.format(buffer) + " рублей.");

            } else {
                System.out.println("Несоответствие строк по месяцам. Проверьте предоставленные данные.");
            }
        }
    }

    public static void compareYecs(YearlyECounter yec, YearlyECounter yecFromMecs) {
        boolean isOK = true;
        for (int i = 1; i < yec.month.size(); i = i + 2) {
            if ((yec.month.get(i - 1).equals(yec.month.get(i))) &&
                    (yecFromMecs.month.get(i - 1).equals(yecFromMecs.month.get(i))) &&
                    (yec.month.get(i - 1).equals(yecFromMecs.month.get(i - 1)))) {
                double income = yecLinesExpenseProfitDefiner(false, yec, i);
                double outcome = yecLinesExpenseProfitDefiner(true, yec, i);
                double in = yecLinesExpenseProfitDefiner(false, yecFromMecs, i);
                double out = yecLinesExpenseProfitDefiner(true, yecFromMecs, i);
                if (!(outcome==out && income==in)) {
                    System.out.println("Несоответствие данных за " +
                            monthNames[yec.month.get(i) - 1] + " " + yec.getYearInt() +
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
        for (int i = 0; i < amount.size(); i++) {
            if (isExpense.get(i)) {
                expense += amount.get(i);
            } else {
                profit += amount.get(i);
            }
        }
        System.out.println("Средний расход за все месяцы составил " +
                df.format(expense / (amount.size() / 2.0)) + " рублей.");
        System.out.println("Средний доход за все месяцы составил " +
                df.format(profit / (amount.size() / 2.0)) + " рублей.");
    }

    static double yecLinesExpenseProfitDefiner(boolean isExpense,
                                               YearlyECounter yec, int i) {
        double income;
        double outcome;
        if (yec.isExpense.get(i - 1)) {
            outcome = yec.amount.get(i - 1);
            income = yec.amount.get(i);
        } else {
            outcome = yec.amount.get(i);
            income = yec.amount.get(i - 1);
        }
        if (isExpense) {
            return outcome;
        } else {
            return income;
        }
    }
}
