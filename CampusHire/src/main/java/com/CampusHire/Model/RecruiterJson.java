package com.CampusHire.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecruiterJson {

    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("company")
    private String company;

    public RecruiterJson(Long id, String phoneNumber, String name, String email, String company) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        Email = email;
        this.company = company;
    }

    public RecruiterJson() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
