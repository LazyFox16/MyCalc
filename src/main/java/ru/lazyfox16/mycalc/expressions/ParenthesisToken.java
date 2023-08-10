package ru.lazyfox16.mycalc.expressions;

import org.apfloat.Apfloat;
import ru.lazyfox16.mycalc.exceptions.UnrecognizedTokenException;

public enum ParenthesisToken implements Token{

    OPEN(Constants.OPEN_PARENTHESIS),
    CLOSE(Constants.CLOSE_PARENTHESIS);

    private static final ParenthesisToken[] VALUES = ParenthesisToken.values();

    private  final String symbol;

    ParenthesisToken(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public Type getType() {
        return Type.PARENTHESIS;
    }

    @Override
    public Apfloat getValue() {
        return null;
    }

    public static ParenthesisToken getParenthesisType(String literal) {
        for (ParenthesisToken token : VALUES) {
            if (token.symbol.equals(literal)) {
                return token;
            }
        }
        throw new UnrecognizedTokenException("unknown character");
    }
}
