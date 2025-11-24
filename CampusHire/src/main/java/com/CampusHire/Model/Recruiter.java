package com.CampusHire.Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Recruiter {
    @Id
    private long id;
    private String name;
    private String phoneNumber;
    private String Email;

    private String company;

    public Recruiter(long id, String phoneNumber, String email, String company, String name) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.Email = email;
        this.company = company;
    }

    public Recruiter(RecruiterJson recruiterJson) {
        this.id = recruiterJson.getId();
        this.name = recruiterJson.getName();
        this.phoneNumber = recruiterJson.getPhoneNumber();
        this.Email = recruiterJson.getEmail();
        this.company = recruiterJson.getCompany();
    }

    public Recruiter() {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Recruiter recruiter = (Recruiter) o;
        return id == recruiter.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
