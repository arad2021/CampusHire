var studentId = localStorage.getItem('studentId');
var student = null;
var jobs = [];

function showLoading(message = 'Loading...', sectionId = null) {
    if (sectionId) {
        const section = document.getElementById(sectionId);
        if (section) {
            section.innerHTML = `<div class="loading">${message}</div>`;
        }
    }
}

function showNotification(message, type = 'error') {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.className = 'notification ' + type;
    notification.style.display = 'block';
    clearTimeout(window._notificationTimeout);
    window._notificationTimeout = setTimeout(() => {
        notification.style.display = 'none';
    }, 4000);
}

async function fetchStudentDetails() {
    if (!studentId) return null;
    try {
        const res = await fetch(`/api/Student/details/${student.id}`);
        if (!res.ok) throw new Error('Failed to fetch student details');
        return await res.json();
    } catch (e) {
        return null;
    }
}

document.getElementById('findJobsBtn').onclick = function () {
    if (!student) {
        showNotification('Could not load student details.', 'error');
        return;
    }
    findJobs();
};

async function findJobs() {
    if (!student) return;
    showLoading('Loading jobs...', 'jobsSection');
    try {
        // Fetch jobs
        const jobsRes = await fetch(`/api/Student/filterAllJobs/${student.id}`);
        if (!jobsRes.ok) throw new Error('Failed to fetch jobs');
        const jobsText = await jobsRes.text();
        if (jobsText) {
            try {
                jobs = JSON.parse(jobsText);
            } catch (err) {
                console.error('Failed to parse jobs JSON:', err, 'Response text:', jobsText);
                jobs = [];
            }
        }
        // Fetch applications
        const appsRes = await fetch(`/api/Student/getAllJobApplicationByStudentId/${student.id}`);
        let appliedJobIds = [];
        if (appsRes.ok) {
            const applications = await appsRes.json();
            appliedJobIds = applications.map(app => app.jobId);
        }
        displayJobsTable(jobs, appliedJobIds);
    } catch (e) {
        let section = document.getElementById('jobsSection');
        section.innerHTML = '<h2 class="section-title">Jobs For You</h2>' + '<div class="error">Could not load jobs.</div>';
        console.error('Error in findJobs:', e);
    }
}

function displayJobsTable(jobs, appliedJobIds = []) {
    let section = document.getElementById('jobsSection');
    let html = '<h2 class="section-title">Jobs For You</h2>';
    if (!jobs || jobs.length === 0) {
        section.innerHTML = html + '<div class="loading">No jobs found matching your profile.</div>';
        return;
    }
    html += '<h3>Matching Jobs</h3><table><tr><th>Company</th><th>Name</th><th>Location</th><th>Type</th><th>Apply</th></tr>';
    for (const job of jobs) {
        const alreadyApplied = appliedJobIds.includes(job.jobId);
        html += `<tr>
  <td>${job.company}</td>
  <td>${job.name}</td>
  <td>${job.jobTicket.location}</td>
  <td>${job.jobTicket.jobType}</td>
  <td>`;
        if (alreadyApplied) {
            html += `<button disabled>Application sent</button>`;
        } else {
            html += `<button onclick="applyToJob(${job.jobId}, this)">Apply</button>`;
        }
        html += `</td>\n</tr>`;
    }
    html += '</table>';
    section.innerHTML = html;
}

window.applyToJob = async function (jobId, btn) {
    if (!student) return;
    if (btn) {
        btn.disabled = true;
        btn.textContent = 'Application sent';
    }
    try {
        const application = { applicantId: Number(student.id), jobId: parseInt(jobId) };
        const res = await fetch('/api/Student/addJobApplication', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(application)
        });
        if (res.ok) {
            showNotification('Application sent!', 'success');
            // Trigger the My Applications button
            document.getElementById('myApplicationsBtn').click();
        } else {
            const errorText = await res.text();
            showNotification('Failed to apply.', 'error');
            console.error('Failed to apply:', errorText);
            if (btn) {
                btn.disabled = false;
                btn.textContent = 'Apply';
            }
        }
    } catch (e) {
        showNotification('Could not apply to job.', 'error');
        console.error('Could not apply to job:', e);
        if (btn) {
            btn.disabled = false;
            btn.textContent = 'Apply';
        }
    }
};

