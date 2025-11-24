package com.CampusHire.Services;

import com.CampusHire.Commands.AcceptJobApplicationCommand;
import com.CampusHire.Commands.DenyJobApplicationCommand;
import com.CampusHire.Commands.JobApplicationCommand;
import com.CampusHire.Model.EntityFactory;
import com.CampusHire.Model.Job;
import com.CampusHire.Model.JobApplication;
import com.CampusHire.Model.JsonJob;
import com.CampusHire.Model.Recruiter;
import com.CampusHire.Model.RecruiterJson;
import com.CampusHire.Reposetories.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecruiterService {
    @Autowired
    RecruiterRepository recruiterRepository;

    @Autowired
    EntityFactory entityFactory;

    @Autowired
    JobService jobService;

    @Autowired
    JobApplicationService jobApplicationService;

    public String signUp(RecruiterJson recruiterJson) {
        if (checkAllId(recruiterJson.getId())) {
            return "id already exist";
        } else {
            Recruiter recruiter = entityFactory.createRecruiterFromJson(recruiterJson);
            this.recruiterRepository.save(recruiter);
            return "accepted";
        }
    }

    public String signIn(long id) {
        ArrayList<Recruiter> allRecruiter = (ArrayList<Recruiter>) this.recruiterRepository.findAll();
        for (Recruiter r : allRecruiter) {
            if (id == r.getId()) {
                return "ok";
            }
        }

        return "error";

    }

    public String edit(RecruiterJson recruiterJson) {
        Recruiter recruiter = recruiterRepository.findById(recruiterJson.getId()).orElse(null);
        if (recruiter == null) {
            return "Recruiter not found";
        }
        recruiter.setName(recruiterJson.getName());
        recruiter.setPhoneNumber(recruiterJson.getPhoneNumber());
        recruiter.setEmail(recruiterJson.getEmail());
        recruiter.setCompany(recruiterJson.getCompany());
        recruiterRepository.save(recruiter);
        return "Recruiter updated successfully";
    }

    public boolean checkAllId(Long id) {
        ArrayList<Recruiter> allRecruiter = (ArrayList<Recruiter>) this.recruiterRepository.findAll();
        for (Recruiter r : allRecruiter) {
            if (id.equals(r.getId())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Job> getJobsByRecruiterId(long id) {
        return jobService.getJobsByRecruiterId(id);
    }

    public Job getJobById(int id) {
        return this.jobService.getJobsById(id);
    }

    public String registerJob(JsonJob job) {
        return jobService.registerJob(job);
    }

    public ArrayList<JobApplication> getJobApplicationByJobId(int jobId) {
        return jobApplicationService.getJobApplicationByJobId(jobId);
    }

    public void removeJob(int id) {
        this.jobService.removeJob(id);
    }

    public String respondToApplication(int applicationId, String response) {
        JobApplicationCommand command;
        if ("accept".equalsIgnoreCase(response)) {
            command = new AcceptJobApplicationCommand(jobApplicationService, applicationId);
        } else if ("deny".equalsIgnoreCase(response)) {
            command = new DenyJobApplicationCommand(jobApplicationService, applicationId);
        } else {
            return "Invalid response type. Use 'accept' or 'deny'.";
        }
        return command.execute();
    }

    public Recruiter getRecruiterById(long id) {
        return recruiterRepository.findById(id).orElse(null);
    }
}
