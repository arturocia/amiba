<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Nueva Evidencia</title>
<jsp:text>
	<![CDATA[ 
			
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Agregar Evidencia</h1>
	<!--<s:url id="urlCancelar" value="/subir-evidencia" includeContext="true" />-->
	<s:url id="urlCancelar" value="/revisar-reportes-avances-proyecto/%{idAccionSel}" includeContext="true" />
	<s:actionerror id="saeArea" theme="jquery" />
	<s:fielderror id="sfeArea" theme="jquery" />

	<s:form action="%{#request.contextPath}/subir-evidencia" method="post"
		theme="simple" acceptcharset="UTF-8" enctype="multipart/form-data" onSubmit="ValidateDataFile()">
		<table>
			<tr>
				<td><label>Nombre:</label>
				</td>
				<td><s:textfield id="Nombre" name="model.nombre" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td><label>Fecha:</label>
				</td>
				<td><sj:datepicker name="model.fecha" displayFormat="yy-mm-dd"
						id="fechaRegistro" cssClass="campoDuracion" />
				</td>
			</tr>
			<tr>
				<td><label>Descripción:</label></td>
				<td><s:textarea rows="3" cols="70" id="Descripcion"
						name="model.descripcion" maxlength="200" /></td>
			</tr>
			<tr>
				<td><label>Clave Documental:</label></td>
				<td><s:textfield id="clave" name="model.claveDocumental" maxlength="50" /></td>
			</tr>
			<tr>
				<td><label>Archivo:</label></td>
				<td><!--<s:file name="file" label="Archivo:"/>--><s:file name="fileUpload" label="Select a File to upload" size="40"  /></td>
			</tr>
			<tr>
				<td><label>Estudio: </label></td>
				<td><s:checkbox id="estudio" name="model.estudio"/></td>
			</tr>
			<tr>
				<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
						button="true" /> <sj:a id="btnCancelar"
						button="true" href="#"
						onclick="location.href='%{urlCancelar}'">Cancelar</sj:a></td>
			</tr>
		</table>
	</s:form>
	<center></center>
</body>
	</html>
</jsp:root>