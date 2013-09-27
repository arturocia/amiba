<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Registrar Ejercicio de Presupuesto</title>
</head>
<body>
	<s:actionerror id="saeUnidad" theme="jquery" />
	<s:fielderror id="sfeUnidad" theme="jquery" />
	<h1>Registrar Ejercicio de Presupuesto</h1>
	<s:form action="%{#request.contextPath}/catalogo-avance-financiero"
		method="post" theme="simple" acceptcharset="UTF-8">
		<table>
			<tr>
				<td>Monto aprobado:</td>
				<td><s:label id="txtLogin"
						value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@INDICADORFINANCIEROSEL].montoAprobado}"
						disabled="true" /></td>
			</tr>
			<tr>
				<td>Fecha :</td>
				<td><sj:datepicker name="model.fechaAvance"
						displayFormat="yy-mm-dd" id="dtpFechaAvance"
						cssClass="campoFechas"/></td>
			</tr>
			<tr>
				<td><label>Monto:</label></td>
				<td><s:textfield id="txtMonto" name="model.monto" /></td>
				<br />
			</tr>
		</table>
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
			/>
			
		<s:url id="urlCancelar" value="/operacion-revisar-avances/%{idProyectoSel}"
			includeContext="true" />
		<sj:a button="true"  href="#"
			onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>

	</s:form>
</body>
	</html>
</jsp:root>