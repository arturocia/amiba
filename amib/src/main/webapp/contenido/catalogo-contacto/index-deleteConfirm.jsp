<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Contacto ${nombre}</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Eliminar Contacto</h1>
	<!--<s:url id="urlCancelar" value="/catalogo-contacto"
		includeContext="true" />-->
	<s:url id="urlCancelar" value="/catalogo-usuario/%{idUsuarioSel}/edit"
		includeContext="true" />
	<s:actionerror id="saeContacto" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-contacto/%{model.idContacto}"
		method="post" theme="simple" acceptcharset="UTF-8" id="deletecontacto">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<table>
			<tr>
				<td><label>Tipo Contacto:</label></td>
				<td><s:textfield id="txtTipoContacto" name="model.tipoContacto.nombre" disabled="true" /></td>
				
			</tr>
			<tr>
				<td><label>Uso:</label></td>
				<td><s:if test="model.principal">Principal</s:if>
					<s:else>Normal</s:else></td>
			</tr>
			<tr>
				<td><label>Contacto:</label></td>
				<td><s:textfield id="txtContacto" name="contacto" disabled="true" /></td>
			</tr>
			<tr>
				<td><label>Descripcion:</label></td>
				<td><s:textfield id="txtDescripcion" name="descripcion" disabled="true" /></td>
			</tr>
		</table>
		<sj:a id="btnAceptar" button="true" href="#"
			onclick="deletecontacto.submit()" value="caca">Aceptar</sj:a>

		<sj:a id="btnCancelar" button="true" href="#"
			onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>