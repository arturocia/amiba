<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Agregar presupuesto</title>

</head>
<body>
	<h1>Agregar fuente de financiamiento</h1>
	<s:url id="urlCancelar"
		value="/operacion-planeacion/%{idProyecto}"
		includeContext="true" />
	<s:actionerror id="saeIndicadorFinanciero" theme="jquery" />
	<s:fielderror id="sfeIndicadorFinanciero" theme="jquery" />
	<s:actionmessage id="smeIndicadorFinanciero" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-indicador-financiero"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden name="model.idProyecto" value="%{idProyecto}" />
		<div class="title">Datos Generales</div>
		<table>
			<tr>
				<td><label>Nombre:</label></td>
				<td><s:textfield id="nombreFuente" name="model.nombreFuente"
						maxlength="20" cssStyle="width:100%;" /></td>
			</tr>
			<tr>
				<td><label>Fecha de Solicitud:</label></td>
				<td><sj:datepicker name="model.fechaSolicitado"
						displayFormat="yy-mm-dd" id="fechaSolicitado"
						cssClass="campoDuracion" readonly="true"/></td>
			</tr>
			<tr>
				<td><label>Monto ($):</label></td>
				<td><s:textfield id="montoSolicitado"
						name="model.montoSolicitado" maxlength="10" cssStyle="width:100%;" />
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="right"><sj:submit id="btnAceptar"
						value="Aceptar" button="true" /> <sj:a
						id="btnCancelar" button="true" 
						href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
				</td>
			</tr>
		</table>
	</s:form>
</body>
	</html>
</jsp:root>