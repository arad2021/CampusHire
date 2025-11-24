package com.CampusHire.Services;

import com.CampusHire.Model.*;
import com.CampusHire.Reposetories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    JobApplicationService jobApplicationService;
    @Autowired
    JobService jobService;
    @Autowired
    EntityFactory entityFactory;
    @Autowired
    StudentCacheConcrete StudentCache;



    public String signUp(StudentJson studentJson) {
        if (checkAllId(studentJson.getId())) {
            return "id already exist";
        } else {
            Student student = entityFactory.createStudentFromJson(studentJson);
            this.studentRepository.save(student);
            return "accepted";
        }
    }

    public String edit(StudentJson studentJson) {

        Student student = studentRepository.findById(studentJson.getId()).orElse(null);
        if (student == null) {
            return "Student not found";
        }

        student.setName(studentJson.getName());
        student.setPhoneNumber(studentJson.getPhoneNumber());
        student.setEmail(studentJson.getEmail());
        student.setSubject(studentJson.getSubject());
        student.setAge(studentJson.getAge());
        student.setGraduateYear(studentJson.getGraduateYear());
        student.setTicket(studentJson.getTicket());
        studentRepository.save(student);
        return "Student updated successfully";
    }

    public String signIn(long id) {
        ArrayList<Student> allStudent = (ArrayList<Student>) this.studentRepository.findAll();
        for (Student s : allStudent) {
            if (id == s.getId()) {
                return "ok";
            }
        }
        return "error";
    }

    public boolean checkAllId(Long id) {
        ArrayList<Student> allStudent = (ArrayList<Student>) this.studentRepository.findAll();
        for (Student s : allStudent) {
            if (id == s.getId()) {
                return true;
            }
        }
        return false;
    }

    public String addApplication(JsonJobApplication jsonJobApplication) {
        return jobApplicationService.addApplication(jsonJobApplication);
    }

    public ArrayList<JobApplication> getJobApplicationByStudentId(long id) {
        return jobApplicationService.getJobApplicationByStudentId(id);
    }

    public void removeJobApplication(int id) {
        this.jobApplicationService.removeJobApplication(id);
    }


    public ArrayList<Job> filterAllJobs(long id) {
       // Student s = studentRepository.findById(id).orElse(null);
        Student s = getStudentById(id);
        if (s == null || s.getTicket() == null)
            return null;
        return this.jobService.filterJobsByAll(
                this.entityFactory.createJobTicket(s.getTicket().getRemote(), s.getTicket().getExperience(),
                        s.getTicket().getLocation(), s.getTicket().getJobType(), s.getTicket().getIndustry()));
    }

    public Student getStudentById(long id) {
        if(StudentCache.containsKey((Long)id)){
            return (Student)StudentCache.get(id);
        }
        else {
            Student s = studentRepository.findById(id).orElse(null);

            StudentCache.put((Long)id, s);
            return s;
        }
    }

    public Job getJobById(int id) {
        return this.jobService.getJobsById(id);
    }
}
