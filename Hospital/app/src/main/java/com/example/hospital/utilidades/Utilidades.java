package com.example.hospital.utilidades;

public class Utilidades {


    public static final String HOSPITAL_DATABASE = "nueva_database";
    public static final String TABLA_HOSPITAL = "Hospital";
    public static final String CAMPO_ID_HOSPITAL = "ID";
    public static final String CAMPO_name_HOSPITAL = "name";


    public static final String TABLA_DOCTOR = "Doctor";
    public static final String CAMPO_ID_DOCTOR = "Doctor_ID";
    public static final String CAMPO_NAME_DOCTOR = "name";
    public static final String CAMPO_ADDS_DOCTOR = "Address";
    public static final String CAMPO_TEL_DOCTOR = "Tel";
    public static final String CAMPO_HOsp_DOCTOR = "id_hopital";

    public static final String TABLA_PACIENTE = "Patient";
    public static final String CAMPO_ID_PACIENTE = "Patient_ID";
    public static final String CAMPO_NAME_PACIENTE= "name";
    public static final String CAMPO_ADDS_PACIENTE = "Address";
    public static final String CAMPO_TEL_PACIENTE = "Tel";
    public static final String CAMPO_DOC_PACIENTE = "id_doctor";

    public static final String TABLA_BOOKING = "Booking";
    public static final String CAMPO_ID_BOOKING = "Booking_ID";
    public static final String CAMPO_DATE_BOOKING= "entering_date";
    public static final String CAMPO_ROOM_BOOKING= "room_number";
    public static final String CAMPO_BED_BOOKING= "bed_number";
    public static final String CAMPO_PATIENT_BOOKING= "id_patient";
    public static final String CAMPO_HOSP_BOOKING= "id_hospital";

    public static final String SQL_search_hospital= "select * from Hospital where name like ";
    public static final String SQL_search_doctor= "select * from "+TABLA_DOCTOR+" where name like ";
    public static final String SQL_search_paciente= "select * from "+TABLA_PACIENTE+" where name like ";
    public static final String SQL_search_booking= "select * from "+TABLA_BOOKING+" where "+CAMPO_DATE_BOOKING+" like ";


    //    constante de tabla hospital
//    public static final String CREAR_HOSPITAL = "CREATE TABLE if not exists "+TABLA_HOSPITAL+" ("+CAMPO_ID_HOSPITAL+" INTEGER, "+CAMPO_name_HOSPITAL+" TEXT)";
    public static final String CREAR_HOSPITAL = "\n" +
            "create table if not exists "+TABLA_HOSPITAL+"(\n" +
            "  ID integer primary key autoincrement,\n" +
            "  name text NOT NULL\n" +
            ")\n";
    public static final String CREAR_DOCTOR = "create table if not exists Doctor(\n" +
            "  Doctor_ID integer primary key autoincrement,\n" +
            "  name text not null,\n" +
            "  Address text not null,\n" +
            "  Tel text not null,    \n" +
            "  id_hopital integer,\n" +
            "  foreign key(id_hopital) references Hospital(ID)\n" +
            ")";
    public static final String CREAR_PATIENT = "create table if not exists Patient(\n" +
            "  Patient_ID integer primary key autoincrement,\n" +
            "  name text not null,\n" +
            "  Address text not null,\n" +
            "  Tel text not null,    \n" +
            "  id_doctor integer,\n" +
            "  foreign key(id_doctor) references Doctor(Doctor_ID)\n" +
            ")";
    public static final String CREAR_BOOKING = "create table if not exists Booking(\n" +
            "  Booking_ID integer primary key autoincrement,\n" +
            "  entering_date date not null,\n" +
            "  room_number text not null,\n" +
            "  bed_number text not null,    \n" +
            "  id_hospital integer not null,\n" +
            "  id_patient integer not null,\n" +
            "  foreign key(id_hospital) references Hospital(ID),\n" +
            "  foreign key(id_patient) references Patient_ID(Patient_ID)\n" +
            ")";
}
