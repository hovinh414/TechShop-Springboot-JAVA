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
        if (response === ' You must login to add this product to cart.')
        {
            swal({
                title: 'Notification',
                text: response,
                icon: 'info',
                button: 'Close'
            });
        } else{
            swal({
                title: 'Notification',
                text: response,
                icon: 'success',
                button: 'Close'
            });
        }

    }).fail(function () {
        swal({
            title: 'Notification',
            text: ' Error when adding product to cart.',
            icon: 'error',
            button: 'Close'
        });
    });
}