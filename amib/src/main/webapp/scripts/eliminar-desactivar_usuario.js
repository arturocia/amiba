/*$(document).ready(function(){
	$("#btnCancelar").click(function() {
		if(confirm("Este usuario tiene proyectos asociados, desea desactivar el usuario?")) {
			//En  caso de aceptar
		}
		else {
			//En caso de cancelar
		}
	});
});
 */

function validaSinProyectos() {

	var valor = $("#hdnConTrabajo").val();
	logger.trace("Tiene trabajo " + valor);

	if (valor == "true") {
		$("#mydialog").dialog("open");
	} else {
		logger.trace("no hace nada");
		$("#frmUsuario").submit();
	}

	return false;
};

function botonCancelar() {
	$('#mydialog').dialog('close');
};

function botonOk() {
	var ruta = $("#hdnRutaContexto").val();
	var usuario = $("#user").val();
	$('#mydialog').dialog('close');
	window.location = ruta + "/catalogo-usuario/" + usuario + "/edit";
};
