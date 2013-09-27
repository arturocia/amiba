<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Modificar Dirección</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
	</head>
	<body>
	<h1>Editar Dirección</h1>
	<s:url id="urlCancelar" value="/catalogo-area" includeContext="true" />
	<s:actionerror id="saeArea" theme="jquery" />
	<s:fielderror id="sfeArea" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-area/%{idArea}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<label>Nombre:</label>
		<s:textfield id="nombre" name="model.nombre" disabled="true" maxlength="20" />
		<br/>
		<label>Siglas:</label>
		<s:textfield id="siglas" name="model.siglas" disabled="true" maxlength="70" />
		<br/>
		<label>Descripción:  </label>
		<br/>
		<s:textarea  rows="3" cols="70"  id="descripcion" name="model.descripcion" maxlength="200"  />
		<br/>
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
		/>
		<sj:a id="btnCancelar" button="true" href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
	</body>
	</html>
</jsp:root>