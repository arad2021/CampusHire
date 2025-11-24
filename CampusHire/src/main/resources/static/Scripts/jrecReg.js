function showMessageBox(title, message) {
    alert(`${title}\n\n${message}`);
}

function validateRecruiterForm() {
    const id = document.getElementById('recruiter-id').value;
    const firstName = document.getElementById('first-name').value;
    const lastName = document.getElementById('last-name').value;
    const phoneNumber = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const company = document.getElementById('company-name').value;

    const fullName = `${firstName.trim()} ${lastName.trim()}`;

    if (!id || !firstName || !lastName || !phoneNumber || !email || !company) {
        return false;
    }
    if (fullName.trim().length === 0) {
        return false;
    }
    const parsedId = parseInt(id);
    if (isNaN(parsedId) || parsedId < 100000 || parsedId > 9999999999) {
        return false;
    }
    const phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(phoneNumber)) {
        return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        return false;
    }
    if (company.trim().length < 2) {
        return false;
    }
    return true;
}

function handleRecruiterRegistration() {
    if (!validateRecruiterForm()) {
        showMessageBox('Input Error', 'Please fill in all required fields correctly.');
        return;
    }
    const id = document.getElementById('recruiter-id').value;
    const firstName = document.getElementById('first-name').value;
    const lastName = document.getElementById('last-name').value;
    const phoneNumber = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const company = document.getElementById('company-name').value;
    const fullName = `${firstName.trim()} ${lastName.trim()}`;
    const parsedId = parseInt(id);
    const recruiterData = {
        id: parsedId,
        name: fullName,
        phoneNumber: phoneNumber.trim(),
        email: email.trim(),
        company: company.trim()
    };
    // Submit directly to backend
    fetch('/api/Recruiter/signUp', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(recruiterData)
    })
        .then(res => {
            if (res.ok) {
                window.location.href = 'RecruiterSuccess.html';
            } else {
                return res.text().then(text => { throw new Error(text); });
            }
        })
        .catch(err => {
            showMessageBox('Registration Failed', err.message || 'An error occurred.');
        });
}

document.addEventListener('DOMContentLoaded', () => {
    const nextBtn = document.getElementById('nextBtn');
    if (nextBtn) {
        nextBtn.addEventListener('click', (event) => {
            event.preventDefault();
            handleRecruiterRegistration();
        });
    }
});