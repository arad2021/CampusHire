package com.CampusHire.Controllers;

import com.CampusHire.Model.Job;
import com.CampusHire.Model.JobApplication;
import com.CampusHire.Model.JsonJobApplication;
import com.CampusHire.Model.Student;
import com.CampusHire.Model.StudentJson;
import com.CampusHire.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/Student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/signUp")
    public String SignUp(@RequestBody StudentJson studentJson) {
        return this.studentService.signUp(studentJson);
    }

    @GetMapping("/SignIn/{id}")
    public String signIn(@PathVariable long id) {
        return studentService.signIn(id);

    }

    @PostMapping("/addJobApplication")
    public String addApplication(@RequestBody JsonJobApplication jsonJobApplication) {
        return studentService.addApplication(jsonJobApplication);
    }

    @GetMapping("/getAllJobApplicationByStudentId/{id}")
    public ArrayList<JobApplication> getJobApplicationByStudentId(@PathVariable long id) {
        return studentService.getJobApplicationByStudentId(id);
    }

    @DeleteMapping("/deleteJobApplication/{id}")
    public void removeJobApplication(@PathVariable int id) {
        studentService.removeJobApplication(id);
    }

    @GetMapping("/filterAllJobs/{id}")
    public ArrayList<Job> filterAllJobs(@PathVariable long id) {
        return (ArrayList<Job>) this.studentService.filterAllJobs(id);
    }

    @GetMapping("/details/{id}")
    public Student getStudentById(@PathVariable long id) {
        return this.studentService.getStudentById(id);
    }

    @GetMapping("/getJobById/{id}")
    public Job getJobById(@PathVariable int id) {
        return this.studentService.getJobById(id);
    }

    @PostMapping("/edit")
    public String editStudent(@RequestBody StudentJson studentJson) {
        return this.studentService.edit(studentJson);
    }

}
