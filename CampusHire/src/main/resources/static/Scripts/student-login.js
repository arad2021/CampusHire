// Add loading state management
function setLoading(isLoading) {
    const submitButton = document.querySelector('.login-btn');
    const errorElement = document.getElementById('loginError');

    if (isLoading) {
        submitButton.disabled = true;
        submitButton.innerHTML = '<span class="loading-spinner"></span> Signing in...';
        errorElement.style.display = 'none';
    } else {
        submitButton.disabled = false;
        submitButton.textContent = 'Sign In';
    }
}

// Validate student ID format (assuming it should be a number)
function validateStudentId(id) {
    return /^\d+$/.test(id) && id.length >= 5; // Adjust length requirement as needed
}

document.getElementById('loginForm').onsubmit = async function (e) {
    e.preventDefault();
    const errorElement = document.getElementById('loginError');
    const studentId = document.getElementById('studentIdInput').value.trim();

    // Input validation
    if (!validateStudentId(studentId)) {
        errorElement.style.display = 'block';
        errorElement.textContent = 'Please enter a valid student ID (numbers only, minimum 5 digits)';
        return;
    }

    setLoading(true);

    try {
        const res = await fetch(`/api/Student/SignIn/${studentId}`);
        if (!res.ok) {
            throw new Error('Network response was not ok');
        }
        const result = await res.text();

        if (result === "ok") {
            localStorage.setItem('studentId', studentId);
            window.location.href = 'student-portal.html';
        } else {
            errorElement.style.display = 'block';
            errorElement.textContent = 'Invalid ID. Please try again.';
        }
    } catch (error) {
        errorElement.style.display = 'block';
        errorElement.textContent = 'An error occurred. Please try again later.';
        console.error('Login error:', error);
    } finally {
        setLoading(false);
    }
}; 