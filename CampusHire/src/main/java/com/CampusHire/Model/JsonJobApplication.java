package com.CampusHire.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonJobApplication {
    @JsonProperty("applicantId")
    private long applicantId;
    @JsonProperty("jobId")
    private int jobId;

    public JsonJobApplication() {
    }

    public JsonJobApplication(int applicantId, int jobId) {
        this.applicantId = applicantId;
        this.jobId = jobId;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(long applicantId) {
        this.applicantId = applicantId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
