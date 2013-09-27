<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<!--Es llamado por struts (GestionarAreas 14)-->
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Subir Evidencia</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/subir-evidencia.js" type="text/javascript"></script>
			 ]]>
	</jsp:text>
	</head>
	<body>
	<h1>Subir Evidencia</h1>
	<s:actionmessage id="algo" theme="jquery"/>
	<table id="tblEvidencia">

		<thead>
			<tr>
				<th>Nombre</th>
				<th>Fecha</th>
				<th>Descripción</th>
				<th>Acciones</th>
				<th>Archivo</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<!--Se crea loop para llenar la tabla obtiene los datos de evidencia-->
			<s:iterator value="list">
				<tr>
					<td>${nombre}</td>
					<td>${fecha}</td>
					<td>${descripcion}</td>
					<td width="60"><a
						href="${pageContext.request.contextPath}/subir-evidencia/${idEvidencia}/edit"><img height="20" width="20" src="images/buttons/editar.png"/></a>
					<a
						href="${pageContext.request.contextPath}/subir-evidencia/${idEvidencia}/deleteConfirm"><img height="20" width="20" src="images/buttons/eliminar.png"/></a>
					</td>
					<td>${nombreArchivo}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/subir-evidencia/new"
		id="lnkNuevaEvidencia"><input type="submit" value="Nueva Evidencia"></input></a> 
	</body>
	</html>
</jsp:root>
