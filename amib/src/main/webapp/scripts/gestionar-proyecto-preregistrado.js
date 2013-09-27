$(document).ready(function() {
	$('#opcionUsuarioExistente').hide();

	$('#buttonCrearCuentaDeUsuario').click(function() {
		$('#opcionCrearusuario').show();
		$('#opcionUsuarioExistente').hide();
	});

	$('#buttonUsuarioRegistrado').click(function() {
		$('#opcionCrearusuario').hide();
		$('#opcionUsuarioExistente').show();
	});

	$(function() {
		$("#listaUsuarioRegistrados").dataTable();
	});

	$(function() {
		manipularButtons();
	});
	validaRedireccionado();
});

function habilita() {
	$("#opcionAceptado").removeAttr("disabled");
	$("#btnAceptar").removeAttr("disabled");
	$("#btnAceptar").removeClass("ui-button-disabled");
	$("#btnAceptar").removeClass("ui-state-disabled");
};

function deshabilitaOpciones() {
	$("#linkAgregar").hide();
	$('#buttonUsuarioRegistrado').hide();
	$('etiquetaButtonUsuarioReg').hide();
	habilita();
};

function validaRedireccionado() {
	var isRedireccionado;
	isRedireccionado = $("#hdnRedireccionado").val();
	if (isRedireccionado) {
		habilita();
	}
};
