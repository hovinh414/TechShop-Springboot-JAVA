/**
 * 
 */
$(document).ready(function(){
	
	$("a[name='linkRemoveDetail']").each(function(index){
		
		$(this).click(function(){
			removeDetailSectionByIndex(index);
		});
		
	});

});

 function addNextDetailSection() {
	 
	 //select all elements starting with the id = "divDetail"
	allDivDetails = $("[id^='divDetail']");
	divDetailsCount = allDivDetails.length;
	
	htmlDetailSection = `
		<div class="form-inline" id="divDetail${divDetailsCount}">
			<input type="hidden" name="detailIDs" value="0" /> 
			<label class="m-3">Name:</label>
			<input style="color: white" type="text" class="form-control" name="detailNames" maxlength="255"/>
			<label class="m-3">Value:</label>
			<input style="color: white" type="text" class="form-control" name="detailValues" maxlength="255"/>
		</div>	
	`;
	
	$("#divProductDetails").append(htmlDetailSection);

	previousDivDetailSection = allDivDetails.last();
	previousDivDetailID = previousDivDetailSection.attr("id");
	 	
	htmlLinkRemove = `
		<a class=" btn btn-sm btn-danger" 
			href="javascript:removeDetailSectionById('${previousDivDetailID}')"
		title="Xóa chi tiết sản phẩm">Xóa chi tiết</a>`;
	
	previousDivDetailSection.append(htmlLinkRemove);
	
	$("input[name='detailNames']").last().focus();
}

function removeDetailSectionById(id) {
	$("#" + id).remove();
}

function removeDetailSectionByIndex(index) {
	$("#divDetail" + index).remove();
}
