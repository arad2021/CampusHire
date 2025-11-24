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
    const studentData = JSON.parse(sessionStorage.getItem('studentData'));
    if (!studentData) {
        showMessageBox('Error', 'Registration data not found. Please start over.');
        window.location.href = 'StudentRegister.html';
        return;
    }
    const completeData = {
        ...studentData,
        ticket: ticketData
    };
    try {
        const response = await fetch('http://localhost:8080/api/Student/signUp', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(completeData),
        });
        if (response.ok) {
            sessionStorage.removeItem('studentData');
            window.location.href = 'StudentSuccess.html';
        } else {
            const errorText = await response.text();
            showMessageBox('Registration Failed', errorText);
        }
    } catch (error) {
        showMessageBox('Error', 'An unexpected error occurred during registration. Please try again.');
    }
}

function prefillTicketForm() {
    const ticketData = JSON.parse(sessionStorage.getItem('studentTicketData'));
    if (ticketData) {
        document.getElementById('remote').value = ticketData.remote || '';
        document.getElementById('experience').value = ticketData.experience || '';
        document.getElementById('location').value = ticketData.location || '';
        document.getElementById('jobType').value = ticketData.jobType || '';
        document.getElementById('industry').value = ticketData.industry || '';
    }
}

function saveTicketFormToSession() {
    const ticketData = {
        remote: document.getElementById('remote').value,
        experience: document.getElementById('experience').value,
        location: document.getElementById('location').value,
        jobType: document.getElementById('jobType').value,
        industry: document.getElementById('industry').value
    };
    sessionStorage.setItem('studentTicketData', JSON.stringify(ticketData));
}

document.addEventListener('DOMContentLoaded', () => {
    prefillTicketForm();
    const backBtn = document.getElementById('backBtn');
    const nextBtn = document.getElementById('nextBtn');
    if (backBtn) {
        backBtn.addEventListener('click', (event) => {
            event.preventDefault();
            saveTicketFormToSession();
            window.location.href = 'StudentRegister.html';
        });
    }
    if (nextBtn) {
        nextBtn.addEventListener('click', (event) => {
            event.preventDefault();
            sessionStorage.removeItem('studentTicketData');
            handleTicketSubmission();
        });
    }
}); 