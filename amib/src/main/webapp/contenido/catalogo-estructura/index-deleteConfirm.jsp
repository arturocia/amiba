<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Indice de estructura</title>

</head>
<body>
	<s:url id="urlCancelar" value="/catalogo-estructura"
		includeContext="true" />
	<center>
		<h1>Eliminar Estructura</h1>


		<s:form method="post"
			action="%{#request.contextPath}/catalogo-estructura/%{model.idEstructura}"
			theme="simple" id="frmEstructura">

			<s:hidden id="hdnMethod" name="_method" value="delete" />

			<table>
				<tr>
				<td><label>Nombre:</label></td>
				<td><s:textfield id="txtNombre" name="nombre" disabled="true" /></td>
				</tr>
				<tr>
				<td><label>Siglas:</label></td>
				<td><s:textfield id="txtNombre" name="siglas" disabled="true" /></td>
				</tr>
				<tr>
				<td><label>Descripción:</label></td>
				<td><s:textarea rows="5" cols="50" id="txtDescripcion"
					name="descripcion" disabled="true" /></td>
				</tr>
				<tr>
				<td colspan="2">
				<sj:submit id="btnAceptar" value="Aceptar" button="true"
				/>
				<sj:a id="btnCancelar" button="true" 
					href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
					</td>
				</tr>
			</table>
		</s:form>
	</center>
</body>
	</html>
</jsp:root>
