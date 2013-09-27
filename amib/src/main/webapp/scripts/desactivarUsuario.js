$(document).ready(function() {
	if ($("#chkbxActivado").attr("checked")) {
		$("#lstUsuariosExistentes").css("display", "none");
		$("#lblUsuariosExistentes").css("display", "none");
	}
	$("#chkbxActivado").click(desactivar);
});

function desactivar() {
	if ($("#chkbxActivado").attr("checked")) {
		$("#lstUsuariosExistentes").css("display", "none");
	} else {
		//if ($("#conProyectos").val() == "true") {
			$("#lstUsuariosExistentes").css("display", "block");
			alert("Ha Desactivado al Usuario");
		//}
	}
};