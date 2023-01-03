package StandartEdition;

import java.util.ArrayList;
import java.util.Scanner;

public class StandartExecutor {
    public static void standartEdition () {
        ReportReader reportReader = new ReportReader();
        Scanner scanner = new Scanner(System.in);
        ArrayList<MonthlyECounter> mecs = new ArrayList<>();
        YearlyECounter yec = null;
        YearlyECounter yecFromMecs;

        while (true) {
            printMenu();
            String input = scanner.nextLine();
            if (input.equals("1")) {
                reportReader.setIsMonthly(true);
                reportReader.getReportFiles("m");
                mecs = reportReader.filesToMECArrayList();
            } else if (input.equals("2")) {
                reportReader.setIsMonthly(false);
                reportReader.getReportFiles("y");
                yec = reportReader.filesToYec();
            } else if (input.equals("3")) {
                if (MonthlyECounter.getWasCreated() && YearlyECounter.getWasCreated()) {
                    yecFromMecs = MonthlyECounter.yecFromMecs(mecs);
                    YearlyECounter.compareYecs(yec, yecFromMecs);
                } else {
                    if (!MonthlyECounter.getWasCreated())
                        System.out.println("Нужно считать месячные отчеты");
                    if (!YearlyECounter.getWasCreated())
                        System.out.println("Нужно считать годовой отчет");
                }
            } else if (input.equals("4")) {
                if (!MonthlyECounter.getWasCreated())
                    System.out.println("Нужно считать месячные отчеты");
                else {
                    System.out.println("Информация из месячных отчетов " + mecs.get(0).getYearInt() + " года.");
                    MonthlyECounter.printMonthlyStats(mecs);
                }
            } else if (input.equals("5")) {
                if (!YearlyECounter.getWasCreated())
                    System.out.println("Нужно считать годовой отчет");
                else {
                    System.out.println("Информация из отчета за " + yec.getYearInt() + " год.");
                    yec.printProfit();
                    yec.printAvgProfitAndExpense();
                }
            } else if (input.equals("182 с половиной зерна в год")) {
                break;
            }
        }
    }

    static void printMenu() {
        System.out.println("\nЧто вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        if (MonthlyECounter.getWasCreated() && YearlyECounter.getWasCreated()) {
            System.out.println("3 - Сверить отчёты");
        } else {
            System.out.println("3(недоступно) - Сверить отчёты");
        }
        if (MonthlyECounter.getWasCreated()) {
            System.out.println("4 - Вывести информацию о всех месячных отчётах");
        } else {
            System.out.println("4(недоступно) - Вывести информацию о всех месячных отчётах");
        }
        if (YearlyECounter.getWasCreated()) {
            System.out.println("5 - Вывести информацию о годовом отчёте");
        } else {
            System.out.println("5(недоступно) - Вывести информацию о годовом отчёте");
        }
        System.out.println("Для выхода из приложения введите следующую фразу " +
                "\"182 с половиной зерна в год\"(без кавычек).");
    }
}


