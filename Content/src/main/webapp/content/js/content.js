var accessKeyID = null;
var expirationTime = null;
var secretAccessKey = null;
var secretAccessKey = null;
var sessionToken = null;
var showDelete = null;
var showEdit = null;
var showLogout = null;

$(function(){
	$('#new-picture-file').customFileInput();	
});

function deleteArea() {
	console.log('delete area [' + sessionStorage.getItem('areaIndex') + ']');

	$.ajax({
		async: false,
		contentType: 'application/json',
   		dataType: 'json',
  		error: function(xhr, status, error) {
  		    alert(xhr.responseText);
  		 },
   		headers: {
         'Authorization':'AWS '
   		},  
		type: 'DELETE',
		success: function(data, status, xhr){
			if (xhr.status == 200) {
			}
		},
   		url: srvURL + '/area/' + sessionStorage.getItem('areaIndex')
	});
}

function editArea() {
	var areaName;
	var areaNameLbl;
	var areaNameTxt;
	
	formObject  = document.forms['rename-area-form'];
	
	areaNameTxt = formObject.elements['rename-area-area-name-text'];

	areaNameLbl = document.getElementById('rename-area-area-name-label');

	areaName    = areaNameTxt.value;
	
	if (!areaName || areaName.length < 6) {
		areaNameLbl.innerHTML = 'Der Areaname muss mindestens 6 Zeichen lang sein';
		areaNameLbl.style.color = '#FF0000';
		areaNameTxt.focus();
		
		return;
	}

	sessionStorage.setItem ('areaName', areaName);
	
	console.log('edit area [' + areaName + ']');

	$.mobile.changePage('#rename-area-dialog', 'pop', true, true);
}

function logout () {
	window.location = lgoURL;
}

