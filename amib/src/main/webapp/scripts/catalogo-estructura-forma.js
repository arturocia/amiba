// prepare the form when the DOM is ready
var estructuraSel = null;
var accionForm;

$(document).ready(function() {
	var options = {
		beforeSubmit : showRequest, // pre-submit callback
		success : showResponse,
		dataType : "text"

	};


	$('#frmEstructura').ajaxForm(options);
	logger.trace("Deje unas viejas pisadas");



});

// pre-submit callback
function showRequest(formData, jqForm, options) {
	// formData is an array; here we use $.param to convert it to a string to
	// display it
	// but the form plugin does this for you automatically when it submits the
	// data
	var queryString = $.param(formData);

	// jqForm is a jQuery object encapsulating the form element. To access the
	// DOM element for the form do this:
	// var formElement = jqForm[0];

	logger.trace("si te acercas un pokito te ando dando un pisoton");

	// here we could return false to prevent the form from being submitted;
	// returning anything other than false will allow the form submit to
	// continue
	return true;
};

// post-submit callback
function showResponse(responseText, statusText, xhr, $form) {
	// for normal html responses, the first argument to the success callback
	// is the XMLHttpRequest object's responseText property

	// if the ajaxForm method was passed an Options Object with the dataType
	// property set to 'xml' then the first argument to the success callback
	// is the XMLHttpRequest object's responseXML property

	// if the ajaxForm method was passed an Options Object with the dataType
	// property set to 'json' then the first argument to the success callback
	// is the json data object returned by the server

	// logger.trace("PAsame el chikistrikis " + responseText);
	var str = responseText.replace("*/", "").replace("/*", "");

	var objetoEstructura = JSON.parse(str);

	customeValidation(objetoEstructura);
	logger.trace("Escucha esta cancion "
			+ introspect("caca", objetoEstructura, "-", 2));

};

function customeValidation(contenedorErrores) {
	logger.trace("entrando a errores")
	// List for errors
	var list = $("#ulErrores");
	logger.trace("entrando a errores mierda" + "")
	// Handle non field errors
	if (contenedorErrores.fieldErrors) {
		$.each(contenedorErrores.fieldErrors, function(index, value) {
			list.append("<li>" + value + "</li>");
			logger.trace("imprimiendo el error" + value);
		});

	} else {
		logger.trace("ernesto culero");

		$('#frmEstructura').feedback("La operación se realizo exitosamente", {
			duration : 3000,
			right : true
		});

	}
};

//
//function clickRellenarForma() {
//	logger.trace("la puta madre");
//
//	var ide = $(this).attr('id');
//
//	ide = ide.replace(/\D/g, '');
//	ide = parseInt(ide);
//	logger.trace("El id seleccionado " + ide);
//	nodo = encontrarPorId(ide, arbolDeNodosDeProgramasYestructuras);
//	if (ide > 1000000) {
//		ide = ide - 1000000;
//	}
//	logger.trace("El id real es " + ide);
//
//	logger.trace("El objeto encontrado para rellenar la forma "
//			+ introspect("caca", nodo, "-", 2));
//	llenarFormaDeNodo(nodo);
//
//	estructuraSel = nodo;
//	$("#btnAceptar").hide('600');
//	
//
//};
//
//function llenarFormaDeNodo(nodo) {
//	var programa;
//	var nivelSiguiente;
//	var fechaInicio;
//	var fechaFin;
//	// $(".botonExclusivoEstructura").attr("disabled", "disabled");
//	$("#txtNombre").val(nodo.nombre);
//	if (nodo.idEstructura) {
//		$("#hdnIdNivel").val(nodo.idNivel);
//		$("#hdnIdPadre").val(nodo.idEstructura);
//		$("#hdnIdPrograma").val(nodo.idPrograma);
//
//		// $(".botonExclusivoEstructura").removeAttr("disabled");
//
//		$("#btnEditar").val("Editar " + nodo.nivel.nombre);
//		$("#btnEliminar").val("Eliminar " + nodo.nivel.nombre);
//
//		programa = encontrarPorId(nodo.idPrograma + 1000000,
//				arbolDeNodosDeProgramasYestructuras);
//
//		logger.trace("El programa q encontro "
//				+ introspect("programa", programa, "-", 1));
//
//		nivelSiguiente = programa.niveles[nodo.idNivel];
//
//		logger.trace("El nivel siguiente del actual "
//				+ introspect("nivel", nivelSiguiente, "-", 1));
//		$("#btnAgregar").val(
//				"Agregar " + (nivelSiguiente ? nivelSiguiente.nombre : ""));
//	} else {
//		$("#hdnIdNivel").val(0);
//		$("#hdnIdPadre").val(0);
//		$("#hdnIdPrograma").val(nodo.idPrograma - 1000000);
//		$("#txtDescripcion").val(nodo.resumen);
//		$("#txtNivel").val("");
//		nivelSiguiente = nodo.niveles[0];
//		$("#btnAgregar").val("Agregar " + nivelSiguiente.nombre);
//	}
//
//	fechaInicio = nodo.periodo.fechaInicio ? $.datepicker.formatDate(
//			'yy-mm-dd', nodo.periodo.fechaInicio) : "";
//	$("#dtpFechaInicio").val(fechaInicio);
//
//	fechaFin = nodo.periodo.fechaFin ? $.datepicker.formatDate('yy-mm-dd',
//			nodo.periodo.fechaFin) : "";
//	$("#dtpFechaFin").val(fechaFin);
//	$("#txtDuracion").val(nodo.periodo.duracion ? nodo.periodo.duracion : "");
//};