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
	<s:url id="urlCancelar" value="/catalogo-contacto-temporal" includeContext="true" />
	<s:actionerror id="saeContacto" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-contacto-temporal/%{model.idContacto}"
		method="post" theme="simple" acceptcharset="UTF-8" id="deletecontacto">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
        <label>Tipo Contacto:</label>
		<s:textfield id="txtTipoContacto" name="model.tipoContacto.nombre" disabled="true" /> 
		<br/>
		<label>Uso:</label>
		<s:if test="model.principal">Principal</s:if><s:else>Normal</s:else>
		<br/>
		<label>Contacto:</label>
		<s:textfield id="txtContacto" name="contacto" disabled="true" />
		<br />
        <label>Descripcion:</label>
		<s:textfield id="txtDescripcion" name="descripcion" disabled="true" />
		<br />
<!-- 
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
			 />
	 -->
	 		
<sj:a id="btnAceptar" button="true" href="#" onclick="deletecontacto.submit()">Aceptar</sj:a>
			
		<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>