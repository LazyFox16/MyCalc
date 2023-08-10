package ru.lazyfox16.mycalc.interpreter;

import java.util.regex.Pattern;

public class Validator {

    private static final String REGEX_INPUT = "^(?:((?:^|[-+*/%^(])\\d{1,15}" +
                                              "|(?:^|[-+*/%^(])(?=\\d+[.]\\d*(?:$|[-+*/%^)]))[\\d.]{1,16})" +
                                              "|((?<=\\d)[)]*(?!\\d)[-+*/%^]?)|((?:^|[-+*/%^])[(]+[-]??))*$";

    private static final String REGEX_READY = "[-+*/%^(][\\d.)]+$";

    private final Pattern patternInput = Pattern.compile(REGEX_INPUT);
    private final Pattern patternReady = Pattern.compile(REGEX_READY);

    private int left, right;

    public boolean checkInput(String expr) {
        return patternInput.matcher(expr).matches();
    }

    public boolean checkCompletness(String expr) {
        return patternReady.matcher(expr).find();
    }

    public boolean checkBrackets(String expr) {
        return ((right < left) && (patternReady.matcher(expr).find()));
    }

   public String updateBrackets(String expr) {
        left = right = 0;
        for (char c : expr.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                right++;
            }
        }
        return (left > right) ? expr + ")".repeat(Math.max(0, left - right)) : expr;
    }
}
