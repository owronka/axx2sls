"use strict";

function login () {
	var formObject;
	var userName;
	var userNameLbl;
	var userNameTxt;
	var password;
	var passwordHash;
	var passwordLbl;
	var passwordTxt;
	var body;
	var shaObj = new jsSHA("SHA-1", "TEXT");
	
	formObject = document.forms['login-form'];
	
	userNameTxt = formObject.elements['user-name-text'];
	passwordTxt = formObject.elements['password-text'];
	
	userNameLbl = document.getElementById('user-name-label');
	passwordLbl = document.getElementById('password-label');

	userNameLbl.innerHTML = 'Benutzer';
	userNameLbl.style.color = '#000000';
	passwordLbl.innerHTML = 'Passwort';
	passwordLbl.style.color = '#000000';

	userName = userNameTxt.value;
	password = passwordTxt.value;
	
	if (!userName || userName.length == 0) {
		userNameLbl.innerHTML = 'Der Benutzername darf nicht leer sein';
		userNameLbl.style.color = '#FF0000';
		userNameTxt.focus();
		
		return;
	}

	if (!password || password.length < 8) {
		passwordLbl.innerHTML = 'Das Passwort muss mindestens 8 Zeichen beinhalten';
		passwordLbl.style.color = '#FF0000';
		passwordTxt.focus();

		return;
	}

	shaObj.update(password);
	passwordHash = shaObj.getHash("HEX");
	
	console.log('login user [' + userName + '] - password [' + password + '] - hash [' + passwordHash + ']');

	body = '{"name" : "' + userName + '","password" : "' + passwordHash + '"}';

	$.ajax({
		async: false,
		contentType: 'application/json',
		data: body,
		dataType: 'json',
		error: function(jqXHR, textStatus, errorThrown ) {
			$.mobile.changePage('#login-error-dialog', 'pop', true, true);
		},
		headers: {
			'Content-Type':'application/json'
		},
		success: function(data, textStatus, request) {
			if (data.sessionToken && data.sessionToken.length > 0) {

				sessionStorage.setItem("userName", userName);
				
				sessionStorage.setItem("accessKeyID", data.accessKeyID);
				sessionStorage.setItem("secretAccessKey", data.secretAccessKey);
				sessionStorage.setItem("sessionToken", data.sessionToken);
				sessionStorage.setItem("expirationTime", data.expirationTime);
			
				sessionStorage.setItem("showDelete", true);
				sessionStorage.setItem("showEdit", true);
				sessionStorage.setItem("showLogout", true);

				window.location = conURL;
			}
		}, 
		type: 'POST',
		url: srvURL + '/login/account'
	});
}

function main () {
	window.location.href = maiURL;
}
