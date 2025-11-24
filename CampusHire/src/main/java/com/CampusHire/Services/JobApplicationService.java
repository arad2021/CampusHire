package com.CampusHire.Services;

import com.CampusHire.Model.EntityFactory;
import com.CampusHire.Model.JobApplication;
import com.CampusHire.Model.JsonJobApplication;
import com.CampusHire.Reposetories.JobApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JobApplicationService {
    @Autowired
    JobApplicationRepository jobApplicationRepository;

    @Autowired
    EntityFactory entityFactory;

    public String addApplication(JsonJobApplication jsonJobApplication) {
        JobApplication jobApplication = entityFactory.createJobApplicationFromJson(jsonJobApplication);
        if (!checkJobApplication(jobApplication.getApplicantId(), jobApplication.getJobId())) {
            if (jobApplication.equals(jobApplicationRepository.save(jobApplication))) {
                return "accepted";
            } else {
                return "denied";
            }
        } else
            return "application already exist";
    }

    private boolean checkJobApplication(long applicantId, int jobId) {
        ArrayList<JobApplication> jobApplications = (ArrayList<JobApplication>) jobApplicationRepository.findAll();
        for (JobApplication j : jobApplications) {
            if (j.getApplicantId() == applicantId && j.getJobId() == jobId)
                return true;
        }
        return false;
    }

    public String edit() {
        return "";
    }

    public ArrayList<JobApplication> getJobApplicationByJobId(int Id) {
        return getJobApplicationsByJobId(Id);
    }

    public ArrayList<JobApplication> getJobApplicationByStudentId(long id) {
        ArrayList<JobApplication> jobApplications = new ArrayList<JobApplication>();
        ArrayList<JobApplication> jobApplications1 = (ArrayList<JobApplication>) this.jobApplicationRepository
                .findAll();
        for (JobApplication j : jobApplications1)
            if (j.getApplicantId() == id) {
                jobApplications.add(j);
            }
        return jobApplications;
    }

    public void removeJobApplication(int id) {
        this.jobApplicationRepository.deleteById(id);
    }

    public String acceptApplication(int applicationId) {
        JobApplication application = jobApplicationRepository.findById(applicationId).orElse(null);
        if (application == null) {
            return "Application not found";
        }
        application.accept();
        jobApplicationRepository.save(application);
        return "Application accepted successfully";
    }

    public String denyApplication(int applicationId) {
        JobApplication application = jobApplicationRepository.findById(applicationId).orElse(null);
        if (application == null) {
            return "Application not found";
        }
        application.deny();
        jobApplicationRepository.save(application);
        return "Application denied successfully";
    }

    public ArrayList<JobApplication> getJobApplicationsByJobId(int id) {
        ArrayList<JobApplication> jobApplications = new ArrayList<JobApplication>();
        ArrayList<JobApplication> jobApplications1 = (ArrayList<JobApplication>) this.jobApplicationRepository
                .findAll();
        for (JobApplication j : jobApplications1)
            if (j.getJobId() == id) {
                jobApplications.add(j);
            }
        return jobApplications;
    }

    public String updateJobApplication(JobApplication jobApplication) {
        jobApplicationRepository.save(jobApplication);
        return "Application updated successfully";
    }
}
