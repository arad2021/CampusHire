package com.CampusHire.Commands;

import com.CampusHire.Services.JobApplicationService;

public class DenyJobApplicationCommand implements JobApplicationCommand {
    private final JobApplicationService jobApplicationService;
    private final int applicationId;

    public DenyJobApplicationCommand(JobApplicationService jobApplicationService, int applicationId) {
        this.jobApplicationService = jobApplicationService;
        this.applicationId = applicationId;
    }

    @Override
    public String execute() {
        return jobApplicationService.denyApplication(applicationId);
    }
}