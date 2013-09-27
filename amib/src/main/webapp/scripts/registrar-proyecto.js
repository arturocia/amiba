var BANDERA_DURACION = 1;
var BANDERA_FECHA = 2;
var BANDERA_INDEFINIDO = 3;

$(document).ready(function() {
	logger.trace("M kiero cortar los uevos");
	$("input[name='tipoPeriodo']:radio").change(clickPeriodo);
	$(document).submit(capturaSubmit);

	switch (parseInt($("#banderaTipoPeriodo").val(), 10)) {
	case BANDERA_DURACION:
		$("#duracion").trigger("click");
		break;
	case BANDERA_FECHA:
		$("#fechas").trigger("click");
		break;
	case BANDERA_INDEFINIDO:
		$("#indefinido").trigger("click");
		break;
	default:
		logger.warn("No hay periodo elegido, por defecto indefinido.");
		$("#indefinido").trigger("click");
	}
});

function clickPeriodo() {
	logger.trace("What ya say!?");
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
	var elSeleccionado = $("input[name='tipoPeriodo']:radio").val();
	var tipoDePeriodo = $("#comboTipoPeriodo").val();
	var duracion = 0;
	var finicio = "";
	var ffin = "";
	var diferencia;
	var fechaFormateada;

	switch (elSeleccionado) {
	case 'duracion':
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
		$("#txtDuracion").val(isNaN(duracion) ? 0 : duracion);
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
};