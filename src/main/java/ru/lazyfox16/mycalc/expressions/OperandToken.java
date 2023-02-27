package ru.lazyfox16.mycalc.expressions;

import org.apfloat.Apfloat;

public class OperandToken implements Token {

    private final static int INPUT_PRECISION = 15;

    private final Apfloat value;

    public OperandToken(String val) {
        this.value = new Apfloat(val, INPUT_PRECISION);
    }

    public OperandToken(Apfloat val) {
        this.value = val;
    }

    @Override
    public Type getType() {
        return Type.OPERAND;
    }

    @Override
    public Apfloat getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
