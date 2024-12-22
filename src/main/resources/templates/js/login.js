document.addEventListener("DOMContentLoaded", () => {
    messageRegister();
    const formLogin = document.getElementById("loginForm");
    formLogin.addEventListener("submit", login);
});

function login(event){
    event.preventDefault();

    if(!checkPassword()){
        displayMessage();
    }else {
        hideMessage();
        requestLogin();
    }
}

function checkPassword(){
    const passwordInput = document.getElementById("password").value;
    return passwordInput.length >= 5;
}
function displayMessage(){
    const password = document.getElementById("password");
    const messagePassword = document.getElementById("messagePassword");
    messagePassword.style.display = "block";
    messagePassword.style.color = "red";
    messagePassword.textContent = "Mật khẩu phải lớn hơn 5 ký tự"
    password.className = "form-control is-invalid"
}
function hideMessage(){
    const password = document.getElementById("password");
    const messagePassword = document.getElementById("messagePassword");
    messagePassword.style.display = "none";
    messagePassword.textContent = "";
    password.className = "form-control";
}

function requestLogin(){
    const formData = new FormData(loginForm);
    const loginData = {
        username: formData.get("username"),
        password: formData.get("password"),
    };

    const apiUrl = "http://localhost:8080/api/user/login";

    fetch(apiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
    })
        .then((response) => {
            if (!response.ok) {
                return response.json().then((data) => {
                    throw new Error(data.message || "Unknown error occurred");
                });
            }
            return response.json();
        })
        .then((data) => {
            if (data) {
                sessionStorage.setItem('userId', data.id);
                localStorage.setItem('successMessage', "Đăng nhập thành công");
                window.location.href = "index.html"; // Redirect to the dashboard
            } else {
                throw new Error("Invalid login data received");
            }
        })
        .catch((error) => {
            showLoginFail(error.message);
        });
}

function messageRegister(){
    const message = localStorage.getItem("successMessageRegister"); // Retrieve message from localStorage
    const usn = localStorage.getItem("usn");
    const pwd = localStorage.getItem("pwd");
    if (message) {
        const messageDiv = document.getElementById('message');

        const username = document.getElementById('username');
        const password = document.getElementById('password');
        username.value = usn;
        password.value = pwd;
        messageDiv.style.display = "inline-flex";
        messageDiv.style.justifyContent = 'center'; // Center the message horizontally
        messageDiv.style.alignItems = 'center'; // Center the message vertically
        messageDiv.className = "alert alert-success text-center position-absolute top-0 start-50 translate-middle-x"; // Bootstrap class for success alert
        messageDiv.style.marginTop = '50px';
        messageDiv.textContent = message; // Set the message text
        localStorage.removeItem('usn');
        localStorage.removeItem('pwd');
        localStorage.removeItem('successMessageRegister'); // Clear the message after displaying
    }
    setTimeout(function() {
        const successMessage = document.getElementById('message');
        if (successMessage) {
            successMessage.style.display = 'none'; // Hide the message after 3 seconds
        }
    }, 3000);
}

function showLoginFail(message) {
    const messageError = document.getElementById("messageError");
    messageError.style.display = "inline-flex";
    messageError.style.justifyContent = "center";
    messageError.style.alignItems = "center";
    messageError.className = "alert alert-danger text-center";
    messageError.textContent = message;
}
