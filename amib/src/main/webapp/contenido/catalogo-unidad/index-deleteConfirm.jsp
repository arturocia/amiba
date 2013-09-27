<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Unidad ${model.nombre}</title>
</head>
<body>
<h1>Eliminar  Unidad</h1>
	<s:actionerror id="saeUnidad" theme="jquery" />
	<s:url id="urlCancelar" value="/catalogo-unidad" includeContext="true" />
	<s:form
		action="%{#request.contextPath}/catalogo-unidad/%{model.idUnidad}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<table>
			<tr>
				<td><label>Nombre:</label>
				</td>
				<td><s:textfield id="txtNombre" name="model.nombre"
						disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Descripción:</label></td>
		<td><s:textarea rows="3" cols="50" maxlength="200" id="txtDescripcion" name="model.descripcion"
			disabled="true"/></td>
			</tr>
			<tr>
				<td><label>Tipo:</label>
				</td>
				<td><s:select list="listTiposUnidad" disabled="true"
						listValue="nombre" listKey="idTipoUnidad"
						name="model.idTipoUnidad" headerValue="- - Seleccione uno - -"
						headerKey="-1" required="true" />
				</td>
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