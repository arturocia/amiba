<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Indice de acciones</title>
	<jsp:text>
		<![CDATA[ 			
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/catalogo-accion.js" type="text/javascript"></script>

		 ]]>
	</jsp:text>
	</head>
	<body>
	<h1>Gestiona meta intermedia</h1>
	<s:actionmessage id="algo" theme="jquery"/>
	<table id="tblAccion">

		<thead>
			<tr>
				<th>Nombre</th>
				<th>Objetivo</th>
				<th>Descripción</th>
				<th>Duracion</th>
				<th>Fecha Inicio</th>
				<th>Fecha Fin</th>				
			</tr>
		</thead>
		<tbody>			
			<s:iterator value="list">
				<tr>
					<td>${nombre}</td>
					<td>${objetivo}</td>
					<td>${descripcion}</td>
					<td>${periodo.duracion}</td>
					<td>${periodo.fechaInicio}</td>
					<td>${periodo.fechaFin}</td>					
					<td width="60"><a
						href="${pageContext.request.contextPath}/catalogo-accion/${idAccion}/edit">Editar</a>
					<a
						href="${pageContext.request.contextPath}/catalogo-accion/${idAccion}/deleteConfirm">Eliminar</a>
					<a
						href="${pageContext.request.contextPath}/catalogo-indicador!index?idAccionSel=${idAccion}">Indicadores</a>	
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/catalogo-accion/new"
		id="lnkNuevaAccion">Nueva Acción</a>
	
	</body>
	</html>
</jsp:root>
