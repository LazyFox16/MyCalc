package ru.lazyfox16.mycalc.interpreter;

import org.apfloat.Apfloat;
import ru.lazyfox16.mycalc.expressions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private final Pattern pattern;

    private static final String tokenRegex = "\\d+(?:\\.\\d+)?|[*/+\\-%^()]";

    public Tokenizer() {
       this.pattern = Pattern.compile(tokenRegex);
    }

    public List<Token> getTokens(String infixExpression) {

        List<Token> tokens = new LinkedList<>();
        Matcher expression = pattern.matcher(infixExpression);

        while (expression.find()) {
            String token = expression.group();

            if (isNumeric(token)) {
                tokens.add(new OperandToken(token));
            } else if (token.equals(Constants.OPEN_PARENTHESIS) || token.equals(Constants.CLOSE_PARENTHESIS)) {
                tokens.add(ParenthesisToken.getParenthesisType(token));
            } else {
                OperationToken operation = OperationToken.getTokenType(token);
                if (operation.equals(OperationToken.MINUS) && tokens.lastIndexOf(ParenthesisToken.OPEN) == tokens.size() - 1) {
                    operation = OperationToken.NEGATE;
                }
                tokens.add(operation);
            }
        }
        return tokens;
    }

    private boolean isNumeric(String s) {
        try {
            Apfloat number = new Apfloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}





