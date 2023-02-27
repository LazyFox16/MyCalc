package ru.lazyfox16.mycalc.interpreter;

import ru.lazyfox16.mycalc.expressions.*;

import java.util.*;

public class Interpreter {

    public static Queue<Token> interpretate(List<Token> expression) {

        Deque<Token> operators = new LinkedList<>();
        Queue<Token> outputExpr = new LinkedList<>();

        for (Token token : expression) {
            if (token instanceof OperandToken) {
                outputExpr.add(token);
            } else if (token instanceof OperationToken) {
                if (!operators.isEmpty() && !operators.peekFirst().equals(ParenthesisToken.OPEN)) {
                    OperationToken operator = (OperationToken) token;
                    OperationToken topOperator = (OperationToken) operators.peek();
                    if (operator.getPrecedent() < topOperator.getPrecedent()) {
                        outputExpr.add(operators.pop());
                    } else if (operator.getPrecedent() == topOperator.getPrecedent()) {
                        if (operator.getAssociativity() == OperationToken.Associativity.LEFT) {
                            outputExpr.add(operators.pop());
                        }
                    }
                }
                operators.push(token);
            } else {
                if (token.equals(ParenthesisToken.OPEN)) {
                    operators.push(token);
                } else  {
                    while (!operators.isEmpty() && !operators.peekFirst().equals(ParenthesisToken.OPEN)) {
                        outputExpr.add(operators.pop());
                    }
                    operators.pop();
                }
            }
        }
        while (!operators.isEmpty()) {
            outputExpr.add(operators.pop());
        }
        return outputExpr;
    }
}
