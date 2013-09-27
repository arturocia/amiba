<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<!--Es llamado por struts (GestionarAreas 14)-->
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Direcciones</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-area.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>

		 ]]>
</jsp:text>
</head>
<body>
	<h1>Gestionar Dirección</h1>
	<s:actionmessage id="algo" theme="jquery" />
	<table id="tblArea">

		<thead>
			<tr>
				<th>Nombre</th>
				<th>Siglas</th>
				<th>Descripción</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<!--Se crea loop para llenar la tabla obtiene los datos de Area(GestionarDireccioness 14)-->
			<s:iterator value="list">
				<tr>
					<td>${nombre}</td>
					<td>${siglas}</td>
					<td>${descripcion}</td>
					<td width="60"><a
						href="${pageContext.request.contextPath}/catalogo-area/${idArea}/edit"><img
							height="20" width="20" src="images/buttons/editar.png" /> </a> <a
						href="${pageContext.request.contextPath}/catalogo-area/${idArea}/deleteConfirm"><img
							height="20" width="20" src="images/buttons/eliminar.png" /> </a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

	<s:url id="urlAgregarNuevaArea" value="/catalogo-area/new"
		includeContext="true" />
	<sj:a id="btnRegresar" button="true" href="#"
		onclick="location.href='%{urlAgregarNuevaArea}'">Nueva Dirección</sj:a>
</body>
	</html>
</jsp:root>
