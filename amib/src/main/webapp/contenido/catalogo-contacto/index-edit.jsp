<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Editar Contacto ${nombre}</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
	</head>
	<body>
	<h1>Editar Contacto</h1>
	<!--s:url id="urlCancelar" value="/catalogo-contacto" includeContext="true" /-->
	<!--<s:url id="urlCancelar" value="/catalogo-usuario/%{idSel}/edit" includeContext="true" />-->
	<s:url id="urlCancelar" value="/catalogo-usuario/%{idUsuarioSel}/edit" includeContext="true" />
	<s:actionerror id="saeContacto" theme="jquery" />
	<s:fielderror id="sfeContacto" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-contacto/%{idSel}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<table>
			<tr>
				<td><label>Tipo Contacto:</label></td>
				<td><s:textfield id="txtTipoContacto" name="model.tipoContacto.nombre" disabled="true" /> </td>
				<td>
				<s:if test="%{model.principal!=true}" >
				<s:select name="model.principal" id="comboPrincipal" onchange="alert('Cuidado! Al modificar el contacto %{model.contacto} a principal, hará que el contacto existente como principal cambie a normal.')" list="#{'0':'normal', '1':'principal'}" value="0" />
				</s:if>
				</td>
			</tr>
			<tr>
				<td><label>Contacto:</label></td>
				<td><s:textfield id="txtContacto" name="model.contacto" maxlength="100" /></td>
			</tr>
			<tr>
				<td><label>Descripcion:</label></td>
				<td><s:textfield id="txtdescripcion" name="model.descripcion" maxlength="70" /></td>
			</tr>
		</table>
		<sj:submit id="btnAceptar" value="Aceptar" button="true"/>
		<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
	</body>
	</html>
</jsp:root>