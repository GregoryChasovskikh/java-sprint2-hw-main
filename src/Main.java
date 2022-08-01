
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        Report report = new Report ();



        report.printMenu();
        Scanner scanner = new Scanner(System.in);
        byte userInput = scanner.nextByte();
        while (userInput != 0) {
            switch (userInput){ //Ситывание всех месячных отчётов
                case 1: report.readMonthlyReports(); //Ситывание всех месячных отчётов
                    break;
                case 2: report.readAnnualReports(); // Считываение годового отчёта
                    break;
                case 3: report.compareData(); //Сверка отчетов
                    break;
                case 4: report.printMonthlyReport (); // Отчёт по месячным отчетам
                    break;
                case 5: report.printFinalAnnualReport(); // Отчет по годовому отчету
                    break;
                default: report.reportAnError();
                    break;

            }
            report.printMenu();
            userInput = scanner.nextByte();

        }// Поехали!
    }
}
