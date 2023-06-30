package calculator.token;

public record UnaryOperationToken(UnaryOperationType operationType) implements Token {
    @Override
    public TokenType type() {
        return TokenType.UNARY_OPERATION;
    }

    @Override
    public TokenType getSymbol() {
        return TokenType.UNARY_OPERATION;
    }

    public String getType(){
        if (operationType.equals(UnaryOperationType.TO_DOLLAR)){
            return "toDollars";
        }else{
            return "toRubles";
        }
    }
}
