import ShapkinsEdition.*;
import StandartEdition.*;
import java.util.Scanner;

// С наступившим Новым Годом!!!
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Здравствуйте!");
            System.out.println("Если вы хотите считать как Александр, " +
                    "введите \"Standart\".");
            System.out.println("Если вы хотите считать как Вячеслав, " +
                    "введите \"Shapkin\".");
            System.out.println("Если вы не хотите больше считать сегодня, " +
                    "введите \"exit\".");
            System.out.println("Ввод осуществляется без кавычек.");
            input = scanner.nextLine();
            switch (input) {
                case "Standart":
                    StandartExecutor.standartEdition();
                    break;
                case "Shapkin":
                    ShapkinsExecutor.shapkinsEdition();
                    break;
                case "exit":
                    System.exit(0);
            }
        }
    }
}


