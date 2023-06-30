package calculator;

import calculator.token.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class StackMachine {
    public double evaluate(List<Token> postfixExpression, double rate) {
        Deque<NumberToken> valueStack = new LinkedList<>();
        TokenType currentCurrency = null;

        for (Token token : postfixExpression) {
             if (token instanceof NumberToken number) {
//                valueStack.push(new NumberToken(number.getValue(),number.getSymbol()));
                valueStack.push(number);
            } else if (token instanceof BinaryOperationToken operation) {
                var right = valueStack.pop();
                var left = valueStack.pop();
                // Ensure the operands have the same currency type before performing the operation
                if (left.getSymbol() != right.getSymbol()) {
                    throw new RuntimeException("Invalid operation: operands have different currency types.");
                }
                var result = switch (operation.operationType()) {
                    case PLUS -> left.getValue() + right.getValue();
                    case MINUS -> left.getValue() - right.getValue();
                    default -> throw new IllegalStateException("Unexpected value: " + operation.operationType());
                };
                valueStack.push(new NumberToken(result,left.getSymbol()));
             } else if (token instanceof UnaryOperationToken operation) {
                var operand = valueStack.pop();
                if (operand.getSymbol() == null) {
                    throw new RuntimeException("Invalid operation: operand has a different currency type.");
                }
                var result = switch (operation.operationType()) {
                    case TO_DOLLAR -> {
                        currentCurrency = TokenType.RUB;
                        if (rate == 0) {
                            throw new RuntimeException("Divide by zero");
                        }
                        yield operand.getValue() / rate;
                    }
                    case TO_RUBBLE -> {
                        currentCurrency = TokenType.USD;
                       yield  operand.getValue() * rate;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + operation.operationType());
                };

                 TokenType currencySymbol;
                 if (currentCurrency == TokenType.USD) {
                     currencySymbol = TokenType.RUB;
                 } else if (currentCurrency == TokenType.RUB) {
                     currencySymbol = TokenType.USD;
                 } else {
                     throw new RuntimeException("Invalid currency type");
                 }

                valueStack.push(new NumberToken(result,currencySymbol));
            }
        }
        if (valueStack.size() != 1) {
            throw new RuntimeException("Invalid expression");
        }
        return valueStack.pop().getValue();
    }
}