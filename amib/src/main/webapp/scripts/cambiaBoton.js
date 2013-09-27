

function funcion()
{
	
	
	var estado=$("#OperacionEstado").attr("value");
	logger.trace("ESTADO"+estado);
	
	
	switch(estado){
		case '1':
			$("#operacionDatos").attr("class"," botonOperacion");
			break;
		case '2':
			$("#operacionSeguimiento").attr("class"," botonOperacion");
			break;
		case '3':
			$("#operacionAvances").attr("class"," botonOperacion");
			break;
		case '4':
			$("#operacionPlaneacion").attr("class"," botonOperacion");
			break;
			default:
				logger.trace("En default");			
		
	}
	
	
	
}


