<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Proyectos Pre-registrados</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-proyecto.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
<h1>Gestionar Proyecto Preregistrado</h1>
<table id="tblProyecto">
		<thead>
			<tr>
				<th colspan="7">Proyectos Pre-Registrados</th>
			</tr>
			<tr>
				<th>Siglas</th>
				<th>Nombre</th>
				<th>Responsable</th>
				<th>Correo</th>
				<th>Telefono</th>
				<th>Fecha</th>
				<th>Accion</th>
				
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${siglas}</td>
					<td>${nombre}</td>
					<td>${responsable.nombre} ${responsable.apPat} ${responsable.apMat}</td>
					<td>${responsable.contactos[0].contacto}</td>
					<td>${responsable.contactos[1].contacto}</td>
					<td>${fechaRegistro}</td>	
					<td>
						<!-- <a href="${pageContext.request.contextPath}/catalogo-proyecto-preregistrado/${idProyecto}/edit">Evaluar</a> -->
						<a href="${pageContext.request.contextPath}/gestionar-proyecto-preregistrado/${idProyecto}/edit">Evaluar</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
</html>
</jsp:root>
