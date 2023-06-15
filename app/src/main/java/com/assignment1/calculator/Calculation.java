package com.assignment1.calculator;

import java.util.ArrayList;

public class Calculation {

    public static float evaluateExpression(ArrayList<String> expression) {
        // Evaluate the mathematical expression based on the PEMDAS rule
        // Perform multiplication and division operations first, then addition and subtraction

        ArrayList<String> expressionCopy = new ArrayList<>(expression);

        // Perform multiplication and division operations
        performMultiplicationAndDivision(expressionCopy);

        // Perform addition and subtraction operations
        return performAdditionAndSubtraction(expressionCopy);
    }

    private static void performMultiplicationAndDivision(ArrayList<String> expression) {
        int index = 0;
        while (index < expression.size()) {
            String token = expression.get(index);
            if (token.equals("*") || token.equals("/")) {
                float operand1 = Float.parseFloat(expression.get(index - 1));
                float operand2 = Float.parseFloat(expression.get(index + 1));
                float result = 0;

                if (token.equals("*")) {
                    result = operand1 * operand2;
                } else if (token.equals("/")) {
                    result = operand1 / operand2;
                }

                expression.remove(index - 1); // Remove the first operand
                expression.remove(index - 1); // Remove the operator
                expression.set(index - 1, String.valueOf(result)); // Replace the result

                index -= 2; // Update the index to account for the removed elements
            }

            index++;
        }
    }

    private static float performAdditionAndSubtraction(ArrayList<String> expression) {
        float result = Float.parseFloat(expression.get(0));

        for (int i = 1; i < expression.size(); i += 2) {
            String operator = expression.get(i);
            float operand = Float.parseFloat(expression.get(i + 1));

            if (operator.equals("+")) {
                result += operand;
            } else if (operator.equals("-")) {
                result -= operand;
            }
        }

        return result;
    }

    public static boolean isValidExpression(ArrayList<String> expression) {
        // Check whether the mathematical expression is valid based on the provided requirements
        // - No consecutive operators
        // - No operators at the beginning or end

        // Check for consecutive operators
        for (int i = 0; i < expression.size() - 1; i++) {
            String currentToken = expression.get(i);
            String nextToken = expression.get(i + 1);

            if (isOperator(currentToken) && isOperator(nextToken)) {
                return false;
            }
        }

        // Check for operators at the beginning or end
        String firstToken = expression.get(0);
        String lastToken = expression.get(expression.size() - 1);

        if (isOperator(firstToken) || isOperator(lastToken)) {
            return false;
        }

        return true;
    }

    private static boolean isOperator(String token) {
        // Check if a token is an operator
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }
}
