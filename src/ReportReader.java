import java.io.*;
import java.util.ArrayList;

/**
 * Этот класс создает список объектов file
 * из папки resources, считывает значение файлов в строку,
 * представляет эту строку как массив строк по разделителю
 * и передает ее в конструктор класса ECounter.
 * В результате создает списки(ArrayList)
 * объектов классов-наследников ECounter.
 */
public class ReportReader {
    boolean isMonthly;
    String classPath = System.getProperty("java.class.path");
    ;
    String fs = System.getProperty("file.separator");
    String resourcesPath = (classPath + fs + ".." + fs +
            ".." + fs + ".." + fs + "resources");
    File resources = new File(resourcesPath);
    ArrayList<File> files;


    public void setIsMonthly(boolean bool) {
        isMonthly = bool;
    }

    public ReportReader() {
        files = new ArrayList<>();
    }

    String[] getFilteredNames() {
        String start;
        if (isMonthly) {
            start = "m";
        } else {
            start = "y";
        }
        /*
        There is no guarantee that the name strings in the resulting array
        will appear in any specific order; they are not, in particular,
        guaranteed to appear in alphabetical order.
        из описания file.list() - на определенном этапе потребуется сортер
         */
        String[] names = resources.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.startsWith(start);
            }
        });
        return names;
    }

    void getReportFiles() {
        System.out.println("В папке " + resources.getName() + " найдены следующие отчеты:");
        String[] filteredNames = getFilteredNames();
        if (!files.isEmpty()) {
            files.clear();
        }
        for (String str : filteredNames) {
            System.out.println(str);
            File file = new File(resourcesPath + fs + str);
            if (file.exists()) {
                files.add(file);
            } else {
                System.out.println("Ошибка приложения! Обратитесь к поставщику ПО.");
                return;
            }
        }
        System.out.println("Готовлюсь к загрузке данных из отчетов.");
    }

    String fileToString(int iterator) {
        String concat = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(files.get(iterator)))) { //дверь с доводчиком)
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

    ArrayList<String> fileToArrayList(int iterator) {
        String[] strings = fileToString(iterator).split(",");
        ArrayList<String> aList = new ArrayList<>();
        for (String str : strings) {
            aList.add(str);
        }
        if (isMonthly) {
            if ((aList.size()) % MonthlyECounter.columnQuantity == 0) {
                System.out.println("Отчет из файла " + files.get(iterator).getName() + " успешно считан.");
            }
        } else {
            if ((aList.size()) % YearlyECounter.columnQuantity == 0) {
                System.out.println("Отчет из файла " + files.get(iterator).getName() + " успешно считан.");
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








