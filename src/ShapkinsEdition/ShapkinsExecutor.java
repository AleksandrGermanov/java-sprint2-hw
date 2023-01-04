package ShapkinsEdition;

import java.util.ArrayList;
import java.util.Scanner;

public class ShapkinsExecutor {
    public static void shapkinsEdition() {
        ReportReaderSE rrSE = new ReportReaderSE();
        Scanner scanner = new Scanner(System.in);
        ArrayList<MonthlyReportSE> mrs = new ArrayList<>();
        YearlyReportSE yr = null;
        YearlyReportSE autoYr;

        
        while (true) {
            printMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    mrs = rrSE.monthlyReportsArrListCreator();
                    break;
                case "2":
                    yr = rrSE.yearlyReportCreator();
                    break;
                case "3":
                    if (MonthlyReportSE.getWasCreated() && YearlyReportSE.getWasCreated() && yr != null) {
                        autoYr = MonthlyReportSE.autoYrCreator(mrs);
                        YearlyReportSE.compareYrs(yr, autoYr);
                    } else {
                        if (!MonthlyReportSE.getWasCreated())
                            System.out.println("Нужно считать месячные отчеты");
                        if (!YearlyReportSE.getWasCreated())
                            System.out.println("Нужно считать годовой отчет");
                        if (yr == null) {
                            System.out.println("При формировании годового отчета что-то пошло не так!" +
                                    "Обратитесь к поставщику ПО.");
                        }
                    }
                    break;
                case "4":
                    if (!MonthlyReportSE.getWasCreated())
                        System.out.println("Нужно считать месячные отчеты");
                    else {
                        System.out.println("Информация из месячных отчетов " + mrs.get(0).getYearInt() + " года.");
                        MonthlyReportSE.printMonthlyStats(mrs);
                    }
                    break;
                case "5":
                    if (!YearlyReportSE.getWasCreated() || yr == null) {
                        if (!YearlyReportSE.getWasCreated()) {
                            System.out.println("Нужно считать годовой отчет");
                        }
                        if (yr == null) {
                            System.out.println("При формировании годового отчета что-то пошло не так!" +
                                    "Обратитесь к поставщику ПО.");
                        }
                    } else {
                        System.out.println("Информация из отчета за " + yr.getYearInt() + " год.");
                        yr.printProfit();
                        yr.printAvgProfitAndExpense();
                    }
                    break;
                case "HashMaps 4ever!!!":
                    return;
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

