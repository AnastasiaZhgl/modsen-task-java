package calculator;

import calculator.token.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

public class Calculator {

    private final Lexer lexer = new Lexer();
    private final PostfixConverter converter = new PostfixConverter();
    private final StackMachine stackMachine = new StackMachine();

    public String calculate(String expression) {
        Properties config = loadConfig();
        double rate = Double.parseDouble(config.getProperty("rate"));
        List<Token> tokens = lexer.getTokens(expression);
        var postfixExpression = converter.convertToPostfix(tokens);
        double result = stackMachine.evaluate(postfixExpression, rate);
        String currencySymbol = getCurrencySymbol(tokens);
        String formattedResult = formatResult(result, currencySymbol);
        System.out.println(formattedResult);
        return formattedResult;
    }

    private String getCurrencySymbol(List<Token> tokens) {
        Token token = tokens.get(0);
        if (token instanceof NumberToken numberToken) {
            return numberToken.getSymbol().toString();
        } else if (token instanceof UnaryOperationToken unaryOperationToken) {
            return unaryOperationToken.getType();
        } else {
            throw new IllegalArgumentException("Invalid token type: " + token.getClass().getSimpleName());
        }
    }

    private String formatResult(double result, String currencySymbol) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedResult = decimalFormat.format(result);
        if (currencySymbol.equals("USD")||currencySymbol.equals("toDollars")) {
            return "Результат: $" + formattedResult;
        } else if (currencySymbol.equals("RUB")||currencySymbol.equals("toRubles")) {
            return "Результат: " + formattedResult + "p";
        } else {
            throw new RuntimeException("Invalid currency symbol");
        }
    }

    private static Properties loadConfig() {
        Properties config = new Properties();
        try (InputStream input = Calculator.class.getClassLoader().getResourceAsStream("rate.properties")) {
            if (input != null) {
                config.load(input);
            } else {
                throw new RuntimeException("Can't find rate.properties file");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading rate.properties file", e);
        }
        return config;
    }
}
