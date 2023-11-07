module com.example.zavrsniprojekat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.zavrsniprojekt to javafx.fxml;
    exports com.example.zavrsniprojekt;
}