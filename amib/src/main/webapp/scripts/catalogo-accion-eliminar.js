$(document).ready(function() {
	$("input[name='tipoPeriodo']:radio").change(clickPeriodo);
	
	logger.trace("En accion ELIMINAR scripasod");
	var tipoDePeriodo = parseInt($("#banderaTipoPeriodo").val(), 10);
	logger.trace("banderaTipoPeriodo: " + $("#banderaTipoPeriodo").val());
	logger.trace("El tipo de periodo es " + tipoDePeriodo);
	switch (tipoDePeriodo) {
	case 1:
		logger.trace("El tipo de periodo es con duracion");
		$("#periodoConDuracion").show();
		$("#duracion").attr("disabled", "false");
		$("#duracion").attr("checked", "checked");
		break;
	case 2:
		$("#periodoSinDuracion").show();
		$("#fechas").attr("disabled", "false");
		$("#fechas").attr("checked", "checked");
		break;
	case 3:
		$("#indefinido").attr("disabled", "false");
		$("#indefinido").attr("checked", "checked");
		break;
	}
});


