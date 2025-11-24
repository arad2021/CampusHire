package com.CampusHire.Model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Student {
    @Id
    private Long id;
    private String name;
    private String phoneNumber;
    private String Email;
    private String subject;
    private int age;
    private int graduateYear;
    @Embedded
    private StudentTicket ticket;

    public Student(Long id, String name, String phoneNumber, String email, String subject, int age, int graduateYear,
            StudentTicket ticket) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.Email = email;
        this.subject = subject;
        this.age = age;
        this.graduateYear = graduateYear;
        this.ticket = ticket;

    }

    public Student(Long id, String name, String phoneNumber, String email, String subject, int age, int graduateYear) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.Email = email;
        this.subject = subject;
        this.age = age;
        this.graduateYear = graduateYear;
        this.ticket = new StudentTicket();

    }

    public Student(StudentJson studentJson) {
        this.id = studentJson.getId();
        this.name = studentJson.getName();
        this.phoneNumber = studentJson.getPhoneNumber();
        this.Email = studentJson.getEmail();
        this.subject = studentJson.getSubject();
        this.age = studentJson.getAge();
        this.graduateYear = studentJson.getGraduateYear();
        this.ticket = studentJson.getTicket();
    }

    public Student() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSubject() {
        return subject;
    }

    public int getAge() {
        return age;
    }

    public StudentTicket getTicket() {
        return ticket;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTicket(StudentTicket ticket) {
        this.ticket = ticket;
    }

    public int getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(int graduateYear) {
        this.graduateYear = graduateYear;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
