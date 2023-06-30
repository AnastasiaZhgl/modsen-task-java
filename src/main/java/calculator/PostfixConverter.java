package calculator;

import calculator.token.*;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PostfixConverter {
    public List<Token> convertToPostfix(List<Token> source) {
        List<Token> postfixExpression = new ArrayList<>();
        Deque<Token> operationStack = new LinkedList<>();
        for (Token token : source) {
            switch (token.type()) {
                case NUMBER ->
                    postfixExpression.add(token);
                case OPEN_BRACKET -> operationStack.push(token);
                case CLOSE_BRACKET -> {
                    while (!operationStack.isEmpty() && operationStack.peek().type() != TokenType.OPEN_BRACKET) {
                        postfixExpression.add(operationStack.pop());
                    }

                    if (!operationStack.isEmpty() && operationStack.peek().type() == TokenType.OPEN_BRACKET) {
                        operationStack.pop();
                    } else {
                        throw new RuntimeException("Unbalanced parentheses in the input expression.");
                    }
                }
                case BINARY_OPERATION, UNARY_OPERATION -> {
                    while (!operationStack.isEmpty() && getPriority(operationStack.peek()) >= getPriority(token)) {
                        postfixExpression.add(operationStack.pop());
                    }
                    operationStack.push(token);
                }
            }
        }
        while (!operationStack.isEmpty()) {
            postfixExpression.add(operationStack.pop());
        }
        return postfixExpression;
    }

    private int getPriority(Token token) {
        if (token instanceof BinaryOperationToken operation) {
            return switch (operation.operationType()) {
                case PLUS, MINUS -> 1;
            };
        } else if (token instanceof UnaryOperationToken operation) {
            return switch (operation.operationType()) {
                case TO_DOLLAR, TO_RUBBLE -> 2;
            };
        }
        return 0;
    }
}
