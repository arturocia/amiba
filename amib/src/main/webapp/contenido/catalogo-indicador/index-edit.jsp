<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Registrar Indicador Fisico</title>

<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-indicador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>

</head>
<body>
	<h1>Modificar Indicador Físico</h1>
	<s:actionerror theme="jquery" />
	<s:fielderror theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-indicador/%{idIndicador}"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmIndi">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<div class="title">Datos Generales</div>
		<table>
			<tr>
				<td><label>Descripción del indicador</label></td>
				<td><s:textarea cols="30" id="txtDescripcion"
						name="model.descripcion" required="true" /></td>
			</tr>
			<tr>
			    <td><label>Tipo de indicador: </label></td>
				<td><s:select id="comboTipoUnidad" list="listTipoUnidad"
						listValue="nombre" listKey="idTipoUnidad" name="tipoUnidadSel"
						headerValue="- - Seleccione un tipo de unidad - -" headerKey="-1"
						onchange="$.publish('reloadsecondlist');" />
				</td>
			</tr>
			<tr>
				<td><label>Meta:</label></td>
				<td><s:textfield id="txtMeta" name="model.meta" required="true" onkeypress="return IsNumber(event);"/>
				</td>
			</tr>
			<tr>
				<td><label>Unidad: </label></td>
				<td><s:url id="urlUnidad"
						action="catalogo-indicador!traerUnidades" includeContext="true" />
					<sj:select href="%{urlUnidad}" id="comboUnidad" formIds="frmIndi"
						reloadTopics="reloadsecondlist" name="model.idUnidad"
						list="listUnidad" listValue="nombre" listKey="idUnidad"
						emptyOption="true" headerKey="-1"
						headerValue="Seleccione una unidad" />
				</td>
			</tr>
			<tr>
				<td><label>Peso:</label></td>
				<td><s:textfield id="txtPeso" name="model.peso" required="true" onkeypress="return IsNumber(event);"/>
				</td>
			</tr>
			<tr>
				<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
						button="true" /> <!--<sj:a id="btnRegresar"
						button="true"  href="#"
						onclick="history.go(-1)">Cancelar	</sj:a>-->
						
						
				<s:url id="urlCancelar"
						action="catalogo-indicador!cancelar?accionIdentificador=%{idAccion}&amp;idPlan=%{idPlan}"></s:url>
					<sj:a id="btnCancelar" button="true" href="#"
						onclick="location.href='%{urlCancelar}'">Cancelar</sj:a></td>
			</tr>
		</table>
		<s:hidden id="hdnAccion" name="model.idAccion" value="%{model.idAccion}"></s:hidden>
		<s:hidden id="hdnPlan" name="idPlan" value="%{idPlan}"></s:hidden>
	</s:form>
</body>
	</html>
</jsp:root>