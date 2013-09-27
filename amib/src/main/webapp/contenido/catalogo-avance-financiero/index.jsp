<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Gestión de Presupuestos</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-unidad.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
	</head>
	<body>
	<table id="tblAvanceFinanciero">
		<thead>
			<tr>
				<th>Proyecto</th>
				<th>Monto</th>
				<th>Avance</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${id_proyecto}</td>
					<td>${monto}</td>
					<td>${avance}</td>
					<td>
					<a
						href="${pageContext.request.contextPath}/catalogo-avance-financiero/${idUnidad}/deleteConfirm">Eliminar</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/catalogo-avance-financiero/new"
		id="lnkNuevoAvanceFinanciero">Nuevo Avance Financiero</a>
	</body>
	</html>
</jsp:root>
