package com.fiuady.home_controlv10.db;

/**
 * Created by owner on 5/19/2017.
 */

public class Cuentas {

    private int id ;
    private int type;
    private String user;
    private String mail;
    private String password;
    private String configuration;

public Cuentas(int id, int type, String user, String mail, String password, String configuration)
{
    this.id = id;
    this.type = type;
    this.user = user;
    this.mail = mail;
    this.password = password;
    this.configuration = configuration;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
