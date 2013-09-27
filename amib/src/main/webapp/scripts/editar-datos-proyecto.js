$(document).ready(
		function() {
		
			var tipoPeriodo;
			// logger.trace("Maradona");
			$("input[name='tipoPeriodo']:radio").change(clickPeriodo);
			$.subscribe("elementoClickeado", nodoSeleccionado);
			$.subscribe("elementoClickeadoSectorial", nodoSeleccionado);
			$.subscribe("caca", obtenerSeleccionArbolSectorial);
			$.subscribe("caca1", obtenerSeleccionArbolOtro);
			$(".checkAlineacion").click(habilitaAlineacion);

			if ($("#chkbxAlineacionSectorial").attr("checked")) {
				$("#treEstructurasProgramaSectorial").show();
			} else {
				$("#treEstructurasProgramaSectorial").hide();
			}
			if ($("#chkbxAlineacion").attr("checked")) {
				$("#treEstructurasProgramaOtro").show();
			} else {
				$("#treEstructurasProgramaOtro").hide();
			}

			$("[name='banderaTipoPeriodo']:radio").change(clickPeriodo);
			tipoPeriodo = $("#hdnBanderaTipoPeriodo").val();
			logger.trace("El tipo de periodo es " + tipoPeriodo);
			$("[name='banderaTipoPeriodo']").filter(
					"[value=" + tipoPeriodo + "]").attr("checked", "checked");
			clickPeriodo();

			$("#frmProyecto").submit(capturaSubmit);
			$("#frmProyecto").submit(seleccionaDisponibles);
		});

function seleccionaDisponibles() {
	var curOption;
	curOption = document.getElementById("slcIdEjesTematicosDisponibles");
	for ( var i = 0; i < curOption.length; i++) {
		curOption[i].selected = true;
	}
	curOption = document.getElementById("slcIdTemasTransversalesDisponibles");
	for ( var i = 0; i < curOption.length; i++) {
		curOption[i].selected = true;
	}
	$("#slcIdEjesTematicosSeleccionados option").attr("selected", "selected");
	$("#slcIdTemasTransversalesSeleccionados option").attr("selected",
			"selected");
}

function obtenerSeleccionArbolSectorial() {
	// logger.trace("El ARBOL factorial " +
	// $("#hdnIdEstructuraSectorial").val());
	if ($("#hdnIdEstructuraSectorial").val()) {
		var estructura = "#Estructura_" + $("#hdnIdEstructuraSectorial").val();
		var treeInstance = jQuery.jstree
				._reference("#treEstructurasProgramaSectorial");
		treeInstance.select_node(estructura, false, false);
	}
}

function obtenerSeleccionArbolOtro() {
	if ($("#hdnIdEstructura").val()) {
		logger.trace("Entro a obtener selctorial arbol otro");
		var estructura = "#Estructura_" + $("#hdnIdEstructura").val();
		var treeInstance = jQuery.jstree
				._reference("#treEstructurasProgramaOtro");
		treeInstance.select_node(estructura, false, false);
	}
}

function nodoSeleccionado(event, data) {
	var item = event.originalEvent.data.rslt.obj;
	var idNodo;
	var idEstructura;
	var componentesId;
	var nombreEvento;
	var idHdnIdEstructura;

	idNodo = item.attr("id");
	nombreEvento = event.type;

	logger.trace("En evento " + nombreEvento);

	logger.trace("El nodo seleccionado es " + idNodo);
	// alert(idNodo);

	componentesId = idNodo.split("_");

	if (componentesId.length == 2) {
		idEstructura = componentesId[1];
		logger.trace("El id de la estructura seleccionada es " + idEstructura);
		if (nombreEvento.match(/Sectorial/)) {
			logger.trace("Se selecciono una sectorial");
			idHdnIdEstructura = "hdnIdEstructuraSectorial";
		} else {
			logger.trace("Se selecciono una normal");
			idHdnIdEstructura = "hdnIdEstructura";
			$('#hdnIdEstructuraTipo').val(2);
		}
		$("#" + idHdnIdEstructura).val(idEstructura);

		determinaPeriodoPermitido();
	} else {

		alert("Favor de seleccionar una estructura del programa para su alinación");
		$('#hdnIdEstructuraTipo').val(1);
		$("#hdnIdEstructura").val(idNodo);

	}
	// determinaPeriodoPermitido();
};

function habilitaAlineacion() {
	var idChkbx;
	var estaSeleccionado;
	var idHdnIdEstructura;
	var idTree;

	idChkbx = $(this).attr("id");
	estaSeleccionado = $(this).attr("checked");
	logger.trace("El id del checkbox " + idChkbx + " y esta seleccionado "
			+ estaSeleccionado);
	if (idChkbx.match(/Sectorial/)) {
		idHdnIdEstructura = "hdnIdEstructuraSectorial";
		idTree = "treEstructurasProgramaSectorial";
	} else {
		idHdnIdEstructura = "hdnIdEstructura";
		idTree = "treEstructurasProgramaOtro";
	}

	if (!estaSeleccionado) {
		$("#" + idHdnIdEstructura).val(null);
		$("#" + idTree + " a").removeClass("jstree-clicked");
		$("#" + idTree + " a").removeClass("ui-state-active");
		$("#" + idTree).hide();
	} else {
		$("#" + idTree).show();
	}

};

