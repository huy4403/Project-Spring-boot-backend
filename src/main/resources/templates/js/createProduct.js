document.addEventListener("DOMContentLoaded", () => {
    const productForm = document.getElementById("productForm");
    //Gọi hàm register khi click button
    productForm.addEventListener("submit", createProduct);
});

function createProduct(event){
    event.preventDefault();
    // Lấy giá trị từ form
    const name = document.getElementById("name").value;
    const quantity = parseInt(document.getElementById("quantity").value, 10);
    const importPrice = parseInt(document.getElementById("importPrice").value, 10);
    const price = parseInt(document.getElementById("price").value, 10);
    const description = document.getElementById("description").value;
    const category = document.getElementById("category").value;
    const file = document.getElementById("file").files[0];

    const categorySelect = {
        id: category
    };
    const productCreate = {
        name: name,
        quantity: quantity,
        importPrice: importPrice,
        price: price,
        description: description,
        category: categorySelect,
    };
    console.log(productCreate);

    const formData = new FormData();
    formData.append("product", JSON.stringify(productCreate));
    formData.append("file", file);  // Giả sử bạn có một input file với tên "file"
    console.log(formData);

// Gửi yêu cầu POST với FormData
    const apiUrl = "http://localhost:8080/api/product";
    fetch(apiUrl, {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (response.ok) {
                console.log(response);
                return response.json(); // Parse the JSON response if successful
            } else {
                throw new Error('Network response was not ok');
            }
        })
        .then(data => {
            console.log('Success:', data); // Handle the response data here
        })
        .catch(error => {
            console.error('Error:', error); // Handle any errors
        });
}
