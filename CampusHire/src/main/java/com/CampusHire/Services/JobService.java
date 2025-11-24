package com.CampusHire.Services;

import com.CampusHire.Model.EntityFactory;
import com.CampusHire.Model.Job;
import com.CampusHire.Model.JobApplication;
import com.CampusHire.Model.JobTicket;
import com.CampusHire.Model.JsonJob;
import com.CampusHire.Reposetories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobApplicationService jobApplicationService;

    @Autowired
    EntityFactory entityFactory;

    public List<Job> getAllJobs() {
        return (ArrayList<Job>) this.jobRepository.findAll();
    }

    public ArrayList<Job> getJobsByRecruiterId(long id) {
        ArrayList<Job> jobsDemo = (ArrayList<Job>) this.jobRepository.findAll();
        ArrayList<Job> jobs = new ArrayList<Job>();
        for (Job j : jobsDemo) {
            if (j.getRecruiterId() == id)
                jobs.add(j);
        }
        return jobs;
    }

    public Job getJobsById(int id) {
        return this.jobRepository.getReferenceById(id);
    }

    public ArrayList<Job> filterJobsByAll(JobTicket jobTicket) {
        ArrayList<Job> jobsDemo = (ArrayList<Job>) this.jobRepository.findAll();
        ArrayList<Job> jobs = new ArrayList<Job>();
        for (Job j : jobsDemo) {
            if (j.getJobTicket().equals(jobTicket))
                jobs.add(j);
        }
        return jobs;
    }

    public String registerJob(JsonJob jsonJob) {
        Job job = entityFactory.createJobFromJson(jsonJob);
        if (null != this.jobRepository.save(job))
            return "The Job was successfully added";
        return "The Job was not added";
    }

    public void removeJob(int id) {
        ArrayList<JobApplication> jobApplications = jobApplicationService.getJobApplicationsByJobId(id);
        for (JobApplication jobApplication : jobApplications) {
            if (jobApplication.getAccepted().equalsIgnoreCase("pending")) {
                jobApplication.deny();
                jobApplicationService.removeJobApplication(jobApplication.getApplicationId());
            }
        }
        this.jobRepository.deleteById(id);
    }
}
