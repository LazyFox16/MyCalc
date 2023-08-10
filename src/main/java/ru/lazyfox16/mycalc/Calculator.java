package ru.lazyfox16.mycalc;

import org.apfloat.OverflowException;
import ru.lazyfox16.mycalc.expressions.Token;
import ru.lazyfox16.mycalc.interpreter.Perfomer;
import ru.lazyfox16.mycalc.interpreter.Interpreter;
import ru.lazyfox16.mycalc.interpreter.Tokenizer;
import ru.lazyfox16.mycalc.interpreter.Validator;

import java.util.List;
import java.util.Queue;

public class Calculator {

    private final Tokenizer tokenizer;
    private final Perfomer perfomer;
    private final Validator validator;

    public Calculator() {
        this.tokenizer = new Tokenizer();
        this.perfomer = new Perfomer();
        this.validator = new Validator();
    }

    public String calculate(String infixExpression) {

        String infix = validator.updateBrackets(infixExpression);
        List<Token> tokens = tokenizer.getTokens(infix);
        Queue<Token> postfixExpr = Interpreter.interpretate(tokens);
        return perfomer.perform(postfixExpr);
    }

    public boolean isRightAllowed(String expr) { // проверяем наличие правой скобки
        return validator.checkBrackets(expr);
    }

    public boolean isValidInput(String expr) {
        return validator.checkInput(expr);
    }

    public boolean canEvaluate(String expr) {
        return validator.checkCompletness(expr);
    }
}
