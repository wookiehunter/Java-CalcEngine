package com.xenos.calculator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        double[] leftVals = {100.00d, 200.0d, 10.00d, 100.0d};
        double[] rightVals = {25.0d, 21.0d, 65.0d, 10.0d};
        char[] opCodes = {'p', 's', 'm', 'd'};
        double[] results = new double[opCodes.length];

        if (args.length == 0) {
            for (int i = 0; i < opCodes.length; i++) {
                results[i] = execute(opCodes[i], leftVals[i], rightVals[i]);
                System.out.println("Your result of " + leftVals[i] + " " + opCodes[i] + " " + rightVals[i] + " is " + results[i]);
            }
        } else if (args.length == 1 && args[0].equals("interactive")) {
            executeInteractively();
        } else if (args.length == 3) {
            handleCommandLine(args);
        } else System.out.println("Please provide an operation code and 2 numeric values");
    }

    static void executeInteractively() {
        System.out.println("Enter 2 numbers and an operator: ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String[] parts = userInput.split(" ");
        performOperation(parts);
    }

    private static void performOperation(String[] parts) {
        char opCode = opCodeFromString(parts[0]);
        if(opCode == 'w')
            handleWhen(parts);
        else {
            double leftVal = valueFromWord(parts[1]);
            double rightVal = valueFromWord(parts[2]);
            double result = execute(opCode, leftVal, rightVal);
            displayResult(opCode, leftVal, rightVal, result);   
        }
    }

    private static void handleWhen(String[] parts) {
        LocalDate startDate = LocalDate.parse(parts[1]);
        long daysToAdd = (long) valueFromWord(parts[2]);
        LocalDate newDate = startDate.plusDays(daysToAdd);
        DateTimeFormatter ukDateFormat = DateTimeFormatter.ofPattern("dd-MM-yy");

        String output = String.format("%s plus %d days is %s", startDate.format(ukDateFormat), daysToAdd, newDate.format(ukDateFormat));
        System.out.println(output);
    }

    private static void displayResult(char opCode, double leftVal, double rightVal, double result) {
        char symbol = symbolFromOpCode(opCode);

        String output = String.format("%.3f %c %.3f = %.3f", leftVal,symbol,rightVal,result);
        System.out.println(output);
    }

    private static char symbolFromOpCode(char opCode) {
        char[] opCodes = {'p', 's', 'm', 'd'};
        char[] symbols = {'+', '-', '*', '/'};

        char symbol = ' ';

        for(int index = 0; index < opCodes.length; index++) {
            if (opCode == opCodes[index]) {
                    symbol = symbols[index];
                    break;
            }
        }
        return symbol;
    }

    private static void handleCommandLine(String[] args) {
        char opCode = args[0].charAt(0);
        double leftVal = Double.parseDouble(args[1]);
        double rightVal = Double.parseDouble(args[2]);

        double result = execute(opCode, leftVal, rightVal);
//        System.out.println("Your result of " + leftVal + " " + opCode + " " + rightVal + " is " + result);
    }

    static double execute(char opCode, double leftVal, double rightVal) {
        double result;

        switch (opCode) {
            case 'p' -> result = leftVal + rightVal;
            case 's' -> result = leftVal - rightVal;
            case 'm' -> result = leftVal * rightVal;
            case 'd' -> result = leftVal / rightVal;
            default -> {
                System.out.println("Invalid OpCode");
                result = 0.0d;
            }
        }
        return result;
    }

    static char opCodeFromString(String operationName) {
        return operationName.charAt(0);
    }

    static double valueFromWord(String  word) {
       String[] numberWords = {
               "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
        };
       double value = -1d;

       for(int index = 0; index < numberWords.length; index++) {
           if (word.equals(numberWords[index])) {
               value = index;
               break;
           }
       }
       if (value == -1)
           value = Double.parseDouble(word);

       return value;
    }
}