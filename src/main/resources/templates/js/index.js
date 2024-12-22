document.addEventListener("DOMContentLoaded", () => {
    checkSession();
    messageLogin();
});

function checkSession() {
    let sessionUserId = sessionStorage.getItem('userId');
    if (!sessionUserId) {
        const menuItems = [
            {text: 'Đăng nhập', link: '../views/login.html'},
            {text: 'Đăng ký', link: '../views/register.html'}
        ];
        showNavBar(menuItems);
    }else{
        const navbar = document.getElementById("navbar");
        navbar.innerHTML = originalNavbarHTML;
    }
}
function logout(){
    localStorage.clear();
    sessionStorage.clear();
    window.location.href = "../views/index.html";
}
function showNavBar(menuItems){
    const navbar = document.getElementById("navbar");
    // Loop through the menu items and create li elements
    navbar.innerHTML = "";
    menuItems.forEach(item => {
        // Create li element
        const li = document.createElement('li');
        li.classList.add('nav-item');

        // Create anchor element
        const a = document.createElement('a');
        a.classList.add('nav-link');
        a.href = item.link
        a.textContent = item.text;

        // Append the anchor to li
        li.appendChild(a);

        // Append li to navbar
        navbar.appendChild(li);
    })
}
function showCart() {
    const cartBackdrop = document.getElementById("cartBackdrop");
    const cartDialog = document.getElementById("cartDialog");
    cartBackdrop.classList.add("open");
    cartDialog.classList.add("open");
}

function closeCart() {
    const cartBackdrop = document.getElementById("cartBackdrop");
    const cartDialog = document.getElementById("cartDialog");
    cartBackdrop.classList.remove("open");
    cartDialog.classList.remove("open");
}

function messageLogin() {
    const message = localStorage.getItem("successMessage"); // Retrieve message from localStorage
    if (message) {
        const messageDiv = document.getElementById('message');
        messageDiv.style.display = "inline-flex";
        messageDiv.style.justifyContent = 'center'; // Center the message horizontally
        messageDiv.style.alignItems = 'center'; // Center the message vertically
        messageDiv.className = "alert alert-success text-center position-absolute top-0 start-50 translate-middle-x"; // Bootstrap class for success alert
        messageDiv.style.marginTop = '50px';
        messageDiv.textContent = message; // Set the message text
        localStorage.removeItem('successMessage'); // Clear the message after displaying
    }
    setTimeout(function () {
        const successMessage = document.getElementById('message');
        if (successMessage) {
            successMessage.style.display = 'none'; // Hide the message after 3 seconds
        }
    }, 3000);
}

let prevScrollPos = window.pageYOffset; // Lấy vị trí cuộn hiện tại

window.onscroll = function () {
    let currentScrollPos = window.pageYOffset; // Lấy vị trí cuộn mới

    if (prevScrollPos > currentScrollPos) {
        // Khi cuộn lên, hiện header
        document.getElementById("navHeader").style.top = "0";
    } else {
        // Khi cuộn xuống, ẩn header
        document.getElementById("navHeader").style.top = "-60px"; // Ẩn header
    }

    prevScrollPos = currentScrollPos; // Cập nhật vị trí cuộn trước đó
}

    window.onload = function () {
    let currentSlide = 0;
    const slides = document.querySelectorAll('.banner-slide');
    const totalSlides = slides.length;

    function showSlide(index) {
    slides.forEach(slide => slide.classList.remove('active'));
    slides[index].classList.add('active');
}

    function nextSlide() {
    currentSlide = (currentSlide + 1) % totalSlides;
    showSlide(currentSlide);
}

    if (totalSlides > 1) {
    showSlide(currentSlide);
    setInterval(nextSlide, 2000); // Change image every 4 seconds
}
};