function newArea() {
	var areaName;
	var areaNameLbl;
	var areaNameTxt;
	var body;
	var bodyHash;

	var date;
	var header;
	var request;
	var requestHash;
	var signature;
	var signatureKey;
	var shaObj;
	var stringToSign;
	
	var now = new Date(new Date().getTime() - 3600000); 

	var day = now.getDate() < 10 ? '0' + now.getDate() : now.getDate();
	var mon = now.getMonth() < 9 ? '0' + (now.getMonth()+1) : (now.getMonth()+1);
	var year = now.getFullYear();
	var hour = now.getHours() < 10 ? '0' + now.getHours() : now.getHours();
	var min = now.getMinutes() < 10 ? '0' + now.getMinutes() : now.getMinutes();
	var sec = now.getSeconds() < 10 ? '0' + now.getSeconds() : now.getSeconds();
	
	var date = year+mon+day
	var datetime = date + "T" + hour + min + sec + "Z";
/*
date = "20170214";
datetime = "20170214T145248Z";
	
accessKeyID = "ASIAIXECFZCB6O3OQAYQ";
secretAccessKey = "tWRC6mP137+l7lo4FFaGhY1jlajuUI1tqBm1EcQj";
sessionToken = "AgoGb3JpZ2luEGsaDGV1LWNlbnRyYWwtMSKAAiMzmQjCZiG9hUdEQxpkE8XYb6R8HB3M1nm/oP+aiXvYE2sJur51QNhPC4LaoOjz1SfVh0HjHaw7TMuIcScwPuC5KcXlI2XyTGl9sjbGHrQvHBJU1ziDvDKWFxX4omu26FKFXbnGz4MZlGVBvygO6GaUaEUE2fdSHhixHmUMRNS65pI3DhZGrrIdJwPa1kr34ULOFiwY6TNDuBB/b1ReRdKQCloilZdJ+GS6EzKsIjFMKSOsMz3jivQ60iXZgLMzwZzIS3qvF1lCViAo7bX/G64LuDMCMKg1D9SaovB8J+aOJS+IHB4vLbXOql3F0shNuWQFxWHtU+H9eYE3Pw+XJoQq1wEI8P//////////ARAAGgw1OTc1MzU0OTk0MjUiDNTd4yTFNHbHBLd5ZSqrATNYZ2ZLEVLNNBcB9XZUoQ/OiD0eYKfaNbbMWgPfLkBCcjDpxL1qrldpMUZBgd8NdRJxZ6SxwPu++yAJbGURQ8x2o0hXVXvHUGQCayrWViwSWMKy6cYWtKkg1bfCqc68zSraSujXAyC1lw6j71eUlKYt0tjpTaIKw6ckA3qIa7vlX7ZdaNSQGr3oClEmjxs/EraktBfy3Tj7Igm5Rqd+DW7h1T1xpXkWzHqtTjDAs4zFBQ==";
*/
	formObject  = document.forms['new-area-form'];
	
	areaNameTxt = formObject.elements['new-area-area-name-text'];

	areaNameLbl = document.getElementById('new-area-area-name-label');

	areaName    = areaNameTxt.value;
	
	if (!areaName || areaName.length < 6) {
		areaNameLbl.innerHTML = 'Der Areaname muss mindestens 6 Zeichen lang sein';
		areaNameLbl.style.color = '#FF0000';
		areaNameTxt.focus();
		
		return;
	}

	sessionStorage.setItem ('areaName', areaName);
	
	console.log('add new area [' + areaName + ']');

	body = '{"atos":[{"id":null, "name":"' + areaName + '", "folder":null, "number":0, "size":0, "date":null, "version":null}]}';
	
	shaObj = new jsSHA("SHA-256", "TEXT");
	shaObj.update(body);
	bodyHash = shaObj.getHash("HEX");

	request = 'POST\n' +
			  '/prd/content/area\n' +
			  '\n' +
			  'content-type:application/json; charset=utf-8\n' +
			  'host:a4sbwzghm9.execute-api.eu-central-1.amazonaws.com\n' +
			  'x-amz-date:' + datetime + '\n' +
			  '\n' +
			  'content-type;host;x-amz-date\n' +
			  bodyHash;
	
	shaObj = new jsSHA("SHA-256", "TEXT");
	shaObj.update(request);
	requestHash = shaObj.getHash("HEX");

	stringToSign = 'AWS4-HMAC-SHA256\n' + datetime + '\n' + date + '/eu-central-1/execute-api/aws4_request\n' + requestHash;

	signatureKey = getSignatureKey(secretAccessKey, date, "eu-central-1", "execute-api");

	shaObj = new jsSHA("SHA-256", "TEXT");
	shaObj.setHMACKey(signatureKey, "HEX");
	shaObj.update(stringToSign);
	signature = shaObj.getHMAC("HEX");
	
	header = 'AWS4-HMAC-SHA256 Credential=' + accessKeyID + '/' + date + '/eu-central-1/execute-api/aws4_request, SignedHeaders=content-type;host;x-amz-date, Signature=' + signature;

	$.ajax({
		async: false,
		data: body,
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		error: function(xhr, status, error) {
			alert(status);
		},
		headers: {
			'authorization':header,
			'content-type':'application/json; charset=utf-8',
			'x-amz-date':datetime,
			'x-amz-security-token':sessionToken
		},  
		success: function(data, status, xhr){
			if (xhr.status == 200) {
				$.mobile.changePage('#new-area-dialog', 'pop', true, true);
			}
		},
		type: 'POST',
		url: srvURL + '/area'
	});
}

function nextPicture () {
	setAreaPage(sessionStorage.getItem('areaIndex'), sessionStorage.getItem('areaFolder'), sessionStorage.getItem('areaName'), 'new-picture');
}

function setArea(areaIndex, areaFolder, areaName) {
	console.log("set area [" + areaIndex + "] [" + areaFolder + "] [" + areaName + "]");
	
	sessionStorage.setItem ('areaIndex', areaIndex);
	sessionStorage.setItem ('areaFolder', areaFolder);
	sessionStorage.setItem ('areaName', areaName);
}

