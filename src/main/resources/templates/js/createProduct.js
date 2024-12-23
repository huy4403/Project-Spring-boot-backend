document.addEventListener("DOMContentLoaded", () => {
    const productForm = document.getElementById("productForm");
    //Gọi hàm register khi click button
    productForm.addEventListener("submit", createProduct);
    loadCategories();
});

function createProduct(event){
    event.preventDefault();

    const quantity = document.getElementById("quantity").value;
    const price = document.getElementById("price").value;
    const importPrice = document.getElementById("importPrice").value;

    quantity == 0 ? showMessageInteger("quantity") : hideMessageInteger("quantity");
    price == 0 ? showMessageInteger("price") : hideMessageInteger("price");
    importPrice == 0 ? showMessageInteger("importPrice") : hideMessageInteger("importPrice");

    if(quantity == 0 || price == 0 || importPrice == 0){
        return;
    }
    if(checkPrice()){
        showMessagePriceError();
    }else{
        hidMessagePriceError();

        const formData = new FormData(productForm);
        const categoryId = formData.get("category");
        const category = {
            id: categoryId
        };
        formData.append("category", JSON.stringify(category));
        console.log(formData);
        // Gửi yêu cầu POST với FormData
        const apiUrl = "http://localhost:8080/api/product";
        fetch(apiUrl, {
            method: 'POST',
            body: formData,
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then((data) => {
                        throw data; // Truyền lỗi dạng object
                    });
                }
                return response.json();
            })
            .then(data => {
                //sure
                msgSucces("Sản phẩm đã được tạo");
                resset();
            })
            .catch(errors => {
                msgError(errors.message);
            });
    }
}

function resset(){
    document.getElementById("name").value = "";
    document.getElementById("importPrice").value = "";
    document.getElementById("price").value = "";
    document.getElementById("quantity").value = "";
    document.getElementById("category").value = "";
    document.getElementById("description").value = "";
    document.getElementById("file").value = null;
}

function loadCategories(){
    const selectDanhMuc = document.getElementById("category");
    const apiUrl = "http://localhost:8080/api/category/get-all";
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            data.forEach(category => {
                const option = document.createElement("option");
                option.value = category.id;
                option.textContent = category.name;
                selectDanhMuc.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching category data:', error);
        });
}

function checkPrice(){
    const importPrice = document.getElementById("importPrice").value;
    const price = document.getElementById("price").value;

    let importPriceInt = parseInt(importPrice);
    let priceInt = parseInt(price);
    return importPriceInt > priceInt;
}
function hidMessagePriceError(){
    const priceME = document.getElementById("priceME");
    priceME.style.display = "none";
    const price = document.getElementById("price");
    price.className = "form-control";
}

function showMessagePriceError(){
    const priceME = document.getElementById("priceME");
    priceME.style.display = "block";
    priceME.style.color = "red"; // Tô màu đỏ cho thông báo lỗi
    priceME.textContent = "Giá bán phải lớn hơn giá nhập";

    const price = document.getElementById("price");
    price.className = "form-control is-invalid";
}

function showMessageInteger(field){
    const elements = {};
    elements[`${field}ME`] = document.getElementById(`${field}ME`);
    elements[`${field}ME`].style.display = "block";
    elements[`${field}ME`].style.color = "red"; // Tô màu đỏ cho thông báo lỗi
    elements[`${field}ME`].textContent = "Trường dữ liệu này không được bằng 0";

    const inputType = document.getElementById(`${field}`);
    inputType.className = "form-control is-invalid";
}

function hideMessageInteger(field){
    const elements = {};
    elements[`${field}ME`] = document.getElementById(`${field}ME`);
    elements[`${field}ME`].style.display = "none";
    elements[`${field}ME`].textContent = "";

    const inputType = document.getElementById(`${field}`);
    inputType.className = "form-control";
}

function msgError(msg){
    const messageError = document.getElementById("messageError");
    messageError.style.display = "block";
    messageError.className = "alert alert-danger";
    messageError.style.textAlign = "center";
    messageError.textContent = msg;
    setTimeout(function() {
        const messageError = document.getElementById('messageError');
        if (messageError) {
            messageError.style.display = 'none'; // Hide the message after 3 seconds
        }
    }, 3000);
}

function msgSucces(msg){
    const messageError = document.getElementById("messageError");
    messageError.style.display = "block";
    messageError.className = "alert alert-success";
    messageError.style.textAlign = "center";
    messageError.textContent = msg;
    setTimeout(function() {
        const messageError = document.getElementById('messageError');
        if (messageError) {
            messageError.style.display = 'none'; // Hide the message after 3 seconds
        }
    }, 3000);
}