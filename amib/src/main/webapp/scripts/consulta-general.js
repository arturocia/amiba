$(function() {
	$("#tblProyecto").dataTable();
});

function des1() {
	$("#idTemas option[value=" + $("#idTemas").val() + "]").attr("selected", false);
	$("#idAreas option[value=" + $("#idAreas").val() + "]").attr("selected", false);
	$("#idProgramas option[value=" + $("#idProgramas").val() + "]").attr("selected", false);
}

function des2() {
	$("#idEjes option[value=" + $("#idEjes").val() + "]").attr("selected", false);
	$("#idAreas option[value=" + $("#idAreas").val() + "]").attr("selected", false);
	$("#idProgramas option[value=" + $("#idProgramas").val() + "]").attr("selected", false);
}

function des3() {
	$("#idTemas option[value=" + $("#idTemas").val() + "]").attr("selected", false);
	$("#idEjes option[value=" + $("#idEjes").val() + "]").attr("selected", false);
	$("#idProgramas option[value=" + $("#idProgramas").val() + "]").attr("selected", false);
}

function des4() {
	$("#idTemas option[value=" + $("#idTemas").val() + "]").attr("selected", false);
	$("#idAreas option[value=" + $("#idAreas").val() + "]").attr("selected", false);
	$("#idEjes option[value=" + $("#idEjes").val() + "]").attr("selected", false);
}

