package ShapkinsEdition;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReportReaderSE {
    public static Path resources = Paths.get((System.getProperty("user.dir") + "/resources"));
    List<Path> files;

    public ReportReaderSE() {
        files = new ArrayList<>();
    }

    void getFilteredPaths(String start) throws IOException { //часть метода getReportFiles()
        if (!files.isEmpty()) {
            files.clear();
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(resources,
                    file -> (file.getFileName().toString().startsWith(start)))){ // теперь и здесь дверь с доводчиком
            System.out.println("Следующие файлы готовы к загрузке:");
            for (Path path : stream) {
                files.add(path);
                System.out.println(path.getFileName().toString());
            }
        } catch (DirectoryIteratorException ex) {
            throw ex.getCause(); // IOException как причина
        }
        try {
            if (files.size() == 0) {
                throw new Exception("Пустой строковый массив");
            }
        } catch (Exception emptyStringArray) {
            System.out.println("Ничего не нашлось! Проверьте правильность " +
                    "значения фильтра и наличие нужных файлов в папке.");
            emptyStringArray.printStackTrace();
            System.exit(404);
        }
    }

    HashMap<Integer, List<String>> reportData(int i) throws IOException {
        HashMap<Integer, List<String>> reportMap = new HashMap<>();
        Integer line = 0;
        for (String str : Files.readAllLines(files.get(i), StandardCharsets.UTF_8)) {
            String[] strings = str.split(",");
            reportMap.put(line, Arrays.asList(strings));
            ++line;
        }
        return reportMap;
    }

    public ArrayList<MonthlyReportSE> monthlyReportsArrListCreator() {
        ArrayList<MonthlyReportSE> reports = new ArrayList<>();
        try {
            getFilteredPaths("m");
            for (int i = 0; i < files.size(); i++) {
                reports.add(new MonthlyReportSE(files.get(i).getFileName().toString(), reportData(i)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return reports;
    }

    public YearlyReportSE yearlyReportCreator() {
        YearlyReportSE yr = null;
        try {
            getFilteredPaths("y");
            yr = new YearlyReportSE(files.get(0).getFileName().toString(), reportData(0));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return yr;
    }
}

