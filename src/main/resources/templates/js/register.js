document.addEventListener("DOMContentLoaded", () => {
    const formRegister = document.getElementById("registerForm");
    //Gọi hàm register khi click button
    formRegister.addEventListener("submit", register);
});


function register(event) {  //ham register
    event.preventDefault(); //Giữ cho trình duyện k refresh

    if (!validate()) { //2 password khong giong nhau
        showMessageError(); //Show message loi password k trung
    } else { //pasword giong nhau => hop le
        hidMessageError(); //Xoa thong bao loi password khong trung khop
        registerRequest(); //Goi ham request den server
    }
}

function registerRequest(){
    const formData = new FormData(document.getElementById("registerForm"));

    const registerData = { //fill data tu form vao registerData
        name: formData.get("name"),
        username: formData.get("username"),
        password: formData.get("password"),
        phone: formData.get("phone"),
        email: formData.get("email"),
        gender: formData.get("gender"),
        birthday: formData.get("birthday"),
    };

    const apiUrl = "http://localhost:8080/api/user/register"; //Link api

    fetch(apiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(registerData),
    })
        .then((response) => {
            if (!response.ok) {
                // Lấy thông tin lỗi từ body của response
                return response.json().then((data) => {
                    throw data; // Truyền lỗi dạng object
                });
            }
            return response.json();
        })
        .then((data) => { //dang ky thanh cong
            if(!createCart(data)){
                alert("K them dc a");
            }else{
                localStorage.setItem("successMessageRegister", "Đăng ký thành công"); //luu
                localStorage.setItem("usn", registerData.username);
                localStorage.setItem("pwd", registerData.password);
                window.location.href = "../views/login.html";
            }
        })
        .catch((errors) => {
            // Hiển thị lỗi từ backend
            displayErrors(errors);
        });
}

function validate(){
    const password = document.getElementById("password").value;
    const passwordComfirm = document.getElementById("passwordcomfirm").value;
    return password === passwordComfirm;
}
function hidMessageError(){
    const passwordcomfirmME = document.getElementById("passwordcomfirmME");
    passwordcomfirmME.style.display = "none";

    const passwordcomfirm = document.getElementById("passwordcomfirm");
    passwordcomfirm.className = "form-control";
}

function showMessageError(){
    const passwordcomfirmME = document.getElementById("passwordcomfirmME");
    passwordcomfirmME.style.display = "block";
    passwordcomfirmME.style.color = "red"; // Tô màu đỏ cho thông báo lỗi
    passwordcomfirmME.textContent = "Mật khẩu không trùng khớp";

    const passwordcomfirm = document.getElementById("passwordcomfirm");
    passwordcomfirm.className = "form-control is-invalid";
}
function displayErrors(errors) {
    // Xóa các thông báo lỗi cũ
    const errorFields = ["username", "phone", "email"];
    errorFields.forEach(field => {
        const errorDiv = document.getElementById(`${field}ME`);
        const errorDivInput = document.getElementById(`${field}`);
        if (errorDiv) {
            errorDiv.textContent = "";
            errorDiv.style.display = "none";

            errorDivInput.className = "form-control";
        }
    });

    // Hiển thị lỗi mới
    for (const field in errors) {
        const errorDiv = document.getElementById(`${field}ME`);
        const errorDivInput = document.getElementById(`${field}`);
        if (errorDiv) {
            errorDiv.textContent = errors[field]; // Thông báo lỗi từ backend
            errorDiv.style.display = "block";
            errorDiv.style.color = "red"; // Tô màu đỏ cho thông báo lỗi
            errorDivInput.className = "form-control is-invalid";
        }
    }
}

function createCart(data) {
    const cartCreate = {
        user: data,
    };
    const apiUrl = "http://localhost:8080/api/cart/create";
    return fetch(apiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(cartCreate),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Failed to create cart");
            }
            return response.json(); // Parse the response body as JSON
        })
        .then((cart) => {
            console.log("Cart created successfully", cart);
            return true;
        })
        .catch((error) => {
            console.error("Error:", error); // Log any errors
            return false;
        });
}


