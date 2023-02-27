package ru.lazyfox16.mycalc.interpreter;

import org.apfloat.Apfloat;
import ru.lazyfox16.mycalc.expressions.OperandToken;
import ru.lazyfox16.mycalc.expressions.OperationToken;
import ru.lazyfox16.mycalc.expressions.Token;

import java.util.Queue;
import java.util.Stack;

public class Perfomer {

    private static final int OUTPUT_PRECISION = 20;

    public String perform(Queue<Token> postfixExpr) {
        Stack<Token> operands = new Stack<>();

        while (!postfixExpr.isEmpty()) {
            Token token = postfixExpr.poll();

            if (token instanceof OperandToken) {
                operands.push(token);
            } else {
                OperationToken operator = (OperationToken) token;
                OperandToken right = (OperandToken) operands.pop();
                if (OperationToken.NEGATE.equals(operator)) {
                    operands.push(operator.startPerform(right.getValue()));
                }else {
                    OperandToken left = (OperandToken) operands.pop();
                    operands.push(operator.startPerform(left.getValue(), right.getValue()));
                }
            }
        }
        return format(operands.pop().getValue());
    }

    private String format (Apfloat value) {
        String pretty = value.toString(true);
        return pretty.length() > OUTPUT_PRECISION
                ? value.toString(false)
                : pretty;
    }
}

