package ru.lazyfox16.mycalc.expressions;

import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.OverflowException;
import ru.lazyfox16.mycalc.exceptions.UnrecognizedTokenException;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public enum OperationToken implements Expression{

    PLUS(Constants.PLUS, Apfloat::add, Precedent.ADD, Associativity.LEFT ),
    MINUS(Constants.MINUS, Apfloat::subtract, Precedent.ADD, Associativity.LEFT),
    MULTIPLICATION(Constants.MULTYPLY, Apfloat::multiply, Precedent.MULT, Associativity.LEFT ),
    DIVISION(Constants.DIVISION, Apfloat::divide, Precedent.MULT, Associativity.LEFT ),
    NEGATE("+-", Apfloat::negate, Precedent.EXP,Associativity.RIGHT),
    REMAINDER("%", Apfloat::mod, Precedent.MULT, Associativity.LEFT),
    POW("^", (x, y) -> ApcomplexMath.pow(x.conj(), y.longValue()).real(), Precedent.EXP, Associativity.RIGHT) {
        @Override
        public OperandToken startPerform(Apfloat left, Apfloat right) {
            checkPow(left, right);
            return super.startPerform(left, right);
        }
    };

    private static final OperationToken[] VALUES = OperationToken.values();
    private static final int MAX_CALCULATE_PRECISION = 10000;

    private final String symbol;
    private final Precedent precedent;
    private final Associativity associativity;

    private  BinaryOperator<Apfloat> binaryOperator;
    private  UnaryOperator<Apfloat> unaryOperator;

    OperationToken(String symbol, UnaryOperator<Apfloat> unaryOperator, Precedent priority, Associativity associativity ) {
        this.unaryOperator = unaryOperator;
        this.precedent = priority;
        this.associativity = associativity;
        this.symbol = symbol;
    }

    OperationToken(String symbol, BinaryOperator<Apfloat> binaryOperator, Precedent priority, Associativity associativity) {
        this.binaryOperator = binaryOperator;
        this.precedent = priority;
        this.associativity = associativity;
        this.symbol = symbol;
    }

    public int getPrecedent() {
        return precedent.getPriority();
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public static OperationToken getTokenType(String literal) {
        for (OperationToken token : VALUES) {
            if (token.symbol.equals(literal)) {
               return token;
            }
        }
        throw new UnrecognizedTokenException("unknown character");
    }

    @Override
    public Type getType() {
        return Type.OPERATION;
    }

    @Override
    public Apfloat getValue() {
       return null;
    }

    public void checkPow(Apfloat left, Apfloat right) {
        if (!isCorrectData(left, right)) {
            throw new IllegalArgumentException("Unsupported data");
        }
    }

    private boolean isCorrectData(Apfloat left, Apfloat right) {
        int leftVal = ApfloatMath.abs(left).toString(true).length();
        int rightVal = ApfloatMath.abs(right).intValue();
        return rightVal <= MAX_CALCULATE_PRECISION / leftVal;
    }

    private Apfloat checkDigitCount(Apfloat apfloat) throws OverflowException {
        if (apfloat.toString(true).length() > MAX_CALCULATE_PRECISION) {
            throw new OverflowException("Overflow");
        }
        return apfloat;
    }

    private enum Precedent {

        ADD(1),
        MULT(2),
        EXP(3);

        private final int priority;

        Precedent(int priority) {
            this.priority = priority;
        }

       public int getPriority() {
           return priority;
       }
   }

    public enum Associativity {

        LEFT,
        RIGHT
    }

    @Override
    public String toString() {
        return symbol;
    }

    public OperandToken startPerform(Apfloat left, Apfloat right) {
        return new OperandToken(checkDigitCount(binaryOperator.apply(left, right)));
    }

    public OperandToken startPerform(Apfloat right) {
        return new OperandToken(checkDigitCount(unaryOperator.apply(right)));
    }
}
