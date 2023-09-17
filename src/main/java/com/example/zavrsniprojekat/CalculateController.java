package com.example.zavrsniprojekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CalculateController implements Initializable {

    @FXML
    TextField txtSpecOtpor, txtSpecOtpor1, txtDuljina, txtRezultat, txtDuljina2, txtSnaga, txtRezultat2;

    @FXML
    ComboBox<String> cmbPovPresjeka, cmbOtporVoda;

    @FXML
    RadioButton rbMonofazni, rbTrofazni;

    ObservableList<String> combo1 = FXCollections.observableArrayList();

    Double povPoprecnog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection con = DBConnector.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM otpornost_voda");

            while (rs.next()) {
                combo1.add(String.valueOf(rs.getInt("broj_zila"))  + " x " + String.valueOf(rs.getDouble("presjek_vodica")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cmbPovPresjeka.setItems(combo1);
        cmbOtporVoda.setItems(combo1);
    }

    @FXML
    public void onOtporChange(ActionEvent event){

        ComboBox comboBox = (ComboBox) event.getSource();
        String str= comboBox.getSelectionModel().getSelectedItem().toString();

        String[] parts = str.split(" x ");

        int br1 = Integer.parseInt(parts[0]);
        double br2 = Double.parseDouble(parts[1]);

        povPoprecnog = br1 * br2;

        try {
            Connection con = DBConnector.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT otpor_vodica_20c FROM otpornost_voda" +
                    " WHERE broj_zila = " + br1 + " AND presjek_vodica = " + br2);

            while (rs.next()) {
                txtSpecOtpor.setText(rs.getString("otpor_vodica_20c"));
                txtSpecOtpor1.setText(rs.getString("otpor_vodica_20c"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @FXML
    public void onOtporVodica(ActionEvent event){

        double rez = Double.parseDouble(txtDuljina.getText()) / povPoprecnog * Double.parseDouble(txtSpecOtpor.getText());

        txtRezultat.setText(String.valueOf(rez));
    }

    @FXML
    public void onPadNapona(ActionEvent event){

        int mnozilac, napon;
        String rez;

        if (rbMonofazni.isSelected()) {
            mnozilac = 2;
            napon = 230*230;
        } else {
            mnozilac = 1;
            napon = 400*400;
        }

        rez = String.valueOf((mnozilac * Double.parseDouble(txtSnaga.getText()) * Double.parseDouble(txtDuljina2.getText()) * Double.parseDouble(txtSpecOtpor1.getText())) / napon * 100) + "%";

        txtRezultat2.setText(rez);

    }

}
