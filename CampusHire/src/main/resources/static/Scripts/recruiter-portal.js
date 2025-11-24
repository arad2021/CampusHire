document.addEventListener('DOMContentLoaded', async function () {

    const recruiterId = localStorage.getItem('recruiterId');
    let recruiterName = '';
    try {
        const res = await fetch(`/api/Recruiter/details/${recruiterId}`);
        if (res.ok) {
            const recruiter = await res.json();
            recruiterName = recruiter && recruiter.name ? recruiter.name : '';
        }
    } catch (e) { }

    async function fetchJobs() {
        const section = document.getElementById('jobsSection');
        section.innerHTML = '<div>Loading jobs...</div>';
        try {
            const res = await fetch(`/api/Recruiter/jobs/${recruiterId}`);
            if (!res.ok) throw new Error('Failed to fetch jobs');
            const jobs = await res.json();
            if (!jobs || jobs.length === 0) {
                section.innerHTML = '<div>No jobs posted yet.</div>';
                return;
            }
            let html = '<table class="job-table"><tr><th>Job ID</th><th>Name</th><th>Location</th><th>Type</th><th>Actions</th></tr>';
            for (const job of jobs) {
                html += `<tr>
                    <td>${job.jobId}</td>
                    <td>${job.name}</td>
                    <td>${job.jobTicket ? job.jobTicket.location : ''}</td>
                    <td>${job.jobTicket ? job.jobTicket.jobType : ''}</td>
                    <td>
                        <button class="action-btn" onclick="viewApplications(${job.jobId})">Applications</button>
                        <button class="action-btn" onclick="deleteJob(${job.jobId}, this)">Delete</button>
                    </td>
                </tr>`;
            }
            html += '</table>';
            section.innerHTML = html;
        } catch (e) {
            section.innerHTML = '<div class="error">Could not load jobs.</div>';
        }
    }


    const modal = document.getElementById('addJobModal');
    const overlay = document.getElementById('modalOverlay');
    const addJobBtn = document.getElementById('addJobBtn');
    const cancelAddJobBtn = document.getElementById('cancelAddJobBtn');
    const addJobForm = document.getElementById('addJobForm');

    function showModal() {
        modal.style.display = 'block';
        overlay.style.display = 'block';
    }
    function hideModal() {
        modal.style.display = 'none';
        overlay.style.display = 'none';
        addJobForm.reset();
    }
    addJobBtn.onclick = showModal;
    cancelAddJobBtn.onclick = hideModal;
    overlay.onclick = hideModal;

    addJobForm.onsubmit = async function (e) {
        e.preventDefault();
        const company = document.getElementById('companyInput').value.trim();
        const name = document.getElementById('jobNameInput').value.trim();
        const location = document.getElementById('locationInput').value.trim();
        const jobType = document.getElementById('typeInput').value;
        const industry = document.getElementById('industryInput').value.trim();
        const remote = document.getElementById('remoteInput').value;
        const experience = parseInt(document.getElementById('experienceInput').value);
        if (!name || !location || !jobType || !industry || !remote || isNaN(experience)) {
            alert('Please fill in all fields correctly.');
            return;
        }
        const recruiterId = localStorage.getItem('recruiterId');
        const job = {
            company: company,
            name: name,
            recruiterId: recruiterId,
            remote: remote,
            experience: experience,
            jobType: jobType,
            location: location,
            industry: industry
        };
        try {
            const res = await fetch('/api/Recruiter/registerJob', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(job)
            });
            if (res.ok) {
                hideModal();
                await fetchJobs();
            } else {
                const errorText = await res.text();
                alert('Failed to add job: ' + errorText);
            }
        } catch (e) {
            alert('Could not add job.');
        }
    };

    window.viewApplications = function (jobId) {
        window.location.href = `job-applications.html?jobId=${jobId}`;
    };
    window.editJob = function (jobId) {
        alert('Edit job ' + jobId + ' (functionality coming soon)');
    };
    window.deleteJob = async function (jobId, btn) {
        if (!confirm('Are you sure you want to delete this job?')) return;
        try {
            const res = await fetch(`/api/Recruiter/removeJob/${jobId}`, {
                method: 'DELETE'
            });
            if (res.ok) {
                await fetchJobs();
            } else {
                alert('Failed to delete job.');
            }
        } catch (e) {
            alert('Could not delete job.');
        }
    };

    fetchJobs();
}); 