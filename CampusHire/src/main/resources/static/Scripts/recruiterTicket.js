function showMessageBox(title, message) {
    alert(`${title}\n\n${message}`);
}

function validateTicketForm() {
    const remote = document.getElementById('remote').value;
    const experience = document.getElementById('experience').value;
    const location = document.getElementById('location').value;
    const jobType = document.getElementById('jobType').value;
    const industry = document.getElementById('industry').value;
    if (!remote || !experience || !location || !jobType || !industry) {
        return false;
    }
    const parsedExperience = parseInt(experience);
    if (isNaN(parsedExperience) || parsedExperience < 0 || parsedExperience > 50) {
        return false;
    }
    if (location.trim().length < 2) {
        return false;
    }
    if (industry.trim().length < 2) {
        return false;
    }
    return true;
}

async function handleTicketSubmission() {
    if (!validateTicketForm()) {
        showMessageBox('Input Error', 'Please fill in all required fields correctly.');
        return;
    }
    const remote = document.getElementById('remote').value;
    const experience = document.getElementById('experience').value;
    const location = document.getElementById('location').value;
    const jobType = document.getElementById('jobType').value;
    const industry = document.getElementById('industry').value;
    const parsedExperience = parseInt(experience);
    const ticketData = {
        remote: remote,
        experience: parsedExperience,
        location: location.trim(),
        jobType: jobType,
        industry: industry.trim()
    };
    const recruiterData = JSON.parse(sessionStorage.getItem('recruiterData'));
    if (!recruiterData) {
        showMessageBox('Error', 'Registration data not found. Please start over.');
        window.location.href = 'RecruiterRegister.html';
        return;
    }
    const completeData = {
        ...recruiterData,
        ticket: ticketData
    };
    try {
        const response = await fetch('http://localhost:8080/api/Recruiter/signUp', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(completeData),
        });
        if (response.ok) {
            sessionStorage.removeItem('recruiterData');
            window.location.href = 'RecruiterSuccess.html';
        } else {
            const errorText = await response.text();
            showMessageBox('Registration Failed', errorText);
        }
    } catch (error) {
        showMessageBox('Error', 'An unexpected error occurred during registration. Please try again.');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const backBtn = document.getElementById('backBtn');
    const nextBtn = document.getElementById('nextBtn');
    if (backBtn) {
        backBtn.addEventListener('click', (event) => {
            event.preventDefault();
            window.location.href = 'RecruiterRegister.html';
        });
    }
    if (nextBtn) {
        nextBtn.addEventListener('click', (event) => {
            event.preventDefault();
            handleTicketSubmission();
        });
    }
}); 