function clickPeriodo() {
	var seleccionado = $("[name='banderaTipoPeriodo']:checked").val();
	logger.trace("Se tipo de periodo que se selecciono " + seleccionado);
	switch (seleccionado) {
	case '0':
		$("#periodoConDuracion").show();
		$("#periodoSinDuracion").hide();

		$(".campoDuracion").removeAttr("disabled");
		$(".campoFechas").attr("disabled", "disabled");
		$("#dtpFechaFin").removeAttr("disabled");
		$("#dtpFechaFin").val(null);

		break;
	case '1':
		$("#periodoSinDuracion").show();
		$("#periodoConDuracion").hide();

		$(".campoFechas").removeAttr("disabled");
		$(".campoDuracion").attr("disabled", "disabled");
		$("#txtDuracion").removeAttr("disabled");
		$("#txtDuracion").val(null);
		break;
	case '2':
		$("#periodoConDuracion").hide();
		$("#periodoSinDuracion").hide();

		$(".campoFechas").attr("disabled", "disabled");
		$(".campoDuracion").attr("disabled", "disabled");

		$("#txtDuracion").removeAttr("disabled");
		$("#txtDuracion").val(null);
		$("#dtpFechaInicio").removeAttr("disabled");
		$("#dtpFechaInicio").val(null);
		$("#dtpFechaFin").removeAttr("disabled");
		$("#dtpFechaFin").val(null);
		break;
	}
};

function capturaSubmit() {
	var elSeleccionado = $("input[name='banderaTipoPeriodo']:radio").val();
	var tipoDePeriodo = $("#comboTipoPeriodo").val();
	var duracion = 0;
	var finicio = "";
	var ffin = "";
	var diferencia;
	var fechaFormateada;

	switch (elSeleccionado) {
	case '0':
		if ($("#dtpFechaInicioDuracion").val() == "") {
			duracion = parseInt($("#txtDuracion").val());
			switch (tipoDePeriodo) {
			case 'day':
				duracion = duracion;
				break;
			case 'week':
				duracion = duracion * 7;
				break;
			case 'month':
				duracion = duracion * 30;
				break;
			case 'year':
				duracion = duracion * 365;
				break;
			}
			$("#txtDuracion").val(isNaN(duracion) ? null : duracion);
		} else {
			finicio = new Date($("#dtpFechaInicioDuracion").val());
			ffin = new Date(finicio);

			duracion = parseInt($("#txtDuracion").val(), 10);
			if (!isNaN(duracion)) {
				logger.trace("Tipo de periodo: " + tipoDePeriodo + " duracion "
						+ duracion);
				logger
						.trace("Fecha Inicio: " + finicio + " Fecha fin: "
								+ ffin);
				ffin.increment(tipoDePeriodo, duracion);
				logger
						.trace("Fecha Inicio: " + finicio + " Fecha fin: "
								+ ffin);
				duracion = distanciaEntreFechas(finicio, ffin);
				logger.trace("Duracion: " + duracion);
				$("#txtDuracion").val(duracion > 0 ? duracion : null);
				fechaFormateada = ffin.format("%Y-%m-%d");
			}
		}

		break;
	case '1':
		finicio = $("#dtpFechaInicio").val();
		ffin = $("#dtpFechaFin").val();

		break;
	case '2':

		break;
	default:
		break;
	}

};

function determinaPeriodoPermitido() {
	var idEstructura;
	var idEstructuraSectorial;

	logger.trace("funcion determina se ejecuta");
	idEstructura = $("#hdnIdEstructura").val();
	idEstructuraSectorial = $("#hdnIdEstructuraSectorial").val();

	if (idEstructura && !isNaN(idEstructura)) {
		idEstructura = idEstructura.toInt(10);

	} else {
		idEstructura = null;
	}

	idEstructuraSectorial = (idEstructuraSectorial && !isNaN(idEstructuraSectorial)) ? idEstructuraSectorial
			.toInt(10)
			: null;

	logger.trace("La estructura sectorial " + idEstructuraSectorial);
	logger.trace("La estructura " + idEstructura);
	OperacionEditarDatosProyectoController.verificarPeriodoRestrictivo(
			idEstructura, idEstructuraSectorial, muestraPeriodoResultado);
};

function muestraPeriodoResultado(periodo) {

	if (periodo == null) {
//		alert("El periodo entra en conflicto con los ancestros de esta estructura, favor de selecionar otra estructura");
		cadenaMensaje = "Los periodos de las estructuras no se interceptan, favor de verificarlos";
		$("#idPeriNull").val(true);
	} else {
		$("#idPeriNull").val(false);
		var fechaInicio = periodo.fechaInicio;
		var fechaFin = periodo.fechaFinCalculada;
		var cadenaMensaje = null;

		if (fechaInicio != null) {
			cadenaMensaje = "El perído esta entre: "
					+ fechaInicio.format("%Y-%m-%d") + " y "
					+ fechaFin.format("%Y-%m-%d") + "";
		} else {
			cadenaMensaje = "El periodo es indefinido";
			// cadenaMensaje = "El periodo no debe exeder" + nodo.duracion + "";
		}

		logger.trace("La estructura " + fechaInicio);

		
	}
	$("#restrictivo").html(cadenaMensaje);
	$("#periodoRestrictivo").show();
}
function onsumbit(){
	$('#idBanderaAux').val(2);
	if($("#idPeriNull").val()){
		if(!$("#chkbxAlineacionSectorial").is(":checked")){
			$("#idPeriNull").val(false);
		}
		if(!$("#chkbxAlineacion").is(":checked")){
			$("#idPeriNull").val(false);
		}
	}
}
