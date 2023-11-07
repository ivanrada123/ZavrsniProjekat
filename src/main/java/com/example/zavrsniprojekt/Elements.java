package com.example.zavrsniprojekt;

public class Elements {

    //Klasa koja se koristi za kreiranje objekata koji trebaju da se prikažu na GUI tabeli

    String ID, Tip, Naziv, Cijena, Proizvodac, SerijskiBroj, Opis;

    public Elements(String ID, String tip, String naziv, String cijena, String proizvodac, String serijskiBroj, String opis) {
        this.ID = ID;
        this.Tip = tip;
        this.Naziv = naziv;
        this.Cijena = cijena;
        this.Proizvodac = proizvodac;
        this.SerijskiBroj = serijskiBroj;
        this.Opis = opis;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String tip) {
        Tip = tip;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public String getCijena() {
        return Cijena;
    }

    public void setCijena(String cijena) {
        Cijena = cijena;
    }

    public String getProizvodac() {
        return Proizvodac;
    }

    public void setProizvodac(String proizvodac) {
        Proizvodac = proizvodac;
    }

    public String getSerijskiBroj() {
        return SerijskiBroj;
    }

    public void setSerijskiBroj(String serijskiBroj) {
        SerijskiBroj = serijskiBroj;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }
}
