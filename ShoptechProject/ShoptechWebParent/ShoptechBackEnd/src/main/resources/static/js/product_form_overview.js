

dropdownBrands = $("#brand");
dropdownCategories = $("#category");

$(document).ready(function() {

	$("#shortDescription").richText();
	$("#fullDescription").richText();

	dropdownBrands.change(function() {
		dropdownCategories.empty();
		getCategories();

	});

	getCategoriesForNewForm();

});

function getCategoriesForNewForm() {
	catIdField = $("#categoryId");
	editMode = false;
	
	if(catIdField.length){
		editMode = true;
	}
	

	if(!editMode) getCategories();
}


function getCategories() {
	brandId = dropdownBrands.val();
	url = brandModuleURL + "/" + brandId + "/categories";

	$.get(url, function(responseJson) {

		$.each(responseJson, function(index, category) {
			$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
		});

	});
}

function checkUnique(form) {
	productId = $("#id").val();
	productName = $("#name").val();

	csrfValue = $("input[name='_csrf']").val();

	params = {id: productId, name: productName, _csrf: csrfValue};

	$.post(checkUniqueUrl, params, function(response) {
		if (response == "OK") {
			form.submit();
		} else if (response == "Duplicate") {
			swal({
				title: 'Thông báo',
				text:'Tên sản phẩm đã tồn tại ' + productName,
				icon:'warning',
				button:'OK'
			});
		} else {
			swal({
				title: 'Thông báo',
				text:'Không thể kết nối tới server',
				icon:'warning',
				button:'OK'
			});
		}

	}).fail(function() {
		swal({
			title: 'Thông báo',
			text:'Không thể kết nối tới server',
			icon:'warning',
			button:'OK'
		});
	});

	return false;
}
