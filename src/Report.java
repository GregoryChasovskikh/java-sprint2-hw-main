import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.ArrayList;

public class Report {
    boolean annualIsLaunched = false;
    boolean monthlyIsLaunched = false;
    HashMap<Integer, AnnualInformationFromFile> profitsAndExpenses = new HashMap<>();
    HashMap<Integer, MonthlyInformationFromFile> profitsAndExpensesMon = new HashMap<>();

    void readAnnualReports (){
        annualIsLaunched = true;


        String contentsOfTheWholeFile = readFileContentsOrNull("resources/y.2021.csv");
        if (contentsOfTheWholeFile == null){
            System.out.println("Не удалось открыть файл.");
        } else {
            System.out.println("Успех!");
        }
        String[] line = contentsOfTheWholeFile.split("\n");
        for (int i = 1; i < line.length; i++) {
            AnnualInformationFromFile annualInformationFromFile = new AnnualInformationFromFile();

            String[] individualValues = line[i].split(",");
            annualInformationFromFile.month = Integer.parseInt(individualValues[0]);
            annualInformationFromFile.amount = Integer.parseInt(individualValues[1]);
            annualInformationFromFile.is_expense = Boolean.parseBoolean(individualValues[2]);
            if (!profitsAndExpenses.containsKey(annualInformationFromFile.month)){
                profitsAndExpenses.put(annualInformationFromFile.month, annualInformationFromFile);
            }
            if (annualInformationFromFile.is_expense){
                profitsAndExpenses.get(annualInformationFromFile.month).expenses = annualInformationFromFile.amount;

            } else  {
                profitsAndExpenses.get(annualInformationFromFile.month).profits = annualInformationFromFile.amount;
            }


        }


    }




    void printAnnualReport () {
        System.out.println("Рассматриваемый год : 2021");
        for (int i = 1; i <= profitsAndExpenses.size(); i++){
            System.out.println("Месяц " + i + ". Прибыль (разность доходов и расходов) по этому месяцу: " + (profitsAndExpenses.get(i).profits - profitsAndExpenses.get(i).expenses));
        }
        int totalSum = 0;
        for (int i = 1; i <= profitsAndExpenses.size(); i++){
            totalSum += profitsAndExpenses.get(i).expenses;
        }
        System.out.println("Средний расход за все месяцы в году: " + (totalSum/profitsAndExpenses.size()));
        totalSum = 0;
        for (int i = 1; i <= profitsAndExpenses.size(); i++){
            totalSum += profitsAndExpenses.get(i).profits;
        }
        System.out.println("Средний доход за все месяцы в году: " + (totalSum/profitsAndExpenses.size()));

    }

    void readMonthlyReports() {
        monthlyIsLaunched = true; //Говорим, что запускались считывания
        for(int i = 1; i < 4; i++){ //Запускаем обход по количеству месячных файлов

            ArrayList <Integer> expensesInOneMonth = new ArrayList<>();
            ArrayList <Integer> profitsInOneMonth = new ArrayList<>();
            String contentsOfTheWholeFile = readFileContentsOrNull("resources/m.20210" + i + ".csv");
            if (contentsOfTheWholeFile == null){
                System.out.println("Не удалось открыть файл.");
            }
            String [] line = contentsOfTheWholeFile.split("\n"); // Разделяем на строчки
            int mostProfitableItemCost = 0; // Здесь храним стоимость самого прибыльного товара
            String mostProfitableItemName = ""; // Его название
            int biggestExpense = 0; // Здесь храним самую большую трату
            String leastProfitableItemName = ""; // Её название
            for (int j = 1; j < line.length; j++){
                MonthlyInformationFromFile informationFromFile = new MonthlyInformationFromFile();
                String [] individualValues = line[j].split(","); //Разделяем строчки в файле
                informationFromFile.itemName = individualValues [0];
                informationFromFile.isExpense = Boolean.parseBoolean(individualValues [1]);
                informationFromFile.quantity = Integer.parseInt(individualValues [2]);
                informationFromFile.sumOfOne = Integer.parseInt(individualValues [3]);


                if (!informationFromFile.isExpense){ //Если это доход от продажи
                    profitsInOneMonth.add(informationFromFile.quantity * informationFromFile.sumOfOne);
                    //System.out.println("Доход от продажи " + informationFromFile.itemName + " составил " + profitsInOneMonth.get(profitsInOneMonth.size()-1));
                    if (informationFromFile.quantity * informationFromFile.sumOfOne > mostProfitableItemCost) {
                        //Проверяем: является ли товар прибыльнее предыдущего
                        mostProfitableItemCost = profitsInOneMonth.get(profitsInOneMonth.size()-1);
                        mostProfitableItemName = informationFromFile.itemName;
                    } else if (informationFromFile.quantity * informationFromFile.sumOfOne == mostProfitableItemCost){
                        mostProfitableItemName = mostProfitableItemName + ", " + informationFromFile.itemName;
                    }
                } else { //Если это расход
                    expensesInOneMonth.add(informationFromFile.quantity * informationFromFile.sumOfOne);
                    //System.out.println("Расход от покупки " + informationFromFile.itemName + " составил " + expensesInOneMonth.get(expensesInOneMonth.size()-1));
                    if (informationFromFile.quantity * informationFromFile.sumOfOne > biggestExpense) {
                        //Проверяем: является ли товар затратнее предыдущего
                        biggestExpense = informationFromFile.quantity * informationFromFile.sumOfOne;
                        leastProfitableItemName = informationFromFile.itemName;

                    } else if (informationFromFile.quantity * informationFromFile.sumOfOne == biggestExpense){
                        leastProfitableItemName = leastProfitableItemName + ", " + informationFromFile.itemName;
                    }

                }



            }

            int fullMonthlyProfit = 0; // Ищем сумму прибылей по месяцу
            for(int k = 0; k < profitsInOneMonth.size(); k++){
                fullMonthlyProfit = fullMonthlyProfit + profitsInOneMonth.get(k);
            }


            int fullMonthlyExpenses = 0; // Ищем сумму расходов по месяцу
            for(int k = 0; k < expensesInOneMonth.size(); k++){
                fullMonthlyExpenses = fullMonthlyExpenses + expensesInOneMonth.get(k);
            }

            MonthlyInformationFromFile finalInformationFromFile = new MonthlyInformationFromFile();
            finalInformationFromFile.leastProfitableCost = biggestExpense;
            finalInformationFromFile.leastProfitableProduct = leastProfitableItemName;
            finalInformationFromFile.mostProfitableCost = mostProfitableItemCost;
            finalInformationFromFile.mostProfitableProduct = mostProfitableItemName;

            finalInformationFromFile.fullMonthlyProfit = fullMonthlyProfit;
            finalInformationFromFile.fullMonthlyExpenses = fullMonthlyExpenses;
            profitsAndExpensesMon.put(i, finalInformationFromFile);


        }
        if (!profitsAndExpensesMon.isEmpty()) {
            System.out.println("Успех!");
        }

    }



