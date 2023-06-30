package calculator.token;

public record BinaryOperationToken(OperationType operationType) implements Token {

    @Override
    public TokenType type() {
        return TokenType.BINARY_OPERATION;
    }

    @Override
    public TokenType getSymbol() {
        if (operationType.equals(OperationType.PLUS)){
            return TokenType.valueOf("+");
        }else{
            return TokenType.valueOf("-");
        }
    }
}