function setAreaPage(areaIndex, areaFolder, areaName, pageId) {
	console.log("set area page [" + areaIndex + "] [" + areaFolder + "] [" + areaName + "] [" + pageId + "]");
	
	formObject = document.forms[pageId + '-form'];
		
	formObject.elements[pageId + '-area-name-text'].value = decodeURIComponent(areaName);
	
	if (formObject.elements[pageId + '-area-id-text'] != null) {
		formObject.elements[pageId + '-area-id-text'].value = areaIndex;
	}
	
	sessionStorage.setItem ('areaIndex', areaIndex);
	sessionStorage.setItem ('areaFolder', areaFolder);
	sessionStorage.setItem ('areaName', areaName);
}

function setPicture(pictureIndex, pictureName) {
	console.log("set picture [" + pictureIndex + "] [" + pictureName + "]");
	
	sessionStorage.setItem ('pictureIndex', pictureIndex);
	sessionStorage.setItem ('pictureName', pictureName);
}

function uploadPicture () {

	document.forms['new-picture-form'].submit();
}

$( '#delete-areas' ).live( 'pagebeforeshow',function(event){
	console.log('deleteAreas');

	$('#delete-area-list li').remove();

	$.getJSON(srvURL + '/areas', 
	function(data) {
		$.each(data.atos, function(index, area) {
			$('#delete-area-list').append('<li data-icon="minus">' +
					                      '<a href="#delete-area-dialog" data-rel="dialog" data-position-to="window" data-role="button" data-inline="true" data-transition="pop" onclick="setArea(\'' + area.id + '\')">'+ area.name + '</a>' +
					                      '</li>');
		});
		$('#delete-area-list').listview('refresh');
	  });
});

$( '#rename-area' ).live( 'pageshow',function(event){
	document.forms['rename-area-form'].elements['rename-area-area-name-text'].focus();
});

$( '#rename-areas' ).live( 'pagebeforeshow',function(event){

	console.log('renameAreas');
	
	$.getJSON(srvURL + '/areas', 
	function(data) {
		$.each(data.atos, function(index, area) {
			$('#rename-area-list').append('<li data-icon="gear"><a href="#rename-area" data-transition="slidefade" onclick=setAreaPage(' + area.id + ',\'' + area.folder + '\',\'' + encodeURIComponent(area.name) + '\',' +  '\'rename-area\')>'+ area.name + '</a></li>');

		});
		$('#rename-area-list').listview('refresh');
	  });
});

$( '#list-areas' ).live( 'pagebeforeshow',function(event){
	
	if (showLogout == null) {
		showDelete      = sessionStorage.getItem("showDelete");
		showEdit        = sessionStorage.getItem("showEdit");
		showLogout      = sessionStorage.getItem("showLogout");
		
		accessKeyID     = sessionStorage.getItem("accessKeyID");
		expirationTime  = sessionStorage.getItem("expirationTime");
		secretAccessKey = sessionStorage.getItem("secretAccessKey");
		sessionToken    = sessionStorage.getItem("sessionToken");
	}
	
	console.log('list-areas live event');

	$('#list-area-list li').remove();

	$.ajax({
		async: false,
		contentType: 'application/x-www-form-urlencoded',
	    dataType: 'json',
		error: function(jqXHR, textStatus, errorThrown ) {
			alert(textStatus);
		},
		headers: {
		},
		success: function(data) {
			$.each(data.atos, function(index, area) {
				var folder = 'empty';
				
				if(area.number != 0) {
					folder = area.folder;
				}
				
				$('#list-area-list').append('<li><a href="#list-pictures" data-transition="slidefade" onclick=setArea(\'' + area.id + '\',\'' + area.folder + '\',\'' + encodeURIComponent(area.name) + '\')>' +
						'<img src="' + imgURL + '/' + folder + '.jpg" height="100" width="150"/>' +
						'<h3>'+ area.name + '</h3><p>Anzahl:' + area.number + ' - Gr&ouml;&szlig;e: ' +
						area.size + ' KB</p></a></li>');
			});
			$('#list-area-list').listview('refresh');
		},
		type: 'GET',
		url: srvURL + '/areas' 
	});

	if (showLogout == 'true') {
		var button;
		var innerHtml;

		button = document.getElementById('list-areas-header-field-set-mid-header');
		innerHtml = button.innerHTML.trim();

		if (innerHtml == '') {
			button.innerHTML='<a class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-a\" ' +
	        'data-role=\"button\" data-transition=\"slideup\" ' +
	        'href=\"#edit-areas\" ' +
	        'data-corners=\"true\" ' +
	        'data-shadow=\"true\" ' +
	        'data-iconshadow=\"true\" ' +
	        'data-wrapperels=\"span\" ' +
	        'data-theme=\"a\">' +
	        	'<span class=\"ui-btn-inner ui-btn-corner-all\">' +
	        		'<span class=\"ui-btn-text\">Bearbeiten</span>' +
	        	'</span>' +
	        '</a>';
		}

		button = document.getElementById('list-areas-header-field-set-right-header');
		innerHtml = button.innerHTML.trim();

		if (innerHtml == '') {
			button.innerHTML='<a class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-a\" ' +
	        'data-role=\"button\" data-transition=\"slideup\" ' +
	        'data-corners=\"true\" ' +
	        'data-shadow=\"true\" ' +
	        'data-iconshadow=\"true\" ' +
	        'data-wrapperels=\"span\" ' +
	        'data-theme=\"a\" ' +
	        'onclick=\"logout()\">' +
	        	'<span class=\"ui-btn-inner ui-btn-corner-all\">' +
	        		'<span class=\"ui-btn-text\">Logout</span>' +
	        	'</span>' +
	        '</a>';
		}
	}
});

