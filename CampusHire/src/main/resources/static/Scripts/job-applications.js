document.addEventListener('DOMContentLoaded', async function () {
    function getJobIdFromUrl() {
        const params = new URLSearchParams(window.location.search);
        return params.get('jobId');
    }

    function goBack() {
        window.location.href = 'recruiter-portal.html';
    }
    window.goBack = goBack;

    const jobId = getJobIdFromUrl();
    const section = document.getElementById('applicationsSection');
    if (!jobId) {
        section.innerHTML = '<div class="error">No job selected.</div>';
        return;
    }

    async function getStudentNameAndPhone(applicantId) {
        try {
            const res = await fetch(`/api/Student/details/${applicantId}`);
            if (res.ok) {
                const student = await res.json();
                return { name: student.name || applicantId, phone: student.phoneNumber || '' };
            }
        } catch (e) { }
        return { name: applicantId, phone: '' };
    }

    async function fetchApplications() {
        section.innerHTML = 'Loading...';
        try {
            const res = await fetch(`/api/Recruiter/jobApplications/${jobId}`);
            if (!res.ok) throw new Error('Failed to fetch applications');
            const applications = await res.json();
            if (!applications || applications.length === 0) {
                section.innerHTML = '<div>No applications for this job.</div>';
                return;
            }
            let html = '<table><tr><th>Student Name</th><th>Phone Number</th><th>Status</th><th>Actions</th></tr>';
            const rows = await Promise.all(applications.map(async (app) => {
                const studentInfo = await getStudentNameAndPhone(app.applicantId);
                let status = app.accepted;
                if (status === 'true') status = 'accepted';
                else if (status === 'false') status = 'denied';
                let acceptClass = status === 'accepted' ? 'selected' : '';
                let denyClass = status === 'denied' ? 'selected' : '';
                return `<tr><td>${studentInfo.name}</td><td>${studentInfo.phone}</td><td>${status}</td><td><button class="action-btn ${acceptClass}" onclick="acceptApplication(${app.applicationId})">Accept</button><button class="action-btn ${denyClass}" onclick="denyApplication(${app.applicationId})">Deny</button></td></tr>`;
            }));
            html += rows.join('');
            html += '</table>';
            section.innerHTML = html;
        } catch (e) {
            section.innerHTML = '<div class="error">Could not load applications.</div>';
        }
    }

    window.acceptApplication = async function (applicationId) {
        try {
            const res = await fetch(`/api/Recruiter/respondToApplication/${applicationId}?response=accept`, { method: 'POST' });
            if (res.ok) {
                await fetchApplications();
            } else {
                alert('Failed to accept application.');
            }
        } catch (e) {
            alert('Could not accept application.');
        }
    };
    window.denyApplication = async function (applicationId) {
        try {
            const res = await fetch(`/api/Recruiter/respondToApplication/${applicationId}?response=deny`, { method: 'POST' });
            if (res.ok) {
                await fetchApplications();
            } else {
                alert('Failed to deny application.');
            }
        } catch (e) {
            alert('Could not deny application.');
        }
    };

    fetchApplications();
}); 