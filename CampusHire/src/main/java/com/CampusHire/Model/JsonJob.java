package com.CampusHire.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonJob {

    @JsonProperty("company")
    private String company;
    @JsonProperty("name")
    private String name;
    @JsonProperty("recruiterId")
    private long recruiterId;
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

    public JsonJob() {
    }

    public JsonJob(String company, String name, String industry, String location, String jobType, int experience,
            String remote, long recruiterId) {
        this.company = company;
        this.name = name;
        this.industry = industry;
        this.location = location;
        this.jobType = jobType;
        this.experience = experience;
        this.remote = remote;
        this.recruiterId = recruiterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
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

    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }
}
