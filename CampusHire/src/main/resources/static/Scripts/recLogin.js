document.getElementById('recruiterLoginForm').onsubmit = async function (e) {
    e.preventDefault();
    const recruiterId = document.getElementById('recruiter-id').value.trim();
    if (!/^\d+$/.test(recruiterId)) {
        alert('Please enter a valid recruiter ID (numbers only)');
        return;
    }
    try {
        const res = await fetch(`/api/Recruiter/SignIn/${recruiterId}`);
        if (!res.ok) throw new Error('Network response was not ok');
        const result = await res.text();
        if (result === "ok") {
            localStorage.setItem('recruiterId', recruiterId);
            window.location.href = 'recruiter-portal.html';
        } else {
            alert('Invalid ID. Please try again.');
        }
    } catch (error) {
        alert('An error occurred. Please try again later.');
        console.error('Login error:', error);
    }
}; 