<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Enviar proyecto para aprobación</title>
</head>
<body>
	<s:url id="urlCancelar" value="/operacion-editar-datos-proyecto/%{idSel}"
		includeContext="true" />
	<s:actionerror id="saeProyecto" theme="jquery" />
	<s:fielderror id="sfeProyecto" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/enviar-proyecto-aprobacion/%{idSel}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<table>
			<tr>
				<td><label>Siglas:</label>
				</td>
				<td><s:property value="model.siglas" />
				</td>
			</tr>
			<tr>
				<td><label>Nombre:</label>
				</td>
				<td><s:property value="model.nombre" />
				</td>
			</tr>
			<tr>
				<td><label>Resumen:</label></td>
				<td><s:property value="model.resumen" /></td>
			</tr>
			<tr>
				<td><label>Responsable:</label></td>
				<td><s:property
						value="model.responsable.nombre + model.responsable.apPat + model.responsable.apMat" />
				</td>
			</tr>
			<tr>
				<td><label>Objetivo General:</label>
				</td>
				<td><s:property value="model.objetivoGeneral" />
				</td>
			</tr>
			<tr>
				<td><label>Duración:</label>
				</td>
				<td><s:property value="model.periodo.duracion" />
				</td>
			</tr>
			<tr>
				<td><label>Inicio:</label>
				</td>
				<td><s:property value="model.periodo.fechaInicio" />
				</td>
			</tr>
			<tr>
				<td><label>Fin:</label>
				</td>
				<td><s:property value="model.periodo.fechaFin" />
				</td>
			</tr>
			<tr>
				<td><label>Estado:</label></td>
				<td><s:property value="model.estado.nombre" /></td>
			</tr>
			<tr>
				<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
						button="true" /> <sj:a id="btnCancelar"
						button="true"   href="#"
						onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
				</td>
			</tr>
		</table>
	</s:form>
</body>
	</html>
</jsp:root>