package com.CampusHire.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentJson {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("age")
    private int age;
    @JsonProperty("graduateYear")
    private int graduateYear;
    @JsonProperty("ticket")
    private StudentTicket ticket;

    public StudentJson(Long id, String name, String phoneNumber, String subject, String email, int age,
            int graduateYear) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
        Email = email;
        this.age = age;
        this.graduateYear = graduateYear;
    }

    public StudentJson() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSubject() {
        return subject;
    }

    public int getAge() {
        return age;
    }

    public int getGraduateYear() {
        return graduateYear;
    }

    public StudentTicket getTicket() {
        return ticket;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGraduateYear(int graduateYear) {
        this.graduateYear = graduateYear;
    }

    public void setTicket(StudentTicket ticket) {
        this.ticket = ticket;
    }
}
