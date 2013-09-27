//$(document).ready(function() {
//	var tipoDePeriodo = $("#hdnBanderaTipoPeriodo").val();
//	logger.trace("El tipo de periodo es " + tipoDePeriodo);
//	switch (tipoDePeriodo) {
//	case "0":
//		logger.trace("El tipo de periodo es con duracion");
//		$("#periodoConDuracion").show();
//		$("#duracion").attr("checked", "checked");
//
//		$(".campoDuracion").removeAttr("disabled");
//		$(".campoFechas").attr("disabled", "disabled");
//		$("#dtpFechaFin").removeAttr("disabled");
//		$("#dtpFechaFin").val(null);
//		break;
//	case "1":
//		$("#periodoSinDuracion").show();
//		$("#fechas").attr("checked", "checked");
//
//		$(".campoFechas").removeAttr("disabled");
//		$(".campoDuracion").attr("disabled", "disabled");
//		$("#txtDuracion").removeAttr("disabled");
//		$("#txtDuracion").val(null);
//		break;
//	case "2":
//		$("#indefinido").attr("checked", "checked");
//
//		$("#txtDuracion").removeAttr("disabled");
//		$("#txtDuracion").val(null);
//		$("#dtpFechaInicio").removeAttr("disabled");
//		$("#dtpFechaInicio").val(null);
//		$("#dtpFechaFin").removeAttr("disabled");
//		$("#dtpFechaFin").val(null);
//		break;
//	}
//});