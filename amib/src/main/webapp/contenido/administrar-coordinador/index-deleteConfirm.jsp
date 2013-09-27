<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Coordinador</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/eliminar-desactivar_usuario.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
<s:url id="urlCancelar" value="/administrar-coordinador" includeContext="true" />
<h1>Eliminar Coordinador</h1>
	<input type="text" style="display: none;" id="hdnRutaContexto"
		value="${pageContext.request.contextPath}" />
	<s:fielderror theme="jquery" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:form action="%{#request.contextPath}/administrar-coordinador/%{idUsuario}"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmUsuario">
		<s:hidden id="hdnMethod" name="_method" value="delete" />
		<label>Nombre:</label>
		<s:textfield id="txtNombre" name="model.nombre" disabled="true" />
		<label>Login:</label>
		<s:textfield id="txtLogin" name="model.login" disabled="true" />
		<br />
		<label>Apellido Paterno:</label>
		<s:textfield id="txtApPat" name="model.apPat" disabled="true" />
		<br />
		<label>Apellido Materno:</label>
		<s:textfield id="txtApMat" name="model.apMat" disabled="true" />
		<br />
		<label>RFC:</label>
		<s:textfield id="txtRFC" name="model.rfc" disabled="true" />
		<br />
		<label>Activado: </label>
		<s:checkbox id="chkbxActivado" name="model.activado" fieldValue="true"
			disabled="true" />
		<br />

		<br />
		<s:hidden id="user" value="%{idUsuario}" />
		<sj:dialog id="mydialog" title="Dialog" modal="true" autoOpen="false"
			buttons="{ 
    		'OK':function() { botonOk(); },
    		'Cancelar':function() { botonCancelar(); } 
    		}">
    	El usuario seleccionado tiene proyectos asociados, no es posible eliminar. 
    	¿Desea desactivar el usuario?    	
    	</sj:dialog>
		<br />
		<sj:a id="btnAceptar" button="true"  href="#" onclick="validaSinProyectos()">Aceptar</sj:a>
		<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>