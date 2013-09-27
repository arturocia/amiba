<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Indice de contactos</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-contacto.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
<s:url id="urlAgregar" value="/catalogo-contacto/new" includeContext="true" />
<h1>Gestionar Contacto</h1>
<s:actionerror theme="jquery" />
<s:actionmessage id="message" theme="jquery"/>
	<table id="tblContacto">
		<thead>
			<tr>
				<th>Tipo</th>
				<th>Contacto</th>
				<th>Descripción</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${tipoContacto.nombre}z</td>
					<td>${Contacto}</td>
					<td>${descripcion}</td>
					<td><a
						href="${pageContext.request.contextPath}/catalogo-contacto/${idContacto}!edit?idUsuarioSel=${idUsuario}"><img height="20" width="20" src="images/buttons/editar.png"/></a>
						<a
						href="${pageContext.request.contextPath}/catalogo-contacto/${idContacto}!deleteConfirm?idUsuarioSel=${idUsuario}"><img height="20" width="20" src="images/buttons/eliminar.png"/></a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<p>
	<sj:a id="btnAgregar" button="true"  href="#" onclick="location.href='%{urlAgregar}'">Agregar Contacto</sj:a>
	</p>
</body>
	</html>
</jsp:root>
