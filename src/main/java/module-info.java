module com.example.zavrsniprojekat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.zavrsniprojekat to javafx.fxml;
    exports com.example.zavrsniprojekat;
}