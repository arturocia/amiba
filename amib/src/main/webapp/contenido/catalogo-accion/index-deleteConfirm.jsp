<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar meta intermedia</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
			
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-accion-editar.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-accion-nuevo.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar" value="/operacion-planeacion/%{model.idProyecto}"
		includeContext="true" />
	<s:actionerror id="saeAccion" theme="jquery" />
	<s:fielderror id="sfeAccion" theme="jquery" />
	<h1>Eliminar meta intermedia</h1>
	<s:form action="%{#request.contextPath}/catalogo-accion/%{idAccion}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="delete" />

		<div class="title">Eliminar meta intermedia</div>
		<div class="section">Datos Generales</div>
		<table>
			<tr>
				<td><label>Nombre de la acción:</label></td>
				<td><s:textfield name="model.nombre" disabled="true" /></td>
			</tr>
			<tr>
				<td><label>Objetivo de la acción:</label></td>
				<td><s:textarea name="model.objetivo" cols="30" rows="10"
						disabled="true" /></td>
			</tr>
			<tr>
				<td><label>Descripcción:</label></td>
				<td><s:textarea name="model.descripcion" cols="30" rows="10"
						disabled="true" /></td>
			</tr>
		</table>
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
			/>
		<sj:a id="btnCancelar" button="true" 
			href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
		<!--<s:a id="btnCancelar" button="true" buttonIcon="ui-icon-bullet"
			onclick="window.location='%{#request.contextPath}/catalogo-accion'">Cancelar</s:a>-->

	</s:form>
</body>
	</html>
</jsp:root>