    void monthlyData () { //здесь печатаем месячный отчет
        for (int i = 0; i < profitsAndExpenses.size(); i++) {
            System.out.println("Месяц " + (i + 1));
            System.out.println("Самый прибыльный товар за этот месяц: " + profitsAndExpensesMon.get(i + 1).mostProfitableProduct + ", доход: " + profitsAndExpensesMon.get(i + 1).mostProfitableCost);
            System.out.println("Самый большой расход за этот месяц: " + profitsAndExpensesMon.get(i + 1).leastProfitableProduct + ", стоимость: " + profitsAndExpensesMon.get(i + 1).leastProfitableCost);
        }
    }

    void printMenu() { // Вывод меню
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выход");
    }

    void reportAnError() {
        System.out.println("К сожалению, такой команды пока нет.");
    }

    String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    void compareData() {
        if (!monthlyIsLaunched || !annualIsLaunched) {
            System.out.println("Сначала считайте годой и месячный отчёты!");
        } else {
            ArrayList<Integer> errMonthExp = new ArrayList<>();
            ArrayList<Integer> errMonthProf = new ArrayList<>();





            int testExpenses = 0;
            int testProfits = 0;
            for (int month : profitsAndExpenses.keySet()) {
                if (profitsAndExpenses.get(month).expenses.equals(profitsAndExpensesMon.get(month).fullMonthlyExpenses)) {
                    testExpenses++;

                } else {
                    errMonthExp.add(month);
                }
                if (profitsAndExpenses.get(month).profits.equals(profitsAndExpensesMon.get(month).fullMonthlyProfit)) {
                    testProfits++;

                } else {
                    errMonthProf.add(month);
                }
                //System.out.println("Сверяем доходы за " + month + " мес " + profitsAndExpensesMon.get(month).fullMonthlyProfit + " год " + profitsAndExpenses.get(month).profits);
                //System.out.println("Сверяем расходы за " + month + " мес " + profitsAndExpensesMon.get(month).fullMonthlyExpenses + " год " + profitsAndExpenses.get(month).expenses);


            }
            if (testExpenses == profitsAndExpenses.size() && testProfits == profitsAndExpenses.size()) {
                System.out.println("Годовой и месячные отчеты содержат идентичную информацию о доходах и расходах по каждому месяцу");
            } else {
                System.out.println("В отчетах есть расхождения по следующим месяцам:");
                System.out.println("Доходов: " + errMonthProf);
                System.out.println("Расходов: " + errMonthExp);
            }
        }
    }

    void printMonthlyReport () {
        if (!monthlyIsLaunched) {
            System.out.println("Сначала считайте месячные отчёты!");
        } else {
            monthlyData();
        }
    }
    void printFinalAnnualReport(){
        if(!annualIsLaunched) {
            System.out.println("Сначала считайте годой отчёт!");
        } else {
            printAnnualReport();
        }
    }


}
