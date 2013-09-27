function opcion(opcion)
{
	switch(opcion)
	{
		case "aprobado":
			$("#observacion").hide();
		break;
		case "rechazado":
			$("#observacion").show();
		break;
	}
}