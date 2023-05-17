$(document).ready(function(){
		$("#logoutLink").on("click", function(e){
			e.preventDefault(); /* stop the hyperlink default behaviour  */
			
			/* use  the form to  log out and navigate to the url path /logout  
			
			 NOTE: spring security requires a POST request form to logout in order to obtain the csrf token, 
			 		the csrf token is a hidden property in the form
			*/
			document.logoutForm.submit();
		});
		
		customizeDropDownMenu();
		customizeTabs();
	});
	
	
function customizeDropDownMenu() {
	
	$(".navbar .dropdown").hover(
		function() {
			$(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
		},
		function() {
			$(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
		}
	);
	
	$(".dropdown > a").click(function() {
		location.href = this.href;
	});
	
	
function customizeTabs() {
	// Javascript to enable link to tab
	var url = document.location.toString();
	if (url.match('#')) {
	    $('.nav-tabs a[href="#' + url.split('#')[1] + '"]').tab('show');
	} 

	// Change hash for page-reload
	$('.nav-tabs a').on('shown.bs.tab', function (e) {
	    window.location.hash = e.target.hash;
	})	
}	
	
}
	