$( '#list-pictures' ).live( 'pagebeforeshow',function(event){
	console.log('list-pictures live event');

	$('#list-picture-list li').remove();

	$.getJSON(srvURL + '/pictures?areaId=' + sessionStorage.getItem ('areaIndex'), 
	function(data) {
		$.each(data.ptos, function(index, picture) {
			var pictureDate = new Date(picture.date);
			
			$('#list-picture-list').append('<li><a href="#show-picture" data-transition="slidefade" onclick=setPicture(\'' + picture.id + '\',\'' + picture.name + '\')>' +
					'<img src="' + imgURL + '/' + data.area.folder + '/' +  picture.name + '-TN.jpg" height="200" width="300"/>' +
					'<h3>Datum: '+ pictureDate.getDate() + '.' + pictureDate.getMonth() + '.' + pictureDate.getFullYear() + '</h3><p>Gr&ouml;&szlig;e: ' + picture.size + ' KB</p></a></li>');
		});
		$('#list-picture-list').listview('refresh');
	  });
});

$( '#edit-areas' ).live( 'pagebeforeshow',function(event){
	console.log('edit-areas live event');

	$('#edit-area-list li').remove();

	$.getJSON(srvURL + '/areas', 
	function(data) {
		$.each(data.atos, function(index, area) {
			var folder = 'empty';
			
			if(area.number != 0) {
				folder = area.folder;
			}
			
			$('#edit-area-list').append('<li><a href="#new-picture" data-transition="slidefade" onclick=setAreaPage(\'' + area.id + '\',\'' + area.folder + '\',\'' + encodeURIComponent(area.name) + '\',' +  '\'new-picture\')>' +
					'<img src="' + imgURL + '/' + folder + '.jpg" height="100" width="150"/>' +
					'<h3>'+ area.name + '</h3><p>Anzahl:' + area.number + ' - Gr&ouml;&szlig;e: ' +
					area.size + ' KB</p></a></li>');

		});
		$('#edit-area-list').listview('refresh');
	  });


	if (showLogout == 'true') {
		var button;
		var innerHtml;
										  
		button = document.getElementById('edit-areas-header-field-set-top-right-header');
		innerHtml = button.innerHTML.trim();
	
		if (innerHtml == '') {
			button.innerHTML='<a class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-a\" ' +
	        'data-role=\"button\" data-transition=\"slideup\" ' +
	        'data-corners=\"true\" ' +
	        'data-shadow=\"true\" ' +
	        'data-iconshadow=\"true\" ' +
	        'data-wrapperels=\"span\" ' +
	        'data-theme=\"a\" ' +
	        'onclick=\"logout()\">' +
	        	'<span class=\"ui-btn-inner ui-btn-corner-all\">' +
	        		'<span class=\"ui-btn-text\">Logout</span>' +
	        	'</span>' +
	        '</a>';
		}
	}
	
	if (showDelete == 'true') {
		var button;
		var innerHtml;

		button = document.getElementById('edit-areas-header-field-set-bottom-right-header');
		innerHtml = button.innerHTML.trim();

		if (innerHtml == '') {
			button.innerHTML='<a class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-a\" ' +
			                 'data-role=\"button\" data-transition=\"slideup\" ' +
			                 'href=\"#delete-areas\" ' +
			                 'data-corners=\"true\" ' +
			                 'data-shadow=\"true\" ' +
			                 'data-iconshadow=\"true\" ' +
			                 'data-wrapperels=\"span\" ' +
			                 'data-theme=\"a\">' +
			                 	'<span class=\"ui-btn-inner ui-btn-corner-all\">' +
			                 		'<span class=\"ui-btn-text\">L&ouml;schen</span>' +
			                 	'</span>' +
			                 '</a>';
		}
	}		

	if (showEdit == 'true') {
		var button;
		var innerHtml;

		button = document.getElementById('edit-areas-header-field-set-bottom-left-header');
		innerHtml = button.innerHTML.trim();

		if (innerHtml == '') {
			button.innerHTML='<a class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-a\" ' +
            'data-role=\"button\" data-transition=\"slideup\" ' +
            'href=\"#new-area\" ' +
            'data-corners=\"true\" ' +
            'data-shadow=\"true\" ' +
            'data-iconshadow=\"true\" ' +
            'data-wrapperels=\"span\" ' +
            'data-theme=\"a\">' +
            	'<span class=\"ui-btn-inner ui-btn-corner-all\">' +
            		'<span class=\"ui-btn-text\">Neu</span>' +
            	'</span>' +
            '</a>';
		}

		button = document.getElementById('edit-areas-header-field-set-bottom-mid-header');
		innerHtml = button.innerHTML.trim();

		if (innerHtml == '') {
			button.innerHTML='<a class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-a\" ' +
            'data-role=\"button\" data-transition=\"slideup\" ' +
            'href=\"#rename-areas\" ' +
            'data-corners=\"true\" ' +
            'data-shadow=\"true\" ' +
            'data-iconshadow=\"true\" ' +
            'data-wrapperels=\"span\" ' +
            'data-theme=\"a\">' +
            	'<span class=\"ui-btn-inner ui-btn-corner-all\">' +
            		'<span class=\"ui-btn-text\">Bearbeiten</span>' +
            	'</span>' +
            '</a>';
		}
	}
});

