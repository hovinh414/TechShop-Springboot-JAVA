$(document).ready(function() {

		$("#buttonCancel").on("click", function(){
			window.location = moduleURL;
		});

		$("#fileImage").change(function() {
			
			if(!checkFileSize(this)) {
				
				return;
			}
			
			showImageThumbnail(this);
		});
		$("#extraImage1").change(function() {

			if(!checkFileSize(this)) {

				return;
			}

			showExtraImageThumbnail(this);
			addNextExtraImageSection()
		});

	});
	function addNextExtraImageSection() {

	}
function showExtraImageThumbnail(fileInput){

	var file = fileInput.files[0];
	var reader = new FileReader();

	reader.onload = function(e){
		$("#extraThumbnail1").attr("src", e.target.result);
	};

	reader.readAsDataURL(file);
}
	function showImageThumbnail(fileInput){
		
		var file = fileInput.files[0];
		var reader = new FileReader();

		reader.onload = function(e){
			$("#thumbnail").attr("src", e.target.result);
		};
		

		reader.readAsDataURL(file);
	}
	
	function checkFileSize(fileInput){
		fileSize = fileInput.files[0].size;

		if(fileSize > MAX_FILE_SIZE) {
			fileInput.setCustomValidity("Bạn phải chọn ảnh có dung lượng < " + MAX_FILE_SIZE + " byes!");
			fileInput.reportValidity();
			
			return false;
		}else {
			fileInput.setCustomValidity("");
			
			return true;
		}
		
	}
	
	
	function showModalDialog(title, message) {
		$("#modalTitle").text(title);
		$("#modalBody").text(message);
		$("#modalDialog").modal();
	}

	function showErrorModal(message) {
		showModalDialog("Error", message);
	}
	
	function showWarningModal(message) {
		showModalDialog("Warning", message);
	}
	
	