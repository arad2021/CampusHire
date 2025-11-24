package com.CampusHire.Model;

import org.springframework.stereotype.Component;

@Component
public class EntityFactory {

    public Job createJob(String company, String name, long recruiterId, JobTicket jobTicket) {
        return new Job(company, name, recruiterId, jobTicket);
    }

    public Job createJobFromJson(JsonJob jsonJob) {
        return new Job(jsonJob);
    }

    public Student createStudent(long id, String name, String phoneNumber, String email,
            String subject, int age, int graduateYear, StudentTicket ticket) {
        return new Student(id, name, phoneNumber, email, subject, age, graduateYear, ticket);
    }

    public Student createStudentFromJson(StudentJson studentJson) {
        return new Student(studentJson);
    }

    public Recruiter createRecruiter(long id, String name, String phoneNumber, String email, String company) {
        return new Recruiter(id, phoneNumber, email, company, name);
    }

    public Recruiter createRecruiterFromJson(RecruiterJson recruiterJson) {
        return new Recruiter(recruiterJson);
    }

    public StudentTicket createStudentTicket(String remote, int experience, String location,
            String jobType, String industry) {
        return new StudentTicket(remote, experience, location, jobType, industry);
    }

    public StudentTicket createStudentTicketFromJson(TicketJson ticketJson) {
        return new StudentTicket(ticketJson);
    }

    public JobTicket createJobTicket(String remote, int experience, String location,
            String jobType, String industry) {
        return new JobTicket(remote, experience, location, jobType, industry);
    }

    public JobTicket createJobTicketFromJson(TicketJson ticketJson) {
        return new JobTicket(ticketJson);
    }

    public JobApplication createJobApplication(long applicantId, int jobId) {
        return new JobApplication(applicantId, jobId);
    }

    public JobApplication createJobApplicationFromJson(JsonJobApplication jsonJobApplication) {
        return new JobApplication(jsonJobApplication);
    }
}