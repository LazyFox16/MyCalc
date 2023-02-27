package ru.lazyfox16.mycalc.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import org.apfloat.OverflowException;
import ru.lazyfox16.mycalc.Calculator;
import ru.lazyfox16.mycalc.util.NotFocusTextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    private final Calculator calculator = new Calculator();
    private final List<Button> buttons = new ArrayList<>();

    @FXML
    private NotFocusTextField input, output;

    @FXML
    private Button btnDigit0, btnDigit1, btnDigit2, btnDigit3, btnDigit4,
            btnDigit5, btnDigit6, btnDigit7, btnDigit8, btnDigit9,
            btnPlus, btnMinus, btnMultiply, btnDivide, btnMod, btnPow,
            btnDot, btnBrackets, btnClear, btnDel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnClear.setAccessibleText("\u001B");
        btnDel.setAccessibleText("\u0008");

        input.setCursor(Cursor.DEFAULT);
        output.setCursor(Cursor.DEFAULT);

        buttons.addAll(Arrays.asList(btnDigit0, btnDigit1, btnDigit2, btnDigit3, btnDigit4,
                btnDigit5, btnDigit6, btnDigit7, btnDigit8, btnDigit9,
                btnPlus, btnMinus, btnMultiply, btnDivide, btnMod, btnPow,
                btnDot, btnBrackets, btnClear, btnDel));

        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!calculator.isValidInput(newValue)) {
                input.setText(oldValue);
            } else if (calculator.canEvaluate(newValue)) {
                calc();
            } else {
                output.setText("");
            }
        });
    }

    @FXML
    private void keyTyped(KeyEvent e) {
        for (Button button : buttons) {
            if (button.getAccessibleText().contains(e.getCharacter())) {
                callButton(button);
            }
        }
    }

    @FXML
    private void printSymbol(ActionEvent e) {
        Button button = (Button) e.getSource();
        input.appendText(button.getAccessibleText());
    }

    @FXML
    private void deleteLast() {
        if(!input.getText().isEmpty()) {
            input.deleteText(input.getLength() - 1, input.getLength());
        }
    }

    @FXML
    private void clearFields() {
        input.clear();
        output.clear();
    }

    @FXML
    private void setSmartBrackets() {
        if (calculator.isRightAllowed(input.getText())) {
            input.appendText(")");
        } else {
            input.appendText("(");
        }
    }

    private void callButton(Button button) {
        button.arm();
        button.fire();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(e -> button.disarm());
        pause.play();
    }

    private void calc() {
        try {
            String result = calculator.calculate(input.getText());
            output.setText(result);
        } catch (ArithmeticException | OverflowException | IllegalArgumentException e) {
            output.setText(e.getMessage());
        }
    }
}
