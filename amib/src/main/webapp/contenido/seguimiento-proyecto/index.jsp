<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Gestionar Usuarios</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/seguimiento-proyecto.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlAgregar" value="/catalogo-usuario/new"
		includeContext="true" />
	<h1>Seguimiento de proyectos</h1>
	<s:actionmessage id="algo" theme="jquery" />
	<table id="tblProyecto">
		<thead>
			<tr>
				<th>Siglas</th>
				<th>Nombre del proyecto</th>
				<th>Responsable</th>
				<th>Resumen</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="listaProyectos">
				<tr>
					<td>${siglas}</td>
					<td>${nombre}</td>
					<td>${responsable.nombre}</td>
					<td>${resumen}</td>
					
					<td><a
						href="${pageContext.request.contextPath}/seguimiento-proyecto!operacionBitacora?idSel=${idProyecto}"><img
							height="20" width="20" src="images/buttons/ver.png" /></a>
							
					</td>
				</tr>
			</s:iterator>
		</tbody>
		
	</table>

	<p>
		
	</p>
</body>
	</html>
</jsp:root>
