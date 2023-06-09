$(document).ready(function () {
    $("#buttonAdd2Cart").on("click", function (evt) {
        addToCart();
    });
});

function addToCart() {
    quantity = $("#quantity" + productId).val();
    url = contextPath + "cart/add/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function (response) {
        swal({
            title: 'Thông báo',
            text: response,
            icon: 'warning',
            button: 'OK'
        });
    }).fail(function () {
        swal({
            title: 'Thông báo',
            text: ' Lỗi khi thêm sản phẩm vào giỏ hàng.',
            icon: 'warning',
            button: 'OK'
        });
    });
}