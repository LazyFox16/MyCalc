package ru.lazyfox16.mycalc.util;

import javafx.scene.control.TextField;

public class NotFocusTextField extends TextField {

    @Override
    public void requestFocus() {}
}
