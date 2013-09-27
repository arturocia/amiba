<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Indice de nivel</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Director.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/catalogo-nivel.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
<s:url id="urlAgregar" value="/catalogo-nivel/new" includeContext="true" />
<h1>Gestionar Nivel</h1>
	<s:actionmessage id="algo" theme="jquery" />
	<table id="tblNivel">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Descripción</th>
				<th>Programa</th>
				<th>Posición</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${nombre}</td>
					<td>${descripcion}</td>
					<td>${Programa.nombre}</td>
					<td>${posicion}</td>
					<td><a
						href="${pageContext.request.contextPath}/catalogo-nivel/${idNivel}/edit"><img height="20" width="20" src="images/buttons/editar.png"/></a>
						<a
						href="${pageContext.request.contextPath}/catalogo-nivel/${idNivel}/deleteConfirm"><img height="20" width="20" src="images/buttons/eliminar.png"/></a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

	<!-- <a href="${pageContext.request.contextPath}/catalogo-nivel/new"
		id="lnkNuevoNivel"><input type="submit" value="Agregar Nivel"></input> </a>!-->
		<sj:a id="btnAgregar" button="true"  href="#" onclick="location.href='%{urlAgregar}'">Agregar Nivel</sj:a>
</body>
	</html>
</jsp:root>
