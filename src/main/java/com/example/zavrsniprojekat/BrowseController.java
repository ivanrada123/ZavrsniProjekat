package com.example.zavrsniprojekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.SQLException;

public class BrowseController implements Initializable {

    @FXML
    TableView<Elements> tblElementi;

    @FXML
    TableColumn<Elements, String> colTip, colNaziv, colCijena, colProizvodac, colOpis;

    @FXML
    TextField txtNaziv, txtProizvodac, txtCod, txtCdo, txtOpis, txtSB, txtNaziv2, txtProizvodac2, txtC2, txtVar1, txtVar2, txtVar3, txtVar4;

    @FXML
    Label lbl1, lbl2, lbl3, lbl4, lblID;

    @FXML
    ComboBox<String> cmbTip, cmbTip2;

    ObservableList<Elements> obe = FXCollections.observableArrayList();
    ObservableList<String> combo = FXCollections.observableArrayList();
    String table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Inicijalizacija kolona tabele - priprema za prikaz podataka

        colTip.setCellValueFactory(new PropertyValueFactory<>("Tip"));
        colNaziv.setCellValueFactory(new PropertyValueFactory<>("Naziv"));
        colCijena.setCellValueFactory(new PropertyValueFactory<>("Cijena"));
        colProizvodac.setCellValueFactory(new PropertyValueFactory<>("Proizvodac"));
        colOpis.setCellValueFactory(new PropertyValueFactory<>("Opis"));

        // Popunjavanje comboboxova na formi

        combo.add("Kondenzatori");
        combo.add("Otpornici");
        combo.add("Kablovi");
        combo.add("Transformatori");
        combo.add("Osiguraci");
        combo.add("RasvjetnaTijela");
        combo.add("Vodici");
        combo.add("Ostalo");
        combo.add("Sve");

        cmbTip.setItems(combo);
        cmbTip.getSelectionModel().selectLast();