document.getElementById('myApplicationsBtn').onclick = function () {
    if (!student) {
        showNotification('Could not load student details.', 'error');
        return;
    }
    showApplications();
};

async function showApplications() {
    if (!student) return;
    showLoading('Loading applications...', 'applicationsSection');
    try {
        const res = await fetch(`/api/Student/getAllJobApplicationByStudentId/${student.id}`);
        if (!res.ok) throw new Error('Failed to fetch applications');
        const applications = await res.json();
        let section = document.getElementById('applicationsSection');
        let html = '<h2 class="section-title">Your Job Applications</h2>';
        if (!applications || applications.length === 0) {
            section.innerHTML = html + '<div class="loading">You have not applied to any jobs yet.</div>';
            return;
        }
        // If jobs array is empty, fetch jobs
        if (!jobs || jobs.length === 0) {
            const jobsRes = await fetch(`/api/Student/filterAllJobs/${student.id}`);
            if (jobsRes.ok) {
                const jobsText = await jobsRes.text();
                if (jobsText) {
                    try {
                        jobs = JSON.parse(jobsText);
                    } catch (err) {
                        console.error('Failed to parse jobs JSON:', err, 'Response text:', jobsText);
                        jobs = [];
                    }
                }
            }
        }
        html += '<h3>My Applications</h3><table><tr><th>Company</th><th>Name</th><th>Location</th><th>Type</th><th>Status</th><th>Delete</th></tr>';
        for (const app of applications) {
            const job = jobs.find(j => j.jobId === app.jobId);
            let status;
            if (app.accepted === "pending") status = "Pending";
            else if (app.accepted === "Accepted") status = "Accepted";
            else if (app.accepted === "Denied") status = "Denied";
            else status = app.accepted;
            html += `<tr>`;
            if (job) {
                html += `<td>${job.company}</td><td>${job.name}</td><td>${job.jobTicket.location}</td><td>${job.jobTicket.jobType}</td>`;
            } else {
                html += `<td colspan="4">Job info not found (ID: ${app.jobId})</td>`;
            }
            html += `<td>${status}</td>`;
            html += `<td><button onclick="deleteApplication(${app.applicationId}, this)">Delete</button></td>`;
            html += `</tr>`;
        }
        html += '</table>';
        section.innerHTML = html;
    } catch (e) {
        let section = document.getElementById('applicationsSection');
        section.innerHTML = '<h2 class="section-title">Your Job Applications</h2>' + '<div class="error">Could not load applications.</div>';
    }
}

window.deleteApplication = async function (applicationId, btn) {
    if (!confirm('Are you sure you want to delete this application?')) return;
    try {
        const res = await fetch(`/api/Student/deleteJobApplication/${applicationId}`, { method: 'DELETE' });
        if (res.ok) {
            showNotification('Application deleted!', 'success');
            // Remove the row from the table
            if (btn && btn.closest('tr')) btn.closest('tr').remove();
        } else {
            showNotification('Failed to delete application.', 'error');
        }
    } catch (e) {
        showNotification('Could not delete application.', 'error');
    }
};

window.onload = async function () {
    student = await fetchStudentDetails();
    if (!student) {
        student = {
            id: studentId ? parseInt(studentId) : 123456789,
            name: "John Doe",
            phoneNumber: "0501234567",
            email: "john@example.com",
            subject: "Computer Science",
            age: 22,
            graduateYear: 2025,
            ticket: {
                remote: "Yes",
                experience: 1,
                location: "Tel Aviv",
                jobType: "Full-time",
                industry: "Technology"
            }
        };
    }
};