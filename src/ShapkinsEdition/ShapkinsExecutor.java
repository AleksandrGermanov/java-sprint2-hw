package ShapkinsEdition;

import java.util.ArrayList;
import java.util.Scanner;

public class ShapkinsExecutor {
    public static void shapkinsEdition () {
        ReportReaderSE rrSE = new ReportReaderSE();
        Scanner scanner = new Scanner(System.in);
        ArrayList<MonthlyReportSE> mrs = new ArrayList<>();
        YearlyReportSE yr = null;
        YearlyReportSE autoYr;

        while (true) {
            printMenu();
            String input = scanner.nextLine();
            if (input.equals("1")) {
                mrs = rrSE.monthlyReportsArrListCreator();
            } else if (input.equals("2")) {
                yr = rrSE.yearlyReportCreator();
            } else if (input.equals("3")) {
                if (MonthlyReportSE.getWasCreated() && YearlyReportSE.getWasCreated()) {
                    autoYr = MonthlyReportSE.autoYrCreator(mrs);
                    YearlyReportSE.compareYrs(yr, autoYr);
                } else {
                    if (!MonthlyReportSE.getWasCreated())
                        System.out.println("Нужно считать месячные отчеты");
                    if (!YearlyReportSE.getWasCreated())
                        System.out.println("Нужно считать годовой отчет");
                }
            } else if (input.equals("4")) {
                if (!MonthlyReportSE.getWasCreated())
                    System.out.println("Нужно считать месячные отчеты");
                else {
                    System.out.println("Информация из месячных отчетов " + mrs.get(0).getYearInt() + " года.");
                    MonthlyReportSE.printMonthlyStats(mrs);
                }
            } else if (input.equals("5")) {
                if (!YearlyReportSE.getWasCreated())
                    System.out.println("Нужно считать годовой отчет");
                else {
                    System.out.println("Информация из отчета за " + yr.getYearInt() + " год.");
                    yr.printProfit();
                    yr.printAvgProfitAndExpense();
                }
            } else if (input.equals("HashMaps 4ever!!!")) {
                break;
            }
        }
    }

    static void printMenu() {
        System.out.println("\nЧто вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        if (MonthlyReportSE.getWasCreated() && YearlyReportSE.getWasCreated()) {
            System.out.println("3 - Сверить отчёты");
        } else {
            System.out.println("3(недоступно) - Сверить отчёты");
        }
        if (MonthlyReportSE.getWasCreated()) {
            System.out.println("4 - Вывести информацию о всех месячных отчётах");
        } else {
            System.out.println("4(недоступно) - Вывести информацию о всех месячных отчётах");
        }
        if (YearlyReportSE.getWasCreated()) {
            System.out.println("5 - Вывести информацию о годовом отчёте");
        } else {
            System.out.println("5(недоступно) - Вывести информацию о годовом отчёте");
        }
        System.out.println("Для выхода из приложения введите следующую фразу " +
                "\"HashMaps 4ever!!!\"(без кавычек).");
    }
}

