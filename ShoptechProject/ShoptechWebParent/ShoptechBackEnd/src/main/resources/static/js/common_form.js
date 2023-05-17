$(document).ready(function() {
		$("#buttonCancel").on("click", function(){
			window.location = moduleURL;
		});
		
		/* display uploaded image in image tag, ensure file size is not more than 1MB */
		$("#fileImage").change(function() {
			
			if(!checkFileSize(this)) {
				
				return;
			}
			
			showImageThumbnail(this);
		});
		
	});
	
	function showImageThumbnail(fileInput){
		
		var file = fileInput.files[0];
		var reader = new FileReader();
		
		
		/* 
			the event.target property returns the html element that triggered an event
		*/
		reader.onload = function(e){
			$("#thumbnail").attr("src", e.target.result);
		};
		
		//image to base64 encoded string
		/*when the  readasDataURL() method completes reading the file 
		 successfully, the filereader returns an object with the result property; the filereader fires the load event
		*/
		reader.readAsDataURL(file);
	}
	
	function checkFileSize(fileInput){
		fileSize = fileInput.files[0].size;
			
		//more than 1 MEGABYTE = 1024 * 1024 = 1048576 bytes
		//prevent the form from being submitted to the server
		if(fileSize > MAX_FILE_SIZE) {
			fileInput.setCustomValidity("you must chose an image  less than " + MAX_FILE_SIZE + " byes!");
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
	
	