package com.example.universitywithyou;

public class User {
    private String id_user;
    private String card_number, first_name, familly_name;
    private String speciality, email, password;
    private Boolean deleg, isDirector, isTeamListening;


    public User() {
    }

    public User(String id_user, String card_number, String first_name, String familly_name, String speciality, String email, String password, Boolean deleg) {

        this.id_user = id_user;
        this.card_number = card_number;
        this.first_name = first_name;
        this.familly_name = familly_name;
        this.speciality = speciality;
        this.email = email;
        this.password = password;
        this.deleg = deleg;
    }

    public User(String id_user, String card_number, String first_name, String familly_name, String speciality, String email, String password, Boolean deleg, Boolean isDirector, Boolean isTeamListening) {
        this.id_user = id_user;
        this.card_number = card_number;
        this.first_name = first_name;
        this.familly_name = familly_name;
        this.speciality = speciality;
        this.email = email;
        this.password = password;
        this.deleg = deleg;
        this.isDirector = isDirector;
        this.isTeamListening = isTeamListening;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFamilly_name() {
        return familly_name;
    }

    public void setFamilly_name(String familly_name) {
        this.familly_name = familly_name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDeleg() {
        return deleg;
    }

    public void setDeleg(Boolean deleg) {
        this.deleg = deleg;
    }

    public Boolean isDirector() {
        return isDirector;
    }

    public void setDirector(Boolean director) {
        isDirector = director;
    }

    public Boolean isTeamListening() {
        return isTeamListening;
    }

    public void setTeamListening(Boolean teamListening) {
        isTeamListening = teamListening;
    }
}
