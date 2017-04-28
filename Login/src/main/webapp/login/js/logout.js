"use strict";

$( '#logout' ).live( 'pagebeforeshow',function(event){
	console.log('logout user '  + sessionStorage.getItem('userName'));

	$.ajax({
		async: false,
		contentType: 'application/json',
   		dataType: 'json',
  		error: function(xhr, status, error) {
  		    alert(xhr.responseText);
  		 },
    	headers: {
			'Content-Type':'application/json'
		},
		success: function(data, status, xhr){
			if (xhr.status == 200) {
				sessionStorage.clear();
			}
		},
		type: 'DELETE',
   		url: srvURL + '/logout/account/' + sessionStorage.getItem('userName')
	});
});


function main () {
	window.location.href = maiURL;
}
