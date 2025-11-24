package com.CampusHire.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;
    private long applicantId;
    private int jobId;
    private boolean closed;
    private String accepted;

    public JobApplication(long applicantId, int jobId) {
        this.applicantId = applicantId;
        this.jobId = jobId;
        this.closed = false;
        this.accepted = "pending";

    }

    public JobApplication(JsonJobApplication jsonJobApplication) {
        this.applicantId = jsonJobApplication.getApplicantId();
        this.jobId = jsonJobApplication.getJobId();
        this.closed = false;
        this.accepted = "pending";

    }

    public JobApplication() {
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicant(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getAccepted() {
        return accepted;
    }

    private void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public void accept() {
        this.setClosed(true);
        this.setAccepted("accepted");
    }

    public void deny() {
        this.setClosed(true);
        this.setAccepted("denied");
    }

}
