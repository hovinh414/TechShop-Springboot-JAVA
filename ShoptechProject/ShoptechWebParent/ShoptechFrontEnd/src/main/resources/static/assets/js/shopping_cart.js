decimalSeparator = decimalPointType == 'COMMA' ? ',' : '.';
thousandsSeparator = thousandsPointType == 'COMMA' ? ',' : '.';

$(document).ready(function () {
    $(".linkMinus").on("click", function (evt) {
        evt.preventDefault();
        decreaseQuantity($(this));
    });

    $(".linkPlus").on("click", function (evt) {
        evt.preventDefault();
        increaseQuantity($(this));
    });

    $(".linkRemove").on("click", function (evt) {
        evt.preventDefault();
        removeProduct($(this));
    });
});

function decreaseQuantity(link) {
    productId = link.attr("pid");
    quantityInput = $("#quantity" + productId);
    newQuantity = parseInt(quantityInput.val()) - 1;

    if (newQuantity > 0) {
        quantityInput.val(newQuantity);
        updateQuantity(productId, newQuantity);
    } else {
        swal({
            title: 'Notification',
            text: 'Minimum quantity is 1' + catName,
            icon: 'warning',
            button: 'Close'
        });
    }
}

function increaseQuantity(link) {
    productId = link.attr("pid");
    quantityInput = $("#quantity" + productId);
    newQuantity = parseInt(quantityInput.val()) + 1;

    if (newQuantity <= 10) {
        quantityInput.val(newQuantity);
        updateQuantity(productId, newQuantity);
    } else {
        swal({
            title: 'Notification',
            text: 'Maximum quantity is 10' + catName,
            icon: 'warning',
            button: 'Close'
        });
    }
}

function updateQuantity(productId, quantity) {
    url = contextPath + "cart/update/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function (updatedSubtotal) {
        updateSubtotal(updatedSubtotal, productId);
        updateTotal();
    }).fail(function () {
        //showErrorModal("Error while updating product quantity.");
		swal({
			title: 'Notification',
			text: 'Error while updating product quantity.',
			icon: 'warning',
			button: 'Close'
		});
    });
}

function updateSubtotal(updatedSubtotal, productId) {
    $("#subtotal" + productId).text(updatedSubtotal+'$');
}

function updateTotal() {
    total = 0.0;
    productCount = 0;

    $(".subtotal").each(function(index, element) {
        productCount++;
        total += parseFloat(element.innerHTML);
    });

    if (productCount < 1) {
        showEmptyShoppingCart();
    } else {
        $("#total").text(formatCurrency(total)+'$');
    }

}

function showEmptyShoppingCart() {
    $("#sectionTotal").hide();
    $("#sectionEmptyCartMessage").removeClass("d-none");
}

function removeProduct(link) {
    url = link.attr("href");

    $.ajax({
        type: "DELETE",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function (response) {
        rowNumber = link.attr("rowNumber");
        removeProductHTML(rowNumber);
        updateTotal();
        updateCountNumbers();

        //showModalDialog("Shopping Cart", response);
		swal({
			title: 'Notification',
			text: response,
			icon: 'warning',
			button: 'Close'
		});

    }).fail(function () {
        //showErrorModal("Error while removing product.");
		swal({
			title: 'Notification',
			text: 'Error while removing product.',
			icon: 'warning',
			button: 'Close'
		});
    });
}

function removeProductHTML(rowNumber) {
    $("#row" + rowNumber).remove();
    $("#blankLine" + rowNumber).remove();
}

function updateCountNumbers() {
    $(".divCount").each(function (index, element) {
        element.innerHTML = "" + (index + 1);
    });
}


function formatCurrency(amount) {
    return $.number(amount, 2);
}

function clearCurrencyFormat(numberString) {
    result = numberString.replaceAll(thousandsSeparator, "");
    return result.replaceAll(decimalSeparator, ".");
}
