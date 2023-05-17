
var extraImagesCount = 0;

$(document).ready(function(){
	
	$("input[name='extraImage']").each(function(index){
		
		extraImagesCount++;
		
		$(this).change(function(){
			if(!checkFileSize(this)) {
				
				return;
			}
			showExtraImageThumbnail(this, index);
			
		});
	});
	
	
	$("a[name='linkRemoveExtraImage']").each(function(index){
		
		$(this).click(function(){
			removeExtraImage(index);
		});
		
	});

});

function showExtraImageThumbnail(fileInput, index) {
	
	var file = fileInput.files[0];
	
	fileName = file.name;
	
	/*when image is changed, change the hidden input value*/
	imageNameHiddenField = $("#imageName" + index);
	if(imageNameHiddenField.length){
		imageNameHiddenField.val(fileName);
	}
	
	var reader = new FileReader();
	/* 
		the event.target property returns the html element that triggered an event
	*/
	reader.onload = function(e){
		$("#extraThumbnail" + index).attr("src", e.target.result);
	};
	
	//image to base64 encoded string
	/*when the  readasDataURL() method completes reading the file 
	 successfully, the filereader returns an object with the result property; the filereader fires the load event
	*/
	reader.readAsDataURL(file);
	
	if (index >= extraImagesCount - 1) {
		addNextExtraImageSection(index + 1);		
	}
	
}

function addNextExtraImageSection(index){
	
	htmlExtraImage = `
		<div class="col border m-3 p-2" id="divExtraImage${index}">
			<div id="extraImageHeader${index}"><label>Extra Image #${index + 1}: </label></div>
			<div class="m-2">
				<img id="extraThumbnail${index}" alt="Extra Image #${index + 1} preview" class="img-fluid"
					src="${defaultImageThumbnailSrc}"/>
			</div>
			<div>
				<input type="file"  name="extraImage"
					onchange="showExtraImageThumbnail(this, ${index})"
					accept="image/png, image/jpeg" />
			</div>
		
		</div>	
	`;
	
	htmlLinkRemove = `
	<a class="btn fas fa-times-circle fa-2x icon-dark float-right"
		href="javascript:removeExtraImage(${index - 1})" 
		title="Remove this image"></a>
	`;

	$("#divProductImages").append(htmlExtraImage);
	
	$("#extraImageHeader" + (index - 1)).append(htmlLinkRemove);
	
	extraImagesCount++;
}

function removeExtraImage(index) {
	$("#divExtraImage" + index).remove();
}


