/**
 * 
 */
$(function() {
	logger.trace("frijolero");
	$(".indicaModificacion").click(habilitaEdicionAvance);
});

function habilitaEdicionAvance() {
	var idCheckBox;
	var idIndicador;
	var activo;
	var idTxtAvance;

	idCheckBox = $(this).attr("id");
	idIndicador = idCheckBox.split("_")[1];
	activo = $(this).attr("checked");
	idTxtAvance = "txtAvance_" + idIndicador;

	logger.trace("El check seleccionado " + idIndicador);
	logger.trace("Su estado " + activo);
	if (activo) {
		$("#" + idTxtAvance).show();
		$("#" + idTxtAvance).Attr("value", "");		
		
		//$("#" + idTxtAvance).removeAttr("disabled");
	} else {
		$("#" + idTxtAvance).hide();
		//$("#" + idTxtAvance).attr("disabled", "disabled");
	}
};