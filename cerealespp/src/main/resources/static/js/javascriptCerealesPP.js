$(document).ready(function() {
	$('#resultados').DataTable({
		"language" : {
			"url" : "//cdn.datatables.net/plug-ins/1.10.19/i18n/Spanish.json"
		},
		responsive : true,
		"info" : false,
		"iDisplayLength" : 5,
		"aLengthMenu" : [ [ 5, 10, 50, -1 ], [ 5, 10, 50, "Todos" ] ]
	});
});

function comprobarCif(cif) {
	var resul = false;
	var temp = cif.value.toUpperCase(); // pasar a mayúsculas

	if (!/^[A-Za-z0-9]{9}$/.test(temp)) { // Son 9 dígitos?
		alert("Longitud incorrecta, un CIF consta de 9 dígitos");

	} else if (!/^[ABCDEFGHKLMNPQS]/.test(temp)) { // Es una letra de las
													// admitidas ?
		alert("El primer dígito es incorrecto, debe ser una letra de las siguientes: A,B,C,D,E,F,G,H,K,L,M,N,P,Q,S ");

	} else {
		resul = true;
	}
	return resul;
}

function comprobarClave(clave2) {
	var clave1=document.getElementById("password1");
	var resul = true;
	if (clave1.value !== clave2.value) {
		alert("Las contraseñas no coinciden");
		resul = false;
	}
	return resul;
}

function comprobarCodigoPostal(cp) {
	var input = cp.value;
	var resul = false;
	console.log(parseInt(input))
	if (input.length == 5 && parseInt(input) >= 1000
			&& parseInt(input) <= 52999) {
		resul = true;
	} else {
		alert("Código postal inválido. Por favor, introduzca de nuevo el código postal.");
	}
	return resul;
}

function validarFormulario(formulario) {
	var resul1 = comprobarCif(formulario.cif);
	var resul2 = comprobarClave(formulario.password1, formulario.password2);
	var resul3 = comprobarCodigoPostal(formulario.postcode);
	var resul4 = comprobarCodigoPostal(formulario.postcode2);
	var resul5 = comprobarTelefono(formulario.telefono);
	if (resul1 && resul2 && resul3 && resul4 && resul5) {
		return true;
	} else {
		return false;
	}
}

function enviar(id) {
	document.getElementById(id).submit();
	alert('entra en enviar formulario');
}
function volverAtras() {
	history.back();
}

function avisarRegistrarParaComprar() {
	alert("Para comprar es necesario estar registrado.")

}
function comprobarTarjeta(num) {
	if (!/^[0-9]{16}$/.test(num.value)) { // Son 16 dígitos?
		alert("Tarjeta de crédito no válida");
		return false;
	} else {
		return true;
	}
}
function comprobarCvv(cvv) {
	if (!/^[0-9]{3}$/.test(cvv.value)) { // Son 16 dígitos?
		alert("CVV de crédito no válida");
		return false;
	} else {
		return true;
	}
}

function comprobarTelefono(tel) {
	if (!/^([0-9]){9}$/.test(tel.value)) { // Son 16 dígitos?
		alert("Número de teléfono no válido");
		return false;
	} else {
		return true;
	}
}

function validarPagar(formulario) {
	var resul1 = comprobarTarjeta(formulario.ccc);
	var resul2 = comprobarCvv(formulario.cvv);
	if (resul1 && resul2) {
		return true;
	} else {
		return false;
	}
}

function comprobarMayorQueCero(num) {
	if (num.value == 0) {
		alert("La cantidad de stock o precio no pueden ser 0");
		return false;
	} else {
		return true;
	}
}

function comprobarFormularioAnadirProducto(formulario) {
	var resul1 = comprobarMayorQueCero(formulario.precioAnadir);
	var resul2 = comprobarMayorQueCero(formulario.stockAnadir);
	if (resul1 && resul2) {
		return true;
	} else {
		return false;
	}

}
function avisarEmail() {
	alert("Este email ya ha sido registrado");
}
function validarFormularioEditarPerfil() {
	var formulario = document.getElementById("editarPerfil");
	var resul1 = comprobarCif(formulario.cif);
	var resul3 = comprobarCodigoPostal(formulario.postcode);
	var resul4 = comprobarCodigoPostal(formulario.postcode2);
	var resul5 = comprobarTelefono(formulario.telefono);
	if (resul1 && resul3 && resul4 && resul5) {
		formulario.submit();
	}
}