        cmbTip2.setItems(combo);
        cmbTip2.getSelectionModel().selectFirst();

    }

    // Funkcija koja služi za pretraživanje elemenata po zadatim parametrima ili bez paramatera (Sve)
    @FXML
    public void onPretraga(ActionEvent event){

        tblElementi.getItems().clear();

        String query = "SELECT * FROM elementi";
        int brojac = 0;

        if (!cmbTip.getSelectionModel().getSelectedItem().equals("Sve") || !txtNaziv.getText().isEmpty() || !txtProizvodac.getText().isEmpty() || !txtCdo.getText().isEmpty() || !txtCod.getText().isEmpty()) {
            query += " WHERE";
        }

        if (!cmbTip.getSelectionModel().getSelectedItem().equals("Sve")) {
            query += " tip='" + cmbTip.getSelectionModel().getSelectedItem() + "'";
            brojac += 1;
        }

        if (!txtNaziv.getText().isEmpty()) {
            if (brojac>0) {
                query += " AND";
            }
            query += " naziv LIKE '%" + txtNaziv.getText() + "%'";
            brojac += 1;
        }

        if (!txtProizvodac.getText().isEmpty()) {
            if (brojac>0) {
                query += " AND";
            }
            query += " proizvodac LIKE '%" + txtProizvodac.getText() + "%'";
            brojac += 1;
        }

        if (!txtCdo.getText().isEmpty()) {
            if (brojac>0) {
                query += " AND";
            }
            query += " cijena<=" + txtCdo.getText();
            brojac += 1;
        }

        if (!txtCod.getText().isEmpty()) {
            if (brojac>0) {
                query += " AND";
            }
            query += " cijena>=" + txtCod.getText();
        }

        //Otvaranje konekcije i smještanje podataka u "ObservableList" nakon čega se popunjava tabela
        try {
            Connection con = DBConnector.getConnection();

            ResultSet rs = con.createStatement().executeQuery(query);

            while (rs.next()) {
                obe.add(new Elements(rs.getString("ID"), rs.getString("Tip"), rs.getString("Naziv"), rs.getString("Cijena"), rs.getString("Proizvodac"), rs.getString("SerijskiBroj"), rs.getString("Opis")));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        tblElementi.setItems(obe);
    }

    //Funkcija za selekciju elemenata iz GUI tabele i prikazivanje dodatnih podataka o tim elementima
    @FXML
    private void onSelectElement(MouseEvent event) {
        Elements element = tblElementi.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(element.getID());
        table = element.getTip();

        System.out.print(id + " " + table);

        String query = "SELECT * FROM " + table + " WHERE ElementiID=" + id;

        try {
            Connection con = DBConnector.getConnection();

            ResultSet rs = con.createStatement().executeQuery(query);

            while (rs.next()) {
                lblID.setText(rs.getString("ElementiID"));
                cmbTip2.getSelectionModel().select(element.getTip());
                txtNaziv2.setText(element.getNaziv());
                txtProizvodac2.setText(element.getProizvodac());
                txtOpis.setText(element.getOpis());
                txtC2.setText(element.getCijena());
                txtSB.setText(element.getSerijskiBroj());

                labelSwitcher();

                // Različite tabele (različiti elementi) imaju različite podatke, stoga Switch
                switch (table) {
                    case "Kablovi":
                        txtVar1.setText(rs.getString("TipKabela"));
                        txtVar2.setText(rs.getString("BrojZica"));
                        txtVar3.setText(rs.getString("Materijal"));
                        txtVar4.setText(rs.getString("Izolacija"));
                        break;
                    case "Kondenzatori":
                        txtVar1.setText(rs.getString("TipKondenzatora"));
                        txtVar2.setText(rs.getString("Kapacitet"));
                        txtVar3.setText("");
                        txtVar4.setText("");
                        break;
                    case "Osiguraci":
                        txtVar1.setText(rs.getString("TipOsiguraca"));
                        txtVar2.setText(rs.getString("Napon"));
                        txtVar3.setText(rs.getString("StrujaPrekida"));
                        txtVar4.setText("");
                        break;
                    case "Otpornici":
                        txtVar1.setText(rs.getString("TipOtpornika"));
                        txtVar2.setText(rs.getString("Otpornost"));
                        txtVar3.setText("");
                        txtVar4.setText("");
                        break;
                    case "Rastavljaci":
                        txtVar1.setText(rs.getString("TipRastavljaca"));
                        txtVar2.setText(rs.getString("Napon"));
                        txtVar3.setText(rs.getString("StrujaPrekid"));
                        txtVar4.setText("");
                        break;
                    case "Rasvjetna":
                        txtVar1.setText(rs.getString("TipRasTijela"));
                        txtVar2.setText(rs.getString("Jačina"));
                        txtVar3.setText("");
                        txtVar4.setText("");
                        break;
                    case "Vodici":
                        txtVar1.setText(rs.getString("TipVodica"));
                        txtVar2.setText(rs.getString("Promjer"));
                        txtVar3.setText(rs.getString("Materijal"));
                        txtVar4.setText("");
                        break;
                    case "Ostalo":
                        txtVar1.setText(rs.getString("TipOstalo"));
                        txtVar2.setText("");
                        txtVar3.setText("");
                        txtVar4.setText("");
                        break;
                    default:
                        return;
                }

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Funkcija za unos novih podataka u bazu
    @FXML
    private void onUnos(ActionEvent event) {
        if(cmbTip2.getSelectionModel().getSelectedItem()!="Sve" && txtNaziv2.getText().length()>0 && txtC2.getText().length()>0 && txtVar1.getText().length()>0) {
            System.out.println("Radi");
            System.out.println(txtNaziv2.getText().length());

            String query = "";
            lblID.setText("");

            switch (table) {
                case "Kablovi":
                    query = "CALL InsertKabel('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "', '" + txtVar2.getText() + "', '" + txtVar3.getText() + "', '" + txtVar4.getText() + "');";
                    break;
                case "Kondenzatori":
                    query = "CALL InsertKondenzator('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "', '" + txtVar2.getText() + "');";
                    break;
                case "Osiguraci":
                    query = "CALL InsertOsigurac('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "', '" + txtVar2.getText() + "', '" + txtVar3.getText() + "');";
                    break;
                case "Otpornici":
                    query = "CALL InsertOtpornik('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "', '" + txtVar2.getText() + "');";
                    break;
                case "Rastavljaci":
                    query = "CALL InsertRastavljac('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "', '" + txtVar2.getText() + "', '" + txtVar3.getText() + "');";
                    break;
                case "RasvjetnaTijela":
                    query = "CALL InsertRasvjetnoTijelo('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "', '" + txtVar2.getText() + "');";
                    break;
                case "Vodici":
                    query = "CALL InsertVodic('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "', '" + txtVar2.getText() + "', '" + txtVar3.getText() + "');";
                    break;
                case "Transformatori":
                    query = "CALL InsertTransformator('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "', '" + txtVar2.getText() + "', '" + txtVar3.getText() + "', '" + txtVar4.getText() + "');";
                    break;
                case "Ostalo":
                    query = "CALL InsertOstalo('" + cmbTip2.getSelectionModel().getSelectedItem() + "', '" + txtNaziv2.getText() + "', " + txtC2.getText() + ", '" + txtProizvodac2.getText() + "', '" + txtSB.getText() + "', '" + txtOpis.getText() + "', '" + txtVar1.getText() + "');";
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Obavijest!");
                    alert.setContentText("Greška! Provjerite podatke.");
            }

            try {
                Connection con = DBConnector.getConnection();

                con.createStatement().executeUpdate(query);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Obavijest!");
                alert.setHeaderText("Provjerite jeste li unijeli sve obavezne podatke!");
                alert.showAndWait();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Obavijest!");
            alert.setHeaderText("Provjerite jeste li unijeli sve obavezne podatke!");
            alert.showAndWait();
        }
    }

    // Na promjenu tipa u desnom panelu može se pretpostaviti da se želi unijeti novi artikal
    @FXML
    private void onTipPromjena(ActionEvent event){
        if (cmbTip2.getSelectionModel().getSelectedItem() == "Sve") {
            cmbTip2.getSelectionModel().selectFirst();
        }
        table = cmbTip2.getSelectionModel().getSelectedItem();
        txtNaziv2.setText("");
        txtProizvodac2.setText("");
        txtSB.setText("");
        txtC2.setText("");
        txtOpis.setText("");
        txtVar1.setText("");
        txtVar2.setText("");
        txtVar3.setText("");
        txtVar4.setText("");
        labelSwitcher();
    }

    // Funkcija za brisanje iz baze (prvo briše child element u bazi zatim parent u glavnoj tabeli)
    @FXML
    private void onBrisi(ActionEvent event) {
        System.out.println(table);
        String query = "DELETE FROM " + table + " WHERE ElementiID = " + lblID.getText();
        System.out.println(query);
        Boolean b = false;
        try {
            Connection con = DBConnector.getConnection();

            con.createStatement().executeUpdate(query);

            b = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (b) {
            query = "DELETE FROM Elementi WHERE ID = " + lblID.getText();
            try {
                Connection con = DBConnector.getConnection();

                con.createStatement().executeUpdate(query);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // Funkcija koja reguliše sadržaj promjenjivih labela na desnom panelu
    public void labelSwitcher() {
        switch (table) {
            case "Kablovi":
                lbl1.setText("Tip kabela:");
                lbl2.setText("*Broj zica:");
                lbl3.setText("*Materijal:");
                lbl4.setText("*Izolacija:");
                break;
            case "Kondenzatori":
                lbl1.setText("Tip kondenzatora:");
                lbl2.setText("*Kapacitet:");
                lbl3.setText("");
                lbl4.setText("");
                break;
            case "Osiguraci":
                lbl1.setText("Tip osigurača:");
                lbl2.setText("*Napon:");
                lbl3.setText("*Struja prekida:");
                lbl4.setText("");
                break;
            case "Otpornici":
                lbl1.setText("Tip otpornika:");
                lbl2.setText("*Otpornost:");
                lbl3.setText("");
                lbl4.setText("");
                break;
            case "Rastavljaci":
                lbl1.setText("Tip rastavljača:");
                lbl2.setText("*Napon:");
                lbl3.setText("*Struja prekida:");
                lbl4.setText("");
                break;
            case "RasvjetnaTijela":
                lbl1.setText("Tip rasvjetnog tijela:");
                lbl2.setText("*Jačina:");
                lbl3.setText("");
                lbl4.setText("");
                break;
            case "Vodici":
                lbl1.setText("Tip vodiča:");
                lbl2.setText("*Promjer:");
                lbl3.setText("*Materijal:");
                lbl4.setText("");
                break;
            case "Transformatori":
                lbl1.setText("Tip transformatora:");
                lbl2.setText("*Snaga:");
                lbl3.setText("*Efikasnost:");
                lbl4.setText("*Gubitak energije:");
                break;
            case "Ostalo":
                lbl1.setText("Tip ostalo");
                lbl2.setText("");
                lbl3.setText("");
                lbl4.setText("");
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Obavijest!");
                alert.setContentText("Greška! Provjerite podatke.");
        }
    }

    // Čisti podatke pretraživanja
    @FXML
    private void onClearS(ActionEvent event) {
        cmbTip.getSelectionModel().selectLast();
        txtNaziv.setText("");
        txtProizvodac.setText("");
        txtCod.setText("");
        txtCdo.setText("");

        tblElementi.getSelectionModel().clearSelection();
    }

    // Čisti podatke desnog panela
    @FXML
    private void onClearN(ActionEvent event){
        cmbTip2.getSelectionModel().selectFirst();
        txtNaziv2.setText("");
        txtProizvodac2.setText("");
        txtC2.setText("");
        lblID.setText("");
        txtSB.setText("");
        txtOpis.setText("");
        txtVar1.setText("");
        txtVar2.setText("");
        txtVar3.setText("");
        txtVar4.setText("");

        labelSwitcher();

        tblElementi.getSelectionModel().clearSelection();
    }
}
