import java.util.HashMap;
import java.util.ArrayList;

public class MonthlyReport {
    boolean monthlyIsLaunched = false;
    Auxiliary auxiliary = new Auxiliary (); //Вспомогательные методы
    MonthData monthData = new MonthData();

    public HashMap <Integer, Integer> expensesByMonths = new HashMap<>(); //Хранит месяцы и расходы
    public HashMap <Integer, Integer> profitsByMonths = new HashMap<>(); //Хранит месяцы и доходы

    void readMonthlyReports() {
        monthlyIsLaunched = true; //Говорим, что запускались считывания
        for(int i = 1; i < 4; i++){ //Запускаем обход по количеству месячных файлов
            //ArrayList <MonthlyInformationFromFile> values = new ArrayList<>();
            ArrayList <Integer> expensesInOneMonth = new ArrayList<>();
            ArrayList <Integer> profitsInOneMonth = new ArrayList<>();
            String contentsOfTheWholeFile = auxiliary.readFileContentsOrNull("resources/m.20210" + i + ".csv");
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
            // System.out.println("Самый затратный товар " + leastProfitableItemName + " " + biggestExpense);
            monthData.leastProfitableCost [i-1] = biggestExpense;
            monthData.leastProfitableProduct [i-1] = leastProfitableItemName;
            monthData.mostProfitableCost [i-1] = mostProfitableItemCost;
            monthData.mostProfitableProduct [i-1] = mostProfitableItemName;



            int fullMonthlyProfit = 0; // Ищем сумму прибылей по месяцу
            for(int k = 0; k < profitsInOneMonth.size(); k++){
                fullMonthlyProfit = fullMonthlyProfit + profitsInOneMonth.get(k);
            }
            profitsByMonths.put(i, fullMonthlyProfit);
            // System.out.println("За месяц " + i + " было получено " + fullMonthlyProfit);

            int fullMonthlyExpenses = 0; // Ищем сумму расходов по месяцу
            for(int k = 0; k < expensesInOneMonth.size(); k++){
                fullMonthlyExpenses = fullMonthlyExpenses + expensesInOneMonth.get(k);
            }
            expensesByMonths.put(i, fullMonthlyExpenses);
            // System.out.println("За месяц " + i + " было потрачено " + fullMonthlyExpenses);


        }
        System.out.println("Успех!");
    }

    public HashMap <Integer, Integer> receiveExpensesFromTheMonthly() {
        return expensesByMonths;
    }

    public HashMap <Integer, Integer> receiveProfitsFromTheMonthly() {
        return profitsByMonths;
    }

void monthlyData (){ //здесь печатаем месячный отчет
        for (int i = 0; i < monthData.mostProfitableProduct.length; i ++){
            System.out.println("Месяц " + (i+1));
            System.out.println("Самый прибыльный товар за этот месяц: " + monthData.mostProfitableProduct[i] + ", доход: " + monthData.mostProfitableCost[i]);
            System.out.println("Самый большой расход за этот месяц: " + monthData.leastProfitableProduct[i] + ", стоимость: " + monthData.leastProfitableCost[i]);
        }

}



}
