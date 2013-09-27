<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Nuevo Contacto</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
</head>
<body>
<h1>Agregar Contacto</h1>
	<s:url id="urlCancelar" value="/catalogo-usuario/new" includeContext="true" />
	<s:actionerror id="saeContacto" theme="jquery" />
	<s:fielderror id="sfeContacto" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-usuario/new"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:select list="tipoContactos" listValue="nombre"
			listKey="idTipoContacto" name="model.idTipoContacto" />
		<s:textfield id="txtContacto" name="model.contacto" maxlength="20" />
		<br/>
		<label>Prioridad:</label>
		<s:select name="model.principal"
			list="#{'0':'normal', '1':'principal'}" value="0"  />
		<br/>
		<label>Descripcion:</label>
		<s:textfield id="txtDescripcion" name="model.descripcion" maxlength="70" />
		<br />
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
		/>
		<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	
	</s:form>
</body>
	</html>
</jsp:root>