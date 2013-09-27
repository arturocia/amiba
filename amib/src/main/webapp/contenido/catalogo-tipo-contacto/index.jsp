<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Indice de Tipos de contacto</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-tipo-contacto.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Gestionar Tipo de Contacto</h1>
<s:actionerror theme="jquery" />
<s:actionmessage id="message" theme="jquery"/>

	<table id="tblTipoContacto">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Descripción</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${nombre}</td>
					<td>${descripcion}</td>
					<td><a
						href="${pageContext.request.contextPath}/catalogo-tipo-contacto/${idTipoContacto}/edit"><img
							height="20" width="20" src="images/buttons/editar.png" /> </a> <a
						href="${pageContext.request.contextPath}/catalogo-tipo-contacto/${idTipoContacto}/deleteConfirm"><img
							height="20" width="20" src="images/buttons/eliminar.png" /> </a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

	<s:url id="urlAgregarTipoContacto" value="/catalogo-tipo-contacto/new"
		includeContext="true" />
	<sj:a id="btnRegresar" button="true" href="#"
		onclick="location.href='%{urlAgregarTipoContacto}'">Agregar</sj:a>

</body>
	</html>
</jsp:root>
