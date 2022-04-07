import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {
	public static void main (String[] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Введите операцию над двумя арабскими или двумя римскими числами: ");
                String inputData = input.nextLine();
		
		checkAndModifyInput checkAndModify = new checkAndModifyInput();
		checkAndModify.setInput(inputData);
                String operator = checkAndModify.getOperatorType();
                if (operator == null) {return;}
                String typeOfNumbers = checkAndModify.getArabicOrRoman();
                if (typeOfNumbers == null) {return;}
                String[] twoNumbers = checkAndModify.getTwoNumbers();
                
                Calculations calc = new Calculations();
                String result = calc.getResult(twoNumbers, operator, typeOfNumbers);
                if (result == null) {return;}
                System.out.println(result);        
	}
}
class checkAndModifyInput {
	private String input;
	private String arabicOrRoman;
	private String operator;
        private String[] twoNumbers;
        
	public void setInput(String calcInput) {
		input = calcInput;
	}
	public String getInput() {
		return input;
	}
        public String[] getTwoNumbers() {
            return twoNumbers;
        }
	
	public String getOperatorType() {
            if (input.contains(" ")) {
		input = input.replaceAll(" ", "");
            }
            String[] operators = {"+", "-", "*", "/"};
            int countOfOperators = 0;
            for (String oneOfOperators : operators) {
                if (input.contains(oneOfOperators)) {
                    operator = oneOfOperators;
                    countOfOperators++;
                }
                if (input.indexOf(oneOfOperators) != input.lastIndexOf(oneOfOperators)) {
                    countOfOperators++;
                }
            }    
            if (countOfOperators == 0) {
                System.out.println("Строка не является математической операцией");
                operator = null;
            }
            if (countOfOperators > 1) {
                System.out.println("Формат математической операции не удовлетворяет заданию - два операнда и один оператор");
                operator = null;
            }
            if (countOfOperators == 1 && (input.indexOf(operator) == 0 || input.indexOf(operator) == input.length() - 1)) {
                System.out.println("Строка не является математической операцией");
                operator = null;
            }
            return operator;
	}
        public String getArabicOrRoman() {
            twoNumbers = input.split("\\" + operator);
            String[] roman = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
            String[] arabic = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
            int ifRoman = 0;
            int ifArabic = 0;
            for (String number : twoNumbers) {
                for (String var : arabic) {
                    if (number.equals(var)) {
                        ifArabic++;
                    }
                }
            }
            for (String number : twoNumbers) {
                for (String var : roman) {
                    if (number.equals(var)) {
                        ifRoman++;
                    }
                }
            }
            if (ifRoman == 2) {
                arabicOrRoman = "roman";
            }
            else if (ifArabic == 2) {
                arabicOrRoman = "arabic";
            }
            else {
                System.out.println("Используются одновременно разные системы счисления или операнды не подходят под условия задания");
                arabicOrRoman = null;
            }
            return arabicOrRoman;
        }
}
class Calculations {
    int firstNumber;
    int secondNumber;
    int result;
    String resultStr;
    
    public String getResult(String[] twoNumbers, String operator, String typeOfNumbers) {
        ArrayList<String> romanList = new ArrayList<>();
        String[] roman = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        romanList.addAll(Arrays.asList(roman));
        
        ArrayList<String> romanList10 = new ArrayList<>();
        String[] roman10 = {"","X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
        romanList10.addAll(Arrays.asList(roman10));
        
        if (typeOfNumbers.equals("arabic")) {
            firstNumber = Integer.parseInt(twoNumbers[0]);
            secondNumber = Integer.parseInt(twoNumbers[1]);
        }
        if (typeOfNumbers.equals("roman")) {
            firstNumber = romanList.indexOf(twoNumbers[0]);
            secondNumber = romanList.indexOf(twoNumbers[1]);
        }
        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                result = firstNumber / secondNumber;
                break;
        }
        resultStr = Integer.toString(result);
        if (typeOfNumbers.equals("roman")) {
            if (result < 1) {
                System.out.println("В римской системе нет отрицательных чисел");
                resultStr = null;
            }
            else {
                resultStr = romanList10.get(result / 10) + romanList.get(result % 10);
            }
        }
        return resultStr;
    }  
}