$(document).ready(function() {
	$("input[name='tipoPeriodo']:radio").change(clickPeriodo);
	// $("#btnAgregar").click(capturaSubmit);
	// clickPeriodo();
});

function clickPeriodo() {
	var seleccionado = $(this).val();

	switch (seleccionado) {
	case 'duracion':
		$("#periodoConDuracion").show();
		$("#periodoSinDuracion").hide();
		$("#banderaTipoPeriodo").val("1");

		$(".campoDuracion").removeAttr("disabled");
		$(".campoFechas").attr("disabled", "disabled");
		$("#dtpFechaFin").removeAttr("disabled");
		$("#dtpFechaFin").val(null);

		break;
	case 'fechas':
		$("#periodoSinDuracion").show();
		$("#periodoConDuracion").hide();
		$("#banderaTipoPeriodo").val("2");

		$(".campoFechas").removeAttr("disabled");
		$(".campoDuracion").attr("disabled", "disabled");
		$("#txtDuracion").removeAttr("disabled");
		$("#txtDuracion").val(null);

		break;
	case 'indefinido':
		$("#periodoConDuracion").hide();
		$("#periodoSinDuracion").hide();
		$("#banderaTipoPeriodo").val("3");

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
	logger.trace("Que bonito pisoton");
	var elSeleccionado = $("input[name='tipoPeriodo']:radio").val();
	var tipoDePeriodo = $("#comboTipoPeriodo").val();
	var duracion = 0;
	var finicio = "";
	var ffin = "";
	var diferencia;
	var fechaFormateada;

	logger.trace("Tipo de periodo " + tipoDePeriodo);
	logger.trace("el seleccionado " + elSeleccionado);
	switch (elSeleccionado) {
	case 'duracion':
		if (!$("#dtpFechaInicioDuracion")
				|| $("#dtpFechaInicioDuracion").val() == "") {
			duracion = parseInt($("#txtDuracion").val());
			logger.trace("La duracion es " + duracion);
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
			logger.trace("Pero que mierda duracion " + duracion);
			$("#txtDuracion").val(isNaN(duracion) ? 0 : duracion);
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
				$("#txtDuracion").val(duracion);
				fechaFormateada = ffin.format("%Y-%m-%d");
			}
		}

		break;
	case 'fechas':
		finicio = $("#dtpFechaInicio").val();
		ffin = $("#dtpFechaFin").val();

		break;
	case 'indefinido':
		break;
	default:
		break;
	}
	logger.trace("antes de hacerle submit a la forma");
	$("#frmProyecto").submit();
};