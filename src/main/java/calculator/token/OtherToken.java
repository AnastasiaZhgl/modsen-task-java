package calculator.token;

public record OtherToken(TokenType type) implements Token {
    @Override
    public TokenType getSymbol() {
        if (type.equals(TokenType.USD)){
            return TokenType.valueOf("$");
        }else{
            return TokenType.valueOf("p");
        }
    }
}
