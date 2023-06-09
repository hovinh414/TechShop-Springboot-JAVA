

dropdownBrands = $("#brand");
dropdownCategories = $("#category");

$(document).ready(function() {

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
				title: 'Notification',
				text:'There is another product having the name ' + productName,
				icon:'warning',
				button:'Close'
			});
		} else {
			swal({
				title: 'Notification',
				text:'Unknown response from server',
				icon:'warning',
				button:'Close'
			});
		}

	}).fail(function() {
		swal({
			title: 'Notification',
			text:'Unknown response from server',
			icon:'warning',
			button:'Close'
		});
	});

	return false;
}
