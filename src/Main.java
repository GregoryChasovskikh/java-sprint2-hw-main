import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Auxiliary auxiliary = new Auxiliary();
        MonthlyReport monthlyReport = new MonthlyReport();
        AnnualReport annualReport = new AnnualReport();
        HashMap <Integer, Integer> expAnn;
        HashMap <Integer, Integer> profAnn;
        HashMap <Integer, Integer> expMon;
        HashMap <Integer, Integer> profMon;


        auxiliary.printMenu();
        Scanner scanner = new Scanner(System.in);
        byte userInput = scanner.nextByte();
        while (userInput != 0) {
            if (userInput == 1){ //Ситывание всех месячных отчётов
                monthlyReport.readMonthlyReports();

            } else if (userInput == 2){ // Считываение годового отчёта
                annualReport.readAnnualReports();


            } else if (userInput == 3){ //Сверка отчетов
                if(!monthlyReport.monthlyIsLaunched|| !annualReport.annualIsLaunched){
                    System.out.println("Сначала считайте годой и месячный отчёты!");
                } else {
                    ArrayList <Integer> errMonthExp = new ArrayList<>();
                    ArrayList <Integer> errMonthProf = new ArrayList<>();



                    expAnn = annualReport.receiveExpensesFromTheAnnul();
                    profAnn = annualReport.receiveProfitsFromTheAnnual();
                    expMon = monthlyReport.receiveExpensesFromTheMonthly();
                    profMon = monthlyReport.receiveProfitsFromTheMonthly();

                    int testExpenses = 0;
                    int testProfits = 0;
                    for (int month : expAnn.keySet()) {
                        if (expAnn.get(month).equals(expMon.get(month))){
                            testExpenses ++;
                            // System.out.println("Сверяем расходы за " + month + " мес " + expMon.get(month) + " год " + expAnn.get(month));
                        } else {
                            errMonthExp.add(month);
                        }
                        if(profAnn.get(month).equals(profMon.get(month))){
                            testProfits ++;
                            // System.out.println("Сверяем доходы за " + month + " мес " + expMon.get(month) + " год " + expAnn.get(month));
                        } else {
                            errMonthProf.add(month);
                        }

                        }
                    if (testExpenses == expAnn.size() && testProfits == profAnn.size()) {
                        System.out.println("Годовой и месячные отчеты содержат идентичную информацию о доходах и расходах по каждому месяцу");
                    } else {
                        System.out.println("В отчетах есть расхождения по следующим месяцам:");
                        System.out.println("Доходов: " + errMonthProf);
                        System.out.println("Расходов: " + errMonthExp);
                    }


                }


            } else if(userInput == 4){ // Отчёт по месячным отчетам
                if(!monthlyReport.monthlyIsLaunched) {
                    System.out.println("Сначала считайте месячные отчёты!");
                } else {
                    monthlyReport.monthlyData();
                }


            } else if(userInput == 5){ // Отчет по годовому отчету
                if(!annualReport.annualIsLaunched) {
                    System.out.println("Сначала считайте годой отчёт!");
                } else {
                    annualReport.printAnnualReport();
                }

            } else {
                auxiliary.reportAnError();
            }

            auxiliary.printMenu();
            userInput = scanner.nextByte();
        }// Поехали!
    }
}
