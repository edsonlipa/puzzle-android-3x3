package com.example.hospital.entidades;

import android.location.Address;

public class Doctor {
    public int Doctor_ID ;
    String name ;
    String Address ;
    String Tel;
    int id_hopital;
    public Doctor(int doctor_ID, String name, String address, String tel, int id_hopital) {
        Doctor_ID = doctor_ID;
        this.name = name;
        Address = address;
        Tel = tel;
        this.id_hopital = id_hopital;
    }
    public Doctor() {
    }
    public int getDoctor_ID() {
        return Doctor_ID;
    }

    public void setDoctor_ID(int doctor_ID) {
        Doctor_ID = doctor_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public int getId_hopital() {
        return id_hopital;
    }

    public void setId_hopital(int id_hopital) {
        this.id_hopital = id_hopital;
    }


}
