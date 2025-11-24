package com.CampusHire.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketJson {
    @JsonProperty("remote")
    private String remote;
    @JsonProperty("experience")
    private int experience;
    @JsonProperty("jobType")
    private String jobType;
    @JsonProperty("location")
    private String location;
    @JsonProperty("industry")
    private String industry;

    public TicketJson(String remote, int experience, String location, String jobType, String industry) {
        this.remote = remote;
        this.experience = experience;
        this.location = location;
        this.jobType = jobType;
        this.industry = industry;
    }

    public TicketJson() {
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
}
