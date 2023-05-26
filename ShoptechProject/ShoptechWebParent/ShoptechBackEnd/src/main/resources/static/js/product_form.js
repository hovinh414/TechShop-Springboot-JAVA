var extraImagesCount = 0;
$(document).ready(function() {

    $("input[name='extraImage']").each(function (index) {
        extraImagesCount++;
        $(this).change(function () {

            showExtraImageThumbnail(this, index);
        });
    });
    $("a[name='linkRemoveExtraImage']").each(function(index){

        $(this).click(function(){
            removeExtraImage(index);
        });

    });
});
function showExtraImageThumbnail(fileInput, index){

    var file = fileInput.files[0];
    var reader = new FileReader();

    reader.onload = function(e){
        $("#extraThumbnail" + index).attr("src", e.target.result);
    };

    reader.readAsDataURL(file);
    if (index >= extraImagesCount -1){
        addNextExtraImageSection(index + 1);
    }

}
function addNextExtraImageSection(index) {
    htmlExtraImage = ` 
        <div class="col border m-3 p-2" id="divExtraImage${index}">
      <div id="extraImageHeader${index}"><label>Ảnh sản pẩm #${index + 1}:</label></div>
      <div class="m-4">
        <img id="extraThumbnail${index}" alt="Ảnh sản phẩm #${index + 1}" style="width: 100px" src="${defaultImageThumbnailSrc}"/>
      </div>
      <div>
        <input type="file" name="extraImage" 
        onchange="showExtraImageThumbnail(this, ${index})"
        accept="image/png, image/jpg"/>
      </div>
    </div>`;
    htmlLinkRemove = `
        <a class=" btn btn-sm btn-danger" 
        href="javascript:removeExtraImage(${index - 1})"
        title="Xóa ảnh">Xóa ảnh</a>`;
    $("#divProductImages").append(htmlExtraImage);
    $("#extraImageHeader" + (index -1)).append(htmlLinkRemove);
    extraImagesCount++;
}

function removeExtraImage(index){
    $("#divExtraImage" + index).remove();
}
function checkFileSize(fileInput){
    fileSize = fileInput.files[0].size;

    if(fileSize > MAX_FILE_SIZE) {
        fileInput.setCustomValidity("you must chose an image  less than " + MAX_FILE_SIZE + " byes!");
        fileInput.reportValidity();

        return false;
    }else {
        fileInput.setCustomValidity("");

        return true;
    }

}


