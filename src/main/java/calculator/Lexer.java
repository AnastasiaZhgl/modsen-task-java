package calculator;

import calculator.token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    public List<Token> getTokens(String source) {
        Pattern pattern = Pattern.compile("\\s+|[-+*/(),]|to[Dd]ollars?|to[Rr]ubles?|-?\\d+(\\.\\d+)?[pр]|[+\\$]\\d+(\\.\\d*)?|-?\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(source);
        List<Token> tokens = new ArrayList<>();
        while (matcher.find()) {
            String token = matcher.group().trim();
            if (token.isEmpty()) {
                continue;
            } else if (token.matches("[+$]\\d+(\\.\\d*)?")) {
                TokenType symbol = getTokenSymbol(token.charAt(0));
                double value = Double.parseDouble(token.substring(1));
                tokens.add(new NumberToken(value, symbol));
            } else if (token.matches("-?\\d+(\\.\\d+)?[pр]")) {
                double value = Double.parseDouble(token.substring(0,token.length()-1));
                TokenType symbol = getTokenSymbol(token.charAt(token.length()-1));
                tokens.add(new NumberToken(value, symbol));
            } else {
                switch (token) {
                    case "+" -> tokens.add(new BinaryOperationToken(OperationType.PLUS));
                    case "-" -> tokens.add(new BinaryOperationToken(OperationType.MINUS));
                    case "(" -> tokens.add(new OtherToken(TokenType.OPEN_BRACKET));
                    case ")" -> tokens.add(new OtherToken(TokenType.CLOSE_BRACKET));
                    case "toDollars" -> tokens.add(new UnaryOperationToken(UnaryOperationType.TO_DOLLAR));
                    case "toRubles" -> tokens.add(new UnaryOperationToken(UnaryOperationType.TO_RUBBLE));
                    default -> throw new IllegalArgumentException("Invalid token: " + token);
                }
            }
        }
        return tokens;
    }

    private TokenType getTokenSymbol(char symbol) {
        if (symbol == '$') {
            return TokenType.USD;
        } else if (symbol == 'p' || symbol == 'р') {
            return TokenType.RUB;
        }
        throw new IllegalArgumentException("Invalid token symbol: " + symbol);
    }

}
