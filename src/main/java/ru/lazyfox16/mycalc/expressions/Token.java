package ru.lazyfox16.mycalc.expressions;

import org.apfloat.Apfloat;

public interface Token {

    enum Type {OPERAND, OPERATION, PARENTHESIS}

    Type getType();

    Apfloat getValue();
}
