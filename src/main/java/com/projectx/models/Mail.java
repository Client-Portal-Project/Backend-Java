package com.projectx.models;

import org.springframework.context.annotation.Bean;


public class Mail {
    private String firstname;
    private String sendToEmail;
    private String password;
    @Bean
    public void setFirstname(String firstname)
    {
        this.firstname=firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSendToEmail() {
        return sendToEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setSendToEmail(String sendToEmail) {
        this.sendToEmail = sendToEmail;
    }
}
