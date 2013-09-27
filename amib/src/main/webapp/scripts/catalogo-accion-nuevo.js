$(document).ready(function() {
	$("input[name='tipoPeriodo']:radio").change(clickPeriodo);
	var bandera = $("#banderaTipoPeriodo").val();
	if(bandera == 1){
		$("#periodoConDuracion").show();
		$("#duracion").attr("checked",true);
		$("#periodoSinDuracion").hide();
		$("#dtpFechaInicioDuracion").attr("disabled",false);
	}
	if(bandera == 2){
		$("#periodoConDuracion").hide();
		$("#fechas").attr("checked",true);
		$("#periodoSinDuracion").show();
		$("#dtpFechaInicioDuracion").attr("disabled",true);
	}
	if(bandera == 3){
		$("#periodoConDuracion").hide();
		$("#indefinido").attr("checked",true);
		$("#periodoSinDuracion").hide();
		$("#dtpFechaInicioDuracion").attr("disabled",true);
	}
	logger.trace("En Accion NUEVO scripasod");
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
		logger.trace("DURACION");

		$(".campoDuracion").removeAttr("disabled");
		$(".campoFechas").attr("disabled", "disabled");
		
		$("#dtpFechaInicio").removeAttr("name");
		$("#dtpFechaFin").removeAttr("name");
		$("#dtpFechaInicioDuracion").attr("name", "model.periodo.fechaInicio");
		
		$("#txtDuracion").val(null);
		$("#dtpFechaInicio").val(null);
		$("#dtpFechaFin").val(null);
		$("#dtpFechaInicioDuracion").val(null);

		$("#dtpFechaInicioDuracion").attr("disabled",false);
		$("#dtpFechaInicio").attr("disabled",true);

		break;
	case 'fechas':
		$("#periodoSinDuracion").show();
		$("#periodoConDuracion").hide();
		$("#banderaTipoPeriodo").val("2");
		logger.trace("FECHAS");

		$(".campoFechas").removeAttr("disabled");
		$(".campoDuracion").attr("disabled", "disabled");
		
		$("#dtpFechaInicioDuracion").removeAttr("name");
		$("#dtpFechaInicio").attr("name", "model.periodo.fechaInicio");
		$("#dtpFechaFin").attr("name", "model.periodo.fechaFin");
		
		$("#txtDuracion").val(null);
		$("#dtpFechaInicio").val(null);
		$("#dtpFechaFin").val(null);
		$("#dtpFechaInicioDuracion").val(null);
		
		$("#dtpFechaInicioDuracion").attr("disabled",true);
		$("#dtpFechaInicio").attr("disabled",false);

		break;
	case 'indefinido':
		$("#periodoConDuracion").hide();
		$("#periodoSinDuracion").hide();
		$("#banderaTipoPeriodo").val("3");
		logger.trace("INDEFINIDO");

		$(".campoFechas").attr("disabled", "disabled");
		$(".campoDuracion").attr("disabled", "disabled");
		
		$("#dtpFechaInicioDuracion").removeAttr("name");
		$("#dtpFechaInicio").removeAttr("name");
		$("#dtpFechaFin").removeAttr("name");
		$("#dtpFechaInicioDuracion").removeAttr("name");
		
		$("#txtDuracion").val(null);
		$("#dtpFechaInicio").val(null);
		$("#dtpFechaFin").val(null);
		$("#dtpFechaInicioDuracion").val(null);
		
		$("#dtpFechaInicioDuracion").attr("disabled",true);
		$("#dtpFechaInicio").attr("disabled",true);
		
		break;
	}
};

function capturaSubmit() {
	var elSeleccionado = $("input[name='tipoPeriodo']:radio").val();
	var tipoDePeriodo = $("#comboTipoPeriodo").val();
	var duracion;
	
	logger.trace("BAndera: " + $("#banderaTipoPeriodo").val());
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
			$("#txtDuracion").val(isNaN(duracion) ? null : duracion);
		

		break;
	case 'fechas':
		
		break;
	case 'indefinido':

		break;
	default:
		break;
	}
	$("#frmPrograma").submit();
};