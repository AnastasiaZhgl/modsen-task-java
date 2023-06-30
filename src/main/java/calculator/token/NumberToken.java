package calculator.token;

public record NumberToken(double value, TokenType symbol) implements Token{
    @Override
    public TokenType type() {
        return TokenType.NUMBER;
    }

    @Override
    public TokenType getSymbol() {
        return symbol;
    }

    public double getValue(){
        return value;
    }
}
