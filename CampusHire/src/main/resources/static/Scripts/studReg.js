// Add this at the top of your file
function showMessageBox(title, message) {
    alert(`${title}\n\n${message}`);
}

function prefillStudentForm() {
    const studentData = JSON.parse(sessionStorage.getItem('studentData'));
    if (studentData) {
        document.getElementById('student-id').value = studentData.id || '';
        const nameParts = (studentData.name || '').split(' ');
        document.getElementById('first-name').value = nameParts[0] || '';
        document.getElementById('last-name').value = nameParts.slice(1).join(' ') || '';
        document.getElementById('phone').value = studentData.phoneNumber || '';
        document.getElementById('age').value = studentData.age || '';
        document.getElementById('email').value = studentData.email || '';
        document.getElementById('subject').value = studentData.subject || '';
        document.getElementById('graduate-year').value = studentData.graduateYear || '';
    }
}

function saveStudentFormToSession() {
    const id = document.getElementById('student-id').value;
    const firstName = document.getElementById('first-name').value;
    const lastName = document.getElementById('last-name').value;
    const phoneNumber = document.getElementById('phone').value;
    const age = document.getElementById('age').value;
    const email = document.getElementById('email').value;
    const subject = document.getElementById('subject').value;
    const graduateYear = document.getElementById('graduate-year').value;
    const fullName = `${firstName.trim()} ${lastName.trim()}`;
    const parsedId = parseInt(id);
    const parsedAge = parseInt(age);
    const parsedGraduateYear = parseInt(graduateYear);
    const studentData = {
        id: parsedId,
        name: fullName,
        phoneNumber: phoneNumber.trim(),
        email: email.trim(),
        subject: subject.trim(),
        age: parsedAge,
        graduateYear: parsedGraduateYear
    };
    sessionStorage.setItem('studentData', JSON.stringify(studentData));
}

function validateStudentForm() {
    const id = document.getElementById('student-id').value;
    const firstName = document.getElementById('first-name').value;
    const lastName = document.getElementById('last-name').value;
    const phoneNumber = document.getElementById('phone').value;
    const age = document.getElementById('age').value;
    const email = document.getElementById('email').value;
    const subject = document.getElementById('subject').value;
    const graduateYear = document.getElementById('graduate-year').value;
    const fullName = `${firstName.trim()} ${lastName.trim()}`;
    if (!id || !firstName || !lastName || !phoneNumber || !age || !email || !subject || !graduateYear) {
        return false;
    }
    if (fullName.trim().length === 0) {
        return false;
    }
    const parsedId = parseInt(id);
    if (isNaN(parsedId) || parsedId <= 0 || id.length !== 9 || !/^\d{9}$/.test(id)) {
        return false;
    }
    const phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(phoneNumber)) {
        return false;
    }
    const parsedAge = parseInt(age);
    if (isNaN(parsedAge) || parsedAge < 18 || parsedAge > 99) {
        return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        return false;
    }
    if (subject.trim().length < 2) {
        return false;
    }
    const currentYear = new Date().getFullYear();
    const parsedGraduateYear = parseInt(graduateYear);
    if (isNaN(parsedGraduateYear) || parsedGraduateYear < currentYear || parsedGraduateYear > currentYear + 10) {
        return false;
    }
    return true;
}

function handleStudentRegistration() {
    if (!validateStudentForm()) {
        showMessageBox('Input Error', 'Please fill in all required fields correctly.');
        return;
    }
    saveStudentFormToSession();
    window.location.href = 'StudentTicket.html';
}

document.addEventListener('DOMContentLoaded', () => {
    prefillStudentForm();
    const nextBtn = document.getElementById('nextBtn');
    if (nextBtn) {
        nextBtn.addEventListener('click', (event) => {
            event.preventDefault();
            handleStudentRegistration();
        });
    }
});