$( '#new-area' ).live( 'pageshow',function(event){
	document.forms['new-area-form'].elements['new-area-area-name-text'].focus();
});

$( '#show-picture' ).live( 'pagebeforeshow',function(event){
	console.log('show-picture live event');
	
	document.images["show-picture"].src = imgURL + '/' + sessionStorage.getItem ('areaFolder') + '/' +  sessionStorage.getItem ('pictureName') + '.jpg';
});

function main () {
	window.location.href = maiURL;
}

function getSignatureKey(secretAccessKey, date, region, service) {
	var kDate;
	var kRegion;
	var kService;
	var kSigning;
	var shaObj;
	
	
	shaObj = new jsSHA("SHA-256", "TEXT");
	shaObj.setHMACKey("AWS4" + secretAccessKey, "TEXT");
	shaObj.update(date);
	kDate = shaObj.getHMAC("HEX")
	
	shaObj = new jsSHA("SHA-256", "TEXT");
	shaObj.setHMACKey(kDate, "HEX");
	shaObj.update(region);
	kRegion = shaObj.getHMAC("HEX")

	shaObj = new jsSHA("SHA-256", "TEXT");
	shaObj.setHMACKey(kRegion, "HEX");
	shaObj.update(service);
	kService = shaObj.getHMAC("HEX")

	shaObj = new jsSHA("SHA-256", "TEXT");
	shaObj.setHMACKey(kService, "HEX");
	shaObj.update("aws4_request");
	kSigning = shaObj.getHMAC("HEX")

	return kSigning;
}
