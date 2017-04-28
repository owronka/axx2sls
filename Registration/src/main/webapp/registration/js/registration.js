function CurrentArea (newIndex, newFolder, newName) {
	this.index = newIndex;
	this.folder = newFolder;
	this.name = newName;
}

function CurrentPicture (newIndex, newName) {
	this.index = newIndex;
	this.name = newName;
}

var ca = new CurrentArea (0, '', '');
var cp = new CurrentPicture (0, '');

function register () {
	var salutation;
	var salutationLbl;
	var salutationCmb;
	var firstName;
	var firstNameLbl;
	var firstNameTxt;
	var lastName;
	var lastNameLbl;
	var lastNameTxt;
	var eMail;
	var eMailLbl;
	var eMailTxt;
	var eMailRepeat;
	var eMailRepeatLbl;
	var eMailRepeatTxt;
	var accountName;
	var accountNameLbl;
	var accountNameTxt;
	var password;
	var passwordHash;
	var passwordLbl;
	var passwordTxt;
	var passwordRepeat;
	var passwordRepeatLbl;
	var passwordRepeatTxt;
	var addressCountry;
	var addressCountryLbl;
	var addressCountryTxt;
	var addressZIP;
	var addressZIPLbl;
	var addressZIPTxt;
	var addressCity;
	var addressCityLbl;
	var addressCityTxt;
	var addressStreet;
	var addressStreetLbl;
	var addressStreetTxt;
	var addressHouseNumber;
	var addressHouseNumberLbl;
	var addressHouseNumberTxt;
	var eMailRegEx = new RegExp ('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$');
	var body;
	var shaObj = new jsSHA("SHA-1", "TEXT");
	
	formObject = document.forms['register-form'];
	
	salutationCmb     = formObject.elements['salutation-list'];
	firstNameTxt      = formObject.elements['first-name-text'];
	lastNameTxt       = formObject.elements['last-name-text'];
	eMailTxt          = formObject.elements['e-mail-text'];
	eMailRepeatTxt    = formObject.elements['e-mail-repeat-text'];
	accountNameTxt    = formObject.elements['account-nametext'];
	passwordTxt       = formObject.elements['password-text'];
	passwordRepeatTxt = formObject.elements['password-repeat-text'];
	
	salutationLbl     = document.getElementById('salutation-label');
	firstNameLbl      = document.getElementById('first-name-label');
	lastNameLbl       = document.getElementById('last-name-label');
	eMailLbl          = document.getElementById('e-mail-label');
	eMailRepeatLbl    = document.getElementById('e-mail-repeat-label');
	accountNameLbl    = document.getElementById('account-namelabel');
	passwordLbl       = document.getElementById('password-label');
	passwordRepeatLbl = document.getElementById('password-repeat-label');

	countryCmb        = document.getElementById('country-list');
	zipTxt            = document.getElementById('zip-text');
	cityTxt           = document.getElementById('city-text');
	streetTxt         = document.getElementById('street-text');
	houseNumberTxt    = document.getElementById('housenumber-text');

	countryLbl        = document.getElementById('country-label');
	zipLbl            = document.getElementById('zip-label');
	cityLbl           = document.getElementById('city-label');
	streetLbl         = document.getElementById('street-label');
	houseNumberLbl    = document.getElementById('housenumber-label');
	
	salutationLbl.innerHTML = 'Anrede';
	salutationLbl.style.color = '#000000';
	firstNameLbl.innerHTML = 'Vorname';
	firstNameLbl.style.color = '#000000';
	lastNameLbl.innerHTML = 'Nachname';
	lastNameLbl.style.color = '#000000';
	eMailLbl.innerHTML = 'E-Mail';
	eMailLbl.style.color = '#000000';
	eMailRepeatLbl.innerHTML = 'E-Mail (Wiederholung)';
	eMailRepeatLbl.style.color = '#000000';
	accountNameLbl.innerHTML = 'Benutzer';
	accountNameLbl.style.color = '#000000';
	passwordLbl.innerHTML = 'Passwort';
	passwordLbl.style.color = '#000000';
	passwordRepeatLbl.innerHTML = 'Passwort (Wiederholung)';
	passwordRepeatLbl.style.color = '#000000';
	countryLbl.innerHTML = 'Land';
	countryLbl.style.color = '#000000';
	zipLbl.innerHTML = 'Postleitzahl';
	zipLbl.style.color = '#000000';
	cityLbl.innerHTML = 'Stadt';
	cityLbl.style.color = '#000000';
	streetLbl.innerHTML = 'Stra&szlig;e';
	streetLbl.style.color = '#000000';
	houseNumberLbl.innerHTML = 'Hausnummer';
	houseNumberLbl.style.color = '#000000';

	salutation     = salutationCmb.value;
	firstName      = firstNameTxt.value;
	lastName       = lastNameTxt.value;
	eMail          = eMailTxt.value;
	eMailRepeat    = eMailRepeatTxt.value;
	accountName    = accountNameTxt.value;
	password       = passwordTxt.value;
	passwordRepeat = passwordRepeatTxt.value;
	country        = countryCmb.value;
	zip            = zipTxt.value;
	city           = cityTxt.value;
	street         = streetTxt.value;
	houseNumber    = houseNumberTxt.value;
	
	if (!firstName || firstName.length == 0) {
		firstNameLbl.innerHTML = 'Der Vorname darf nicht leer sein';
		firstNameLbl.style.color = '#FF0000';
		firstNameTxt.focus();
		
		return;
	}

	if (!lastName || lastName.length == 0) {
		lastNameLbl.innerHTML = 'Der Nachname darf nicht leer sein';
		lastNameLbl.style.color = '#FF0000';
		lastNameTxt.focus();

		return
	}

	if (!eMail || eMail.length == 0) {
		eMailLbl.innerHTML = 'Die E-Mail darf nicht leer sein';
		eMailLbl.style.color = '#FF0000';
		eMailTxt.focus();

		return;
	}
	    
	if (eMailRegEx.test (eMail) == false) {
		eMailLbl.innerHTML = 'Die E-Mail einen ung&uuml;ltigen Aufbau';
		eMailLbl.style.color = '#FF0000';
		eMailTxt.focus();

		return;
	}
	
	if (!eMailRepeat || eMailRepeat.length == 0) {
		eMailRepeatLbl.innerHTML = 'Die E-Mail Wiederholung darf nicht leer sein';
		eMailRepeatLbl.style.color = '#FF0000';
		eMailRepeatTxt.focus();

		return;
	}
	
	if (eMail != eMailRepeat) {
		eMailRepeatLbl.innerHTML = 'Die E-Mail und die E-Mail Wiederholung sind unterschiedlich';
		eMailRepeatLbl.style.color = '#FF0000';
		eMailRepeatTxt.focus();

		return;
	}
	
	if (!accountName || accountName.length == 0) {
		accountNameLbl.innerHTML = 'Der Benutzername darf nicht leer sein';
		accountNameLbl.style.color = '#FF0000';
		accountNameTxt.focus();
		
		return;
	}

	if (!password || password.length < 8) {
		passwordLbl.innerHTML = 'Das Passwort muss mindestens 8 Zeichen beinhalten';
		passwordLbl.style.color = '#FF0000';
		passwordTxt.focus();

		return;
	}

	if (!passwordRepeat || passwordRepeat.length < 8) {
		passwordRepeatLbl.innerHTML = 'Die Passwort Wiederholung muss mindestens 8 Zeichen beinhalten';
		passwordRepeatLbl.style.color = '#FF0000';
		passwordRepeatTxt.focus();

		return;
	}

	if (password != passwordRepeat) {
		passwordRepeatLbl.innerHTML = 'Das Passwort und die Passwort Wiederholung sind unterschiedlich';
		passwordRepeatLbl.style.color = '#FF0000';
		passwordRepeatTxt.focus();

		return;
	}
	
	shaObj.update(password);
	passwordHash = shaObj.getHash("HEX");

	if (!zip || zip.length == 0) {
		zip.innerHTML = 'Das Postleitzahl darf nicht leer sein';
		zipLbl.style.color = '#FF0000';
		zipTxt.focus();

		return;
	}

	if (!city || city.length == 0) {
		city.innerHTML = 'Die Stadt darf nicht leer sein';
		cityLbl.style.color = '#FF0000';
		cityTxt.focus();

		return;
	}

	if (!street || street.length == 0) {
		street.innerHTML = 'Die Stra&szlig;e darf nicht leer sein';
		streetLbl.style.color = '#FF0000';
		streetTxt.focus();

		return;
	}

	if (!houseNumber || houseNumber.length == 0) {
		houseNumber.innerHTML = 'Die Hausnummer darf nicht leer sein';
		houseNumberLbl.style.color = '#FF0000';
		houseNumberTxt.focus();

		return;
	}
	
	body = 	'{"person":{' +
				'"personId":0,"salutation":"' + salutation + '","firstName":"' + firstName + '","lastName":"' + lastName + '","email":"' + eMail + '"},' +
			'"account":{' +
				'"id":0,"name":"' + accountName + '","pwdHash":"' + passwordHash + '","pwdSalt":null},' +
			'"address":{' +
				'"addressId":0,"country":"' + country + '","zip":"' + zip + '","city":"' + city + '","street":"' + street + '","houseNumber":"' + houseNumber + '","type":"HOME"}' +
			'}'

	console.log(body);

	$.ajax({
		async: false,
		contentType: 'application/json',
		data: body,
		dataType: 'json',
		error: function(data) {
			console.log(data);
			$.mobile.changePage('#error', 'pop', true, true);
		},
		headers: {
			'Content-Type':'application/json'
		},
		success: function(data) {
			window.location.href=maiURL;
		}, 
		type: 'POST',
		url: srvURL + '/person'
	});
}

$( '#register' ).live( 'pageshow',function(event){
	$.ajax({
		async: true,
		contentType: 'application/json',
		data: null,
		dataType: 'json',
		headers: {
			'Content-Type':'application/json'
		},
		type: 'POST',
		url: srvURL + '/person'
	});
	document.forms['register-form'].elements['first-name-text'].focus();
});

function main () {
	window.location.href = maiURL;
}
