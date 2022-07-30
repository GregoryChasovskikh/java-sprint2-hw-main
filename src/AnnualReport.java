
import java.util.HashMap;
import java.util.ArrayList;

public class AnnualReport {
    boolean annualIsLaunched = false;

    Auxiliary auxiliary = new Auxiliary ();


    HashMap <Integer, Integer> expensesByMonths = new HashMap<>();
    HashMap <Integer, Integer> profitsByMonths = new HashMap<>();
    ArrayList<AnnualInformationFromFile> values = new ArrayList<>();
    void readAnnualReports() {
        annualIsLaunched = true;


        String contentsOfTheWholeFile = auxiliary.readFileContentsOrNull("resources/y.2021.csv");
        String[] line = contentsOfTheWholeFile.split("\n");
        for (int i = 1; i < line.length; i++) {
            AnnualInformationFromFile annualInformationFromFile = new AnnualInformationFromFile();
            String[] individualValues = line[i].split(",");
            annualInformationFromFile.month = Integer.parseInt(individualValues[0]);
            annualInformationFromFile.amount = Integer.parseInt(individualValues[1]);
            annualInformationFromFile.is_expense = Boolean.parseBoolean(individualValues[2]);

            //values.add(annualInformationFromFile);

            if (!annualInformationFromFile.is_expense) {
                profitsByMonths.put(annualInformationFromFile.month, annualInformationFromFile.amount);
            } else {
                expensesByMonths.put(annualInformationFromFile.month, annualInformationFromFile.amount);
            }

        }
        System.out.println("Успех!");

    }




    public HashMap <Integer, Integer> receiveExpensesFromTheAnnul() {

        // System.out.println("YYY Ключи в классе " + expensesByMonths.keySet());
        return expensesByMonths;
    }
    public HashMap <Integer, Integer> receiveProfitsFromTheAnnual() {
        // System.out.println("Ключи в классе " + profitsByMonths.keySet());
        return profitsByMonths;
    }

    void printAnnualReport () {
        System.out.println("Рассматриваемый год : 2021");
        for (int i = 0; i < expensesByMonths.size(); i++){
            System.out.println("Месяц " + (i + 1) + ". Прибыль (разность доходов и расходов) по этому месяцу: " + (profitsByMonths.get(i+1) - expensesByMonths.get(i+1)));
        }
        int totalSum = 0;
        for (int i = 0; i < expensesByMonths.size(); i++){
            totalSum += expensesByMonths.get(i+1);
        }
        System.out.println("Средний расход за все месяцы в году: " + (totalSum/expensesByMonths.size()));
        totalSum = 0;
        for (int i = 0; i < profitsByMonths.size(); i++){
            totalSum += profitsByMonths.get(i+1);
        }
        System.out.println("Средний доход за все месяцы в году: " + (totalSum/profitsByMonths.size()));

    }

    }


