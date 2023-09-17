package com.example.zavrsniprojekat;

public class ComboOtpori {

    int broj_zila;
    float presjek_vodica, otpor_vodica_20c;


    public ComboOtpori(int broj_zila, float presjek_vodica, float otpor_vodica_20c) {
        this.broj_zila = broj_zila;
        this.presjek_vodica = presjek_vodica;
        this.otpor_vodica_20c = otpor_vodica_20c;
    }


    public int getBroj_zila() {
        return broj_zila;
    }

    public void setBroj_zila(int broj_zila) {
        this.broj_zila = broj_zila;
    }

    public float getPresjek_vodica() {
        return presjek_vodica;
    }

    public void setPresjek_vodica(float presjek_vodica) {
        this.presjek_vodica = presjek_vodica;
    }

    public float getOtpor_vodica_20c() {
        return otpor_vodica_20c;
    }

    public void setOtpor_vodica_20c(float otpor_vodica_20c) {
        this.otpor_vodica_20c = otpor_vodica_20c;
    }

}
