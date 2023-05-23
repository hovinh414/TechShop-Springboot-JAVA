$(document).ready(function() {

    $("input[name='extraImage']").each(function (index) {
        $(this).change(function () {
            showExtraImageThumbnail(this, index);
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
    addNextExtraImageSection(index + 1);
}
function addNextExtraImageSection(index) {
    html = ` 
        <div class="col border m-3 p-2">
      <div><label>Ảnh sản pẩm #${index + 1}:</label></div>
      <div class="m-4">
        <img id="extraThumbnail${index}" alt="Ảnh sản phẩm #${index + 1}" src="${defaultImageThumbnailSrc}"/>
      </div>
      <div>
        <input type="file" name="extraImage" 
        onchange="showExtraImageThumbnail(this, ${index})"
        accept="image/png, image/jpg"/>
      </div>
    </div>`;
    $("#divProductImages").append(html);
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


