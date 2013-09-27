<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar grupo ${nombre}</title>
</head>
<body>
	<h1>Eliminar tipo de contacto</h1>
	<s:url id="urlCancelar" value="/catalogo-tipo-contacto"
		includeContext="true" />
	<s:actionerror id="saeTipoContacto" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-tipo-contacto/%{idTipoContacto}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<table>
			<tr>
				<td><label>Nombre:</label>
				</td>
				<td><s:textfield id="txtNombre" name="nombre" disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Descripción:</label>
				</td>
				<td><s:textfield id="txtDescripcion" name="descripcion"
						disabled="true" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
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