package com.CampusHire.Controllers;

import com.CampusHire.Model.Job;
import com.CampusHire.Model.JobApplication;
import com.CampusHire.Model.JsonJob;
import com.CampusHire.Model.Recruiter;
import com.CampusHire.Model.RecruiterJson;
import com.CampusHire.Services.RecruiterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/Recruiter")
public class RecruiterController {
    @Autowired
    RecruiterService recruiterService;

    @PostMapping("/signUp")
    public String SignUp(@RequestBody RecruiterJson recruiterJson) {
        return this.recruiterService.signUp(recruiterJson);
    }
    
    @GetMapping("/SignIn/{id}")
    public String signIn(@PathVariable long id) {
        return this.recruiterService.signIn(id);
    }

    @GetMapping("/jobs/{recruiterId}")
    public ArrayList<Job> getJobsByRecruiterId(@PathVariable long recruiterId) {
        return this.recruiterService.getJobsByRecruiterId(recruiterId);
    }

    @GetMapping("/getJobById/{id}")
    public Job getJobById(@PathVariable int id) {
        return this.recruiterService.getJobById(id);
    }

    @PostMapping("/registerJob")
    public String registerJob(@RequestBody JsonJob job) {
        return this.recruiterService.registerJob(job);
    }

    @GetMapping("/jobApplications/{jobId}")
    public ArrayList<JobApplication> getJobApplicationByJobId(@PathVariable int jobId) {
        return recruiterService.getJobApplicationByJobId(jobId);
    }

    @DeleteMapping("/removeJob/{id}")
    public void removeJob(@PathVariable int id) {
        recruiterService.removeJob(id);
    }

    @PostMapping("/respondToApplication/{applicationId}")
    public String respondToApplication(@PathVariable int applicationId, @RequestParam String response) {
        return recruiterService.respondToApplication(applicationId, response);
    }

    @GetMapping("/details/{id}")
    public Recruiter getRecruiterById(@PathVariable long id) {
        return this.recruiterService.getRecruiterById(id);
    }

    @PostMapping("/edit")
    public String editRecruiter(@RequestBody RecruiterJson recruiterJson) {
        return this.recruiterService.edit(recruiterJson);
    }
}
