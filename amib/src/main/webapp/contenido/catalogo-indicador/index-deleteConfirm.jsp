<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Indicador Fisico</title>
</head>
<body>
	<h1>Eliminar Indicador Fisico</h1>
	<s:form
		action="%{#request.contextPath}/catalogo-indicador/%{idIndicador}"
		method="post" theme="simple" acceptcharset="UTF-8" id="perro">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<div class="title">Datos Generales</div>
		<table>
			<tr>
				<td><label>Descripción del indicador</label></td>
				<td><s:textfield id="txtDescripcion" name="model.descripcion"
						disabled="true" /></td>
			</tr>

			<tr>
				<td><label>Meta:</label></td>
				<td><s:textfield id="txtMeta" name="model.meta" disabled="true" /></td>
			</tr>


			<tr>
				<td><label>Tipo de unidad: </label></td> <td><s:property
						value="%{model.unidad.tipoUnidad.nombre}" /></td>
			</tr>

			<tr>
				<td><label>Unidad: </label></td><td> <s:property
						value="%{model.unidad.nombre}" /></td>
			</tr>



			<tr>
				<td><label>Peso:</label></td>
				<td><s:textfield id="txtPeso" name="model.peso" disabled="true" /></td>
			</tr>
			<tr>
				<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
						button="true" /> <s:url id="urlCancelar"
						action="catalogo-indicador!cancelar?accionIdentificador=%{idAccion}&amp;idPlan=%{idPlan}"></s:url>


					<sj:a id="btnCancelar" button="true" href="#"
						onclick="location.href='%{urlCancelar}'">Cancelar</sj:a></td>
			</tr>
		</table>
		<s:hidden id="hdnPlan" name="idPlan" value="%{idPlan}"></s:hidden>
	</s:form>
</body>
	</html>
</jsp:root>