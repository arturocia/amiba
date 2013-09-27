<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Proyecto ${nombre}</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
</head>
<body>
	<s:url id="urlCancelar" value="/catalogo-proyecto-preregistrado" includeContext="true" />
	<s:actionerror id="saeProyecto" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-proyecto-preregistrado/%{idProyecto}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		
		<label>Siglas:</label>
		<s:textfield id="txtSiglas" name="model.siglas" disabled="true" />
		<label>Nombre:</label>
		<s:textfield id="txtNombre" name="model.nombre" disabled="true" />
		<label>Resumen:</label>
		<s:textfield id="txtResumen" name="model.resumen" disabled="true" />
		<label>Responsable:</label>
		<s:textfield id="txtResponsable" name="model.responsable.nombre + model.responsable.apPat + model.responsable.apMat" disabled="true" />
		<label>Objetivo General:</label>
		<s:textfield id="txtObjetivoGenaral" name="model.objetivoGeneral" disabled="true" />
		<label>Datos Pe-registro:</label>
		<s:textfield id="txtDatosPreregistro" name="model.datosPreregistro" disabled="true" />
		<label>Duración:</label>
		<s:textfield id="txtDuracion" name="model.periodo.duracion" disabled="true"/>
		<label>Inicio:</label>
		<s:textfield id="txtFechaInicio" name="model.periodo.fechaInicio" disabled="true"/>
		<label>Fin:</label>
		<s:textfield id="txtFechaFin" name="model.periodo.fechaFin" disabled="true"/>
		<label>Estado:</label>
		<s:textfield id="txtEstado" name="model.estado.nombre" disabled="true"/>
		
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
		/>
		<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
	</body>
	</html>
</jsp:root>