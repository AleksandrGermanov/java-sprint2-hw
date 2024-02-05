package StandartEdition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Этот класс создает список объектов file
 * из папки resources, считывает значение файлов в строку,
 * представляет эту строку как массив строк по разделителю
 * и передает этот массив в конструктор наследников ECounter.
 * В результате создает список(ArrayList)
 * из объектов класса MonthlyECounter или
 * объект YearlyECounter.
 */
public class ReportReader {
    boolean isMonthly;// переключает годовой и месячный режим
    String userDir = System.getProperty("user.dir");
    String resourcesPath = (userDir + "/resources");
    File resources = new File(resourcesPath);
    ArrayList<File> files;

    public void setIsMonthly(boolean bool) {
        isMonthly = bool;
    }

    public ReportReader() {
        files = new ArrayList<>();
    }

    String[] getFilteredNames(String start) { //часть метода getReportFiles()
        /*
        There is no guarantee that the name strings in the resulting array
        will appear in any specific order; they are not, in particular,
        guaranteed to appear in alphabetical order.
        из описания file.list() - на определенном этапе потребуется сортер
         */
        String[] names = resources.list((file, name) -> name.startsWith(start));
        try {
            if (names.length == 0) {
                throw new Exception("Пустой строковый массив");
            }
            return names;
        } catch (Exception emptyStringArray) {
            System.out.println("Ничего не нашлось! Проверьте правильность " +
                    "значения фильтра и наличие нужных файлов в папке.");
            emptyStringArray.printStackTrace();
            System.exit(404);
        }
        return names;
    }

    void getReportFiles(String start) {
        System.out.println("В папке " + resources.getName() + " найдены следующие отчеты:");
        String[] filteredNames = getFilteredNames(start);//отфильтровали имена файлов
        if (!files.isEmpty()) { //почистили корзинку
            files.clear();
        }
        for (String str : filteredNames) {
            System.out.println(str);
            File file = new File(resourcesPath + "/" + str); //создаем новый file
            if (file.exists() && file.isFile()) {//проверяем
                files.add(file);//и кладем в корзину
            } else {
                System.out.println("Ошибка приложения! Обратитесь к поставщику ПО.");
                return;
            }
        }
        System.out.println("Готовлюсь к загрузке данных из отчетов.");
    }

    String fileToString(int fileNum) {//часть метода fileToArrayList(int iterator)
        String concat = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(files.get(fileNum), StandardCharsets.UTF_8))) {
            String str;
            while ((str = reader.readLine()) != null) {
                concat += str + ",";
            }
        } catch (IOException ex) {
            System.out.println("Ошибка приложения! Обратитесь к поставщику ПО.");
            ex.printStackTrace();
            return null;
        }
        return concat;
    }

    ArrayList<String> fileToArrayList(int fileNum) { //поскольку количество значений может меняться
        // от месяца к месяцу или в годовом отчете, конструкотор ECounter(...,...) принимает список
        String[] strings = fileToString(fileNum).split(",");
        ArrayList<String> aList = new ArrayList<>();
        for (String str : strings) {
            aList.add(str);
        }
        if (isMonthly) {//здесь проверяем, чтобы количество значений было кратно количеству колонок в отчетах
            //
            if ((aList.size()) % MonthlyECounter.columnQuantity == 0) {
                System.out.println("Отчет из файла " + files.get(fileNum).getName() + " успешно считан.");
                for (int i = 0; i < MonthlyECounter.columnQuantity; i++) {//здесь удаляем 1 строку с загловками
                    aList.remove(0);
                }
            }
        } else {
            if ((aList.size()) % YearlyECounter.columnQuantity == 0) {
                System.out.println("Отчет из файла " + files.get(fileNum).getName() + " успешно считан.");
                for (int i = 0; i < YearlyECounter.columnQuantity; i++) {
                    aList.remove(0);
                }
            }
        }
        return aList;
    }

    public ArrayList<MonthlyECounter> filesToMECArrayList() {
        ArrayList<MonthlyECounter> mec = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            mec.add(new MonthlyECounter(files.get(i).getName(), fileToArrayList(i)));
        }
        return mec;
    }

    public YearlyECounter filesToYec() {
        YearlyECounter yec = new YearlyECounter(files.get(0).getName(), fileToArrayList(0));
        return yec;
    }
}








