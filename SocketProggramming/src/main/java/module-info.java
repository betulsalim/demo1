module com.example.socketproggramming {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires AnimateFX;
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires javafx.swing;


    opens com.example.socketproggramming to javafx.fxml;
    exports com.example.socketproggramming;
}