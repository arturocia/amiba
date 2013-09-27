<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Presupuesto ${nombreFuente}</title>
</head>
<body>
	<h1>Eliminar Presupuesto</h1>
	<s:url id="urlCancelar"
		value="/operacion-planeacion/%{idProyecto}"
		includeContext="true" />
	<s:actionerror id="saeIndicadorFinanciero" theme="jquery" />
	<s:fielderror id="sfeIndicadorFinanciero" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-indicador-financiero/%{idSel}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<br />
		<table>
			<tr>
				<td><label>Nombre Fuente: </label>
				</td>
				<td><s:textfield id="nombreFuente" name="model.nombreFuente"
						disabled="true" />
				</td>
			</tr>
			<td><label>Fecha de Solicitud: </label>
			</td>
			<td><s:textfield name="model.fechaSolicitado"
					displayFormat="yy-mm-dd" id="fechaSolicitado"
					cssClass="campoDuracion" disabled="true" />
			</td>
			<tr>
				<td><label>Monto Solicitado:</label>
				</td>
				<td><s:textfield id="montoSolicitado"
						name="model.montoSolicitado" disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Fecha de Aprobación: </label>
				</td>
				<td><s:textfield name="model.fechaAprobado"
						displayFormat="yy-mm-dd" id="fechaAprobado"
						cssClass="campoDuracion" disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Monto Aprobado: </label></td>
				<td><s:textfield id="montoAprobado" name="model.montoAprobado"
						disabled="true" /></td>
			</tr>
		</table>
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
		/>
		<sj:a id="btnCancelar" button="true" 
			href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>