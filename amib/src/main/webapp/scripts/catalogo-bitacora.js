function nuevaRestriccion(){
	$("#nuevaRestriccion").show();
	$("#btnnueva").hide();
};

function cancelaRestriccion(){
	$("#nuevaRestriccion").hide();
	$("#descripcion").val(null);
	$("#sugerencia").val(null);
	$("#btnnueva").show();
	//$("#").val(null);
};

function atenderRestriccion(restriccion)
{
	$("#inputs_r"+restriccion).show();
	$("#controles_r"+restriccion).hide();
	$("#btnAtender"+restriccion).show();
	$("#btnTurnar"+restriccion).hide();
	$("#estado_"+restriccion).show();
}

function atencionInmediata(restriccion)
{
	if($("#detallesRestriccion_"+restriccion).val() != "")
	{
		if($('#estado_'+restriccion).attr('checked'))
		{
			$("#estadoEdicion").val("habilitarEdicion");
		}
		$("#accionForma").val("atencionRestriccionInmediata");
		$("#idRestriccionSel").val(restriccion);
		$("#detallesAccionRestriccion").val($("#detallesRestriccion_"+restriccion).val());
		$('#bitacoraSeguimiento').submit();
	}
	else
	{
		alert("Favor de ingresar detalles a la atencion de la restricción!");
	}
}

function atencionSecretaria(restriccion)
{
	if($("#detallesRestriccion_"+restriccion).val() != "")
	{
		$("#accionForma").val("atencionRestriccionTurnada");
		$("#idRestriccionSel").val(restriccion);
		$("#detallesAccionRestriccion").val($("#detallesRestriccion_"+restriccion).val());
		$('#bitacoraSeguimiento').submit();
	}
	else
	{
		alert("Favor de ingresar detalles a la atencion de la restricción!");
	}
}

function atencionRestriccionSecretaria(restriccion)
{
	if($("#detallesRestriccion_"+restriccion).val() != "")
	{
		if($('#estado_'+restriccion).attr('checked'))
		{
			$("#estadoEdicion").val("habilitarEdicion");
		}
		$("#accionForma").val("atencionRestriccionSecretaria");
		$("#idRestriccionSel").val(restriccion);
		$("#detallesAccionRestriccion").val($("#detallesRestriccion_"+restriccion).val());
		$('#bitacoraSeguimiento').submit();
	}
	else
	{
		alert("Favor de ingresar detalles a la atencion de la restricción!");
	}
}

function turnarRestriccion(restriccion)
{
	$("#inputs_r"+restriccion).show();
	$("#controles_r"+restriccion).hide();
	$("#btnAtender"+restriccion).hide();
	$("#btnTurnar"+restriccion).show();
	$("#estado_"+restriccion).hide();
}

function turnar(restriccion)
{
	if($("#detallesRestriccion_"+restriccion).val() != "")
	{
		$("#accionForma").val("turnarRestriccion");
		$("#idRestriccionSel").val(restriccion);
		$("#detallesAccionRestriccion").val($("#detallesRestriccion_"+restriccion).val());
		$('#bitacoraSeguimiento').submit();
	}
	else
	{
		alert("Favor de ingresar detalles para turnar la restricción!");
	}
}

function cancelar(restriccion)
{
	$("#inputs_r"+restriccion).hide();
	$("#controles_r"+restriccion).show();
}