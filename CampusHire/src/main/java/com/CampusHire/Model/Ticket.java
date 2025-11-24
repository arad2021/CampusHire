package com.CampusHire.Model;

import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public abstract class Ticket {
    private String remote;
    private int experience;
    private String jobType;
    private String location;
    private String industry;

    public Ticket(String remote, int experience, String location, String jobType, String industry) {
        this.remote = remote;
        this.experience = experience;
        this.location = location;
        this.jobType = jobType;
        this.industry = industry;
    }

    public Ticket() {
    }

    public Ticket(TicketJson ticketJson) {
        this.remote = ticketJson.getRemote();
        this.experience = ticketJson.getExperience();
        this.location = ticketJson.getLocation();
        this.jobType = ticketJson.getJobType();
        this.industry = ticketJson.getIndustry();
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Ticket ticket = (Ticket) o;
        return experience <= ticket.experience && Objects.equals(remote, ticket.remote)
                && Objects.equals(jobType, ticket.jobType) && Objects.equals(location, ticket.location)
                && Objects.equals(industry, ticket.industry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remote, experience, jobType, location, industry);
    }
}
