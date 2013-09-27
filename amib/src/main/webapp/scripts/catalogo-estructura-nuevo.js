$(document).ready(
		function() {
			var tipoPeriodo;
			$("[name='banderaTipoPeriodo']:radio").change(clickPeriodo);
			tipoPeriodo = $("#hdnBanderaTipoPeriodo").val();
			logger.trace("El tipo de periodo es " + tipoPeriodo);
			$("[name='banderaTipoPeriodo']").filter(
					"[value=" + tipoPeriodo + "]").attr("checked", "checked");
			clickPeriodo();
		});

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
	$("#frmEstructura").submit();
};