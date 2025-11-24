package com.CampusHire.Model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;
    private String name;
    private String company;
    private long recruiterId;
    @Embedded
    private JobTicket jobTicket;

    public Job(String company, String name, long recruiterId, JobTicket jobTicket) {
        this.company = company;
        this.name = name;
        this.recruiterId = recruiterId;
        this.jobTicket = jobTicket;
    }

    public Job(String company, String name, long recruiterId) {
        this.company = company;
        this.name = name;
        this.recruiterId = recruiterId;
        this.jobTicket = new JobTicket();
    }

    public Job(JsonJob jsonJob) {
        this.company = jsonJob.getCompany();
        this.name = jsonJob.getName();
        this.recruiterId = jsonJob.getRecruiterId();
        this.jobTicket = new JobTicket(jsonJob.getRemote(), jsonJob.getExperience(), jsonJob.getLocation(),
                jsonJob.getJobType(), jsonJob.getIndustry());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Job() {
    }

    public int getJobId() {
        return jobId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiter(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    public JobTicket getJobTicket() {
        return jobTicket;
    }

    public void setJobTicket(JobTicket jobTicket) {
        this.jobTicket = jobTicket;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Job job = (Job) o;
        return jobId == job.jobId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jobId);
    }
}
