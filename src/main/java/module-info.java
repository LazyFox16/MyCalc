module MyCalc {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apfloat;

    exports ru.lazyfox16.mycalc.controller to javafx.fxml;
    exports ru.lazyfox16.mycalc.util to javafx.fxml;
    exports ru.lazyfox16.mycalc to javafx.graphics;
    opens ru.lazyfox16.mycalc.controller to javafx.fxml;
}
