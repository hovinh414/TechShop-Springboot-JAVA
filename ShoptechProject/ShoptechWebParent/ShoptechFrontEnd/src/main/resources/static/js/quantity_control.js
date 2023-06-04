$(document).ready(function() {
	$(".linkMinus").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val()) - 1;
		
		if (newQuantity > 0) {
			quantityInput.val(newQuantity);
		} else {
			swal({
				title: 'Thông báo',
				text:'Số lượng phải lớn hơn 0 ',
				icon:'warning',
				button:'OK'
			});
		}
	});
	
	$(".linkPlus").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val()) + 1;
		
		if (newQuantity <= 10) {
			quantityInput.val(newQuantity);
		} else {
			swal({
				title: 'Thông báo',
				text:'Số lượng phải bé hơn 10 ',
				icon:'warning',
				button:'OK'
			});
		}
	});	
});