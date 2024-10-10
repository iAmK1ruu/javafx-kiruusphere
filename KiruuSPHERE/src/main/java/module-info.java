module com.kiruu.kiruusphere {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;


    opens com.kiruu.kiruusphere to javafx.fxml;
    exports com.kiruu.kiruusphere;
}