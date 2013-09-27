$(document).ready(function() {
	$("input[name='tipoPeriodo']:radio").change(clickPeriodo);
	if($('#banderaTipoPeriodo').val()=="2"){
		$("#fechas").attr("checked",true);
		$("#periodoSinDuracion").show();
				
	}else if($('#banderaTipoPeriodo').val()=="3"){
		$("#indefinido").attr("checked",true);
		$("#periodoSinDuracion").hide();
	}
	
});
$(document).submit(capturaSubmit);
function clickPeriodo() {
	var seleccionado = $(this).val();

	switch (seleccionado) {
	case 'duracion':
		$("#periodoConDuracion").show();
		$("#periodoSinDuracion").hide();
		$("#banderaTipoPeriodo").val("1");

//		$(".campoDuracion").removeAttr("disabled");
//		$(".campoFechas").attr("disabled", "disabled");
//		$("#dtpFechaFin").removeAttr("disabled");
//		$("#dtpFechaFin").val(null);

		break;
	case 'fechas':
		$("#periodoSinDuracion").show();
//		$("#periodoConDuracion").hide();
		$("#banderaTipoPeriodo").val("2");

		$(".campoFechas").removeAttr("disabled");
//		$(".campoDuracion").attr("disabled", "disabled");
//		$("#txtDuracion").removeAttr("disabled");
		$("#txtDuracion").val(null);
		break;
	case 'indefinido':
//		$("#periodoConDuracion").hide();
		$("#periodoSinDuracion").hide();
		$("#banderaTipoPeriodo").val("3");

		$(".campoFechas").attr("disabled", "disabled");
//		$(".campoDuracion").attr("disabled", "disabled");
		
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
	var elSeleccionado = $("input[name='tipoPeriodo']:checked").val();
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
		$("#txtDuracion").val(0);
		break;
	default:
		break;
	}
};