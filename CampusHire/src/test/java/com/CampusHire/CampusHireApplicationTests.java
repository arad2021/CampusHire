package com.CampusHire;

import com.CampusHire.Model.*;
import com.CampusHire.Services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CampusHireApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RecruiterService recruiterService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private JobService jobService;
	@Autowired
	private JobApplicationService jobApplicationService;
	@Autowired
	private EntityFactory entityFactory;

	@Test
	void contextLoads() {
	}

	@Test
	void recruiterControllerEndpoints() throws Exception {
		RecruiterJson recruiter = new RecruiterJson(100000001L, "1112223333", "Recruiter1", "rec1@company.com",
				"CompanyA");
		mockMvc.perform(post("/api/Recruiter/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(recruiter)))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("accepted")));
		mockMvc.perform(get("/api/Recruiter/SignIn/100000001"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("ok")));
		recruiter.setName("Recruiter1Updated");
		mockMvc.perform(post("/api/Recruiter/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(recruiter)))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("updated")));
		mockMvc.perform(get("/api/Recruiter/details/100000001"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Recruiter1Updated"));
	}

	@Test
	void studentControllerEndpoints() throws Exception {
		StudentJson student = new StudentJson(200000001L, "Student1", "2223334444", "CS", "stud1@univ.com", 23, 2025);
		// signUp
		mockMvc.perform(post("/api/Student/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student)))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("accepted")));
		// signIn
		mockMvc.perform(get("/api/Student/SignIn/200000001"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("ok")));
		// edit
		student.setName("Student1Updated");
		mockMvc.perform(post("/api/Student/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student)))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("updated")));
		// get details
		mockMvc.perform(get("/api/Student/details/200000001"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Student1Updated"));
	}

	@Test
	void jobAndApplicationEndpoints() throws Exception {
		RecruiterJson recruiter = new RecruiterJson(300000001L, "3334445555", "Recruiter2", "rec2@company.com",
				"CompanyB");
		StudentJson student = new StudentJson(400000001L, "Student2", "4445556666", "Math", "stud2@univ.com", 24, 2026);
		mockMvc.perform(post("/api/Recruiter/signUp").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(recruiter)));
		mockMvc.perform(post("/api/Student/signUp").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student)));
		JsonJob job = new JsonJob("CompanyB", "Job1", "IT", "CityA", "FullTime", 2, "Yes", 300000001L);
		mockMvc.perform(post("/api/Recruiter/registerJob")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(job)))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("successfully")));
		mockMvc.perform(get("/api/Recruiter/jobs/300000001"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Job1"));
		ArrayList<Job> jobs = jobService.getJobsByRecruiterId(300000001L);
		int jobId = jobs.get(0).getJobId();
		mockMvc.perform(get("/api/Recruiter/getJobById/" + jobId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Job1"));
		JsonJobApplication jobApp = new JsonJobApplication();
		jobApp.setApplicantId(400000001L);
		jobApp.setJobId(jobId);
		mockMvc.perform(post("/api/Student/addJobApplication")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(jobApp)))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("accepted")));
		// Get job applications by student
		mockMvc.perform(get("/api/Student/getAllJobApplicationByStudentId/400000001"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].jobId").value(jobId));
		mockMvc.perform(get("/api/Recruiter/jobApplications/" + jobId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].applicantId").value(400000001L));
		ArrayList<JobApplication> jobApps = jobApplicationService.getJobApplicationByJobId(jobId);
		int appId = jobApps.get(0).getApplicationId();
		mockMvc.perform(post("/api/Recruiter/respondToApplication/" + appId)
				.param("response", "accept"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("accepted")));
		mockMvc.perform(delete("/api/Student/deleteJobApplication/" + appId))
				.andExpect(status().isOk());
		mockMvc.perform(delete("/api/Recruiter/removeJob/" + jobId))
				.andExpect(status().isOk());
	}

	@Test
	void recruiterServiceLogic() {
		RecruiterJson recruiterJson = new RecruiterJson(500000001L, "5556667777", "Recruiter3", "rec3@company.com",
				"CompanyC");
		String signUpResult = recruiterService.signUp(recruiterJson);
		assertThat(signUpResult).contains("accepted");
		String signInResult = recruiterService.signIn(500000001L);
		assertThat(signInResult).isEqualTo("ok");
		recruiterJson.setName("Recruiter3Updated");
		String editResult = recruiterService.edit(recruiterJson);
		assertThat(editResult).contains("updated");
		Recruiter recruiter = recruiterService.getRecruiterById(500000001L);
		assertThat(recruiter.getName()).isEqualTo("Recruiter3Updated");
	}

	@Test
	void studentServiceLogic() {
		StudentJson studentJson = new StudentJson(600000001L, "Student3", "6667778888", "Physics", "stud3@univ.com", 25,
				2027);
		String signUpResult = studentService.signUp(studentJson);
		assertThat(signUpResult).contains("accepted");
		String signInResult = studentService.signIn(600000001L);
		assertThat(signInResult).isEqualTo("ok");
		studentJson.setName("Student3Updated");
		String editResult = studentService.edit(studentJson);
		assertThat(editResult).contains("updated");
		Student student = studentService.getStudentById(600000001L);
		assertThat(student.getName()).isEqualTo("Student3Updated");
	}

	@Test
	void jobServiceLogic() {
		RecruiterJson recruiterJson = new RecruiterJson(700000001L, "7778889999", "Recruiter4", "rec4@company.com",
				"CompanyD");
		recruiterService.signUp(recruiterJson);
		JsonJob job = new JsonJob("CompanyD", "Job2", "Finance", "CityB", "PartTime", 1, "No", 700000001L);
		String regResult = jobService.registerJob(job);
		assertThat(regResult).contains("successfully");
		ArrayList<Job> jobs = jobService.getJobsByRecruiterId(700000001L);
		assertThat(jobs).isNotEmpty();
		Job jobEntity = jobs.get(0);
		assertThat(jobEntity.getName()).isEqualTo("Job2");
		jobService.removeJob(jobEntity.getJobId());
		ArrayList<Job> jobsAfter = jobService.getJobsByRecruiterId(700000001L);
		assertThat(jobsAfter).isEmpty();
	}

	@Test
	void jobApplicationServiceLogic() {
		StudentJson studentJson = new StudentJson(800000001L, "Student4", "8889990000", "Bio", "stud4@univ.com", 26,
				2028);
		studentService.signUp(studentJson);
		RecruiterJson recruiterJson = new RecruiterJson(900000001L, "9990001111", "Recruiter5", "rec5@company.com",
				"CompanyE");
		recruiterService.signUp(recruiterJson);
		JsonJob job = new JsonJob("CompanyE", "Job3", "HR", "CityC", "Intern", 0, "Yes", 900000001L);
		jobService.registerJob(job);
		ArrayList<Job> jobs = jobService.getJobsByRecruiterId(900000001L);
		int jobId = jobs.get(0).getJobId();
		JsonJobApplication jobApp = new JsonJobApplication();
		jobApp.setApplicantId(800000001L);
		jobApp.setJobId(jobId);
		String appResult = jobApplicationService.addApplication(jobApp);
		assertThat(appResult).contains("accepted");
		ArrayList<JobApplication> apps = jobApplicationService.getJobApplicationByStudentId(800000001L);
		assertThat(apps).isNotEmpty();
		int appId = apps.get(0).getApplicationId();
		String acceptResult = jobApplicationService.acceptApplication(appId);
		assertThat(acceptResult).contains("accepted");
		String denyResult = jobApplicationService.denyApplication(appId);
		assertThat(denyResult).contains("denied");
		jobApplicationService.removeJobApplication(appId);
		ArrayList<JobApplication> appsAfter = jobApplicationService.getJobApplicationByStudentId(800000001L);
		assertThat(appsAfter).isEmpty();
	}

	@Test
	void entityFactoryWorksProperly() {
		// StudentTicket and JobTicket
		StudentTicket st = entityFactory.createStudentTicket("Yes", 2, "CityA", "FullTime", "IT");
		assertThat(st.getRemote()).isEqualTo("Yes");
		assertThat(st.getExperience()).isEqualTo(2);
		assertThat(st.getLocation()).isEqualTo("CityA");
		assertThat(st.getJobType()).isEqualTo("FullTime");
		assertThat(st.getIndustry()).isEqualTo("IT");

		JobTicket jt = entityFactory.createJobTicket("No", 3, "CityB", "PartTime", "Finance");
		assertThat(jt.getRemote()).isEqualTo("No");
		assertThat(jt.getExperience()).isEqualTo(3);
		assertThat(jt.getLocation()).isEqualTo("CityB");
		assertThat(jt.getJobType()).isEqualTo("PartTime");
		assertThat(jt.getIndustry()).isEqualTo("Finance");

		TicketJson ticketJson = new TicketJson("Hybrid", 1, "CityC", "Intern", "HR");
		StudentTicket stFromJson = entityFactory.createStudentTicketFromJson(ticketJson);
		assertThat(stFromJson.getRemote()).isEqualTo("Hybrid");
		JobTicket jtFromJson = entityFactory.createJobTicketFromJson(ticketJson);
		assertThat(jtFromJson.getLocation()).isEqualTo("CityC");

		// Student
		Student student = entityFactory.createStudent(900000001L, "Alice", "1234567890", "alice@email.com", "Math", 22,
				2025, st);
		assertThat(student.getId()).isEqualTo(900000001L);
		assertThat(student.getName()).isEqualTo("Alice");
		assertThat(student.getTicket().getIndustry()).isEqualTo("IT");
		StudentJson studentJson = new StudentJson(900000002L, "Bob", "0987654321", "CS", "bob@email.com", 23, 2026);
		studentJson.setTicket(stFromJson);
		Student studentFromJson = entityFactory.createStudentFromJson(studentJson);
		assertThat(studentFromJson.getName()).isEqualTo("Bob");
		assertThat(studentFromJson.getTicket().getRemote()).isEqualTo("Hybrid");

		// Recruiter
		Recruiter recruiter = entityFactory.createRecruiter(900000003L, "Charlie", "1112223333", "charlie@email.com",
				"CompanyX");
		assertThat(recruiter.getId()).isEqualTo(900000003L);
		assertThat(recruiter.getName()).isEqualTo("Charlie");
		RecruiterJson recruiterJson = new RecruiterJson(900000004L, "4445556666", "Dana", "dana@email.com", "CompanyY");
		Recruiter recruiterFromJson = entityFactory.createRecruiterFromJson(recruiterJson);
		assertThat(recruiterFromJson.getName()).isEqualTo("Dana");
		assertThat(recruiterFromJson.getCompany()).isEqualTo("CompanyY");

		// Job
		Job job = entityFactory.createJob("CompanyZ", "Engineer", 900000003L, jt);
		assertThat(job.getCompany()).isEqualTo("CompanyZ");
		assertThat(job.getName()).isEqualTo("Engineer");
		assertThat(job.getRecruiterId()).isEqualTo(900000003L);
		assertThat(job.getJobTicket().getIndustry()).isEqualTo("Finance");
		JsonJob jsonJob = new JsonJob("CompanyA", "Analyst", "IT", "CityA", "FullTime", 2, "Yes", 900000004L);
		Job jobFromJson = entityFactory.createJobFromJson(jsonJob);
		assertThat(jobFromJson.getName()).isEqualTo("Analyst");
		assertThat(jobFromJson.getRecruiterId()).isEqualTo(900000004L);

		// JobApplication
		JobApplication jobApp = entityFactory.createJobApplication(900000001L, 123);
		assertThat(jobApp.getApplicantId()).isEqualTo(900000001L);
		assertThat(jobApp.getJobId()).isEqualTo(123);
		JsonJobApplication jsonJobApp = new JsonJobApplication();
		jsonJobApp.setApplicantId(900000002L);
		jsonJobApp.setJobId(456);
		JobApplication jobAppFromJson = entityFactory.createJobApplicationFromJson(jsonJobApp);
		assertThat(jobAppFromJson.getApplicantId()).isEqualTo(900000002L);
		assertThat(jobAppFromJson.getJobId()).isEqualTo(456);
	}
}
