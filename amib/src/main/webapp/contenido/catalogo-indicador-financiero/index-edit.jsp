<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Aprobar presupuesto</title>

</head>
<body>
	<h1>Aprobar Presupuesto</h1>
	<s:url id="urlCancelar"
		value="/operacion-revisar-avances/%{model.idProyecto}"
		includeContext="true" />
	<s:actionerror id="saeIndicadorFinanciero" theme="jquery" />
	<s:fielderror id="sfeIndicadorFinanciero" theme="jquery" />
	<s:actionmessage id="smeIndicadorFinanciero" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-aprobar-presupuesto/%{idIndicadorFinanciero}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<s:hidden name="model.idIndicadorFinanciero"
			value="%{idIndicadorFinanciero}" />

		<div class="title">Datos Generales</div>
		<table>
			<tr>
				<td><label>Nombre:</label>
				</td>
				<td><s:label id="nombreFuente" name="model.nombreFuente"
						maxlength="20" cssStyle="width:100%;" /></td>
			</tr>
			<tr>
				<td><label>Fecha de Solicitud:</label>
				</td>
				<td><s:label id="fechaSolicitado" name="model.fechaSolicitado"
						maxlength="20" cssStyle="width:100%;" /></td>
			</tr>
			<tr>
				<td><label>Monto ($):</label>
				</td>
				<td><s:label id="montoSolicitado" name="model.montoSolicitado"
						maxlength="10" cssStyle="width:100%;" /></td>
			</tr>
			<tr>
				<td><label>Fecha de Aprobación:</label>
				</td>
				<td><sj:datepicker name="model.fechaAprobado"
						displayFormat="yy-mm-dd" id="fechaAprobado"
						cssClass="campoDuracion" readonly="true"/></td>
			</tr>
			<tr>
				<td><label>Monto Aprobado ($):</label>
				</td>
				<td><s:textfield id="montoAprobado" name="model.montoAprobado"
						maxlength="10" cssStyle="width:100%;" /></td>
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